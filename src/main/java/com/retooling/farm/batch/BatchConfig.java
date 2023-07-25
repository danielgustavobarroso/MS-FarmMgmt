package com.retooling.farm.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import com.retooling.farm.entity.Farm;

@Configuration
public class BatchConfig {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Bean
	public Job importDataJob(JobRepository jobRepository, JobCompletionNotificationListener listener,
			Step step1, Step step2, Step step3) {
	    return new JobBuilder("importDataJob", jobRepository)
	      .incrementer(new RunIdIncrementer())
	      .listener(listener)
	      .start(step1)
	      .build();
	}

	@Bean
	public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("step1", jobRepository)
		  .<Farm, Farm> chunk(1, transactionManager)
		  .allowStartIfComplete(true)
	      .reader(farmJsonItemReader())
	      .writer(farmMongoItemWriter(mongoTemplate))
	      .build();
	}

	@Bean
	public JsonItemReader<Farm> farmJsonItemReader() {
		return new JsonItemReaderBuilder<Farm>()
				.jsonObjectReader(new JacksonJsonObjectReader<>(Farm.class))
				.resource(new ClassPathResource("farm.json"))
				.name("farmJsonItemReader")
				.build();
	}

	@Bean
	public MongoItemWriter<Farm> farmMongoItemWriter(MongoTemplate mongoTemplate) {
		return new MongoItemWriterBuilder<Farm>()
				.template(mongoTemplate)
				.collection("farms")
				.build();
	}
	
}
