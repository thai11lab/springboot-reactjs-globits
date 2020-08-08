package com.globits.sample.service.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.globits.sample.domain.Employee;
import com.globits.sample.dto.EmployeeDto;
import com.globits.sample.dto.EmployeeSearchDTO;
import com.globits.sample.repository.EmployeeRepository;
import com.globits.sample.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeRepository employeeRepository; 
	
	@Override
	public List<EmployeeDto> findAll() {
		List<Employee> employees = employeeRepository.findAll();
		List<EmployeeDto> dtos = new ArrayList<>();
		Integer stt=1;
		for(Employee items : employees) {
			EmployeeDto employeeDto = new EmployeeDto();
			employeeDto.setSTT(stt++);
			employeeDto.setId(items.getId());
			employeeDto.setCode(items.getCode());
			employeeDto.setName(items.getName());
			employeeDto.setEmail(items.getEmail());
			employeeDto.setPhone(items.getPhone());
			employeeDto.setAge(items.getAge());
			dtos.add(employeeDto);
		}
		return dtos;
	}
	
	public EmployeeDto convertToDTO(Employee employee) {
		ModelMapper mapper = new ModelMapper();
		EmployeeDto dto =mapper.map(employee,EmployeeDto.class);
		return dto;
	}
	
	public EmployeeSearchDTO convertToDTO1(Employee employee) {
		ModelMapper mapper = new ModelMapper();
		EmployeeSearchDTO dto =mapper.map(employee,EmployeeSearchDTO.class);
		return dto;
	}

	@Override
	public EmployeeDto update(Employee employee,Long id) {
		Employee employees = employeeRepository.findById(id).get();
		employees = employeeRepository.save(employee);
		return convertToDTO(employees);
	}

	@Override
	public EmployeeDto insert(Employee employee) {
		Employee employee2 = employeeRepository.save(employee);
		return convertToDTO(employee2);
	}

	@Override
	public EmployeeDto deleteOne(Long id) {
		if(employeeRepository.existsById(id)) {
			Employee employee = new Employee();
			employee = employeeRepository.findById(id).get();
			employeeRepository.deleteById(id);
			return convertToDTO(employee);
		}
		return null;
	}

	@Override
	public EmployeeDto findById(Long id) {
		Employee employee = employeeRepository.findById(id).get();
		return convertToDTO(employee);
	}

	@Override
	public Page<EmployeeDto> findBySearch(String keString,Pageable pageable) {
		
		Page<Employee> employees =  employeeRepository.findAllEmployee(keString, pageable);
		
		List<EmployeeDto> dtos = new ArrayList<>();
	
		Integer stt=pageable.getPageSize()*pageable.getPageNumber()+1;
		for(Employee items : employees) {
			EmployeeDto employeeDto = new EmployeeDto();
			employeeDto.setSTT(stt++);
			employeeDto.setId(items.getId());
			employeeDto.setCode(items.getCode());
			employeeDto.setName(items.getName());
			employeeDto.setEmail(items.getEmail());
			employeeDto.setPhone(items.getPhone());
			employeeDto.setAge(items.getAge());
			dtos.add(employeeDto);
		}
		
		
		return new PageImpl<EmployeeDto>(dtos,pageable,employees.getTotalElements());
	}

	@Override
	public List<EmployeeDto> findBySearch(String keyString) {
		List<Employee> employees = employeeRepository.findAllEmployee(keyString);
		List<EmployeeDto> dtos = new ArrayList<>();
		Integer stt=1;
		for(Employee items : employees) {
			EmployeeDto employeeDto = new EmployeeDto();
			employeeDto.setSTT(stt++);
			employeeDto.setId(items.getId());
			employeeDto.setCode(items.getCode());
			employeeDto.setName(items.getName());
			employeeDto.setEmail(items.getEmail());
			employeeDto.setPhone(items.getPhone());
			employeeDto.setAge(items.getAge());
			dtos.add(employeeDto);
		}
		return dtos;
	}
}
