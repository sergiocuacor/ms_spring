package com.springbootapp.productos.controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springbootapp.productos.models.entity.Producto;
import com.springbootapp.productos.models.service.IProductoService;

@RestController // Convierte a JSON lo que devuelven los métodos handler(los que manejan solicitudes HTTP como GET, POST, PUT DELETE..)
public class ProductoController {

	@Autowired
	private IProductoService productoService; // Buscará la clase que implemente los métodos.
	
	
	// ENDPOINT : Es la ruta de la API Rest
	
	@GetMapping("/listar") 
	public List<Producto> listar(){
		return productoService.findAll();
	}
	
	@GetMapping("/listar/{id}")
	public Producto listarPorId(@PathVariable Long id) throws InterruptedException {
		
		if(id.equals(10L)) {
			throw new IllegalStateException("Producto no encontrado");
		}
		if(id.equals(7L)) {
			TimeUnit.SECONDS.sleep(5L);
		}
		if(id.equals(5L)) {
			TimeUnit.SECONDS.sleep(3L);
		}
		
		return productoService.findById(id);
	}
	
}
