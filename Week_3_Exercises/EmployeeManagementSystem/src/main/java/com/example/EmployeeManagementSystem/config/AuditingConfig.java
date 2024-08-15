package com.example.EmployeeManagementSystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.EmployeeManagementSystem.repository")
@EnableJpaAuditing
public class AuditingConfig {
    // Other bean definitions if needed
}


