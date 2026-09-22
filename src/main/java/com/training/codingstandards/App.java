package com.training.codingstandards;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

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

        LOGGER.info("CSV to Excel processor starting");
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
            String employeeId = args.length > 3 ? args[3] : employees.get(0).getEmpId();
            Employee lookedUp = db.findEmployee(employeeId);
            LOGGER.info(() -> String.format("Lookup completed: %s",
                    lookedUp == null ? "not found" : lookedUp.getName()));
        }

        String reportPath = excelPath;
        LOGGER.info(() -> String.format("Processed %d employees into %s", rows.size(), reportPath));
    }
}
