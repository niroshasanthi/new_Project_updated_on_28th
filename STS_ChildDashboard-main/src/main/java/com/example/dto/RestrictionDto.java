package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestrictionDto {
    private int noOfUsedApps;
    private int timeOfUsage;
    private int noOfAttempts;
}
