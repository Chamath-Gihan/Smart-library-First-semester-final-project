package lk.ijse.smartLibrary.controller;

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
import lk.ijse.smartLibrary.bo.BoFactory;
import lk.ijse.smartLibrary.bo.custom.EmployeeManagementBO;
import lk.ijse.smartLibrary.dto.Employee;
import lk.ijse.smartLibrary.dto.tm.EmployeeTM;
import lk.ijse.smartLibrary.model.EmployeeModel;
import org.controlsfx.control.Notifications;

import java.sql.*;
import java.util.*;

public class EmployeeManagementFormController implements Initializable {
    private static final Notifications notification = Notifications.create();
    EmployeeManagementBO employeeManagementBO = BoFactory.getBoFactory().getBO(BoFactory.BOTypes.EMPLOYEE_BO);

    public TableView<EmployeeTM> tblEmployee;

    @FXML
    private TableColumn<?, ?> address;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private TextField btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> contact;

    @FXML
    private TableColumn<?, ?> employeeId;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private AnchorPane root;

    @FXML
    private TableColumn<?, ?> salaryForADay;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmployeeAddress;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtSalaryForADay;

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
        try {
            txtEmployeeId.setText(String.valueOf(employeeManagementBO.getNextEmployeeID()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        employeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        salaryForADay.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    private void getAll() {
        try {
            ObservableList<EmployeeTM> obList = FXCollections.observableArrayList();
            List<Employee> employeeList = employeeManagementBO.getAllEmployers();

            for (Employee employee : employeeList) {
                obList.add(new EmployeeTM(
                        employee.getEmployeeId(),
                        employee.getName(),
                        employee.getAddress(),
                        employee.getContact(),
                        employee.getSalary()
                ));
            }
            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        try {
            txtEmployeeId.setText(String.valueOf(employeeManagementBO.getNextEmployeeID()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        txtSalaryForADay.clear();
        txtContact.clear();
        txtEmployeeAddress.clear();
        txtEmployeeName.clear();
        btnSearch.clear();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if(!validateFields()){
            return;
        }
        String employeeId = txtEmployeeId.getText();
        String name = txtEmployeeName.getText();
        String address = txtEmployeeAddress.getText();
        String contact = txtContact.getText();
        Double salary = Double.valueOf(txtSalaryForADay.getText());

        try {
            if(true == employeeManagementBO.addEmployers(new Employee (employeeId, name, contact, address, salary))){
            new Alert(Alert.AlertType.CONFIRMATION,
                    "Employee added :)")
                    .show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getAll();
        txtEmployeeAddress.clear();
        txtEmployeeName.clear();
        txtEmployeeId.clear();
        txtContact.clear();
        txtSalaryForADay.clear();
        try {
            txtEmployeeId.setText(String.valueOf(employeeManagementBO.getNextEmployeeID()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtEmployeeId.getText();
        if (id.isEmpty() || !id.matches("\\d")) {
            notification.title("Smart Library")
                    .text("Please enter a valid Member ID.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return;
        }
        try {
            if(true == employeeManagementBO.deleteEmployers(id)) {
                notification.title("Smart Library")
                        .text("Employee deleted !").darkStyle()
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT);
                notification.show();
            }else{
                notification.title("Smart Library")
                        .text("Employee deleted unsuccess !").darkStyle()
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT);
                notification.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getAll();
        txtEmployeeAddress.clear();
        txtEmployeeName.clear();
        txtEmployeeId.clear();
        txtContact.clear();
        txtSalaryForADay.clear();
        try {
            txtEmployeeId.setText(String.valueOf(employeeManagementBO.getNextEmployeeID()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = btnSearch.getText();
        Employee employee = null;
        try {
            employee = employeeManagementBO.searchEmployers(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        txtEmployeeId.setText(employee.getEmployeeId());
        txtEmployeeName.setText(employee.getName());
        txtEmployeeAddress.setText(employee.getAddress());
        txtContact.setText(employee.getContact());
        txtSalaryForADay.setText(String.valueOf(employee.getSalary()));
        getAll();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if(!validateFields()){
            return;
        }
        String employeeId = txtEmployeeId.getText();
        String name = txtEmployeeName.getText();
        String address = txtEmployeeAddress.getText();
        String contact = txtContact.getText();
        double salary = Double.parseDouble(txtSalaryForADay.getText());

        try {
            if(true == employeeManagementBO.updateEmployers(new Employee (employeeId, name, contact, address, salary))){
                notification.title("Smart Library")
                        .text("Employee update success !").darkStyle()
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT);
                notification.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        getAll();
        txtEmployeeAddress.clear();
        txtEmployeeName.clear();
        txtEmployeeId.clear();
        txtContact.clear();
        txtSalaryForADay.clear();
        try {
            txtEmployeeId.setText(String.valueOf(employeeManagementBO.getNextEmployeeID()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean validateFields() {
        String employeeIdInput = txtEmployeeId.getText();
        if (employeeIdInput.isEmpty() || !employeeIdInput.matches("\\d+")) {
            notification.title("Smart Library")
                    .text("Please enter a valid employee ID.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return false;
        }

        String employeeNameInput = txtEmployeeName.getText();
        if (employeeNameInput.isEmpty() || !employeeNameInput.matches("^[a-zA-Z]+$")) {
            notification.title("Smart Library")
                    .text("Please enter a valid name.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return false;
        }

        String contactInput = txtContact.getText();
        if (contactInput.isEmpty() || !contactInput.matches("^\\+?\\d{10,13}$")) {
            notification.title("Smart Library")
                    .text("Please enter a valid contact number.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return false;
        }

        String employeeAddressInput = txtEmployeeAddress.getText();
        if (employeeAddressInput.isEmpty() || !employeeAddressInput.matches("^[a-zA-Z0-9!@#$%^&*()_+-={};:\"<>,.?\\/\\s]+$")) {
            notification.title("Smart Library")
                    .text("Please enter a valid address.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return false;
        }

        String salaryForADayInput = txtSalaryForADay.getText();
        if (salaryForADayInput.isEmpty() || !salaryForADayInput.matches("\\d+(\\.\\d+)?")) {
            notification.title("Smart Library")
                    .text("Please enter a valid salary for a day.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return false;
        }
        return true;
    }
}
