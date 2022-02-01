package com.api.shopping.list.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.shopping.list.model.entities.Purchase;
import com.api.shopping.list.repositories.PurchaseRepository;

@Service
public class PurchaseService {
	@Autowired
	PurchaseRepository repository;
	
	public List<Purchase> getAllPurchase() {
		List<Purchase> purchases = repository.findAll();
		
		return purchases;
	}
	
	public Purchase savePurchase(Purchase purchase) {
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
	
	public void update(Purchase entity, Purchase purchase) {
		entity.setTitle(purchase.getTitle());
		entity.setTotalPrice(purchase.getTotalPrice());
	}
	
	public Purchase updateItems(Long id, List<String> items) {
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
