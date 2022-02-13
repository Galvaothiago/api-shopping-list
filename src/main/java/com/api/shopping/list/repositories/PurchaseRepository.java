package com.api.shopping.list.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.shopping.list.model.entities.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>{
	@Query(value = "SELECT * FROM purchases WHERE users_id = :id", 
			nativeQuery = true)
	List<Purchase> findAllPurchaseByUser(Long id);
}
