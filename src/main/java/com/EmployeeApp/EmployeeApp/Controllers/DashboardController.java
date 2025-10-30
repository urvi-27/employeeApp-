package com.EmployeeApp.EmployeeApp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

	@GetMapping("/home")
	public String HomeRoute() {
		return "index";
	}
	
	@GetMapping("/about_page")
	public String AboutRoute() {
		return "about";
	}
}
