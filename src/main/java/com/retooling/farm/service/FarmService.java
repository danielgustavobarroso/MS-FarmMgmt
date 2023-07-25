package com.retooling.farm.service;

import java.util.List;

import com.retooling.farm.entity.Farm;
import com.retooling.farm.exception.FarmNotFoundException;

public interface FarmService {
	
	public List<Farm> getAllFarms();

	public Farm getFarmById(String id) throws FarmNotFoundException;
	
	public Farm saveFarm(Farm farm);
	
	public void deleteFarm(Farm farm);
	
}
