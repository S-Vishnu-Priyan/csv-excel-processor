package com.training.codingstandards;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvEmployeeReader {

    public List<Employee> read(String csvPath) {
        List<Employee> employees = new ArrayList<>();
        try (InputStream inputStream = openInputStream(csvPath);
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             CSVParser parser = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build().parse(reader)) {
            for (CSVRecord record : parser) {
                Employee employee = new Employee();
                employee.empId = record.get("empId");
                employee.name = record.get("name");
                employee.email = record.get("email");
                employee.department = record.get("department");
                employee.salary = Double.parseDouble(record.get("salary"));
                employee.yearsOfService = Integer.parseInt(record.get("yearsOfService"));
                employee.country = record.get("country");
                employee.managerEmail = record.get("managerEmail");
                employees.add(employee);
            }
        } catch (IOException | IllegalArgumentException e) {
            throw new IllegalStateException("Could not read employee CSV", e);
        }
        return employees;
    }

    private InputStream openInputStream(String csvPath) throws IOException {
        if (csvPath == null) {
            InputStream resource = CsvEmployeeReader.class.getResourceAsStream("/employees.csv");
            if (resource == null) {
                throw new IOException("Bundled employee CSV was not found");
            }
            return resource;
        }
        return java.nio.file.Files.newInputStream(java.nio.file.Path.of(csvPath));
    }
}
