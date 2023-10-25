package com.pruebaTecnica.msvc.clientepersona;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcClientePersonaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcClientePersonaApplication.class, args);
	}

}
