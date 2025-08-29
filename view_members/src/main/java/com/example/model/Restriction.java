package com.example.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="restriction")
public class Restriction {
    @Id
    private Long controlId;
    private Long childId;
    private Long parentId;
    private String usedApps;
    private Integer noOfAttempts;
    private Integer noOfUsedApps;
    private Integer timeOfUsage;

    // getters and setters
    public Long getControlId() { return controlId; }
    public void setControlId(Long controlId) { this.controlId = controlId; }
    public Long getChildId() { return childId; }
    public void setChildId(Long childId) { this.childId = childId; }
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
    public String getUsedApps() { return usedApps; }
    public void setUsedApps(String usedApps) { this.usedApps = usedApps; }
    public Integer getNoOfAttempts() { return noOfAttempts; }
    public void setNoOfAttempts(Integer noOfAttempts) { this.noOfAttempts = noOfAttempts; }
    public Integer getNoOfUsedApps() { return noOfUsedApps; }
    public void setNoOfUsedApps(Integer noOfUsedApps) { this.noOfUsedApps = noOfUsedApps; }
    public Integer getTimeOfUsage() { return timeOfUsage; }
    public void setTimeOfUsage(Integer timeOfUsage) { this.timeOfUsage = timeOfUsage; }
}
