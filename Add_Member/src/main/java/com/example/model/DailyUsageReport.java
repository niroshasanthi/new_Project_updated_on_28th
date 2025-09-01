package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "daily_usage_reports")
public class DailyUsageReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id", nullable = false)
    private Parent parentid;

    @ManyToOne
    @JoinColumn(name = "child_id", referencedColumnName = "id", nullable = false)
    private ChildMember childid;

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
	@Column(nullable = false)
    private LocalDate reportDate;

    private String appsUsed;
    private String timeUsed;
    private String dataUsed;
    
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	public LocalDate getReportDate() {
		return reportDate;
	}
	public void setReportDate(LocalDate reportDate) {
		this.reportDate = reportDate;
	}
	public String getAppsUsed() {
		return appsUsed;
	}
	public void setAppsUsed(String appsUsed) {
		this.appsUsed = appsUsed;
	}
	public String getTimeUsed() {
		return timeUsed;
	}
	public void setTimeUsed(String timeUsed) {
		this.timeUsed = timeUsed;
	}
	public String getDataUsed() {
		return dataUsed;
	}
	public void setDataUsed(String dataUsed) {
		this.dataUsed = dataUsed;
	}

    
}
