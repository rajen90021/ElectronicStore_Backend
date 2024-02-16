package com.rajen.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class homecontroller {

	
	
	
	@GetMapping
	public String data() {
		
		return "helloddddddddddddd";
	}
}
