package com.api.shopping.list.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

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
	
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public void run(String... args) throws Exception {
		User user = new User("Thiago", "Galvao", "thiagosilva128@gmail.com", encoder.encode("123456"), null);
		User user2 = new User("Marcos", "Pereira", "marcos123@hotmail.com",encoder.encode("123456"), null);
		List<String> items = new ArrayList<>();
		
		items.add("Escova de dentes");
		items.add("Kit shampoo e condicionador");
		items.add("2kg de carne moida");
		items.add("8 paes frances");
	
		
		userRepository.save(user);
		userRepository.save(user2);
		Purchase purchase = new Purchase(null, "compra para 10 dias", items);
		Purchase purchase2 = new Purchase(null, "compra final de semana", items);
		Purchase purchase3 = new Purchase(null, "compra para dois dias", items);
		Purchase purchase4 = new Purchase(null, "compra para 8 dias", items);
		
		purchase.setUser(user);
		purchase2.setUser(user2);
		purchase3.setUser(user2);
		purchase4.setUser(user);
		
		repository.saveAll(Arrays.asList(purchase, purchase2, purchase3, purchase4));
	}

}
