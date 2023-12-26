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

public class ReservationReport {
    public  void generateReport() throws SQLException, IOException {
        String url = "jdbc:mysql://localhost:3306/SmartLibrary";
        String username = "root";
        String password = "Chamath2005.";

        // Connect to the database
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Reservation " +
                    "JOIN Members ON Reservation.Member_Id = Members.Member_Id " +
                    "JOIN BookReservation ON Reservation.Reservation_Id = BookReservation.Reservation_Id " +
                    "JOIN Books ON BookReservation.Book_Id = Books.Book_Id");

            // Store the data in a List of ReservationObjects
            List<ReservationObject> data = new ArrayList<>();
            while (rs.next()) {
                int reservationId = rs.getInt("Reservation_Id");
                int bookId = rs.getInt("Book_Id");
                String bookName = rs.getString("Book_Name");
                int memberId = rs.getInt("Member_Id");
                String memberName = rs.getString("Member_Name");
                String bookCategory = rs.getString("Book_Category");
                String about = rs.getString("About");
                String reserveDate = rs.getString("Reserve_Date");
                String bringDate = rs.getString("Bring_Date");
                data.add(new ReservationObject(reservationId, bookId, memberId, bookName, memberName, bookCategory, about, reserveDate, bringDate));
            }

            // Create a new Excel workbook
            Workbook workbook = new XSSFWorkbook();

            // Create a new sheet in the workbook
            Sheet sheet = workbook.createSheet("Reservation Report");

            // Add headers to the sheet
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Reservation ID");
            headerRow.createCell(1).setCellValue("Book ID");
            headerRow.createCell(3).setCellValue("Member ID");
            headerRow.createCell(2).setCellValue("Book Name");
            headerRow.createCell(4).setCellValue("Member Name");
            headerRow.createCell(5).setCellValue("Book Category");
            headerRow.createCell(6).setCellValue("About");
            headerRow.createCell(7).setCellValue("Reserve Date");
            headerRow.createCell(8).setCellValue("Bring Date");

            // Add data to the sheet
            int rowNum = 1;
            for (ReservationObject obj : data) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(obj.getReservationId());
                row.createCell(3).setCellValue(obj.getBookId());
                row.createCell(4).setCellValue(obj.getBookName());
                row.createCell(1).setCellValue(obj.getMemberId());
                row.createCell(2).setCellValue(obj.getMemberName());
                row.createCell(5).setCellValue(obj.getBookCategory());
                row.createCell(6).setCellValue(obj.getAbout());
                row.createCell(7).setCellValue(obj.getReserveDate());
                row.createCell(8).setCellValue(obj.getBringDate());

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
                FileOutputStream outputStream = new FileOutputStream("reservation.xlsx");
                workbook.write(outputStream);
                workbook.close();
            }
        }
    }
