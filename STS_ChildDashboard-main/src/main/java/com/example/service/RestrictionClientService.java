package com.example.service;

import com.example.dto.RestrictionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class RestrictionClientService {

    private final String ADD_RESTRICTIONS_BASE_URL = "http://add_restrictions_service_host/api/restrictions";

    @Autowired
    private RestTemplate restTemplate;

    public List<RestrictionDto> getRestrictions(Long parentId, Long childId) {
        String url = String.format("%s/%d/%d", ADD_RESTRICTIONS_BASE_URL, parentId, childId);
        RestrictionDto[] restrictions = restTemplate.getForObject(url, RestrictionDto[].class);
        return restrictions != null ? Arrays.asList(restrictions) : List.of();
    }
}
