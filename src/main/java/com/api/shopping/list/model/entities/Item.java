package com.api.shopping.list.model.entities;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Embeddable
public class Item {	
	@NotBlank
	@Size(max = 255)
	private String name;
}
