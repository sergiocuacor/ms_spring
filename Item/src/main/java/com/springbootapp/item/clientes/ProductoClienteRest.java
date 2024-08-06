package com.springbootapp.item.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.springbootapp.item.models.Producto;

@FeignClient(name = "Productos") // Ponemos el nombre del microservicio con el que nos queremos
															// comunicar(fijarnos en su application.properties)
public interface ProductoClienteRest {

	/*
	 * Cuando usamos OpenFeign tenemos copiar los métodos del microservicio con el
	 * que nos comuniquemos con su mapping correspondiente y su endpoint, teniendo
	 * en cuenta que estamos creando una interfaz
	 */
	@GetMapping("/listar")
	public List<Producto> listar();

	@GetMapping("/listar/{id}")
	public Producto listarPorId(@PathVariable Long id);
}
