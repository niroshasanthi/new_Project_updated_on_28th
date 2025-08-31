package com.example.repository;

import com.example.entity.ChildMember;
import com.example.entity.Parent;
import com.example.entity.Restriction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RestrictionRepository extends JpaRepository<Restriction, Long> {

	
	 @Query("SELECT r FROM Restriction r WHERE r.child.id = :childId")
	    List<Restriction> findByChildId(@Param("childId") Long childId);
	/*
	  List<Restriction> findByChildAndParent(ChildMember child, Parent parent);
	 


	 @Query("SELECT r FROM Restriction r WHERE r.child.id = :childId")
	 List<Restriction> findByChildId(@Param("childId") Long childId);

	 @Query("SELECT r FROM Restriction r WHERE r.child.id = :childId AND r.parent.id = :parentId")
	 List<Restriction> findByChildIdAndParentId(@Param("childId") Long childId, @Param("parentId") Long parentId);

//List<Restriction> findByChildidAndParentid(ChildMember childid, Parent parentid);

List<Restriction> findByChild(ChildMember child);
//List<Restriction> findByChildId(Long childId);


    //List<Restriction> findByParentidAndReportDate(Long parentId, LocalDate reportDate);
*/
}
