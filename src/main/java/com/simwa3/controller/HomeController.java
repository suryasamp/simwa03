package com.simwa3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.simwa3.model.WargaModel;
import com.simwa3.repository.WargaRepository;


@Controller
@RequestMapping("/")
public class HomeController {

	@GetMapping
	public String index() {
		return "index"; // file: src/main/resources/templates/index.html
	}
	
}
