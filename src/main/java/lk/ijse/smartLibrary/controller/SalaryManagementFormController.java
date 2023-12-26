package lk.ijse.smartLibrary.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.smartLibrary.bo.BoFactory;
import lk.ijse.smartLibrary.bo.custom.SalaryManagementBO;
import lk.ijse.smartLibrary.dto.Salary;
import lk.ijse.smartLibrary.dto.tm.SalaryTM;
import lk.ijse.smartLibrary.model.SalaryModel;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class SalaryManagementFormController implements Initializable {

    private static final String URL = "jdbc:mysql://localhost:3306/SmartLibrary";
    private static final Properties props = new Properties();
    private static final Notifications notification = Notifications.create();
    SalaryManagementBO salaryManagementBO = BoFactory.getBoFactory().getBO(BoFactory.BOTypes.SALARY_BO);

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "Chamath2005.");
    }

    public TableView<SalaryTM> tblSalary;

    @FXML
    private Button btnCalculate;

    @FXML
    private Button btnPay;

    @FXML
    private TableColumn<?, ?> employeeId;

    @FXML
    private ImageView gifGoBack;

    @FXML
    private AnchorPane root;

    @FXML
    private Label lblError;

    @FXML
    private TableColumn<?, ?> salaryForMonth;

    @FXML
    private TableColumn<?, ?> salaryId;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtMonth;

    @FXML
    private TextField txtMonthSalary;

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
        try {
            curMonth();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        employeeId.setCellValueFactory(new PropertyValueFactory<>("salaryId"));
        salaryId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        salaryForMonth.setCellValueFactory(new PropertyValueFactory<>("salaryForMonth"));
    }

    private void getAll() {
        try {
            ObservableList<SalaryTM> obList = FXCollections.observableArrayList();
            List<Salary> salaryList = salaryManagementBO.getAllSalaryPayments();

            for (Salary salary : salaryList) {
                obList.add(new SalaryTM(
                        salary.getEmployeeId(),
                        salary.getSalaryId(),
                        salary.getSalaryForMonth()
                ));
            }
            tblSalary.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @FXML
    void btnCalculateOnAction(ActionEvent event) {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            int employeeId = Integer.parseInt(txtEmployeeId.getText());

            // Check if the employee exists in the database
            String employeeQuery = "SELECT * FROM Employee WHERE Employee_Id = ?";
            PreparedStatement employeeStatement = con.prepareStatement(employeeQuery);
            employeeStatement.setInt(1, employeeId);

            ResultSet employeeResult = employeeStatement.executeQuery();
            if (!employeeResult.next()) {
                // Show error message if employee doesn't exist
                notification.title("Salary Calculator")
                        .text("Employee not found in database!").darkStyle()
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT);
                notification.show();
                txtEmployeeId.clear();
                txtMonthSalary.clear();
                return;
            }

            // Check if the employee has attendance for the current month
            LocalDate now = LocalDate.now();
            String attendanceQuery = "SELECT * FROM Attendance WHERE Employee_Id = ? AND YEAR(Date) = ? AND MONTH(Date) = ?";
            PreparedStatement attendanceStatement = con.prepareStatement(attendanceQuery);
            attendanceStatement.setInt(1, employeeId);
            attendanceStatement.setInt(2, now.getYear());
            attendanceStatement.setInt(3, now.getMonthValue());

            ResultSet attendanceResult = attendanceStatement.executeQuery();
            int attendanceCount = 0;
            while (attendanceResult.next()) {
                attendanceCount += attendanceResult.getInt("Monthly_Attendance_Count");
            }

            if (attendanceCount > 0) {
                double salaryForDay = employeeResult.getDouble("Salary_For_Day");
                double salaryForMonth = attendanceCount * salaryForDay;

                txtMonthSalary.setText(String.valueOf(salaryForMonth));
            } else {
                // Show message if attendance count is 0
                notification.title("Smart Library")
                        .text("\nAttendance count is 0 for current month!").darkStyle()
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT);
                notification.show();
                txtEmployeeId.clear();
                txtMonthSalary.clear();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnPayOnAction(ActionEvent event) {
        if(!validate()){
            return;
        }
        int employeeId = Integer.parseInt(txtEmployeeId.getText());
        double salaryForMonth = Double.parseDouble(txtMonthSalary.getText());



        try (Connection con = DriverManager.getConnection(URL, props)) {
            LocalDate now = LocalDate.now();
            int year = now.getYear();
            int month = now.getMonthValue();

            String paymentQuery = "SELECT COUNT(*) AS count FROM PaymentOfSalaries WHERE Employee_Id = ? AND YEAR(Salary_Date) = ? AND MONTH(Salary_Date) = ?";
            PreparedStatement paymentStatement = con.prepareStatement(paymentQuery);
            paymentStatement.setInt(1, employeeId);
            paymentStatement.setInt(2, year);
            paymentStatement.setInt(3, month);

            ResultSet paymentResult = paymentStatement.executeQuery();
            paymentResult.next();
            int paymentCount = paymentResult.getInt("count");
            if (paymentCount > 0) {
                // Show error message if payment already exists for employee in current month
                notification.title("Salary Payment")
                        .text("Payment already made for employee " + employeeId + " in " + now.getMonth() + " " + now.getYear() + "!").darkStyle()
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT);
                notification.show();
                return;
            }

            // If payment doesn't already exist, insert the payment
            paymentQuery = "INSERT INTO PaymentOfSalaries (Employee_Id, Salary_For_Month, Salary_Date) VALUES (?, ?, ?)";
            paymentStatement = con.prepareStatement(paymentQuery);
            paymentStatement.setInt(1, employeeId);
            paymentStatement.setDouble(2, salaryForMonth);
            paymentStatement.setObject(3, now);
            paymentStatement.executeUpdate();

            getAll();
            txtEmployeeId.clear();
            txtMonthSalary.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void gifGoBackOnMouseClicked(MouseEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/homepage_form.fxml"));

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage)root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Home");
        stage.centerOnScreen();
    }

    public void curMonth() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "SELECT DATE_FORMAT(NOW(), '%M')";
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                String currentMonth = rs.getString(1);
                txtMonth.setText(currentMonth);
            }
        }
    }

    private boolean validate(){
        if (txtMonthSalary.getText().isEmpty() || txtEmployeeId.getText().isEmpty() || txtMonth.getText().isEmpty()){
            lblError.setText("Please Provide All Fields Correctly");
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
        }else{
            return true;
        }
    }
}
