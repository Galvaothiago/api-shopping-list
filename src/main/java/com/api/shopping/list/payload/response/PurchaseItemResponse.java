package com.api.shopping.list.payload.response;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.api.shopping.list.model.entities.EStatus;
import com.api.shopping.list.model.entities.Item;

public class PurchaseItemResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String title;
	private Instant createdAt;
	private Double totalPrice;
	private String marketName;
	private EStatus status;
	
	private List<Item> items = new ArrayList<>();
	
	public PurchaseItemResponse() {
		
	}

	public PurchaseItemResponse(Long id, String title, Instant createdAt, Double totalPrice, String marketName, EStatus status,
			List<Item> items) {
		this.id = id;
		this.title = title;
		this.createdAt = createdAt;
		this.totalPrice = totalPrice;
		this.marketName = marketName;
		this.status = status;
		this.items = items;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public EStatus getStatus() {
		return status;
	}

	public void setStatus(EStatus status) {
		this.status = status;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
}
