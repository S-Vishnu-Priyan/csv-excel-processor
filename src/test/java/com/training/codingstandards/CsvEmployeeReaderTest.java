package com.training.codingstandards;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CsvEmployeeReaderTest {

    @Test
    void readsBundledEmployeeCsv() {
        CsvEmployeeReader reader = new CsvEmployeeReader();
        List<Employee> employees = reader.read(null);

        assertFalse(employees.isEmpty());
        assertEquals(8, employees.size());
        assertEquals("1001", employees.get(0).getEmpId());
    }
}
