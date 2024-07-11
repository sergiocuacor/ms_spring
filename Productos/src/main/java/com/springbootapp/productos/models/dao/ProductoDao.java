package com.springbootapp.productos.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.springbootapp.productos.models.entity.Producto;

// A las clases/packages Dao también las podemos llamar Repository

public interface ProductoDao extends CrudRepository<Producto, Long>{

	
	
}
