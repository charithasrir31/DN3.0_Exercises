package com.example.EmployeeManagementSystem.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImplement implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // Replace with logic to fetch the current user
        return Optional.of("system");  // This is a placeholder
    }
}

