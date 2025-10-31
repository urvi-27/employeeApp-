package com.EmployeeApp.EmployeeApp.Services;

import com.EmployeeApp.EmployeeApp.Models.Registrations;
import com.EmployeeApp.EmployeeApp.Repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    // Get total number of employees
    public long getTotalEmployees() {
        return registrationRepository.count();
    }

    // Get total number of distinct departments
    public long getTotalDepartments() {
        return registrationRepository.findAll().stream()
                .map(Registrations::getDepartment)
                .filter(dept -> dept != null && !dept.isEmpty())
                .distinct()
                .count();
    }

    // Calculate average salary
    public double getAverageSalary() {
        return registrationRepository.findAll().stream()
                .mapToDouble(Registrations::getSalary)
                .average()
                .orElse(0.0);
    }

    // Get number of new hires (simplified approach)
    public long getNewHiresThisMonth() {
        // Since we don't have a hire date field, we'll use the last 5 employees as "new hires"
        return Math.min(5, getTotalEmployees());
    }

    // Get recent employees (last 5 added)
    public List<Registrations> getRecentEmployees(int limit) {
        return registrationRepository.findAll().stream()
                .sorted(Comparator.comparingInt(Registrations::getId).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    // Find employee by ID
    public Optional<Registrations> getEmployeeById(int id) {
        return registrationRepository.findById(id);
    }

    // Save or update employee
    public Registrations saveEmployee(Registrations employee) {
        return registrationRepository.save(employee);
    }

    // Delete employee by ID
    public void deleteEmployee(int id) {
        registrationRepository.deleteById(id);
    }

    // Get all employees
    public List<Registrations> getAllEmployees() {
        return registrationRepository.findAll();
    }

    // Search employees by name, department, or job role
    public List<Registrations> searchEmployees(String query) {
        String lowerQuery = query.toLowerCase();
        return registrationRepository.findAll().stream()
                .filter(employee -> 
                    (employee.getEmployeeFullname() != null && 
                     employee.getEmployeeFullname().toLowerCase().contains(lowerQuery)) ||
                    (employee.getDepartment() != null && 
                     employee.getDepartment().toLowerCase().contains(lowerQuery)) ||
                    (employee.getJobRole() != null && 
                     employee.getJobRole().toLowerCase().contains(lowerQuery))
                )
                .collect(Collectors.toList());
    }

    // Get employees by department
    public List<Registrations> getEmployeesByDepartment(String department) {
        return registrationRepository.findAll().stream()
                .filter(employee -> department.equals(employee.getDepartment()))
                .collect(Collectors.toList());
    }

    // Get employees by gender
    public List<Registrations> getEmployeesByGender(String gender) {
        return registrationRepository.findAll().stream()
                .filter(employee -> gender.equals(employee.getEmployeeGender()))
                .collect(Collectors.toList());
    }

    // Get salary statistics
    public Map<String, Double> getSalaryStatistics() {
        DoubleSummaryStatistics stats = registrationRepository.findAll().stream()
                .mapToDouble(Registrations::getSalary)
                .summaryStatistics();
        
        Map<String, Double> salaryStats = new HashMap<>();
        salaryStats.put("min", stats.getMin());
        salaryStats.put("max", stats.getMax());
        salaryStats.put("avg", stats.getAverage());
        salaryStats.put("total", stats.getSum());
        
        return salaryStats;
    }

    // Get all departments
    public Set<String> getAllDepartments() {
        return registrationRepository.findAll().stream()
                .map(Registrations::getDepartment)
                .filter(dept -> dept != null && !dept.isEmpty())
                .collect(Collectors.toSet());
    }

    // Get all job roles
    public Set<String> getAllJobRoles() {
        return registrationRepository.findAll().stream()
                .map(Registrations::getJobRole)
                .filter(role -> role != null && !role.isEmpty())
                .collect(Collectors.toSet());
    }

    // Get employees by salary range
    public List<Registrations> getEmployeesBySalaryRange(double min, double max) {
        return registrationRepository.findAll().stream()
                .filter(employee -> employee.getSalary() >= min && employee.getSalary() <= max)
                .collect(Collectors.toList());
    }
}