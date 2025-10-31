package com.EmployeeApp.EmployeeApp.Controllers;

import com.EmployeeApp.EmployeeApp.Models.Registrations;
import com.EmployeeApp.EmployeeApp.Services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/home")
    public String homeRoute(Model model) {
        // Get statistics
        model.addAttribute("totalEmployees", registrationService.getTotalEmployees());
        model.addAttribute("totalDepartments", registrationService.getTotalDepartments());
        model.addAttribute("averageSalary", String.format("%.2f", registrationService.getAverageSalary()));
        model.addAttribute("newHires", registrationService.getNewHiresThisMonth());
        
        // Get recent employees
        List<Registrations> recentEmployees = registrationService.getRecentEmployees(5);
        model.addAttribute("recentEmployees", recentEmployees);
        
        return "index";
    }
}