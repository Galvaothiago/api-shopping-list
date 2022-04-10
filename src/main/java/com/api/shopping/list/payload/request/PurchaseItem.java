package com.api.shopping.list.payload.request;

import java.util.HashSet;
import java.util.Set;

public class PurchaseItem {
	private Set<String> items = new HashSet<>();
	
	public PurchaseItem() {
		
	}
	
	public PurchaseItem(Set<String> items) {
		this.items = items;
	}

	public Set<String> getItems() {
		return items;
	}

	public void setItems(Set<String> items) {
		this.items = items;
	}
	
}
