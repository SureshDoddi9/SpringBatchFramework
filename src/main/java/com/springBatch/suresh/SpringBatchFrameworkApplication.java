package com.springBatch.suresh;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchFrameworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchFrameworkApplication.class, args);
	}

}
