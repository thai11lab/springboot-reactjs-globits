package com.globits.sample.service.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.modelmapper.Converters;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageDto;
import org.springframework.stereotype.Service;

import com.globits.sample.domain.Employee;
import com.globits.sample.dto.EmployeeDto;
import com.globits.sample.dto.EmployeeSearchDTO;
import com.globits.sample.repository.EmployeeRepository;
import com.globits.sample.service.EmployeeService;

import ch.qos.logback.core.pattern.Converter;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Page<EmployeeDto> findAll(Pageable pageable) {
		Page<Employee> employees = employeeRepository.findAll(pageable);
		List<EmployeeDto> dtos = new ArrayList<>();
		Long stt = (long) (pageable.getPageSize() * pageable.getPageNumber() + 1);
		for (Employee items : employees) {
			EmployeeDto employeeDto = new EmployeeDto();
			employeeDto.setId(items.getId());
			employeeDto.setCode(items.getCode());
			employeeDto.setName(items.getName());
			employeeDto.setEmail(items.getEmail());
			employeeDto.setPhone(items.getPhone());
			employeeDto.setAge(items.getAge());
			dtos.add(employeeDto);
		}
		return new PageImpl<EmployeeDto>(dtos, pageable, employees.getTotalElements());
	}

	public EmployeeDto convertToDTO(Employee employee) {
		ModelMapper mapper = new ModelMapper();
		EmployeeDto dto = mapper.map(employee, EmployeeDto.class);
		return dto;
	}

	public EmployeeSearchDTO convertToDTO1(Employee employee) {
		ModelMapper mapper = new ModelMapper();
		EmployeeSearchDTO dto = mapper.map(employee, EmployeeSearchDTO.class);
		return dto;
	}

	@Override
	public EmployeeDto update(Employee employee, Long id) {
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
		if (employeeRepository.existsById(id)) {
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
	public Page<EmployeeDto> findBySearch(EmployeeSearchDTO employeeSearchDTO, Pageable pageable) {

		Page<Employee> employees = employeeRepository.findAllEmployee(employeeSearchDTO, pageable);

		List<EmployeeDto> dtos = new ArrayList<>();

		Long stt = (long) (pageable.getPageSize() * pageable.getPageNumber() + 1);
		for (Employee items : employees) {
			EmployeeDto employeeDto = new EmployeeDto();
			employeeDto.setId(items.getId());
			employeeDto.setCode(items.getCode());
			employeeDto.setName(items.getName());
			employeeDto.setEmail(items.getEmail());
			employeeDto.setPhone(items.getPhone());
			employeeDto.setAge(items.getAge());
			dtos.add(employeeDto);
		}

		return new PageImpl<EmployeeDto>(dtos, pageable, employees.getTotalElements());
	}

	@Override
	public List<EmployeeDto> findBySearch(String keyString) {
		List<Employee> employees = employeeRepository.findAllEmployee(keyString);
		List<EmployeeDto> dtos = new ArrayList<>();
		Integer stt = 1;
		for (Employee items : employees) {
			EmployeeDto employeeDto = new EmployeeDto();
			employeeDto.setStt(stt++);
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

	@Override
	public List<EmployeeDto> findAll() {
		List<Employee> employees = employeeRepository.findAll();
		List<EmployeeDto> dtos = new ArrayList<>();
		Integer stt = 1;
		for (Employee items : employees) {
			EmployeeDto employeeDto = new EmployeeDto();
			employeeDto.setStt(stt++);
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

	@Override
	public Page<EmployeeDto> findAll1(Pageable pageable) {
		Page<Employee> data = employeeRepository.getListAll(pageable);

		List<EmployeeDto> listDto = new ArrayList<EmployeeDto>();
		for (Employee items : data) {
			EmployeeDto employeeDto = new EmployeeDto();
			employeeDto.setId(items.getId());
			employeeDto.setCode(items.getCode());
			employeeDto.setName(items.getName());
			employeeDto.setEmail(items.getEmail());
			employeeDto.setPhone(items.getPhone());
			employeeDto.setAge(items.getAge());
			listDto.add(employeeDto);
		}

		return new PageImpl<EmployeeDto>(listDto, pageable, data.getTotalElements());
	}
}
