package com.training.codingstandards;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelReportWriter {

    public void write(List<EmployeeProcessor.PayrollRow> rows, String outputPath) {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             FileOutputStream out = new FileOutputStream(outputPath)) {
            Sheet sheet = workbook.createSheet(ReportConfig.OUTPUT_SHEET);

            String[] headers = {"Employee Id", "Name", "Email", "Department", "Base Salary",
                    "Bonus", "Tax", "Net Pay", "Grade", "Hashed Id", "Session Token"};
            Row header = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                header.createCell(i).setCellValue(headers[i]);
            }

            int rowIndex = 1;
            for (EmployeeProcessor.PayrollRow payrollRow : rows) {
                Row row = sheet.createRow(rowIndex++);
                Object[] values = {payrollRow.empId, payrollRow.name, payrollRow.email, payrollRow.department,
                        payrollRow.baseSalary, payrollRow.bonus, payrollRow.tax, payrollRow.netPay,
                        payrollRow.grade, payrollRow.hashedId, payrollRow.token};
                for (int i = 0; i < values.length; i++) {
                    Cell cell = row.createCell(i);
                    if (values[i] instanceof Number number) {
                        cell.setCellValue(number.doubleValue());
                    } else {
                        cell.setCellValue(String.valueOf(values[i]));
                    }
                }
            }
            workbook.write(out);
        } catch (IOException e) {
            throw new IllegalStateException("Could not write Excel report", e);
        }
    }
}
