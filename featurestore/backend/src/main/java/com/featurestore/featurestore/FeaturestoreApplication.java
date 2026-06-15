package com.featurestore.featurestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class FeaturestoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeaturestoreApplication.class, args);
	}

}
