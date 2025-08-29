
package com.vas.restriction.model;

import jakarta.persistence.*;

@Entity
@Table(name = "restrictions")
public class Restriction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String appName;
   
    private String dataLimit;   // e.g. "500MB/day"
    private String timeLimit;   // e.g. "2 hours/day"
    private String customRule;  // optional text rule

    // Constructors
    public Restriction() {}

    public Restriction(Long id, Long memberId, String appName, String dataLimit, String timeLimit, String customRule) {
        this.id = id;
        this.memberId = memberId;
        this.appName = appName;
        this.dataLimit = dataLimit;
        this.timeLimit = timeLimit;
        this.customRule = customRule;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDataLimit() {
        return dataLimit;
    }

    public void setDataLimit(String dataLimit) {
        this.dataLimit = dataLimit;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getCustomRule() {
        return customRule;
    }

    public void setCustomRule(String customRule) {
        this.customRule = customRule;
    }
}