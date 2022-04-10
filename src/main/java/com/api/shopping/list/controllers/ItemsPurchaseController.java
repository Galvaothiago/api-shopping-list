package com.api.shopping.list.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.shopping.list.services.PurchaseService;

@RestController
@RequestMapping(path = "/api/purchaseItem")
public class ItemsPurchaseController {
	
	@Autowired
	private PurchaseService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getAllPurchase(@PathVariable Long id) {
		
		List<String> items = service.getItemsFromPurchase(id);
		
		return 	ResponseEntity.ok().body(items);
	}
}
