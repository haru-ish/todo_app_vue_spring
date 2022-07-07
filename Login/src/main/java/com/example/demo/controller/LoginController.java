package com.example.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:8082")
@RestController
public class LoginController {
		
	@GetMapping("/api/checkLoginStatus")
	public String checkLoginStatus() {
		return "Authentication has been successful.";
	}
	
}
