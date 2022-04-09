package com.api.shopping.list.security.jwt;

import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.api.shopping.list.model.auth.User;
import com.api.shopping.list.repositories.UserRepository;
import com.api.shopping.list.security.exceptions.TokenException;
import com.api.shopping.list.security.services.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	
	@Value("${purchase.app.jwtSecret}")
	private String jwtSecret;

	@Value("${purchase.app.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	@Autowired
	UserRepository userRepository;
	
	public String generateJwtToken(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		
		return Jwts.builder()
				.setSubject(userPrincipal.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public String generateTokenFromUsername(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
		        	.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
		        	.compact();
	}
	
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
			
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: " + e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Ivalid JWT token: " + e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: " + e.getMessage());
			throw new TokenException("token is expired");
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: " + e.getMessage());
		} catch(IllegalArgumentException e) {
			logger.error("JWT claims string is empty: " + e.getMessage());
		}
		
		return false;
	}
	
	public User getUserByToken(HttpServletRequest request) {
		  String[] headerAuth = request.getHeader("Authorization").split(" ");
		  String token = headerAuth[1];
		  
		  var email = this.getUserNameFromJwtToken(token);
		  
		  Optional<User> user = userRepository.findByEmail(email);
		  
		  if(!user.isPresent()) {
			  return null;
		  }
		  
		  return user.get();
	}

}
