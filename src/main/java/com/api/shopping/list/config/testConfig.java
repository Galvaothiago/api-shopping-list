package com.api.shopping.list.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.api.shopping.list.model.auth.User;
import com.api.shopping.list.model.entities.Purchase;
import com.api.shopping.list.repositories.PurchaseRepository;
import com.api.shopping.list.repositories.UserRepository;

@Configuration
public class testConfig implements CommandLineRunner{

	@Autowired
	PurchaseRepository repository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void run(String... args) throws Exception {
		User user = new User(null, "Thiago", "Galvao", "thiagosilva128@gmail.com", "123456");
		List<String> items = new ArrayList<>();
		
		items.add("Escova de dentes");
		items.add("Kit shampoo e condicionador");
		items.add("2kg de carne moida");
		items.add("8 paes frances");
		
		userRepository.save(user);
		Purchase purchase = new Purchase(null, "compra para 10 dias", user, items);
		
		repository.save(purchase);
	}

}
