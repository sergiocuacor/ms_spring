package com.springbootapp.productos.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springbootapp.productos.models.dao.ProductoDao;
import com.springbootapp.productos.models.entity.Producto;


@Service //Registramos la clase como un bean, nos permite inyectarlo en otros componentes con inyeccion de dependencias
public class ProductoServiceImpl implements IProductoService{

	@Autowired
	private ProductoDao productoDao;
	
	@Override
	@Transactional(readOnly=true) //Transactional para que esté sincronizada con la BDD. Se importa de springframework
	public List<Producto> findAll() {
		
		return  (List<Producto>) productoDao.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Producto findById(Long id) {
		
		return productoDao.findById(id).orElse(null);
	}
	
	

}
