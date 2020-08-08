package com.globits.sample.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.globits.core.service.GenericService;
import com.globits.sample.domain.Animal;

@Service
public interface AnimalService extends GenericService<Animal, Long> {
	
}