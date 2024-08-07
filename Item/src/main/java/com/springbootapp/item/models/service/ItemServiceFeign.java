package com.springbootapp.item.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.springbootapp.item.clientes.ProductoClienteRest;
import com.springbootapp.item.models.Item;
import com.springbootapp.item.models.Producto;

@Service("serviceFeign")
//@Primary Como hemos implementado tanto Feign como RestTemplate, con esta anotación le
			// decimos cuál tiene que priorizar. También podemos usar @Qualifier en el controller
public class ItemServiceFeign implements ItemService {

	@Autowired
	private ProductoClienteRest clienteFeign;

	@Override
	public List<Item> findAll() {

		return clienteFeign.listar().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {

		return new Item(clienteFeign.listarPorId(id), cantidad);

	}

}
