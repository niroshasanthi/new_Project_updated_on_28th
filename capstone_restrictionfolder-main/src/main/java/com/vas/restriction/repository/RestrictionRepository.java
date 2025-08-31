package com.vas.restriction.repository;

import com.vas.restriction.model.Restriction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RestrictionRepository extends JpaRepository<Restriction, Long> {
    List<Restriction> findByParentId(Long parentId);
    List<Restriction> findByChildMemberId(Long childMemberId);
}
