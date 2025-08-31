package com.example.dto;

public class RestrictionDto {

    private Long id;
    private Long parentId;
    private Long childId;
    private String appName;
    private String dataLimit;
    private String timeLimit;
    private String customRule;
    private String bedTimeStart;
    private String bedTimeEnd;

    // Getters and setters for all fields
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }

    public Long getChildId() { return childId; }
    public void setChildId(Long childId) { this.childId = childId; }

    public String getAppName() { return appName; }
    public void setAppName(String appName) { this.appName = appName; }

    public String getDataLimit() { return dataLimit; }
    public void setDataLimit(String dataLimit) { this.dataLimit = dataLimit; }

    public String getTimeLimit() { return timeLimit; }
    public void setTimeLimit(String timeLimit) { this.timeLimit = timeLimit; }

    public String getCustomRule() { return customRule; }
    public void setCustomRule(String customRule) { this.customRule = customRule; }

    public String getBedTimeStart() { return bedTimeStart; }
    public void setBedTimeStart(String bedTimeStart) { this.bedTimeStart = bedTimeStart; }

    public String getBedTimeEnd() { return bedTimeEnd; }
    public void setBedTimeEnd(String bedTimeEnd) { this.bedTimeEnd = bedTimeEnd; }
}
