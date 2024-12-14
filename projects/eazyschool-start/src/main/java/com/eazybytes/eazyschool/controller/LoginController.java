package com.eazybytes.eazyschool.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class LoginController {

	@GetMapping("/login")
	public String displayLoginPage(@RequestParam(value = "error", required = false) String error, Model model) {
		String errorMsg = null;
		
		if (error != null) {
			System.out.println(error);
			errorMsg = "Username or Password is incorrect!!!!";
		}
		
		model.addAttribute("errorMessage", errorMsg);
		
		return "login.html";
	}

}