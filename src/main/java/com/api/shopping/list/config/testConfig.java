package com.api.shopping.list.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.api.shopping.list.model.auth.ERole;
import com.api.shopping.list.model.auth.Role;
import com.api.shopping.list.model.auth.User;
import com.api.shopping.list.model.entities.Item;
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
		User user = new User("Thiago", "Galvao", "thiagosilva128@gmail.com", encoder.encode("12345678"), null);
		User user2 = new User("Marcos", "Pereira", "marcos123@hotmail.com",encoder.encode("12345678"), null);
		List<Item> items = new ArrayList<>();
		
		Set<Role> roles = new HashSet<>();
		roles.add(new Role(ERole.ROLE_ADMIN));
		roles.add(new Role(ERole.ROLE_USER));

		
		userRepository.save(user);
		userRepository.save(user2);
		
		
		Purchase purchase = new Purchase(null, "compra para 10 dias");
		Purchase purchase2 = new Purchase(null, "compra final de semana");
		Purchase purchase3 = new Purchase(null, "compra para dois dias");
		Purchase purchase4 = new Purchase(null, "compra para 8 dias");
		
		items.add(new Item("Escova de dentes", purchase));
		items.add(new Item("Kit shampoo e condicionador", purchase));
		items.add(new Item("Cerveja Corona", purchase));
		items.add(new Item("Presunto e queijo", purchase));
		
		
		purchase.setUser(user);
		purchase2.setUser(user2);
		purchase3.setUser(user2);
		purchase4.setUser(user);
		
		purchase.setItems(items);
		
		repository.saveAll(Arrays.asList(purchase, purchase2, purchase3, purchase4));
	}

}
