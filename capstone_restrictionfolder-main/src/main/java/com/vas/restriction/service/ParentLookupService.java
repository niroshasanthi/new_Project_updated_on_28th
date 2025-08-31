package com.vas.restriction.service;

import com.vas.restriction.model.Parent;  // Make sure Parent class exists in your project
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class ParentLookupService {

    private static final Logger log = LoggerFactory.getLogger(ParentLookupService.class);

    private final RestTemplate restTemplate = new RestTemplate();

    // Base URL of your parent microservice API for email lookup
    private static final String PARENT_SERVICE_BASE_URL = "http://localhost:8081/api/parents/email";

    // Base URL to get Parent entity by ID
    private static final String PARENT_SERVICE_BY_ID_URL = "http://localhost:8081/api/parents";

    /**
     * Calls the parent microservice endpoint to get parent ID by email.
     * Expects a JSON response containing at least an "id" field.
     */
    public Long getParentIdByEmail(String email) {
        String url = UriComponentsBuilder.fromHttpUrl(PARENT_SERVICE_BASE_URL)
                .queryParam("email", email)
                .toUriString();

        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Object idObj = response.getBody().get("id");
                if (idObj instanceof Number) {
                    Long id = ((Number) idObj).longValue();
                    log.info("Found parent id {} for email {}", id, email);
                    return id;
                }
                log.error("id field is missing or invalid in response for email {}", email);
            } else {
                log.error("Failed to get parent id by email {}, status: {}", email, response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Error fetching parent ID for email {}: {}", email, e.getMessage(), e);
            throw new RuntimeException("Failed to lookup parent ID for email: " + email, e);
        }

        throw new RuntimeException("Parent not found for email: " + email);
    }

    /**
     * Calls the parent microservice endpoint to get full Parent entity by ID.
     */
    public Parent getParentById(Long parentId) {
        String url = PARENT_SERVICE_BY_ID_URL + "/" + parentId;
        try {
            ResponseEntity<Parent> response = restTemplate.getForEntity(url, Parent.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                log.info("Found parent entity for id {}", parentId);
                return response.getBody();
            } else {
                log.error("Failed to get parent entity by id {}, status: {}", parentId, response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Error fetching parent entity for id {}: {}", parentId, e.getMessage(), e);
            throw new RuntimeException("Failed to lookup parent entity for id: " + parentId, e);
        }

        throw new RuntimeException("Parent entity not found for id: " + parentId);
    }
}
