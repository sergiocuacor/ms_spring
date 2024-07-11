package com.springbootapp.productos.models.service;

import java.util.List;

import com.springbootapp.productos.models.entity.Producto;

public interface IProductoService {

	
	public List<Producto> findAll();
	
	public Producto findById(Long id);
}
