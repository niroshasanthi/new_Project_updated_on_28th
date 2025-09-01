package com.example.service;

import com.example.model.ChildMember;
import com.example.model.DailyUsageReport;
import com.example.model.Parent;
import com.example.model.Restriction;
import com.example.repository.ChildMemberRepository;
import com.example.repository.DailyUsageReportRepository;
import com.example.repository.ParentRepository;
import com.example.repository.RestrictionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class MemberService {

    @Autowired
    private ChildMemberRepository childMemberRepository;

    @Autowired
    private ParentRepository parentRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DailyUsageReportRepository reportRepository;

    @Autowired
    private RestrictionRepository restrictionRepository;

    @Autowired
    private Random random; 
    
    private static final List<String> APPS = List.of(
            "gmail", "classroom", "youtube kids", "candycrush", "zoom", "WhatsApp", "instagram"
        );
    
    public ChildMember addChildMember(String parentEmail, String username, String password) {
        String normalizedEmail = parentEmail.trim().toLowerCase();
        Parent parent = parentRepository.findByEmailIgnoreCase(normalizedEmail)
            .orElseThrow(() -> new RuntimeException("Parent not found"));

        ChildMember child = new ChildMember();
        child.setUsername(username);
        child.setPassword(passwordEncoder.encode(password));  // encode password
        child.setParent(parent);
        ChildMember savedChild = childMemberRepository.save(child);
        
        // Create mock reports for the new child
        createMockReportsForChild(parent, savedChild);

        return savedChild;
    }

    private void createMockReportsForChild(Parent parent, ChildMember child) {
        LocalDate today = LocalDate.now();

        // Fetch restrictions for the child (to respect custom rules/apps)
        List<Restriction> restrictions = restrictionRepository.findByChildMemberId(child.getId());

        if (restrictions.isEmpty()) {
            // No restrictions, create some random reports with random apps
            int numberOfReports = random.nextInt(3) + 1;  // Between 1 and 3 reports

            for (int i = 0; i < numberOfReports; i++) {
                DailyUsageReport report = new DailyUsageReport();
                report.setParentid(parent);
                report.setChildid(child);
                report.setReportDate(today);

                String app = APPS.get(random.nextInt(APPS.size()));

                report.setAppsUsed(app);
                report.setDataUsed(generateRandomDataUsage(500, 3000));  // 500MB to 3000MB
                report.setTimeUsed(generateRandomTimeUsage(30, 180));   // 30min to 3hr (180min)
                reportRepository.save(report);
            }
        } else {
            // Create reports based on restrictions (respecting limits)
            for (Restriction restriction : restrictions) {
                DailyUsageReport report = new DailyUsageReport();
                report.setParentid(parent);
                report.setChildid(child);
                report.setReportDate(today);

                report.setAppsUsed(restriction.getAppName());
                report.setDataUsed(generateRandomDataUsageFromLimit(restriction.getDataLimit()));
                report.setTimeUsed(generateRandomTimeUsageFromLimit(restriction.getTimeLimit()));
                reportRepository.save(report);
            }
        }
    }

    // Generates random usage time in "Xh Ym" format between minMinutes and maxMinutes
    private String generateRandomTimeUsage(int minMinutes, int maxMinutes) {
        int usedMinutes = minMinutes + random.nextInt(maxMinutes - minMinutes + 1);
        int hours = usedMinutes / 60;
        int minutes = usedMinutes % 60;
        return String.format("%dh %dm", hours, minutes);
    }

    // Generate random data usage between minMB and maxMB in "XXXMB" format
    private String generateRandomDataUsage(int minMB, int maxMB) {
        int usedMB = minMB + random.nextInt(maxMB - minMB + 1);
        return usedMB + "MB";
    }

    private int parseTimeLimitToMinutes(String timeLimit) {
        // Same parsing logic as your scheduler...
        if (timeLimit == null) return 0;
        java.util.regex.Pattern patternHour = java.util.regex.Pattern.compile("(\\d+)\\s*hour");
        java.util.regex.Pattern patternMinute = java.util.regex.Pattern.compile("(\\d+)\\s*minute");
        java.util.regex.Matcher matcherHour = patternHour.matcher(timeLimit.toLowerCase());
        java.util.regex.Matcher matcherMinute = patternMinute.matcher(timeLimit.toLowerCase());
        int totalMinutes = 0;
        if (matcherHour.find()) totalMinutes += Integer.parseInt(matcherHour.group(1)) * 60;
        if (matcherMinute.find()) totalMinutes += Integer.parseInt(matcherMinute.group(1));
        return totalMinutes;
    }

    private int parseDataLimitToMB(String dataLimit) {
        if (dataLimit == null) return 0;
        java.util.regex.Pattern patternMB = java.util.regex.Pattern.compile("(\\d+)\\s*mb");
        java.util.regex.Pattern patternGB = java.util.regex.Pattern.compile("(\\d+)\\s*gb");
        java.util.regex.Matcher matcherMB = patternMB.matcher(dataLimit.toLowerCase());
        if (matcherMB.find()) return Integer.parseInt(matcherMB.group(1));
        java.util.regex.Matcher matcherGB = patternGB.matcher(dataLimit.toLowerCase());
        if (matcherGB.find()) return Integer.parseInt(matcherGB.group(1)) * 1024;
        return 0;
    }

    private String generateRandomTimeUsageFromLimit(String timeLimit) {
        int maxMinutes = parseTimeLimitToMinutes(timeLimit);
        if (maxMinutes <= 0) return "0h 0m";
        int minMinutes = Math.min(30, maxMinutes); // At least 30 mins or maxMinutes if less
        int usedMinutes = minMinutes + random.nextInt(maxMinutes - minMinutes + 1);
        int hours = usedMinutes / 60;
        int minutes = usedMinutes % 60;
        return String.format("%dh %dm", hours, minutes);
    }

    private String generateRandomDataUsageFromLimit(String dataLimit) {
        int maxMB = parseDataLimitToMB(dataLimit);
        if (maxMB <= 0) return "0MB";
        int minMB = Math.min(500, maxMB); // At least 500MB or maxMB if less
        int usedMB = minMB + random.nextInt(maxMB - minMB + 1);
        return usedMB + "MB";
    }
    
    public List<ChildMember> getChildrenByParentEmail(String parentEmail) {
        Parent parent = parentRepository.findByEmailIgnoreCase(parentEmail)
                .orElseThrow(() -> new RuntimeException("Parent not found"));
        return childMemberRepository.findByParent(parent);
    }

    public ChildMember loginChild(String username, String password) {
        ChildMember child = childMemberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Child not found"));
        if (!new BCryptPasswordEncoder().matches(password, child.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return child;
    }
}
