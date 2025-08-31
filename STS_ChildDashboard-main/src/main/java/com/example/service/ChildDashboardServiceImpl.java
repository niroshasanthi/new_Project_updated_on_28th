package com.example.service;

import com.example.dto.AppDto;
import com.example.dto.RestrictionDto;
import com.example.entity.Restriction;
import com.example.repository.RestrictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChildDashboardServiceImpl implements ChildDashboardService {

    @Autowired
    private RestrictionRepository restrictionRepository;

    private static final List<String> MASTER_APPS = Arrays.asList(
        "netflix", "hotstar", "primevideo", "youtube", "zoom", "teams", "gmeet", "whatsapp", "instagram"
    );

    @Override
    public List<AppDto> getDashboardApps(Long childId) {
        List<Restriction> restrictions = restrictionRepository.findByChildId(childId);

        Set<String> restrictedAppNames = new HashSet<>();
        for (Restriction r : restrictions) {
            if (r.getAppName()!=null) {
                for (String app : r.getAppName().split(",")) {
                    restrictedAppNames.add(app.trim().toLowerCase());
                }
            }
        }

        List<AppDto> apps = new ArrayList<>();
        for (String appName : MASTER_APPS) {
            AppDto appDto = new AppDto();
            appDto.setName(appName);
            appDto.setCategory("Entertainment");
            appDto.setRestricted(restrictedAppNames.contains(appName));
            apps.add(appDto);
        }
        return apps;
    }

    @Override
    public List<RestrictionDto> getRestrictionsByChildId(Long childId) {
        List<Restriction> restrictions = restrictionRepository.findByChildId(childId);
        List<RestrictionDto> dtos = new ArrayList<>();
        for (Restriction r : restrictions) {
            RestrictionDto dto = new RestrictionDto();
            dto.setId(r.getId());
            dto.setAppName(r.getAppName());
            dto.setBedTimeStart(r.getBedTimeStart() != null ? r.getBedTimeStart().toString() : null);
            dto.setBedTimeEnd(r.getBedTimeEnd() != null ? r.getBedTimeEnd().toString() : null);
            dto.setParentId(r.getParentId());
            dto.setChildId(r.getChildId());
            dto.setCustomRule(r.getCustomRule());
            dto.setDataLimit(r.getDataLimit());
            dto.setTimeLimit(r.getTimeLimit());
            dtos.add(dto);
        }
        return dtos;
    }
}
