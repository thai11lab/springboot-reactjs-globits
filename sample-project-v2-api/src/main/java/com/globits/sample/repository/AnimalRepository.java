package com.globits.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.globits.sample.domain.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
	
}
