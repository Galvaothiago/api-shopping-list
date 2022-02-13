package com.api.shopping.list.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.shopping.list.model.auth.User;
import com.api.shopping.list.model.entities.Purchase;
import com.api.shopping.list.payload.request.MessageResponse;
import com.api.shopping.list.security.jwt.JwtUtils;
import com.api.shopping.list.services.PurchaseService;

@RestController
@RequestMapping(path = "/api/purchase")
public class PurchaseController {
	@Autowired
	private PurchaseService service;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@GetMapping
	public ResponseEntity<List<Purchase>> getAllPurchase(HttpServletRequest request) {
		User user = jwtUtils.getUserByToken(request);
		
		List<Purchase> purchases = service.getAllPurchase(user);
		
		if(user == null) {
			return null;
		}
		return 	ResponseEntity.ok().body(purchases);
	}
	
	@PostMapping
	public ResponseEntity<Purchase> savePurchase(@RequestBody Purchase purchase, HttpServletRequest request) {
		User user = jwtUtils.getUserByToken(request);
		
		if(user == null) {
			return null;
		}
		Purchase newPurchase = service.savePurchase(purchase, user);
		
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
	
	@PutMapping(value = "/items/{id}")
	public ResponseEntity<Purchase> saveItemIntoPurchase(@PathVariable Long id, @RequestBody List<String> items) {
		Purchase updatedPurchase = service.insertItems(id, items);
		
		if(updatedPurchase == null) ResponseEntity.notFound();
		
		return ResponseEntity.ok(updatedPurchase);
	}
	
	@DeleteMapping(value = "/items/{id}/{toRemove}")
	public ResponseEntity<MessageResponse> deleteItems(@PathVariable Long id, @PathVariable String toRemove, HttpServletRequest request) {
		User user = jwtUtils.getUserByToken(request);
		
		boolean wasDeleted = service.deleteItems(id, toRemove, user);
		
		if(wasDeleted) {
			return ResponseEntity.ok(new MessageResponse("Items deleted successfully!"));
		}
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("You can't delete that purchase. It's not yours"));
	}
	
}











