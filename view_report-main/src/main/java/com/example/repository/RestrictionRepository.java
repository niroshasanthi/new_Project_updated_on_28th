package com.example.repository;

import com.example.model.ChildMember;
import com.example.model.Parent;
import com.example.model.Restriction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RestrictionRepository extends JpaRepository<Restriction, Long> {


List<Restriction> findByChildidAndParentid(ChildMember childid, Parent parentid);

List<Restriction> findByChildid(ChildMember childid);

    //List<Restriction> findByParentidAndReportDate(Long parentId, LocalDate reportDate);
}
