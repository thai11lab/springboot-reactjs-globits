package com.globits.sample.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.globits.sample.domain.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	@Query(value = "SELECT * FROM employee e WHERE e.code LIKE %:searchText% OR e.name LIKE %:searchText% OR e.email LIKE %:searchText% OR e.phone LIKE %:searchText% OR e.age LIKE %:searchText% ",nativeQuery = true)
	Page<Employee> findAllEmployee(String searchText,Pageable pageable);
	
	@Query(value = "SELECT * FROM employee e WHERE e.code LIKE %:searchText% OR e.name LIKE %:searchText% OR e.email LIKE %:searchText%  OR e.phone LIKE %:searchText% OR e.age LIKE %:searchText% ",nativeQuery = true)
	List<Employee> findAllEmployee(String searchText);
}
