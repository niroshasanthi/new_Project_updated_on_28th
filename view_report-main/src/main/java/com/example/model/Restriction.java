package com.example.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "restrictions")
public class Restriction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id", nullable = false)
    private Parent parentid;

    @ManyToOne
    @JoinColumn(name = "child_id", referencedColumnName = "id", nullable = false)
    private ChildMember childid;

	@Column(nullable = false)
    private String appName;

    private String dataLimit;   // e.g. "500MB/day"
    private String timeLimit;   // e.g. "2 hours/day"

    private LocalTime bedTimeStart;
    private LocalTime bedTimeEnd;

    private String customRule;

    public Restriction() {}
    
    

    // Getters and setters

    public Parent getParentid() {
		return parentid;
	}



	public void setParentid(Parent parentid) {
		this.parentid = parentid;
	}



	public ChildMember getChildid() {
		return childid;
	}



	public void setChildid(ChildMember childid) {
		this.childid = childid;
	}



	public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAppName() { return appName; }
    public void setAppName(String appName) { this.appName = appName; }

    public String getDataLimit() { return dataLimit; }
    public void setDataLimit(String dataLimit) { this.dataLimit = dataLimit; }

    public String getTimeLimit() { return timeLimit; }
    public void setTimeLimit(String timeLimit) { this.timeLimit = timeLimit; }

    public LocalTime getBedTimeStart() { return bedTimeStart; }
    public void setBedTimeStart(LocalTime bedTimeStart) { this.bedTimeStart = bedTimeStart; }

    public LocalTime getBedTimeEnd() { return bedTimeEnd; }
    public void setBedTimeEnd(LocalTime bedTimeEnd) { this.bedTimeEnd = bedTimeEnd; }

    public String getCustomRule() { return customRule; }
    public void setCustomRule(String customRule) { this.customRule = customRule; }
}
