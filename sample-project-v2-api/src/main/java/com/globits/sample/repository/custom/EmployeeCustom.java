package com.globits.sample.repository.custom;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.globits.sample.domain.Employee;
import com.globits.sample.dto.EmployeeSearchDTO;

public interface EmployeeCustom {
	Page<Employee> findAllEmployee(EmployeeSearchDTO employeeSearchDTO,Pageable pageable);
	Employee addEmployee(Employee employee);
	Page<Employee> getListAll(Pageable pageable);
}
