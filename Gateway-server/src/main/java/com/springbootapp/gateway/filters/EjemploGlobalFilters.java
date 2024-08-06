package com.springbootapp.gateway.filters;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class EjemploGlobalFilters implements GlobalFilter{

	private final Logger logger = LoggerFactory.getLogger(EjemploGlobalFilters.class);
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
	
		logger.info("ejecuta filtro pre");
		exchange.getRequest().mutate().headers(h -> h.add("token", "123456"));
		/* Esta línea modifica la solicitud entrante añadiendo un header "token" con el valor "123456".
Esto se hace antes de que la solicitud se envíe al servicio de destino.
*/


		
		
		return chain.filter(exchange).then(Mono.fromRunnable(()->{
			logger.info("ejecutando filtro post");
			
			Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token")).ifPresent(valor ->{
				exchange.getResponse().getHeaders().add("token", valor);
			});
			/* Esta parte busca el header "token" en la solicitud original.
Si existe, añade ese mismo token a los headers de la respuesta. */
			
			
			
			exchange.getResponse().getCookies().add("color", ResponseCookie.from("color","rojo").build());
			exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
		}));
	}

}
