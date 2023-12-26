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
import lk.ijse.smartLibrary.bo.custom.AttendanceManagementBO;
import lk.ijse.smartLibrary.dto.Attendance;
import lk.ijse.smartLibrary.dto.tm.AttendanceTM;
import lk.ijse.smartLibrary.model.AttendanceModel;
import org.controlsfx.control.Notifications;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class AttendanceManagementFormController implements Initializable {
    private static final Notifications notification = Notifications.create();
    AttendanceManagementBO attendanceManagementBO = BoFactory.getBoFactory().getBO(BoFactory.BOTypes.ATTENDANCE_BO);

    public TableView<AttendanceTM> tblAttendance;

    @FXML
    private TableColumn<?, ?> atendance;

    @FXML
    private Button btnAttend;

    @FXML
    private Button btnReset;

    @FXML
    private TextField btnSearch;

    @FXML
    private TableColumn<?, ?> date;

    @FXML
    private TableColumn<?, ?> employeeId;

    @FXML
    private ImageView gifGoBack;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDetail;

    @FXML
    private TextField txtEmployeeId;

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
        curDate();
    }

    @FXML
    void btnAttendOnAction(ActionEvent event) {
        if(!validateFields()){
            return;
        }
        String id = txtEmployeeId.getText();
        LocalDate date = LocalDate.parse(txtDate.getText());

        try {
            if(true== attendanceManagementBO.attendEmployers(id, date)) {
                notification.title("Smart Library")
                        .text("Attendance added for Employee ID: " + id).darkStyle()
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT);
                notification.show();
            }


        } catch (SQLException e) {
            e.printStackTrace();
            notification.title("Smart Library")
                    .text("Attendance already recorded for Employee ID: " + id + " on " + date).darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();

        }
        getAll();
        txtEmployeeId.clear();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = btnSearch.getText();

        if (id.isEmpty()) {
            notification.title("Smart Library")
                    .text("Please enter an employee ID.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return;
        }

        if (!id.matches("\\d+")) {
            notification.title("Smart Library")
                    .text("Employee ID must contain only digits.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return;
        }
        try {
            txtDetail.setText(String.valueOf(attendanceManagementBO.searchEmployers(id)));
        } catch (SQLException e) {
            notification.title("Smart Library")
                    .text("No attendance records found for Employee ID: " + id + " in the current month").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        txtDetail.clear();
        btnSearch.clear();
        txtEmployeeId.clear();
    }

    private void setCellValueFactory() {
        employeeId.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        atendance.setCellValueFactory(new PropertyValueFactory<>("monthlyAttendanceCount"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void getAll() {
        try {
            ObservableList<AttendanceTM> obList = FXCollections.observableArrayList();
            List<Attendance> attendanceList = attendanceManagementBO.getAllEmployeesAttendance();

            for (Attendance attendance : attendanceList) {
                obList.add(new AttendanceTM(
                        attendance.getEmployeeID(),
                        attendance.getMonthlyAttendanceCount(),
                        attendance.getDate()
                ));
            }
            tblAttendance.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            notification.title("Smart Library")
                    .text("SQL Error!").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
        }
    }

    public void curDate() {
        LocalDate currentDate = LocalDate.now();
        txtDate.setText(currentDate.toString());
    }

    private boolean validateFields() {
        String dateInput = txtDate.getText();
        if (!dateInput.matches("\\d{4}-\\d{2}-\\d{2}")) {
            notification.title("Smart Library")
                    .text("txtDate should be in the format yyyy-mm-dd.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return false;
        }

        String employeeIdInput = txtEmployeeId.getText();
        if (!employeeIdInput.matches("\\d+")) {
            notification.title("Smart Library")
                    .text("txtEmployeeId should only contain integers.").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return false;
        }
        return true;
    }
}
