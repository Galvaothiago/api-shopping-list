package com.api.shopping.list.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.shopping.list.model.entities.Purchase;
import com.api.shopping.list.repositories.PurchaseRepository;

@Service
public class PurchaseService {
	@Autowired
	PurchaseRepository repository;
	
	public Purchase save(Purchase purchase) {
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
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
