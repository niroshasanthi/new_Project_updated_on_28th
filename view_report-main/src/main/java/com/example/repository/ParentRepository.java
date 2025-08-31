package com.example.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.Parent;

import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
	
	@Query("SELECT p FROM Parent p WHERE LOWER(p.email) = LOWER(:email)")
	//Optional<Parent> findByEmail(String email);
	Optional<Parent> findByEmailIgnoreCase(@Param("email") String email);

}
