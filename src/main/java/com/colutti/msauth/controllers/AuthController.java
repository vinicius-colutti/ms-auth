package com.colutti.msauth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.colutti.msauth.security.AccountCredentialsVO;
import com.colutti.msauth.services.UserService;
import com.colutti.msauth.vo.AuthUserVO;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UserService userService;

	@PostMapping("/signin")
	public AuthUserVO signin(@RequestBody AccountCredentialsVO data) {
		return userService.authUser(data);
	}

}
