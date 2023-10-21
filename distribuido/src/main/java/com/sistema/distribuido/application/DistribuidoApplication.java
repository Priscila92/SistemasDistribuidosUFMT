package com.sistema.distribuido.application;


import com.sistema.distribuido.config.Config;
import com.sistema.distribuido.controller.ClientController;
import com.sistema.distribuido.service.ClientService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackageClasses = {ClientController.class, ClientService.class,Config.class})
public class DistribuidoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistribuidoApplication.class, args);
	}
}
