package com.example.employee.service;

import com.example.employee.employeeDTO.EmployeeDTO;
import com.example.employee.entity.Employee;
import com.example.employee.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Create Employee
    public Employee saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setDepartment(employeeDTO.getDepartment());
        employee.setSalary(employeeDTO.getSalary());
        return employeeRepository.save(employee);
    }

    // Get All Employees
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(emp -> new EmployeeDTO(emp.getName(), emp.getDepartment(), emp.getSalary()))
                .collect(Collectors.toList());
    }

    // Get Employee by ID
    public EmployeeDTO getEmployeeById(Long id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
        return new EmployeeDTO(emp.getName(), emp.getDepartment(), emp.getSalary());
    }

    // Update Employee
    public Employee updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
        emp.setName(employeeDTO.getName());
        emp.setDepartment(employeeDTO.getDepartment());
        emp.setSalary(employeeDTO.getSalary());
        return employeeRepository.save(emp);
    }

    // Partial Update Employee
    public Employee updateEmployeePartially(Long id, Map<String, Object> updates) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    emp.setName((String) value);
                    break;
                case "department":
                    emp.setDepartment((String) value);
                    break;
                case "salary":
                    emp.setSalary(Double.parseDouble(value.toString()));
                    break;
                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid field: " + key);
            }
        });

        return employeeRepository.save(emp);
    }

    // Delete Employee
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        }
        employeeRepository.deleteById(id);
    }
}
