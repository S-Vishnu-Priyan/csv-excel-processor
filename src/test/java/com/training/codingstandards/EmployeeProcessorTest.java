package com.training.codingstandards;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EmployeeProcessorTest {

    @Test
    void processCreatesPayrollRowForEachEmployee() {
        Employee employee = new Employee("1001", "Asha Raman", "asha.raman@example.com",
                "Engineering", 92000, 6, "IN");
        employee.setManagerEmail("lead.eng@example.com");

        EmployeeProcessor processor = new EmployeeProcessor();
        List<EmployeeProcessor.PayrollRow> rows = processor.process(Arrays.asList(employee));

        assertNotNull(rows);
        assertEquals(1, rows.size());
        assertEquals("1001", rows.get(0).getEmpId());
        assertEquals("Engineering", rows.get(0).getDepartment());
    }

    @Test
    void processUsesValueEqualityForDepartmentAndCountry() {
        Employee employee = new Employee("1002", "Ben Carter", "ben@example.com",
                new String("Finance"), 78000, 3, new String("US"));

        EmployeeProcessor.PayrollRow row = new EmployeeProcessor().process(Arrays.asList(employee)).get(0);

        assertEquals(3120, row.getBonus(), 0.001);
        assertEquals(14040, row.getTax(), 0.001);
    }

    @Test
    void processReturnsEmptyListForNullInput() {
        assertEquals(0, new EmployeeProcessor().process(null).size());
    }

    @Test
    void processCoversPayrollRules() {
        List<Employee> employees = Arrays.asList(
                employee("e1", "Engineering", 50000, 2, "IN"),
                employee("e2", "Engineering", 95000, 6, "IN"),
                employee("e3", "Engineering", 85000, 6, "IN"),
                employee("e4", "Engineering", 99000, 11, "IN"),
                employee("e5", "Engineering", 120000, 11, "JP"),
                employee("e6", "Engineering", 105000, 11, "IN"),
                employee("e7", "Engineering", 100000, 13, "IN"),
                employee("f1", "Finance", 70000, 6, "IN"),
                employee("f2", "Finance", 90000, 6, "IN"),
                employee("s1", "Sales", 70000, 5, "US"),
                employee("s2", "Sales", 70000, 2, "SG"),
                employee("h1", "HR", 70000, 4, "JP"),
                employee("h2", "HR", 70000, 2, "AE"),
                employee("t1", "Engineering", 120000, 2, "US"),
                employee("t2", "Finance", 70000, 2, "US"));

        List<EmployeeProcessor.PayrollRow> rows = new EmployeeProcessor().process(employees);

        assertEquals(15, rows.size());
        assertEquals("L5", rows.get(4).getGrade());
        assertEquals("L2", rows.get(12).getGrade());
    }

    @Test
    void employeeEqualityUsesEmployeeId() {
        Employee first = employee("same", "HR", 50000, 1, "IN");
        Employee second = employee("same", "Sales", 90000, 5, "US");

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        assertEquals(first, first);
    }

    private Employee employee(String id, String department, double salary, int years, String country) {
        return new Employee(id, "Test", id + "@example.com", department, salary, years, country);
    }
}
