package com.retooling.farm.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.retooling.farm.FarmMgmtApplication;
import com.retooling.farm.exception.FarmNotFoundException;
import com.retooling.farm.service.FarmService;

@Component
public class InitialDataConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(FarmMgmtApplication.class);

	@Autowired
	FarmService service;

	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	Job job;
	
	@EventListener(ApplicationReadyEvent.class)
	public void loadData() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		try {
			logger.info("Buscando granja con id=1...");
			service.getFarmById("1");
			logger.info("Datos de granja encontrados");
		} catch (FarmNotFoundException ex ) {
			logger.info("No se encontró granja. Se cargan datos...");
			jobLauncher.run(job, new JobParametersBuilder().toJobParameters());
			logger.info("Datos cargados");
		}
	}
	
}
