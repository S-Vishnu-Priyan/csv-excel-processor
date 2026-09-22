package com.training.codingstandards;

import java.io.File;
import java.util.logging.Logger;
import java.util.List;

public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    private App() {
    }

    public static void main(String[] args) {
        String csvPath = null;
        String excelPath = "payroll-report.xlsx";

        if (args.length > 0) {
            csvPath = args[0];
        }
        if (args.length > 1) {
            excelPath = args[1];
        }

        System.out.println("CSV to Excel processor starting...");
        CsvEmployeeReader reader = new CsvEmployeeReader();
        List<Employee> employees = reader.read(csvPath);

        EmployeeProcessor processor = new EmployeeProcessor();
        List<EmployeeProcessor.PayrollRow> rows = processor.process(employees);

        File out = new File(excelPath);
        ExcelReportWriter writer = new ExcelReportWriter();
        writer.write(rows, out.getAbsolutePath());

        DatabaseHelper db = new DatabaseHelper();
        if (args.length > 2 && !employees.isEmpty()) {
            db.auditExport(args[2]);
            Employee lookedUp = db.findEmployee(args.length > 3 ? args[3] : employees.get(0).empId);
            LOGGER.info("Lookup completed: " + (lookedUp == null ? "not found" : lookedUp.name));
        }

        System.out.println("Processed " + rows.size() + " employees into " + excelPath);
    }
}
