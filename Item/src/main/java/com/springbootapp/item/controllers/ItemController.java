package com.springbootapp.item.controllers;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springbootapp.item.models.Item;
import com.springbootapp.item.models.Producto;
import com.springbootapp.item.models.service.ItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RestController
public class ItemController {

	/* Inyeccion del Circuit breaker. Tiene 3 estados: cerrado, abierto y semiabierto.
	Cerrado: estado normal y correcto. 
	Abierto: Cuando supera la tasa de fallos que configuremos. Puede utilizar un camino alternativo. Pasado cierto tiempo o X fallos, pasa al estado semiabierto.
	Semiabierto: Hace pruebas con el microservicio. Si funciona correctamente, pasa el estado cerrado y si no vuelve al abierto. 
	*/
	
	private final Logger logger = LoggerFactory.getLogger(ItemController.class);
	
	@Autowired
	private CircuitBreakerFactory cbFactory;
	
	@Autowired
	@Qualifier("serviceFeign") 
	private ItemService itemService;
	
	@GetMapping("/listar")
	public List<Item> listar(){
		return itemService.findAll();
	}
	
	@GetMapping("/listar/{id}/cantidad/{cantidad}")
	public Item listarPorId(@PathVariable Long id, @PathVariable Integer cantidad){
		return cbFactory.create("items")
				.run(() -> itemService.findById(id, cantidad), e -> metodoAlternativo(id, cantidad, e));
	}
	
	@CircuitBreaker(name="items")
	@TimeLimiter(name="items", fallbackMethod="metodoAlternativo2")
	@GetMapping("/listar2/{id}/cantidad/{cantidad}")
	public CompletableFuture<Item> listarPorId2(@PathVariable Long id, @PathVariable Integer cantidad){
		return CompletableFuture.supplyAsync(()-> itemService.findById(id, cantidad)) ;
	}
	
	public Item metodoAlternativo(Long id, Integer cantidad, Throwable e) {
		
		logger.info(e.getMessage());
		
		return new Item(new Producto(id, "Cámara Sony", 500.00, null), cantidad);
	}
	

	
public CompletableFuture<Item> metodoAlternativo2(Long id, Integer cantidad, Throwable e) {
		
		logger.info(e.getMessage());
		Item item = new Item();
		Producto producto = new Producto();
		item.setCantidad(cantidad);
		producto.setId(id);
		producto.setNombre("ibrahimovic");
		producto.setPrecio(500.0);
		item.setProducto(producto);
		return CompletableFuture.supplyAsync(()-> item);
	}
}
