package com.api.shopping.list.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.shopping.list.exceptions.TokenException;
import com.api.shopping.list.model.auth.ERole;
import com.api.shopping.list.model.auth.RefreshToken;
import com.api.shopping.list.model.auth.Role;
import com.api.shopping.list.model.auth.User;
import com.api.shopping.list.payload.request.LoginRequest;
import com.api.shopping.list.payload.request.SignupRequest;
import com.api.shopping.list.payload.request.TokenRefreshRequest;
import com.api.shopping.list.payload.response.JwtResponse;
import com.api.shopping.list.payload.response.MessageResponse;
import com.api.shopping.list.payload.response.exception.InvalidPasswordPolicyResponse;
import com.api.shopping.list.payload.response.exception.TokenExceptionResponse;
import com.api.shopping.list.repositories.RoleRepository;
import com.api.shopping.list.repositories.UserRepository;
import com.api.shopping.list.security.exceptions.TokenRefreshException;
import com.api.shopping.list.security.exceptions.ValidatePolicyPasswordException;
import com.api.shopping.list.security.jwt.JwtUtils;
import com.api.shopping.list.security.services.RefreshTokenService;
import com.api.shopping.list.security.services.UserDetailsImpl;
import com.api.shopping.list.security.services.ValidatePolicyPassword;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	RefreshTokenService refreshTokenService;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
		
		return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), 
																userDetails.getId(),
																userDetails.getFirstName(),
																userDetails.getLastName(),
																userDetails.getEmail(),
																roles));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		var validatePassword = new ValidatePolicyPassword(signUpRequest.getPassword());
		
		
		boolean isValid = validatePassword.validate();
		
		if(!isValid) {
			validatePassword.brokenRulesMessagens();
			throw new ValidatePolicyPasswordException("Your password does not comply with the security policies",
						validatePassword.getListRulesViolated()
					);
		}
		
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Email is already in use!"));
		}

		
		// Create new user's account
		User user = new User(signUpRequest.getFirstName(),
								signUpRequest.getLastName(),
								signUpRequest.getEmail(), 
								encoder.encode(signUpRequest.getPassword()), null);

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
		String requestRefreshToken = request.getRefreshToken();

	    return refreshTokenService.findByToken(requestRefreshToken)
	        .map(refreshTokenService::verifyExpiration)
	        .map(RefreshToken::getUser)
	        .map(user -> {
	          String token = jwtUtils.generateTokenFromUsername(user.getEmail());
	          return ResponseEntity.ok(new TokenRefreshRequest(token, requestRefreshToken));
	        })
	        .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
	            "Refresh token is not in database!"));
	}
	
	@ExceptionHandler(ValidatePolicyPasswordException.class)
	public ResponseEntity<?> ValidatePolicyPasswordException(ValidatePolicyPasswordException e) {
		InvalidPasswordPolicyResponse errorMessage = new InvalidPasswordPolicyResponse();
		
		errorMessage.setMessage(e.getReason());
		errorMessage.setStatus(e.getStatus().value());
		errorMessage.setRulesBroken(e.getViolatedRules());
		errorMessage.setQuantityRulesBroken(e.getViolatedRules().size());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
	}
}
