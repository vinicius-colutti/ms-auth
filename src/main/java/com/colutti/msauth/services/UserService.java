package com.colutti.msauth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.colutti.msauth.model.User;
import com.colutti.msauth.repository.UserRepository;
import com.colutti.msauth.security.AccountCredentialsVO;
import com.colutti.msauth.security.jwt.JwtTokenProvider;
import com.colutti.msauth.vo.AuthUserVO;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	UserRepository repository;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtTokenProvider tokenProvider;

	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	public AuthUserVO authUser(AccountCredentialsVO data) {
		try {
			AuthUserVO authUser = new AuthUserVO();
			String username = data.getUsername();
			String password = data.getPassword();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			User user = repository.findByUserName(username);

			String token = "";
			if (user != null) {
				token = tokenProvider.createToken(username, user.getRoles());
			} else {
				throw new UsernameNotFoundException("Username " + username + " not found");
			}

			authUser.setUsername(username);
			authUser.setToken(token);
			return authUser;

		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByUserName(username);
		if (user != null) {
			return user;
		} else {
			throw new UsernameNotFoundException("Username " + username + " not found.");
		}
	}

}
