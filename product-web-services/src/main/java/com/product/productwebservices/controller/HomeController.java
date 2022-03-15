package com.product.productwebservices.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@Value("${product.services.greetings}")
	private String greetings;
	
	@GetMapping({"/",""})
	public String greetings()
	{
		return greetings;
	}
}
