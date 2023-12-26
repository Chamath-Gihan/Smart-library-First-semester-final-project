package lk.ijse.smartLibrary.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.smartLibrary.bo.BoFactory;
import lk.ijse.smartLibrary.bo.custom.EmployeeManagementBO;
import lk.ijse.smartLibrary.bo.custom.FinanceManagementBO;
import lk.ijse.smartLibrary.dto.Expenditure;
import lk.ijse.smartLibrary.dto.tm.ExpenditureTM;
import lk.ijse.smartLibrary.model.ExpenditureModel;
import lk.ijse.smartLibrary.model.FinanceModel;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;
import java.util.*;

public class FinanceManagementFormController implements Initializable {
    private static final Notifications notification = Notifications.create();
    FinanceManagementBO financeManagementBO = BoFactory.getBoFactory().getBO(BoFactory.BOTypes.FINANCE_BO);

    public TableView<ExpenditureTM> tblFinance;

    @FXML
    private Button btnSave;

    @FXML
    private TableColumn<?, ?> details;

    @FXML
    private TableColumn<?, ?> expenditureId;

    @FXML
    private ImageView gifGoBack;

    @FXML
    private TableColumn<?, ?> price;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtCurrentMoneyBalance;

    @FXML
    private Button btnReset;

    @FXML
    private TextField txtDetail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtExpenditureId;

    @FXML
    private TextField txtPrice;

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
        try {
            txtExpenditureId.setText(String.valueOf(financeManagementBO.getNextExpenditureId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            txtCurrentMoneyBalance.setText(String.valueOf(financeManagementBO.getTotalExpenditurePrice()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        expenditureId.setCellValueFactory(new PropertyValueFactory<>("expenditureId"));
        details.setCellValueFactory(new PropertyValueFactory<>("detail"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void getAll() {
        try {
            ObservableList<ExpenditureTM> obList = FXCollections.observableArrayList();
            ArrayList<Expenditure> memberList = financeManagementBO.getAllExpenditures();

            for (Expenditure expenditure : memberList) {
                obList.add(new ExpenditureTM(
                        expenditure.getExpenditureId(),
                        expenditure.getDetail(),
                        expenditure.getPrice()
                ));
            }
            tblFinance.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            notification.title("Smart Library")
                    .text("SQL Error ! ").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        txtPrice.clear();
        txtDetail.clear();
        txtId.clear();
        try {
            txtExpenditureId.setText(String.valueOf(financeManagementBO.getNextExpenditureId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void txtIdOnAction(ActionEvent event) {
        String id = txtId.getText();
        Expenditure expenditure = null;
        try {
            expenditure =  financeManagementBO.searchExpenditures(id);
            txtExpenditureId.setText(expenditure.getExpenditureId());
            txtDetail.setText(expenditure.getDetail());
            txtPrice.setText(String.valueOf(expenditure.getPrice()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtExpenditureId.getText();
        String detail = txtDetail.getText();
        double price  = Double.parseDouble(txtPrice.getText());

        if(!validate()){
            return;
        }
        try {
            if(true == financeManagementBO.addExpenditures(new Expenditure(id, detail, price))){
                notification.title("Smart Library")
                        .text("Expenditure Added ! ").darkStyle()
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT);
                notification.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getAll();
        txtPrice.clear();
        txtDetail.clear();
        txtExpenditureId.clear();
        try {
            txtExpenditureId.setText(String.valueOf(financeManagementBO.getNextExpenditureId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            txtCurrentMoneyBalance.setText(String.valueOf(financeManagementBO.getTotalExpenditurePrice()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean validate(){
        String priceInput = txtPrice.getText();
        if (!priceInput.matches("\\d+(\\.\\d{1,2})?")) {
            notification.title("Smart Library")
                    .text("Price should be a number with up to 2 decimal places.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return false;
        }

        String detailInput = txtDetail.getText();
        if (detailInput.trim().isEmpty()) {
            notification.title("Smart Library")
                    .text("Detail cannot be empty.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return false;
        }
        return true;
    }
}
