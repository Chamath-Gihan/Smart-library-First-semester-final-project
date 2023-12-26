package lk.ijse.smartLibrary.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.smartLibrary.bo.BoFactory;
import lk.ijse.smartLibrary.bo.custom.BookManagementBO;
import lk.ijse.smartLibrary.dto.Book;
import lk.ijse.smartLibrary.model.BookModel;
import lk.ijse.smartLibrary.report.BookReport;
import lk.ijse.smartLibrary.report.ReservationReport;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.controlsfx.control.Notifications;
import javafx.beans.property.ReadOnlyObjectWrapper;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookManagementFormController {
    private static final Notifications notification = Notifications.create();
    BookManagementBO bookManagementBO = BoFactory.getBoFactory().getBO(BoFactory.BOTypes.BOOK_BO);

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnReservation;

    @FXML
    private Button btnBook;

    @FXML
    private TextField btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private ImageView gifGoBack;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtAbout;

    @FXML
    private TextField txtBookCategory;

    @FXML
    private TextField txtBookId1;

    @FXML
    private TextField txtBookId2;

    @FXML
    private TextField txtBookName;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtMemberId;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtReservationId;

    @FXML
    private TextField txtplace;

    @FXML
    void btnBookOnAction(ActionEvent event) {
        BookReport bookReport = new BookReport();
        try {
            bookReport.generateReport();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileInputStream file = null;
        try {
            file = new FileInputStream(new File("report2.xlsx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        XSSFSheet sheet = workbook.getSheetAt(0);

        TableView<ObservableList<String>> tableView = new TableView<>();

        for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
            final int col = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(sheet.getRow(0).getCell(i).getStringCellValue());
            column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(col)));
            tableView.getColumns().add(column);
        }

        DataFormatter formatter = new DataFormatter();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int j = 0; j < sheet.getRow(i).getLastCellNum(); j++) {
                XSSFCell cell = sheet.getRow(i).getCell(j);
                if (cell.getCellType() == CellType.NUMERIC) {
                    row.add(formatter.formatCellValue(cell));
                } else {
                    row.add(cell.getStringCellValue());
                }
            }
            tableView.getItems().add(row);
        }
        Stage stage = new Stage();
        stage.setTitle("Book Report");
        stage.setScene(new Scene(tableView, 550, 600));
        stage.show();
    }

    @FXML
    void btnReservationOnAction(ActionEvent event) {
        ReservationReport reservationReport = new ReservationReport();
        try {
            reservationReport.generateReport();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileInputStream file = null;
        try {
            file = new FileInputStream(new File("reservation.xlsx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        XSSFSheet sheet = workbook.getSheetAt(0);

        TableView<ObservableList<String>> tableView = new TableView<>();

        for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
            final int col = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(sheet.getRow(0).getCell(i).getStringCellValue());
            column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(col)));
            tableView.getColumns().add(column);
        }

        DataFormatter formatter = new DataFormatter();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int j = 0; j < sheet.getRow(i).getLastCellNum(); j++) {
                XSSFCell cell = sheet.getRow(i).getCell(j);
                if (cell.getCellType() == CellType.NUMERIC) {
                    row.add(formatter.formatCellValue(cell));
                } else {
                    row.add(cell.getStringCellValue());
                }
            }
            tableView.getItems().add(row);
        }
        Stage stage = new Stage();
        stage.setTitle("Reservation Report");
        stage.setScene(new Scene(tableView, 920, 600));
        stage.show();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if(!validateFields()){
            return;
        }
        String id = txtBookId1.getText();
        String name = txtBookName.getText();
        String category = txtBookCategory.getText();
        String about = txtAbout.getText();
        String place = txtplace.getText();
        String qty = txtQuantity.getText();

        try {
            bookManagementBO.addBook(new Book(id, name, category, about, place, qty));

            notification.title("Smart Library")
                    .text("Book added Successfully:)").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();

        } catch (SQLException e) {

            notification.title("Smart Library")
                    .text("Book ID already exists in the table.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();

        }
        txtBookId1.clear();
        txtBookName.clear();
        txtQuantity.clear();
        txtplace.clear();
        txtAbout.clear();
        txtBookCategory.clear();
        try {
            txtBookId1.setText(String.valueOf(bookManagementBO.getNextBookId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String bookIdInput = txtBookId1.getText();
        if (!bookIdInput.matches("\\d+")) {
            notification.title("Smart Library")
                    .text("Book Id should only contain integers.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return;
        }
        String id = txtBookId1.getText();
        try {
            bookManagementBO.deleteBook(id);

            notification.title("Smart Library")
                    .text("deleted!").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        txtBookId1.clear();
        txtBookName.clear();
        txtQuantity.clear();
        txtplace.clear();
        txtAbout.clear();
        txtBookCategory.clear();
        try {
            txtBookId1.setText(String.valueOf(bookManagementBO.getNextBookId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String quantityInput = txtMemberId.getText();
        if (!quantityInput.matches("\\d+")) {
            notification.title("Smart Library")
                    .text("Member ID should only contain integers.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return;
        }

        String quantityInput1 = txtBookId2.getText();
        if (!quantityInput1.matches("\\d+")) {
            notification.title("Smart Library")
                    .text("Book ID On reservation should only contain integers.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return;
        }

        String dateInput = txtDate.getText();
        if (!dateInput.matches("\\d{4}-\\d{2}-\\d{2}")) {
            notification.title("Smart Library")
                    .text("Date should be in the format yyyy-MM-dd.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return;
        }

        String bookId = txtBookId2.getText();
        String memberId = txtMemberId.getText();
        String date = txtDate.getText();
        String reservationId1 = txtReservationId.getText();

        try {
            if(true ==bookManagementBO.addReservation(bookId,memberId,reservationId1,date)){

                notification.title("Smart Library")
                        .text("Reservation saved successfully.").darkStyle()
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT);
                notification.show();
            }else{

                notification.title("Smart Library")
                        .text("Failed to save reservation.").darkStyle()
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT);
                notification.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        txtBookId2.clear();
        txtMemberId.clear();
        txtReservationId.clear();
        try {
            txtReservationId.setText(String.valueOf(bookManagementBO.reservationId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String bookIdInput = btnSearch.getText();
        if (!bookIdInput.matches("\\d+")) {
            notification.title("Smart Library")
                    .text("search should only contain integers.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return;
        }
        Book book = null;
        try {
            book = bookManagementBO.searchBook(bookIdInput);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        txtBookId1.setText(book.getBookId());
        txtBookName.setText(book.getBookName());
        txtBookCategory.setText(book.getBookCategory());
        txtAbout.setText(book.getAbout());
        txtplace.setText(book.getPlace());
        txtQuantity.setText(book.getQty());
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String bookIdInput = txtBookId1.getText();
        if (!bookIdInput.matches("\\d+")) {
            notification.title("Smart Library")
                    .text("Book Id should only contain integers.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return;
        }
        String id = txtBookId1.getText();
        String name = txtBookName.getText();
        String category = txtBookCategory.getText();
        String about = txtAbout.getText();
        String place = txtplace.getText();
        String qty = txtQuantity.getText();

        try {
            bookManagementBO.updateBook(new Book (name, category, about, place, qty, id));
            notification.title("Smart Library")
                    .text("Update success !").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        txtBookId1.clear();
        txtBookName.clear();
        txtQuantity.clear();
        txtplace.clear();
        txtAbout.clear();
        txtBookCategory.clear();
        try {
            txtBookId1.setText(String.valueOf(bookManagementBO.getNextBookId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        try {
            txtBookId1.setText(String.valueOf(bookManagementBO.getNextBookId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        txtBookName.clear();
        txtQuantity.clear();
        txtplace.clear();
        txtAbout.clear();
        txtBookCategory.clear();
        btnSearch.clear();
    }

    private void date() {
        LocalDate today = LocalDate.now();
        LocalDate dateAfter15Days = today.plusDays(15);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = dateAfter15Days.format(formatter);
        txtDate.setText(formattedDate);
    }

    public void initialize(){
        try {
            txtBookId1.setText(String.valueOf(bookManagementBO.getNextBookId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            txtReservationId.setText(String.valueOf(bookManagementBO.reservationId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        date();
    }

    private boolean validateFields() {
        String aboutInput = txtAbout.getText();
        if (!aboutInput.matches("[a-zA-Z]+")) {
            notification.title("Smart Library")
                    .text("About should only contain letters.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return false;
        }

        String bookCategoryInput = txtBookCategory.getText();
        if (!bookCategoryInput.matches("[a-zA-Z]+")) {
            notification.title("Smart Library")
                    .text("Book Category should only contain letters.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return false;
        }

        String bookIdInput = txtBookId1.getText();
        if (!bookIdInput.matches("\\d+")) {
            notification.title("Smart Library")
                    .text("Book Id should only contain integers.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return false;
        }

        String bookNameInput = txtBookName.getText();
        if (!bookNameInput.matches("[a-zA-Z]+")) {
            notification.title("Smart Library")
                    .text("Book Name should only contain letters.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return false;
        }

        String quantityInput = txtQuantity.getText();
        if (!quantityInput.matches("\\d+")) {
            notification.title("Smart Library")
                    .text("Quantity should only contain integers.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return false;
        }

        String placeInput = txtplace.getText();
        if (!placeInput.matches("\\d+")) {
            notification.title("Smart Library")
                    .text("Place should only contain integers.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return false;
        }

        return true;
    }
}

