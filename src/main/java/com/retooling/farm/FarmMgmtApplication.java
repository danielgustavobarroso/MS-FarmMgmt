package com.retooling.farm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.retooling.farm.exception.FarmNotFoundException;
import com.retooling.farm.service.FarmService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(servers = {@Server(url = "http://localhost:8011/ms-farm")})
public class FarmMgmtApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(FarmMgmtApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(FarmMgmtApplication.class, args);
		logger.info("Iniciando FarmMgmtApplication...");
	}

}
