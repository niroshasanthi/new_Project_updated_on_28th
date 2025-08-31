package com.example.service;

import java.util.List;
import com.example.dto.AppDto;
import com.example.dto.RestrictionDto;

public interface ChildDashboardService {
    List<AppDto> getDashboardApps(Long childId);
    List<RestrictionDto> getRestrictionsByChildId(Long childId);
}
