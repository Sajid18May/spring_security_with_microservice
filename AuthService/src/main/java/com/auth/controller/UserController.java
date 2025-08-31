package com.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth.dto.APIResponse;
import com.auth.dto.LoginDto;
import com.auth.dto.UserDto;
import com.auth.entity.AppUser;
import com.auth.serviceimpl.JwtService;
import com.auth.serviceimpl.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth/v1")
public class UserController {
	@Autowired
	UserServiceImpl userServiceImpl;
	@Autowired
	JwtService jwtService;
	@Autowired
	AuthenticationManager authenticationManager;
	
	@PostMapping("/register")
	public ResponseEntity<APIResponse<String>> userRegister(@RequestBody UserDto dto) {
		APIResponse<String>response= userServiceImpl.userRegistration(dto);
		return new ResponseEntity<APIResponse<String>>(response, HttpStatusCode.valueOf(response.getStatus()));
	}
	
	@PostMapping("/login")
	public ResponseEntity<APIResponse<String>> userLogin(@RequestBody LoginDto dto) {
		APIResponse<String>response=new APIResponse<>();
		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(dto.getUsername(),dto.getPassword());
		Authentication authentication=authenticationManager.authenticate(authenticationToken);
		if(authentication.isAuthenticated()) {
			String token = jwtService.generateToken(dto.getUsername(), authentication.getAuthorities().iterator().next().getAuthority());
			response.setMessage("Login Successful");
			response.setStatus(200);
			response.setData(token);
			return new ResponseEntity<APIResponse<String>>(response, HttpStatusCode.valueOf(response.getStatus()));
		}
		response.setMessage("Login Failed");
		response.setStatus(401);
		response.setData("Wrong username or Password");
		return new ResponseEntity<APIResponse<String>>(response, HttpStatusCode.valueOf(response.getStatus()));
	}
	
	@GetMapping("/getUser")
	public AppUser getUserByUsername(@RequestParam String username) {
		return userServiceImpl.getUserByUsername(username);
	}
	
}
