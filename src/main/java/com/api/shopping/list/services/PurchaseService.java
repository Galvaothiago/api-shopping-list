package com.api.shopping.list.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.shopping.list.exceptions.PurchaseItemNotExistsException;
import com.api.shopping.list.exceptions.PurchaseNotFoundException;
import com.api.shopping.list.exceptions.PurchaseUnmatchedUserException;
import com.api.shopping.list.model.auth.User;
import com.api.shopping.list.model.entities.EStatus;
import com.api.shopping.list.model.entities.Item;
import com.api.shopping.list.model.entities.Purchase;
import com.api.shopping.list.payload.request.PurchaseItem;
import com.api.shopping.list.payload.response.PurchaseItemResponse;
import com.api.shopping.list.repositories.ItemPurchaseRepository;
import com.api.shopping.list.repositories.PurchaseRepository;

@Service
public class PurchaseService {
	
	@Autowired
	PurchaseRepository repository;
	
	@Autowired
	ItemPurchaseRepository itemRepository;
	
	public List<Purchase> getAllPurchase(User user) {
		List<Purchase> purchases = repository.findAllPurchaseByUser(user.getId());
		
		return purchases;
	}
	
	public Purchase savePurchase(Purchase purchase, User user) {
		purchase.setUser(user);
		
		Purchase result = repository.save(purchase);
		return result;
	}
	
	public PurchaseItemResponse findById(Long id, User user) {
		Optional<Purchase> result = repository.findById(id);
		
		boolean isYourPurchase = user.equals(result.get().getUser());
		
		if(!isYourPurchase) {
			throw new PurchaseUnmatchedUserException("This item does not belong to your purchase");
		}
		
		if(result.isPresent() && isYourPurchase) {
			PurchaseItemResponse purchase = new PurchaseItemResponse();
			
			Purchase originalPurchase = result.get();
			purchase.setId(originalPurchase.getId());
			purchase.setTitle(originalPurchase.getTitle());
			purchase.setCreatedAt(originalPurchase.getCreatedAt());
			purchase.setTotalPrice(originalPurchase.getTotalPrice());
			purchase.setMarketName(originalPurchase.getMarketName());
			purchase.setStatus(originalPurchase.getStatus());
			purchase.setItems(originalPurchase.getItems());
			
			return purchase;
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
	public void finalizePurchase(Long id, Double value, User user, String marketName) {
		try {
			
			Optional<Purchase> purchaseOptional = repository.findById(id);
			Purchase purchase = purchaseOptional.get();
			
			Boolean isYourPurchase = user.getId() == purchase.getUser().getId();
			
			if(!isYourPurchase) {
				throw new PurchaseUnmatchedUserException("This item does not belong to your purchase");
			}
			
			purchase.setTotalPrice(value);
			purchase.setStatus(EStatus.FINISHED);
			
			if(marketName != null) purchase.setMarketName(marketName);
			
			repository.save(purchase);
			
		} catch(NoSuchElementException e) {
			throw new PurchaseNotFoundException(
					String.format("Purchase with the id %d not found", id)
				);
		}
	}
	
	public List<String> getItemsFromPurchase(Long id) {
		List<String> itemsPurchase = itemRepository.getItemsFromPurchase(id);
		
		return itemsPurchase;
	}
	
	public Purchase insertItems(Long id, PurchaseItem items, User user) {
		try {
			Optional<Purchase> purchase = repository.findById(id);
			Purchase entity = purchase.get();
			
			Boolean isYourPurchase = user.equals(entity.getUser()); 
			
			if(!isYourPurchase) {
				throw new PurchaseUnmatchedUserException("This item does not belong to your purchase");
			}
			
			List<Item> ListItems = new ArrayList<>();
			
			
			for(String item : items.getItems()) {
				ListItems.add(new Item(item, entity));
			}
		
			
			if(entity.getItems().isEmpty()) {
				entity.setItems(ListItems);
				
				return repository.save(entity);
			} 
			
			for(Item item : ListItems) {
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
