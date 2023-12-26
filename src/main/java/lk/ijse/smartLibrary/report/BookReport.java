package lk.ijse.smartLibrary.report;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class BookReport {
    public void generateReport() throws SQLException, IOException {
        String url = "jdbc:mysql://localhost:3306/SmartLibrary";
        String username = "root";
        String password = "Chamath2005.";

        // Connect to the database
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Books");

            // Store the data in a List of Objects
            List<BookObject> data = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("Book_Id");
                String name = rs.getString("Book_Name");
                String category = rs.getString("Book_Category");
                String about = rs.getString("About");
                String place = rs.getString("Place");
                String qty = rs.getString("Qty");
                data.add(new BookObject(id, name, category, about, place, qty));
            }

            // Create a new Excel workbook
            Workbook workbook = new XSSFWorkbook();

            // Create a new sheet in the workbook
            Sheet sheet = workbook.createSheet("Report");

            // Add headers to the sheet
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Book Name");
            headerRow.createCell(2).setCellValue("Category");
            headerRow.createCell(3).setCellValue("About");
            headerRow.createCell(4).setCellValue("Stored Place");
            headerRow.createCell(5).setCellValue("Available Quantity");

            // Add data to the sheet
            int rowNum = 1;
            for (BookObject obj : data) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(obj.getId());
                row.createCell(1).setCellValue(obj.getName());
                row.createCell(2).setCellValue(obj.getCategory());
                row.createCell(3).setCellValue(obj.getAbout());
                row.createCell(4).setCellValue(obj.getPlace());
                row.createCell(5).setCellValue(obj.getQty());

            }

            // Apply formatting to the cells
            CellStyle style = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            style.setDataFormat(format.getFormat("#,##0"));
            for (int i = 1; i <= data.size(); i++) {
                Row row = sheet.getRow(i);
                Cell cell = row.getCell(2);
                cell.setCellStyle(style);
            }

            // Autosize columns
            for (int i = 0; i < 3; i++) {
                sheet.autoSizeColumn(i);
            }

            // Save the workbook to a file
            FileOutputStream outputStream = new FileOutputStream("report2.xlsx");
            workbook.write(outputStream);
            workbook.close();
        }
    }
}
