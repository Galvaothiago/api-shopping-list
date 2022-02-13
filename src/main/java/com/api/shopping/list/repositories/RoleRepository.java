package com.api.shopping.list.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.shopping.list.model.auth.ERole;
import com.api.shopping.list.model.auth.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Optional<Role> findByName(ERole name);
}
