package com.taskmanager.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Utility class to generate Excel test data file
 */
public class ExcelGenerator {

    public static void main(String[] args) {
        String filePath = "src/test/resources/testdata/taskdata.xlsx";

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("TaskData");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"title", "description", "priority", "dueDate", "category", "status"};

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Add test data rows
            Object[][] data = {
                {"Review Code", "Review pull request #123 for new feature", "High", "2025-12-15", "Work", "Incomplete"},
                {"Team Meeting", "Weekly team sync meeting", "Medium", "2025-12-10", "Work", "Incomplete"},
                {"Buy Groceries", "Get milk eggs and bread", "Low", "2025-12-08", "Shopping", "Incomplete"},
                {"Doctor Appointment", "Annual checkup at 2pm", "High", "2025-12-20", "Health", "Incomplete"},
                {"Update Documentation", "Update README and API docs", "Medium", "2025-12-18", "Work", "Incomplete"},
                {"Gym Workout", "Cardio and strength training", "Low", "2025-12-09", "Health", "Complete"},
                {"Pay Bills", "Electricity and water bills", "High", "2025-12-12", "Personal", "Incomplete"},
                {"Read Book", "Finish reading Clean Code chapter 5", "Low", "2025-12-25", "Personal", "Incomplete"},
                {"Client Presentation", "Prepare Q4 presentation slides", "High", "2025-12-14", "Work", "Incomplete"},
                {"Home Maintenance", "Fix leaking faucet in bathroom", "Medium", "2025-12-16", "Personal", "Incomplete"},
                {"Code Refactoring", "Refactor authentication module", "Medium", "2025-12-22", "Work", "Incomplete"},
                {"Meal Prep", "Prepare meals for the week", "Low", "2025-12-11", "Personal", "Complete"},
                {"Submit Report", "Submit monthly project status report", "High", "2025-12-13", "Work", "Incomplete"},
                {"Call Parents", "Weekly check-in call", "Low", "2025-12-09", "Personal", "Complete"},
                {"Research Tools", "Research new testing frameworks", "Medium", "2025-12-19", "Work", "Incomplete"}
            };

            for (int i = 0; i < data.length; i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < data[i].length; j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(data[i][j].toString());
                }
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write to file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                System.out.println("Excel file created successfully at: " + filePath);
            }

        } catch (IOException e) {
            System.err.println("Error creating Excel file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
