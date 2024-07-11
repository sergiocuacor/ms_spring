package com.springbootapp.item;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration // Nos permite crear objetos (componentes) de Spring (Beans)
public class AppConfig {

// RestTemplate: Cliente HTTP para trabajar con API Rest. Para poder acceder a recursos que están en otros microservicios
	
	@Bean("clienteRest")
	public RestTemplate registrarRestTemplate() {
		return new RestTemplate();
	}
}
