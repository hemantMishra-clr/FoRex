package com.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


//(exclude=SecurityAutoConfiguration.class)
@SpringBootApplication
public class BootApp1Application {

	public static void main(String[] args) {
		SpringApplication.run(BootApp1Application.class, args);
	}

}
