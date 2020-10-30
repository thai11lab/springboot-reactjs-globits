package com.globits.sample.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.globits.sample.domain.Employee;
import com.globits.sample.dto.EmployeeDto;
import com.globits.sample.dto.EmployeeSearchDTO;

@Service
public interface EmployeeService {
	List<EmployeeDto> findAll();
	Page<EmployeeDto> findAll(Pageable pageables);
	EmployeeDto update(Employee employee,Long id);
	EmployeeDto insert(Employee employee);
	EmployeeDto deleteOne(Long id);
	EmployeeDto findById(Long id);
	Page<EmployeeDto> findBySearch(EmployeeSearchDTO employeeSearchDTO,Pageable pageable);
	List<EmployeeDto> findBySearch(String keyString);
	Page<EmployeeDto> findAll1(Pageable pageable);
}
