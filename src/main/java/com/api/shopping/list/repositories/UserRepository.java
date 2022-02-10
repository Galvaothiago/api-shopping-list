package com.api.shopping.list.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.shopping.list.model.auth.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
	
	Boolean existsByEmail(String email);
}
