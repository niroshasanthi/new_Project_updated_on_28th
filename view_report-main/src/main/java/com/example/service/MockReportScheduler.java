package com.example.service;

import com.example.model.ChildMember;
import com.example.model.DailyUsageReport;
import com.example.model.Parent;
import com.example.model.Restriction;
import com.example.repository.ChildMemberRepository;
import com.example.repository.DailyUsageReportRepository;
import com.example.repository.RestrictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MockReportScheduler {

    @Autowired
    private ChildMemberRepository childMemberRepo;

    @Autowired
    private RestrictionRepository restrictionRepo;

    @Autowired
    private DailyUsageReportRepository reportRepo;

    /**
     * Sync daily reports with children hourly:
     * - For each child and restriction, create report if missing for today
     * - Otherwise update existing report with new random usage data
     */
    @Scheduled(cron = "*/10 * * * * ?")  // Every hour on the hour
    public void syncReportsWithChildren() {
        LocalDate today = LocalDate.now();
        List<ChildMember> children = childMemberRepo.findAll();

        for (ChildMember child : children) {
            Parent parent = child.getParent();
            List<Restriction> restrictions = restrictionRepo.findByChildid(child);

            for (Restriction restriction : restrictions) {
                DailyUsageReport existingReport = reportRepo.findByParentidAndChildidAndReportDateAndAppsUsed(
                    parent, child, today, restriction.getAppName());

                if (existingReport == null) {
                    DailyUsageReport report = new DailyUsageReport();
                    report.setParentid(parent);
                    report.setChildid(child);
                    report.setReportDate(today);
                    report.setAppsUsed(restriction.getAppName());
                    report.setTimeUsed(generateRandomTimeUsage(restriction.getTimeLimit()));
                    report.setDataUsed(generateRandomDataUsage(restriction.getDataLimit()));
                    reportRepo.save(report);
                } else {
                    existingReport.setTimeUsed(generateRandomTimeUsage(restriction.getTimeLimit()));
                    existingReport.setDataUsed(generateRandomDataUsage(restriction.getDataLimit()));
                    reportRepo.save(existingReport);
                }
            }
        }
    }

    private String generateRandomTimeUsage(String timeLimit) {
        int maxMinutes = parseTimeLimitToMinutes(timeLimit);
        if (maxMinutes <= 0) return "0h 0m";

        int usedMinutes = (int) (Math.random() * maxMinutes);
        int hours = usedMinutes / 60;
        int minutes = usedMinutes % 60;

        return String.format("%dh %dm", hours, minutes);
    }

    private String generateRandomDataUsage(String dataLimit) {
        int maxMB = parseDataLimitToMB(dataLimit);
        if (maxMB <= 0) return "0MB";

        int usedMB = (int) (Math.random() * maxMB);

        return usedMB + "MB";
    }

    private int parseTimeLimitToMinutes(String timeLimit) {
        if (timeLimit == null) return 0;

        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(\\d+)\\s*hour");
        java.util.regex.Matcher matcher = pattern.matcher(timeLimit.toLowerCase());

        int totalMinutes = 0;
        if (matcher.find()) {
            totalMinutes += Integer.parseInt(matcher.group(1)) * 60;
        }
        pattern = java.util.regex.Pattern.compile("(\\d+)\\s*minute");
        matcher = pattern.matcher(timeLimit.toLowerCase());
        if (matcher.find()) {
            totalMinutes += Integer.parseInt(matcher.group(1));
        }
        return totalMinutes;
    }

    private int parseDataLimitToMB(String dataLimit) {
        if (dataLimit == null) return 0;

        java.util.regex.Pattern patternMB = java.util.regex.Pattern.compile("(\\d+)\\s*mb");
        java.util.regex.Pattern patternGB = java.util.regex.Pattern.compile("(\\d+)\\s*gb");

        java.util.regex.Matcher matcherMB = patternMB.matcher(dataLimit.toLowerCase());
        if (matcherMB.find()) {
            return Integer.parseInt(matcherMB.group(1));
        }
        java.util.regex.Matcher matcherGB = patternGB.matcher(dataLimit.toLowerCase());
        if (matcherGB.find()) {
            return Integer.parseInt(matcherGB.group(1)) * 1024;
        }
        return 0;
    }
}
