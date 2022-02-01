package com.api.shopping.list.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.shopping.list.model.entities.Purchase;
import com.api.shopping.list.services.PurchaseService;

@RestController
@RequestMapping(path = "/api/purchase")
public class PurchaseController {
	@Autowired
	private PurchaseService service;
	
	@GetMapping
	public ResponseEntity<List<Purchase>> getAllPurchase() {
		List<Purchase> purchases = service.getAllPurchase();
		
		return 	ResponseEntity.ok().body(purchases);
	}
	
	@PostMapping
	public ResponseEntity<Purchase> savePurchase(@RequestBody Purchase purchase) {
		Purchase newPurchase = service.savePurchase(purchase);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newPurchase);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Purchase> getById(@PathVariable Long id) {
		Purchase purchase = service.findById(id);
		
		return ResponseEntity.ok().body(purchase);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Purchase> updatePurchase(@PathVariable Long id, @RequestBody Purchase purchase) {
		Purchase updatedPurchase = service.updateById(id, purchase);
		
		return ResponseEntity.ok().body(updatedPurchase);
	}
}