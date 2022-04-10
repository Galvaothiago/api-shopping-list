package com.api.shopping.list.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.shopping.list.model.entities.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>{
	
	@Query("select p from Purchase p where p.user.id = :id")
	List<Purchase> findAllPurchaseByUser(Long id);	
}
