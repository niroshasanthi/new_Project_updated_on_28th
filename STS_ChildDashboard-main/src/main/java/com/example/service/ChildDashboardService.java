package com.example.service;

import java.util.List;

import com.example.dto.AppDto;

public interface ChildDashboardService {
    List<AppDto> getChildApps(Long childId);
}
