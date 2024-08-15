package com.example.EmployeeManagementSystem.service;

import com.example.EmployeeManagementSystem.model.Department;
import com.example.EmployeeManagementSystem.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {

        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {

        return departmentRepository.findById(id).orElse(null);
    }

    public List<Department> getDepartmentsByName(String name) {

        return departmentRepository.findByName(name);
    }

    public Department saveDepartment(Department department) {

        return departmentRepository.save(department);
    }

    public void deleteDepartment(Long id) {

        departmentRepository.deleteById(id);
    }
}
