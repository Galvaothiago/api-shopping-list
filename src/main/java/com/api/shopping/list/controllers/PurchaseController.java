package com.api.shopping.list.controllers;

import java.time.Instant;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.shopping.list.exceptions.PurchaseItemNotExistsException;
import com.api.shopping.list.exceptions.PurchaseNotFoundException;
import com.api.shopping.list.exceptions.PurchaseUnmatchedUserException;
import com.api.shopping.list.model.auth.User;
import com.api.shopping.list.model.entities.Purchase;
import com.api.shopping.list.payload.request.MessageResponse;
import com.api.shopping.list.payload.response.exception.PurchaseErrorMessage;
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
	public ResponseEntity<Purchase> saveItemIntoPurchase(@Valid @PathVariable Long id, @RequestBody List<String> items, HttpServletRequest request) {
		User user = jwtUtils.getUserByToken(request);
		
		Purchase updatedPurchase = service.insertItems(id, items, user);
		
		
		if(updatedPurchase == null) ResponseEntity.notFound();
		
		return ResponseEntity.ok(updatedPurchase);
	}
	
	@PutMapping(value = "/checkout/{id}/{value}")
	public ResponseEntity<?> finalizePurchase(@PathVariable Long id, @PathVariable Double value, HttpServletRequest request) {
		User user = jwtUtils.getUserByToken(request);
		
		service.finalizePurchase(id, value, user);
		
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
	
	@ExceptionHandler(PurchaseNotFoundException.class)
	public ResponseEntity<?> PurchaseNotFoundException(PurchaseNotFoundException e) {
		PurchaseErrorMessage errorMessage = new PurchaseErrorMessage();
		
		errorMessage.setTimestamp(Instant.now());
		errorMessage.setMessage(e.getReason());
		errorMessage.setStatus(e.getStatus().value());
		errorMessage.setError(e.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}
	
	@ExceptionHandler(PurchaseItemNotExistsException.class)
	public ResponseEntity<?> PurchaseItemNotExistsException(PurchaseItemNotExistsException e) {
		PurchaseErrorMessage errorMessage = new PurchaseErrorMessage();
		
		errorMessage.setTimestamp(Instant.now());
		errorMessage.setMessage(e.getReason());
		errorMessage.setStatus(e.getStatus().value());
		errorMessage.setError(e.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
	}
	
	@ExceptionHandler(PurchaseUnmatchedUserException.class)
	public ResponseEntity<?> PurchaseUnmatchedUserException(PurchaseUnmatchedUserException e) {
		PurchaseErrorMessage errorMessage = new PurchaseErrorMessage();
		
		errorMessage.setTimestamp(Instant.now());
		errorMessage.setMessage(e.getReason());
		errorMessage.setStatus(e.getStatus().value());
		errorMessage.setError(e.getMessage());
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
	}
	
}











