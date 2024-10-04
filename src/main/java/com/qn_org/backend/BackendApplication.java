package com.qn_org.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.net.URI;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		System.out.println("Local server host: http://localhost:8080/swagger-ui/index.html");
		System.out.println("Project SRS Documentation: https://drive.google.com/drive/folders/1sgtiG-LjlUluagPA_B1DsIIjzubvHQiS?usp=drive_link");
	}



}
