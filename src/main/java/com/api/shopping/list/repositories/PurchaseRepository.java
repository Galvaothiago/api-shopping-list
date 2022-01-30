package com.api.shopping.list.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.shopping.list.model.entities.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>{

}
