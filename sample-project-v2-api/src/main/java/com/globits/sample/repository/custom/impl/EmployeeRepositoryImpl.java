package com.globits.sample.repository.custom.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageDto;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fasterxml.classmate.AnnotationConfiguration;
import com.globits.sample.domain.Employee;
import com.globits.sample.dto.EmployeeSearchDTO;
import com.globits.sample.repository.custom.EmployeeCustom;

@Repository
public class EmployeeRepositoryImpl implements EmployeeCustom {
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<Employee> findAllEmployee(EmployeeSearchDTO employeeSearchDTO, Pageable pageable) {
		
		StringBuilder sql= new StringBuilder(" select *FROM employee WHERE ");
		sql.append("name like '%"+employeeSearchDTO.getKeyword() +"%' ");
		sql.append(" OR id like '%"+employeeSearchDTO.getKeyword()+"%' ");
		sql.append(" OR code like '%"+employeeSearchDTO.getKeyword()+"%' ");
		sql.append(" OR email like '%"+employeeSearchDTO.getKeyword()+"%' ");
		sql.append(" OR phone like '%"+employeeSearchDTO.getKeyword()+"%' ");
		sql.append(" OR age like '%"+employeeSearchDTO.getKeyword()+"%' ");
		
		Query query =  em.createNativeQuery(sql.toString(), Employee.class);
		List<Employee> resultList = query.getResultList();
		int start = (int) pageable.getOffset();
		int end = (int) ((start + pageable.getPageSize()) > resultList.size() ? resultList.size()
				  : (start + pageable.getPageSize()));
		Page<Employee> page3 = new PageImpl<Employee>(resultList.subList(start, end),pageable,resultList.size());
		return page3;
	}

	@Transactional
	@Override
	public Employee addEmployee(Employee employee) {
		
		
		StringBuilder sqlInsert = new StringBuilder("INSERT INTO employee(age,code,email,name,phone)");
		sqlInsert.append(" values (?,?,?,?,?)");
		
		em.createNativeQuery(sqlInsert.toString())
		.setParameter(1, employee.getAge())
		.setParameter(2, employee.getCode())
		.setParameter(3, employee.getEmail())
		.setParameter(4, employee.getName())
		.setParameter(5, employee.getPhone())
		.executeUpdate();
		
		return employee;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<Employee> getListAll(Pageable pageable) {
		StringBuilder sql= new StringBuilder(" select *FROM employee ORDER BY id DESC ");
		Query query = em.createNativeQuery(sql.toString(), Employee.class);	
		final List<Employee> resultList = query.getResultList();
		int start = (int) pageable.getOffset();
		int end = (int) ((start + pageable.getPageSize()) > resultList.size() ? resultList.size()
				  : (start + pageable.getPageSize()));
		Page<Employee> page2 = new PageImpl<Employee>(resultList.subList(start, end),pageable,resultList.size());
		return page2;
	}
	
}
