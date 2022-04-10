package com.api.shopping.list.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.shopping.list.model.entities.Item;

public interface ItemPurchaseRepository extends JpaRepository<Item, Long>{
	
	@Query(value = "select name from items where items.purchase_id = :id", nativeQuery = true)
	List<String> getItemsFromPurchase(Long id);
}
