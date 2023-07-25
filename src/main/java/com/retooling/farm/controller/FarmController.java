package com.retooling.farm.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.retooling.farm.entity.Farm;
import com.retooling.farm.exception.FarmNotFoundException;
import com.retooling.farm.exception.FarmValidationErrorException;
import com.retooling.farm.service.FarmService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class FarmController {

	private static final Logger logger = LoggerFactory.getLogger(FarmController.class);
	
	@Autowired
	FarmService service;
	
	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	Job job;
	
	//Obtener todas las granjas
	@GetMapping("farms")
	public ResponseEntity<List<Farm>> getAllFarms() {
		logger.info("Controller - Calling method getAllFarms...");
		return new ResponseEntity<>(service.getAllFarms(), HttpStatus.OK);
	}
	
	//Obtener una granja por id
	@GetMapping("farms/{id}")
	public ResponseEntity<Farm> getFarmById(@PathVariable("id") String id) throws FarmNotFoundException {
		logger.info("Controller - Calling method getFarmById con id=" + id);
		return new ResponseEntity<>(service.getFarmById(id), HttpStatus.OK);				
	}
	
	//Guardar una granja
	@PostMapping("farms")
	public ResponseEntity<Farm> createFarm(@Valid @RequestBody Farm farm, BindingResult bindingResult) throws FarmValidationErrorException {
		logger.info("Controller - Calling method createFarm...");
		if (bindingResult.hasErrors()) {
			String message = new String();
			for(FieldError error : bindingResult.getFieldErrors()) {
				if (message.isEmpty()) {
					message = message + error.getField() + " : " + error.getDefaultMessage();
				} else {
					message = message + ", " + error.getField() + " : " + error.getDefaultMessage();
				}
			}
			throw new FarmValidationErrorException(message);
		}
		return new ResponseEntity<>(service.saveFarm(farm), HttpStatus.CREATED);				
	}	

	//Actualizar datos de una granja
	@PutMapping("farms")
	public ResponseEntity<Farm> updateFarm(@Valid @RequestBody Farm farmUpdated, BindingResult bindingResult) throws FarmNotFoundException,
			FarmValidationErrorException{
		logger.info("Controller - Calling method updateFarm...");
		if (bindingResult.hasErrors()) {
			String message = new String();
			for(FieldError error : bindingResult.getFieldErrors()) {
				if (message.isEmpty()) {
					message = message + error.getField() + " : " + error.getDefaultMessage();
				} else {
					message = message + ", " + error.getField() + " : " + error.getDefaultMessage();
				}
			}
			throw new FarmValidationErrorException(message);
		}
		Farm farm = service.getFarmById(farmUpdated.getFarmId());
		BeanUtils.copyProperties(farmUpdated, farm);
		return new ResponseEntity<>(service.saveFarm(farm), HttpStatus.OK);
	}
	
	//Borrar una granja
	@DeleteMapping("farms/{id}")
	public ResponseEntity<Farm> deleteFarm(@PathVariable("id") String id) throws FarmNotFoundException{
		logger.info("Controller - Calling method deleteFarm...");
		Farm farm = service.getFarmById(id);
		service.deleteFarm(farm);
		return new ResponseEntity<>(farm, HttpStatus.OK);
	}
	
	//Guardar una granja
	@PostMapping("farms/load")
	public Long loadFarm() throws Exception {
		logger.info("Controller - Calling method loadFarm...");
		JobExecution jobExecution = jobLauncher.run(job, new JobParametersBuilder()
				.toJobParameters());
		return jobExecution.getId();		
	}
	
}
