package com.api.shopping.list.model.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

import com.api.shopping.list.model.auth.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "purchases")
public class Purchase implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 100)
	private String title;
	
	@CreationTimestamp
	private Instant createdAt;
	
	@PositiveOrZero
	private Double totalPrice = 0.00D;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "users_id", nullable = false)
	private User user;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 25)
	private EStatus status = EStatus.CREATED;

	@Valid
	@ElementCollection
	@CollectionTable(name = "purchases_items", joinColumns = @JoinColumn(name = "purchase_id"))
	private List<String> items = new ArrayList<>();
	
	public Purchase() {
		
	}
	
	public Purchase(Long id, String title, List<String> items) {
		this.id = id;
		this.title = title;
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
	
	public EStatus getStatus() {
		return status;
	}
	
	public void setStatus(EStatus status) {
		this.status = status;
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
