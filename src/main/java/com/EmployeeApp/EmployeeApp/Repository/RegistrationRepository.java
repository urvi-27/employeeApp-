package com.EmployeeApp.EmployeeApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.EmployeeApp.EmployeeApp.Models.Registrations;

public interface RegistrationRepository extends JpaRepository<Registrations, Integer> {
    
}