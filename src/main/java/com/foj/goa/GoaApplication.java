package com.foj.goa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GoaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GoaApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
	}

}
