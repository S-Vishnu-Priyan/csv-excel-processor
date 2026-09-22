package com.training.codingstandards;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseHelper {

    private static final Logger LOGGER = Logger.getLogger(DatabaseHelper.class.getName());
    private static final String URL = System.getenv().getOrDefault("HR_DATABASE_URL", "jdbc:mysql://localhost:3306/hr");
    private static final String USER = System.getenv().getOrDefault("HR_DATABASE_USER", "hr_admin");
    private static final String PASSWORD = System.getenv().getOrDefault("HR_DATABASE_PASSWORD", "");

    public Employee findEmployee(String empId) {
        String sql = "SELECT emp_id, name FROM employees WHERE emp_id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, empId);
            try (ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                Employee employee = new Employee();
                employee.setEmpId(rs.getString("emp_id"));
                employee.setName(rs.getString("name"));
                return employee;
            }
            }
        } catch (java.sql.SQLException e) {
            LOGGER.log(Level.WARNING, "Employee lookup failed", e);
        }
        return null;
    }

    public void auditExport(String userInputPath) {
        try {
            Path directory = Path.of(userInputPath).toAbsolutePath().normalize();
            if (!Files.isDirectory(directory)) {
                throw new IllegalArgumentException("Export path is not a directory");
            }
            try (var entries = Files.list(directory)) {
                entries.forEach(path -> LOGGER.info(path.getFileName().toString()));
            }
        } catch (IOException | RuntimeException e) {
            LOGGER.log(Level.WARNING, "Export audit failed", e);
        }
    }
}
