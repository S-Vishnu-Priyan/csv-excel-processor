package com.training.codingstandards;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeeProcessor {

    public List<PayrollRow> process(List<Employee> employees) {
        List<PayrollRow> rows = new ArrayList<>();
        if (employees == null) {
            return rows;
        }
        for (Employee employee : employees) {
            rows.add(createPayrollRow(employee));
        }
        return rows;
    }

    private PayrollRow createPayrollRow(Employee employee) {
        double bonus = calculateBonus(employee);
        double tax = calculateTax(employee.getSalary(), employee.getCountry());
        return new PayrollRow(employee, bonus, tax, grade(employee.getSalary(),
                employee.getYearsOfService(), employee.getDepartment()));
    }

    private double calculateBonus(Employee employee) {
        String department = employee.getDepartment();
        if (Objects.equals(department, "Engineering")) {
            return engineeringBonus(employee);
        }
        if (Objects.equals(department, "Finance")) {
            return financeBonus(employee);
        }
        if (Objects.equals(department, "Sales")) {
            return employee.getSalary() * (employee.getYearsOfService() > 4 ? 0.11 : 0.06);
        }
        return employee.getSalary() * (employee.getYearsOfService() > 3 ? 0.05 : 0.03);
    }

    private double engineeringBonus(Employee employee) {
        double salary = employee.getSalary();
        int years = employee.getYearsOfService();
        if (years <= 5) {
            return salary * 0.05;
        }
        if (years <= 10) {
            return salary * (salary > 90000 ? 0.10 : 0.08);
        }
        if (salary <= 100000) {
            return salary * (years > 12 ? 0.14 : 0.10);
        }
        if (Objects.equals(employee.getCountry(), "JP")
                || Objects.equals(employee.getCountry(), "SG")) {
            return salary * 0.18;
        }
        return salary * (salary > 110000 ? 0.15 : 0.12);
    }

    private double financeBonus(Employee employee) {
        double salary = employee.getSalary();
        if (employee.getYearsOfService() <= 5) {
            return salary * 0.04;
        }
        return salary * (salary > 80000 ? 0.09 : 0.07);
    }

    private double calculateTax(double salary, String country) {
        if (Objects.equals(country, "IN")) {
            return salary * indiaTaxRate(salary);
        }
        if (Objects.equals(country, "US")) {
            return salary * usTaxRate(salary);
        }
        if (Objects.equals(country, "SG")) {
            return salary * 0.15;
        }
        if (Objects.equals(country, "JP")) {
            return salary * 0.20;
        }
        return salary * 0.10;
    }

    private double indiaTaxRate(double salary) {
        if (salary > 100000) {
            return 0.30;
        }
        return salary > 70000 ? 0.20 : 0.10;
    }

    private double usTaxRate(double salary) {
        if (salary > 100000) {
            return 0.28;
        }
        return salary > 70000 ? 0.18 : 0.12;
    }

    private String grade(double salary, int years, String department) {
        if (salary > 100000) {
            return years > 8 && Objects.equals(department, "Engineering") ? "L5" : "L4";
        }
        if (salary > 80000) {
            return years > 5 ? "L3" : "L2";
        }
        return salary > 60000 ? "L2" : "L1";
    }

    public static class PayrollRow {
        private final String empId;
        private final String name;
        private final String email;
        private final String department;
        private final double baseSalary;
        private final double bonus;
        private final double tax;
        private final double netPay;
        private final String grade;
        private final String hashedId;
        private final String token;

        PayrollRow(Employee employee, double bonus, double tax, String grade) {
            this.empId = employee.getEmpId();
            this.name = employee.getName();
            this.email = employee.getEmail();
            this.department = employee.getDepartment();
            this.baseSalary = employee.getSalary();
            this.bonus = bonus;
            this.tax = tax;
            this.netPay = employee.getSalary() + bonus - tax;
            this.grade = grade;
            this.hashedId = SecurityUtil.hashIdentifier(employee.getEmpId() + employee.getEmail());
            this.token = SecurityUtil.sessionToken();
        }

        public String getEmpId() { return empId; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getDepartment() { return department; }
        public double getBaseSalary() { return baseSalary; }
        public double getBonus() { return bonus; }
        public double getTax() { return tax; }
        public double getNetPay() { return netPay; }
        public String getGrade() { return grade; }
        public String getHashedId() { return hashedId; }
        public String getToken() { return token; }
    }
}
