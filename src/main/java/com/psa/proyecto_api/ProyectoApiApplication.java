package com.psa.proyecto_api;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ProyectoApiApplication {

	public static void main(String[] args) {
		try {
			Dotenv dotenv = Dotenv.configure().load();
			dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
			System.out.println("Variables .env cargadas para desarrollo local");
		} catch (Exception e) {
			System.out.println("No se pudo cargar .env, usando variables de entorno del sistema");
			System.out.println("Ejecutando en Docker, usando variables de entorno del contenedor");
		}
		
		SpringApplication.run(ProyectoApiApplication.class, args);
	}

	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
