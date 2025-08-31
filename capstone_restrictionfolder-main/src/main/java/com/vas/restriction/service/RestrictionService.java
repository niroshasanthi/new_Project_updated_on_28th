package com.vas.restriction.service;

import com.vas.restriction.model.Restriction;
import com.vas.restriction.repository.RestrictionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestrictionService {

    private final RestrictionRepository restrictionRepository;

    public RestrictionService(RestrictionRepository restrictionRepository) {
        this.restrictionRepository = restrictionRepository;
    }

    public Restriction addRestriction(Restriction restriction) {
        return restrictionRepository.save(restriction);
    }

    public List<Restriction> getRestrictionsForParent(Long parentId) {
        return restrictionRepository.findByParentId(parentId);
    }

    public List<Restriction> getRestrictionsForChild(Long childId) {
        return restrictionRepository.findByChildMemberId(childId);
    }
}
