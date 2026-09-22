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
            for (CSVRecord csvRecord : parser) {
                Employee recordEmployee = new Employee();
                recordEmployee.setEmpId(csvRecord.get("empId"));
                recordEmployee.setName(csvRecord.get("name"));
                recordEmployee.setEmail(csvRecord.get("email"));
                recordEmployee.setDepartment(csvRecord.get("department"));
                recordEmployee.setSalary(Double.parseDouble(csvRecord.get("salary")));
                recordEmployee.setYearsOfService(Integer.parseInt(csvRecord.get("yearsOfService")));
                recordEmployee.setCountry(csvRecord.get("country"));
                recordEmployee.setManagerEmail(csvRecord.get("managerEmail"));
                employees.add(recordEmployee);
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
