package com.api.shopping.list.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.shopping.list.controllers.AuthController;
import com.api.shopping.list.model.auth.User;
import com.api.shopping.list.model.entities.Purchase;
import com.api.shopping.list.repositories.PurchaseRepository;

@Service
public class PurchaseService {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	PurchaseRepository repository;
	
	public List<Purchase> getAllPurchase(User user) {
		List<Purchase> purchases = repository.findAllPurchaseByUser(user.getId());
		
		System.out.println(user.getId());
		return purchases;
	}
	
	public Purchase savePurchase(Purchase purchase, User user) {
		purchase.setUser(user);
		
		Purchase result = repository.save(purchase);
		return result;
	}
	
	public Purchase findById(Long id) {
		Optional<Purchase> result = repository.findById(id);
		
		if(result.isPresent()) {
			return result.get();
		}
		
		return null;
	}
	
	public Purchase updateById(Long id, Purchase purchase) {
		Optional<Purchase> result = repository.findById(id);
		
		if(result.isPresent()) {
			update(result.get(), purchase);
			
			return result.get();
		}
		
		return null;
	}
	
	public boolean deleteItems(Long id, String toRemove, User user) {
		try {
			Optional<Purchase> purchase = repository.findById(id);
			
			Boolean isYourPurchase = user.getId() == purchase.get().getUser().getId();
			
			if(purchase.isEmpty()) {
				// throw error here
			}
			
			if(isYourPurchase) {
				List<String> items = purchase.get().getItems();
				
				int index = items.indexOf(toRemove);
				items.remove(index);
				
				System.out.println(items);
				repository.save(purchase.get());
				return true;
			}
			
			return false;
			
		} catch(IndexOutOfBoundsException e) {
			logger.error("");
			return false;
		}
 	}
	
	public void update(Purchase entity, Purchase purchase) {
		entity.setTitle(purchase.getTitle());
		entity.setTotalPrice(purchase.getTotalPrice());
	}
	
	public Purchase insertItems(Long id, List<String> items) {
		Optional<Purchase> purchase = repository.findById(id);
		
		if(!purchase.isPresent()) {
			return null;
		}
		
		Purchase entity = purchase.get();
		
		if(entity.getItems().isEmpty()) {
			entity.setItems(items);
			
			return repository.save(entity);
		}
		
		for(String item : items) {
			entity.getItems().add(item);
		}
		
		return repository.save(entity);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
