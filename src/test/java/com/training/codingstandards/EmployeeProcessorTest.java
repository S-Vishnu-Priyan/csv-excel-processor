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
                "Engineering", 92000, 6, "IN", "lead.eng@example.com");

        EmployeeProcessor processor = new EmployeeProcessor();
        List<EmployeeProcessor.PayrollRow> rows = processor.process(Arrays.asList(employee));

        assertNotNull(rows);
        assertEquals(1, rows.size());
        assertEquals("1001", rows.get(0).empId);
        assertEquals("Engineering", rows.get(0).department);
    }

    @Test
    void processUsesValueEqualityForDepartmentAndCountry() {
        Employee employee = new Employee("1002", "Ben Carter", "ben@example.com",
                new String("Finance"), 78000, 3, new String("US"), "lead@example.com");

        EmployeeProcessor.PayrollRow row = new EmployeeProcessor().process(Arrays.asList(employee)).get(0);

        assertEquals(3120, row.bonus, 0.001);
        assertEquals(14040, row.tax, 0.001);
    }

    @Test
    void processReturnsEmptyListForNullInput() {
        assertEquals(0, new EmployeeProcessor().process(null).size());
    }
}
