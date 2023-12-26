package lk.ijse.smartLibrary.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.smartLibrary.dto.Book;
import lk.ijse.smartLibrary.dto.Donate;
import lk.ijse.smartLibrary.dto.tm.DonateTM;
import lk.ijse.smartLibrary.model.DonateModel;
import org.controlsfx.control.Notifications;

import java.sql.*;
import java.util.*;

public class DonateManagementFormController implements Initializable {
    private static final Notifications notification = Notifications.create();

    public TableView<DonateTM> tblDonate;

    @FXML
    private TableColumn<?, ?> bookName;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnReset;

    @FXML
    private TextField btnSearch;

    @FXML
    private TableColumn<?, ?> donateId;

    @FXML
    private TableColumn<?, ?> quantity;

    @FXML
    private AnchorPane root;

    @FXML
    private Label lblError;

    @FXML
    private TextField txtBookId;

    @FXML
    private TextField txtBookName;

    @FXML
    private TextField txtMemberId;

    @FXML
    private TextField txtQuantity;

    @FXML
    void btnAddOnAction(){
        String memberId = txtMemberId.getText();
        String bookId = txtBookId.getText();
        String quantity = txtQuantity.getText();
        String bookName1 = txtBookName.getText();

        if(!validate()){
            return;
        }
        DonateModel.addDonate(memberId, bookId, quantity, bookName1);
        getAll();
        txtQuantity.clear();
        txtMemberId.clear();
        txtBookName.clear();
        txtBookId.clear();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String idInput = btnSearch.getText();
        if (!idInput.matches("\\d+")) {
            notification.title("Smart Library")
                    .text("Search should only contain integers.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return;
        }
        int id = Integer.parseInt(idInput);

        Book book = null;
        try {
            book = DonateModel.search(String.valueOf(id));
        } catch (SQLException e) {
            notification.title("Smart Library")
                    .text("The Book was not found. So you can add the book externally!").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
        }
        txtBookId.setText(book.getBookId());
        txtBookName.setText(book.getBookName());
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        txtBookName.clear();
        txtQuantity.clear();
        txtBookId.clear();
        btnSearch.clear();
    }

    private void getAll() {
        try {
            ObservableList<DonateTM> obList = FXCollections.observableArrayList();
            List<Donate> memberList = DonateModel.getAll();

            for (Donate donate : memberList) {
                obList.add(new DonateTM(
                        donate.getDonateId(),
                        donate.getBookName(),
                        donate.getQuantity()
                ));
            }
            tblDonate.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            notification.title("Smart Library")
                    .text("Something is wrong !").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
        }
    }

    private void setCellValueFactory() {
        donateId.setCellValueFactory(new PropertyValueFactory<>("donateId"));
        bookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }

    private boolean validate(){
        String bookId = txtBookId.getText();
        String bookName = txtBookName.getText();
        String memberId = txtMemberId.getText();
        String quantity = txtQuantity.getText();

        // Regex patterns
        String idPattern = "\\d+";
        String namePattern = "[A-Za-z]+";
        String qtyPattern = "\\d+";

        if (bookId.isEmpty() || bookName.isEmpty() || memberId.isEmpty() || quantity.isEmpty()){
            lblError.setText("Please provide all fields correctly");
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> lblError.setText(""));
                        }
                    },
                    2000
            );
            return false;
        }

        if (!bookId.matches(idPattern)) {
            lblError.setText("Book ID should only contain integers.");
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> lblError.setText(""));
                        }
                    },
                    2000
            );
            return false;
        }

        if (!bookName.matches(namePattern)) {
            lblError.setText("Book name should only contain letters.");
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> lblError.setText(""));
                        }
                    },
                    2000
            );
            return false;
        }

        if (!memberId.matches(idPattern)) {
            lblError.setText("Member ID should only contain integers.");
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> lblError.setText(""));
                        }
                    },
                    2000
            );
            return false;
        }

        if (!quantity.matches(qtyPattern)) {
            lblError.setText("Quantity should only contain integers.");
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> lblError.setText(""));
                        }
                    },
                    2000
            );
            return false;
        }
        return true;
    }

}
