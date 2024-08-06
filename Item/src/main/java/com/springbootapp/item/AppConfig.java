package com.springbootapp.item;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration // Nos permite crear objetos (componentes) de Spring (Beans)
public class AppConfig {

// RestTemplate: Cliente HTTP para trabajar con API Rest. Para poder acceder a recursos que están en otros microservicios

	@Bean("clienteRest")
	@LoadBalanced
	public RestTemplate registrarRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> {
			return new Resilience4JConfigBuilder(id)
					.circuitBreakerConfig(CircuitBreakerConfig.custom().slidingWindowSize(10) // Numero de llamadas que
																								// se tienen que dar
																								// para que el Circuit
																								// breaker se abra o se
																								// cierre.
							.failureRateThreshold(50) // Umbral de tasa de fallos. Si es superado, el circuito se abre
							.waitDurationInOpenState(Duration.ofSeconds(10L)) // Segundos en estados abierto antes de
																				// pasar a semi-abierto
							.permittedNumberOfCallsInHalfOpenState(5).slowCallRateThreshold(50)
							.slowCallDurationThreshold(Duration.ofSeconds(2L))
							/*
							 * NO CONFUNDIR SLOW CALLS CON EL TIMEOUT (TimeLimiter): Las slow calls siempre
							 * se ejecutan correctamente, pero tardan más tiempo del deseado y se acumulan
							 * en el conteo de fallos del Circuit Breaker. En el caso del TimeLimiter,
							 * cuando se supera el umbral se va a la excepcion del fallback o al método
							 * alternativo directamente, y también se acumulan al conteo de fallos.
							 */
							.build())
					.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4L)).build())
					/*
					 * Tiempo en el que se tiene que realizar una operación. Si se supera se
					 * considera como un fallo, que se acumularia al FailureRateThreshold
					 */
					.build();
		});
	}

}
