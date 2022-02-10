package com.api.shopping.list.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.api.shopping.list.model.auth.RefreshToken;
import com.api.shopping.list.model.auth.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
    @Override
    Optional<RefreshToken> findById(Long id);

    Optional<RefreshToken> findByToken(String token);
    
    @Modifying
    int deleteByUser(User user);
}
