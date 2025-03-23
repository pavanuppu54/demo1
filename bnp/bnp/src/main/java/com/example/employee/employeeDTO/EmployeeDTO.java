package com.example.employee.employeeDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class EmployeeDTO {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Department cannot be empty")
    private String department;

    @Min(value = 1, message = "Salary must be positive")
    private double salary;

    public EmployeeDTO() {}

    public EmployeeDTO(String name, String department, double salary) {
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
