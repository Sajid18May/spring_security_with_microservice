package com.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/auth/v1/admin")
public class AdminController {
	
	@GetMapping("/welcome")
	public String getMethodName() {
		return "welcome admin";
	}
	
}
