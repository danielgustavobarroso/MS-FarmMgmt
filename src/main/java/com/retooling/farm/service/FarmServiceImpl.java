package com.retooling.farm.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retooling.farm.entity.Farm;
import com.retooling.farm.exception.FarmNotFoundException;
import com.retooling.farm.repository.FarmRepository;

@Service
public class FarmServiceImpl implements FarmService {

	private static final Logger logger = LoggerFactory.getLogger(FarmServiceImpl.class);
	
	@Autowired
	FarmRepository repository;
	
	@Override
	public List<Farm> getAllFarms() {
		logger.info("Service - Calling method getAllFarms...");
		return repository.findAll();			
	}

	@Override
	public Farm getFarmById(String id) throws FarmNotFoundException{
		logger.info("Service - Calling method getFarmById...");
		Farm farm = repository.findByFarmId(id);
		if (farm != null) {
			return farm;
		} else {
			throw new FarmNotFoundException("Farm not found with id=" + id); 
		}	
	}
	
	@Override
	public Farm saveFarm(Farm farm) {
		logger.info("Service - Calling method saveFarm...");
		return repository.save(farm);
	}

	@Override
	public void deleteFarm(Farm farm) {
		logger.info("Service - Calling method deleteFarm...");
		repository.delete(farm);
	}

}
