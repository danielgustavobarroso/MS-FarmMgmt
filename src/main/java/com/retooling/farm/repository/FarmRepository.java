package com.retooling.farm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.retooling.farm.entity.Farm;

public interface FarmRepository extends MongoRepository<Farm, String>{

	//Necesita el atributo farmId en el modelo para funcionar
	Farm findByFarmId(String id);
	
}
