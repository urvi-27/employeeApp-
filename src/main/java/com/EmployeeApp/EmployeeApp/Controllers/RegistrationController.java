package com.EmployeeApp.EmployeeApp.Controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.EmployeeApp.EmployeeApp.Models.Registrations;  
import com.EmployeeApp.EmployeeApp.Models.RegistrationsDTO; 
import com.EmployeeApp.EmployeeApp.Repository.RegistrationRepository;

import jakarta.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private RegistrationRepository repo;
    
    @GetMapping("/employee_form")
    public String employeeFormRoute() {
        return "employee_form";
    }
    
    @GetMapping("/all_employees")
    public String allEmployeesRoute(Model model) {
        List <Registrations> employees = repo.findAll();
        model.addAttribute("employees", employees);
        return "AllEmployees";
    }
    
    @PostMapping("/new_employee")
    public String addEmployee(@Valid @ModelAttribute RegistrationsDTO regdto, RedirectAttributes redirectAttributes) {
        Registrations reg = new Registrations();
        reg.setEmployeeFullname(regdto.getEmployeeFullname());
        reg.setEmployeeGender(regdto.getEmployeeGender());
        reg.setEmployeeEmailAddress(regdto.getEmployeeEmailAddress());
        reg.setEmployeePhoneNumber(regdto.getEmployeePhoneNumber());
        reg.setDepartment(regdto.getDepartment());
        reg.setJobRole(regdto.getJobRole());
        reg.setSalary(regdto.getSalary());
        reg.setNextOfKinName(regdto.getNextOfKinName());
        reg.setNextOfKinPhoneNumber(regdto.getNextOfKinPhoneNumber());
        reg.setAddress(regdto.getAddress());
        repo.save(reg);
        redirectAttributes.addFlashAttribute("successMessage", "Employee Saved Successfully");
        return "redirect:/employee_form";
    }
    
    @GetMapping("/employees/{id}")
    public String viewEmployeeDetails(@PathVariable("id") Integer id, Model model) {
        Registrations reg = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Employee ID: " + id));
        model.addAttribute("employeeDetails", reg);
        return "Employee_Detail";
    }
    
    @PostMapping("/updateEmployeeInformation")
    public String updateEmployeeInformation(
            @RequestParam("id") Integer id,
            @RequestParam("Employee_Fullname") String employeeFullname,
            @RequestParam("Employee_Gender") String employeeGender,
            @RequestParam("Employee_Email_Address") String employeeEmailAddress,
            @RequestParam("Employee_Phone_Number") String employeePhoneNumber,
            @RequestParam("Department") String department,
            @RequestParam("Job_Role") String jobRole,
            @RequestParam("Salary") double salary,
            @RequestParam("Next_Of_Kin_Name") String nextOfKinName,
            @RequestParam("Next_Of_Kin_Phone_Number") String nextOfKinPhoneNumber,
            @RequestParam("Address") String address,
            RedirectAttributes redirectAttributes) {

        Registrations reg = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Employee ID: " + id));

        reg.setEmployeeFullname(employeeFullname);
        reg.setEmployeeGender(employeeGender);
        reg.setEmployeeEmailAddress(employeeEmailAddress);
        reg.setEmployeePhoneNumber(employeePhoneNumber);
        reg.setDepartment(department);
        reg.setJobRole(jobRole);
        reg.setSalary(salary);
        reg.setNextOfKinName(nextOfKinName);
        reg.setNextOfKinPhoneNumber(nextOfKinPhoneNumber);
        reg.setAddress(address);
        
        repo.save(reg);
        redirectAttributes.addFlashAttribute("successMessage", "Employee information updated successfully!");

        return "redirect:/employees/" + id;
    }
    
    @PostMapping("/employee/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            repo.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Employee deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting employee: " + e.getMessage());
        }
        return "redirect:/all_employees";
    }

}