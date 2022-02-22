package com.api.shopping.list.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.api.shopping.list.exceptions.PurchaseItemNotExistsException;
import com.api.shopping.list.exceptions.PurchaseNotFoundException;
import com.api.shopping.list.exceptions.PurchaseUnmatchedUserException;
import com.api.shopping.list.model.auth.User;
import com.api.shopping.list.model.entities.EStatus;
import com.api.shopping.list.model.entities.Purchase;
import com.api.shopping.list.repositories.PurchaseRepository;

@Service
public class PurchaseService {
	
	@Autowired
	PurchaseRepository repository;
	
	public List<Purchase> getAllPurchase(User user) {
		List<Purchase> purchases = repository.findAllPurchaseByUser(user.getId());
		
		return purchases;
	}
	
	public Purchase savePurchase(Purchase purchase, User user) {
		purchase.setUser(user);
		
		Purchase result = repository.save(purchase);
		return result;
	}
	
	public Purchase findById(Long id, User user) {
		Optional<Purchase> result = repository.findById(id);
		
		boolean isYourPurchase = user.equals(result.get().getUser());
		
		if(result.isPresent() && isYourPurchase) {
			return result.get();
		}
		
		return null;
	}
	
	public Purchase updateById(Long id, Purchase purchase, User user) {
		Optional<Purchase> result = repository.findById(id);
		
		boolean isYourPurchase = user.equals(result.get().getUser());
		
		if(result.isPresent() && isYourPurchase) {
			update(result.get(), purchase);
			
			return result.get();
		}
		
		return null;
	}
	
	public void deleteItems(Long id, String toRemove, User user) {
		try {
			Optional<Purchase> purchase = repository.findById(id);
			
			Boolean isYourPurchase = user.getId() == purchase.get().getUser().getId();
			
			if(!isYourPurchase) {
				throw new PurchaseUnmatchedUserException("This item does not belong to your purchase");
			}
			
			List<String> items = purchase.get().getItems();
			
			int index = items.indexOf(toRemove);
			items.remove(index);
			
			System.out.println(items);
			repository.save(purchase.get());
			
		} catch(IndexOutOfBoundsException e) {
			throw new PurchaseItemNotExistsException(
						String.format("item - %s not found in this purchase", toRemove)
					);
		} catch(NoSuchElementException e) {
			throw new PurchaseNotFoundException(
						String.format("Purchase with the id %d not found", id)
					); 
		}
 	}
	
	public void update(Purchase entity, Purchase purchase) {
		entity.setTitle(purchase.getTitle());
		entity.setTotalPrice(purchase.getTotalPrice());
	}
	
	@Transactional
	public void finalizePurchase(Long id, Double value, User user) {
		try {
			
			Optional<Purchase> purchaseOptional = repository.findById(id);
			Purchase purchase = purchaseOptional.get();
			
			Boolean isYourPurchase = user.getId() == purchase.getUser().getId();
			
			if(!isYourPurchase) {
				throw new PurchaseUnmatchedUserException("This item does not belong to your purchase");
			}
			
			purchase.setTotalPrice(value);
			purchase.setStatus(EStatus.FINISHED);
			
			repository.save(purchase);
			
		} catch(NoSuchElementException e) {
			throw new PurchaseNotFoundException(
					String.format("Purchase with the id %d not found", id)
				);
		}
	}
	
	public Purchase insertItems(Long id, List<String> items, User user) {
		try {
			Optional<Purchase> purchase = repository.findById(id);
			Purchase entity = purchase.get();
			
			Boolean isYourPurchase = user.equals(entity.getUser()); 
			
			if(!isYourPurchase) {
				throw new PurchaseUnmatchedUserException("This item does not belong to your purchase");
			}
			
			if(entity.getItems().isEmpty()) {
				entity.setItems(items);
				
				return repository.save(entity);
			} 
				
			for(String item : items) {
				entity.getItems().add(item);
			}
				
			return repository.save(entity);
				
		}catch(PurchaseItemNotExistsException e) {
			throw new PurchaseNotFoundException(
					String.format("Purchase with the id %d not found", id)
				);
		}
	}
	
	public void deletePurchase(Long id, User user) {
		Optional<Purchase> purchase = repository.findById(id);
			
		if(purchase.isEmpty()) {
			throw new PurchaseNotFoundException(
					String.format("Purchase with the id %d not found", id)
					);
		}
		
		Boolean isYourPurchase = user.equals(purchase.get().getUser());
			
		if(isYourPurchase) {
			repository.deleteById(id);				
		} else {
			throw new PurchaseUnmatchedUserException("You can't delete this purchase");
		}
	}
}
