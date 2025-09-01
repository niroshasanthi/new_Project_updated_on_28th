package com.example.repository;

import com.example.model.ChildMember;
import com.example.model.DailyUsageReport;
import com.example.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DailyUsageReportRepository extends JpaRepository<DailyUsageReport, Long> {

    List<DailyUsageReport> findByParentidAndChildidAndReportDate(Parent parentid, ChildMember childid, LocalDate reportDate);

    boolean existsByParentidAndChildidAndReportDate(Parent parentid, ChildMember childid, LocalDate reportDate);

    List<DailyUsageReport> findByParentid(Parent parentid);
    
    List<DailyUsageReport> findByParentidAndReportDate(Parent parent, LocalDate reportDate);
    
    List<DailyUsageReport> findByReportDate(LocalDate reportDate);
    
    DailyUsageReport findByParentidAndChildidAndReportDateAndAppsUsed(Parent parent, ChildMember child, LocalDate date, String appName);

}
