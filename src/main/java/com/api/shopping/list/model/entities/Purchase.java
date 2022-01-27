package com.api.shopping.list.model.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

import com.api.shopping.list.model.auth.User;

public class Purchase implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String title;
	private Instant createdAt;
	private Double totalPrice;
	private User user;
	private EStatus status;
	private List<String> items;
	
	public Purchase() {
		
	}
	
	public Purchase(Long id, String title, Instant createdAt, Double totalPrice, User user, List<String> items) {
		this.id = id;
		this.title = title;
		this.createdAt = createdAt;
		this.totalPrice = totalPrice;
		this.user = user;
		this.items = items;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Purchase other = (Purchase) obj;
		return Objects.equals(id, other.id);
	}
	
}
