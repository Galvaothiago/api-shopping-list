package com.api.shopping.list.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.api.shopping.list.payload.request.PurchaseItem;
import com.api.shopping.list.payload.response.MessageResponse;
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
		
		return 	ResponseEntity.ok().body(purchases);
	}
	
	@PostMapping
	public ResponseEntity<Purchase> savePurchase(@Valid @RequestBody Purchase purchase, HttpServletRequest request) {
		User user = jwtUtils.getUserByToken(request);
		
		if(user == null) {
			return null;
		}
		Purchase newPurchase = service.savePurchase(purchase, user);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newPurchase);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Purchase> getById(@PathVariable Long id, HttpServletRequest request) {
		User user = jwtUtils.getUserByToken(request);
		
		Purchase purchase = service.findById(id, user);
		
		if(purchase == null) ResponseEntity.notFound();
		
		System.out.println(purchase);
		return ResponseEntity.ok().body(purchase);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Purchase> updatePurchase(@Valid @PathVariable Long id, @RequestBody Purchase purchase, HttpServletRequest request) {
		User user = jwtUtils.getUserByToken(request);
		
		Purchase updatedPurchase = service.updateById(id, purchase, user);
		
		return ResponseEntity.ok().body(updatedPurchase);
	}
	
	@PutMapping(value = "/items/{id}")
	public ResponseEntity<Purchase> saveItemIntoPurchase(@Valid @PathVariable Long id, @RequestBody PurchaseItem purchaseItems, HttpServletRequest request) {
		User user = jwtUtils.getUserByToken(request);
		Purchase updatedPurchase = service.insertItems(id, purchaseItems, user);			
		
		if(updatedPurchase == null) ResponseEntity.notFound();
		
		return ResponseEntity.ok(updatedPurchase);
	}
	
	@PutMapping(value = "/checkout/{id}/{value}/{marketName}")
	public ResponseEntity<?> finalizePurchase(@PathVariable Long id, @PathVariable Double value, @PathVariable(required = false) String marketName, HttpServletRequest request) {
		User user = jwtUtils.getUserByToken(request);
		
		service.finalizePurchase(id, value, user, marketName);
		
		return ResponseEntity.ok().body(
					new MessageResponse("Your purchase was finalized successfully!")
				);
	}
	
	@DeleteMapping(value = "/items/{id}/{toRemove}")
	public ResponseEntity<MessageResponse> deleteItems(@Valid @PathVariable Long id, @PathVariable String toRemove, HttpServletRequest request) {
		User user = jwtUtils.getUserByToken(request);
		
		service.deleteItems(id, toRemove, user);
		
		return ResponseEntity.ok(new MessageResponse("Items deleted successfully!"));
		
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deletePurchase(@PathVariable Long id, HttpServletRequest request) {
		User user = jwtUtils.getUserByToken(request);
		service.deletePurchase(id, user);
		
		return ResponseEntity.ok().body(new MessageResponse("your purchase was deleted!"));
	}
}











