package com.example.repository;
import com.example.model.Restriction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RestrictionRepository extends JpaRepository<Restriction, Long> {
    List<Restriction> findByChildIdAndParentId(Long childId, Long parentId);
}
