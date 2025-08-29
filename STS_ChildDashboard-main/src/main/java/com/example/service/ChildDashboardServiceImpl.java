package com.example.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import com.example.dto.AppDto;

@Service
public class ChildDashboardServiceImpl implements ChildDashboardService {

    @Override
    public List<AppDto> getChildApps(Long childId) {
        return Arrays.asList(
            new AppDto(1L, "YouTube kids", "Video"),
            new AppDto(2L, "Google Classroom", "Education"),
            new AppDto(3L, "Minecraft", "Game")
        );
    }
}
