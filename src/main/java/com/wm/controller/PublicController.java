package com.wm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wm.dto.ClientDto;
import com.wm.service.ClientService;

import jakarta.validation.Valid;

@RestController
public class PublicController {

	@Autowired
	private ClientService clientService;

	@GetMapping("/welcome")
	public ResponseEntity<String> welcomeHandeler() {
		String html = "<html><head><title>Welcome</title></head><body>"
				+ "<h1>Welcome to Insurance Management System</h1>"
				+ "<p><a href=\"/signup\">Sign Up</a> | <a href=\"/login\">Log In</a></p>"
				+ "</body></html>";
		return ResponseEntity.ok().header("Content-Type", "text/html; charset=UTF-8").body(html);
	}


	@PostMapping("/signup")
	public ResponseEntity<Void> signupClient(@Valid @RequestBody ClientDto client){
		clientService.addClient(client);
		// Redirect to /login with a query param that frontend can use to show a small bubble
		return ResponseEntity.status(302).header("Location", "/login?signup=success").build();
	}

}
