package com.training.codingstandards;

import java.util.Date;
import java.util.Objects;

/**
 * Employee record loaded from CSV.
 */
public class Employee {

    private String empId;
    private String name;
    private String email;
    private String department;
    private double salary;
    private int yearsOfService;
    private String country;
    private String managerEmail;
    private Date lastProcessed;

    public Employee() {
    }

    public Employee(String empId, String name, String email, String department, double salary,
                    int yearsOfService, String country) {
        this.empId = empId;
        this.name = name;
        this.email = email;
        this.department = department;
        this.salary = salary;
        this.yearsOfService = yearsOfService;
        this.country = country;
        this.lastProcessed = new Date();
    }

    public String getEmpId() { return empId; }
    public void setEmpId(String empId) { this.empId = empId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    public int getYearsOfService() { return yearsOfService; }
    public void setYearsOfService(int yearsOfService) { this.yearsOfService = yearsOfService; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getManagerEmail() { return managerEmail; }
    public void setManagerEmail(String managerEmail) { this.managerEmail = managerEmail; }
    public Date getLastProcessed() { return lastProcessed; }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) obj;
        return Objects.equals(empId, other.empId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empId);
    }
}
