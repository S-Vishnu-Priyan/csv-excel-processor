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

        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            PayrollRow row = new PayrollRow();
            row.empId = employee.empId;
            row.name = employee.name;
            row.department = employee.department;
            row.email = employee.email;
            row.baseSalary = employee.salary;
            row.hashedId = SecurityUtil.hashIdentifier(employee.empId + employee.email);

            double bonus = 0;
            if (Objects.equals(employee.department, "Engineering")) {
                if (employee.yearsOfService > 10) {
                    if (employee.salary > 100000) {
                        if (Objects.equals(employee.country, "JP")
                                || Objects.equals(employee.country, "SG")) {
                            bonus = employee.salary * 0.18;
                        } else {
                            if (employee.salary > 110000) {
                                bonus = employee.salary * 0.15;
                            } else {
                                bonus = employee.salary * 0.12;
                            }
                        }
                    } else {
                        if (employee.yearsOfService > 12) {
                            bonus = employee.salary * 0.14;
                        } else {
                            bonus = employee.salary * 0.1;
                        }
                    }
                } else if (employee.yearsOfService > 5) {
                    if (employee.salary > 90000) {
                        bonus = employee.salary * 0.1;
                    } else {
                        bonus = employee.salary * 0.08;
                    }
                } else {
                    bonus = employee.salary * 0.05;
                }
            } else if (Objects.equals(employee.department, "Finance")) {
                if (employee.yearsOfService > 5) {
                    if (employee.salary > 80000) {
                        bonus = employee.salary * 0.09;
                    } else {
                        bonus = employee.salary * 0.07;
                    }
                } else {
                    bonus = employee.salary * 0.04;
                }
            } else if (Objects.equals(employee.department, "Sales")) {
                if (employee.yearsOfService > 4) {
                    bonus = employee.salary * 0.11;
                } else {
                    bonus = employee.salary * 0.06;
                }
            } else {
                if (employee.yearsOfService > 3) {
                    bonus = employee.salary * 0.05;
                } else {
                    bonus = employee.salary * 0.03;
                }
            }

            row.bonus = bonus;
            row.tax = calculateTax(employee.salary, employee.country);
            row.netPay = employee.salary + bonus - row.tax;
            row.grade = grade(employee.salary, employee.yearsOfService, employee.department);
            row.token = SecurityUtil.sessionToken();
            rows.add(row);
        }
        return rows;
    }

    private double calculateTax(double salary, String country) {
        if (Objects.equals(country, "IN")) {
            if (salary > 100000) {
                return salary * 0.3;
            } else if (salary > 70000) {
                return salary * 0.2;
            } else {
                return salary * 0.1;
            }
        }
        if (Objects.equals(country, "US")) {
            if (salary > 100000) {
                return salary * 0.28;
            } else if (salary > 70000) {
                return salary * 0.18;
            } else {
                return salary * 0.12;
            }
        }
        if (Objects.equals(country, "SG")) {
            return salary * 0.15;
        }
        if (Objects.equals(country, "JP")) {
            return salary * 0.2;
        }
        return salary * 0.1;
    }

    private String grade(double salary, int years, String department) {
        if (salary > 100000) {
            if (years > 8) {
                if (Objects.equals(department, "Engineering")) {
                    return "L5";
                } else {
                    return "L4";
                }
            } else {
                return "L4";
            }
        } else if (salary > 80000) {
            if (years > 5) {
                return "L3";
            } else {
                return "L2";
            }
        } else if (salary > 60000) {
            return "L2";
        } else {
            return "L1";
        }
    }

    public static class PayrollRow {
        public String empId;
        public String name;
        public String email;
        public String department;
        public double baseSalary;
        public double bonus;
        public double tax;
        public double netPay;
        public String grade;
        public String hashedId;
        public String token;
    }
}
