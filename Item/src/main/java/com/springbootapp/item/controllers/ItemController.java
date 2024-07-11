package com.springbootapp.item.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springbootapp.item.models.Item;
import com.springbootapp.item.models.service.ItemService;

@RestController
public class ItemController {

	@Autowired
	@Qualifier("serviceFeign") 
	private ItemService itemService;
	
	@GetMapping("/listar")
	public List<Item> listar(){
		return itemService.findAll();
	}
	
	@GetMapping("/listar/{id}/cantidad/{cantidad}")
	public Item listarPorId(@PathVariable Long id, @PathVariable Integer cantidad){
		return itemService.findById(id, cantidad);
	}
}
