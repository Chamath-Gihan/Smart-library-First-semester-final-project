package lk.ijse.smartLibrary.controller;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.smartLibrary.getCurrentDateAndTime.GetCurrentDateAndTime;

import java.io.IOException;

public class HomePageFormController {
    GetCurrentDateAndTime getCurrentDateAndTime = new GetCurrentDateAndTime();
    String hoverStyle = "-fx-background-color: #00FFFF; -fx-border-width: 2px; -fx-text-fill: black;";
    String originalStyle = "-fx-background-color: #263A80; -fx-border-color: black;";

    @FXML
    private Button btnAttendanceManagement;

    @FXML
    private Button btnBookManagement;

    @FXML
    private Button btnDonateManagement;

    @FXML
    private Button btnEmployeeManagement;

    @FXML
    private Button btnFinanceManagement;

    @FXML
    private Button btnMemberManagement;

    @FXML
    private Button btnSalaryManagement;

    @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane root1;

    @FXML
    private Label dateTimeLabel;

    @FXML
    private Label mouseLocationLabel;

    @FXML
    void btnAttendanceManagementOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/attendanceManagement_form.fxml"));
        AnchorPane anchorPane = loader.load();
        AttendanceManagementFormController attendanceManagementFormController = loader.getController();
        TranslateTransition t1 = transition(anchorPane);
        t1.play();
        btnAttendanceManagement.setStyle(hoverStyle);
    }

    @FXML
    void btnBookManagementOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/bookManagement_form.fxml"));
        AnchorPane anchorPane = loader.load();
        BookManagementFormController bookManagementFormController = loader.getController();
        TranslateTransition t1 = transition(anchorPane);
        t1.play();
        btnBookManagement.setStyle(hoverStyle);
    }

    @FXML
    void btnDonateManagementOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/donateManagement_form.fxml"));
        AnchorPane anchorPane = loader.load();
        DonateManagementFormController donateManagementFormController = loader.getController();
        TranslateTransition t1 = transition(anchorPane);
        t1.play();
        btnDonateManagement.setStyle(hoverStyle);
    }

    @FXML
    void btnEmployeeManagementOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/employeeManagement_form.fxml"));
        AnchorPane anchorPane = loader.load();
        EmployeeManagementFormController employeeManagementFormController = loader.getController();
        TranslateTransition t1 = transition(anchorPane);
        t1.play();
        btnEmployeeManagement.setStyle(hoverStyle);
    }

    @FXML
    void btnFinanceManagementOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/financeManagement_form.fxml"));
        AnchorPane anchorPane = loader.load();
        FinanceManagementFormController financeManagementFormController = loader.getController();
        TranslateTransition t1 = transition(anchorPane);
        t1.play();
        btnFinanceManagement.setStyle(hoverStyle);
    }

    @FXML
    void btnMemberManagementOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/memberManagement_form.fxml"));
        AnchorPane anchorPane = loader.load();
        MemberManagementFormController memberManagementFormController = loader.getController();
        TranslateTransition t1 = transition(anchorPane);
        t1.play();
        btnMemberManagement.setStyle(hoverStyle);
    }

    @FXML
    void btnSalaryManagementOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/salaryManagement_form.fxml"));
        AnchorPane anchorPane = loader.load();
        SalaryManagementFormController salaryManagementFormController = loader.getController();
        TranslateTransition t1 = transition(anchorPane);
        t1.play();
        btnSalaryManagement.setStyle(hoverStyle);
    }

    @FXML
    private void setButtonsOriginalStyle(){
        btnAttendanceManagement.setStyle(originalStyle);
        btnBookManagement.setStyle(originalStyle);
        btnDonateManagement.setStyle(originalStyle);
        btnEmployeeManagement.setStyle(originalStyle);
        btnFinanceManagement.setStyle(originalStyle);
        btnMemberManagement.setStyle(originalStyle);
        btnSalaryManagement.setStyle(originalStyle);
    }

    public void initialize() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        event -> dateTimeLabel.setText(getCurrentDateAndTime.getCurrentDateTime())),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        Reflection reflection = new Reflection();
        reflection.setFraction(0.75);

        btnMemberManagement.setOnMouseEntered(event -> {
            mouseLocationLabel.setLayoutX(90);
            mouseLocationLabel.setLayoutY(37);
            btnMemberManagement.setEffect(null);
            mouseLocationLabel.setText("Member Management -:)");
            mouseLocationLabel.setVisible(true);
        });
        btnMemberManagement.setOnMouseExited(event ->{
            mouseLocationLabel.setVisible(false);
            btnMemberManagement.setEffect(reflection);
        });

        btnAttendanceManagement.setOnMouseEntered(event -> {
            mouseLocationLabel.setLayoutX(1186);
            mouseLocationLabel.setLayoutY(37);
            btnAttendanceManagement.setEffect(null);
            mouseLocationLabel.setText("Attendance Management -:)");
            mouseLocationLabel.setVisible(true);
        });
        btnAttendanceManagement.setOnMouseExited(event ->{
            mouseLocationLabel.setVisible(false);
            btnAttendanceManagement.setEffect(reflection);
        });

        btnBookManagement.setOnMouseEntered(event -> {
            mouseLocationLabel.setLayoutX(364);
            mouseLocationLabel.setLayoutY(37);
            btnBookManagement.setEffect(null);
            mouseLocationLabel.setText("Book Management -:)");
            mouseLocationLabel.setVisible(true);
        });
        btnBookManagement.setOnMouseExited(event ->{
            mouseLocationLabel.setVisible(false);
            btnBookManagement.setEffect(reflection);
        });

        btnDonateManagement.setOnMouseEntered(event -> {
            mouseLocationLabel.setLayoutX(1734);
            mouseLocationLabel.setLayoutY(37);
            btnDonateManagement.setEffect(null);
            mouseLocationLabel.setText("Donate Management -:)");
            mouseLocationLabel.setVisible(true);
        });
        btnDonateManagement.setOnMouseExited(event ->{
            mouseLocationLabel.setVisible(false);
            btnDonateManagement.setEffect(reflection);
        });

        btnFinanceManagement.setOnMouseEntered(event -> {
            mouseLocationLabel.setLayoutX(638);
            mouseLocationLabel.setLayoutY(37);
            btnFinanceManagement.setEffect(null);
            mouseLocationLabel.setText("Finance Management -:)");
            mouseLocationLabel.setVisible(true);
        });
        btnFinanceManagement.setOnMouseExited(event ->{
            mouseLocationLabel.setVisible(false);
            btnFinanceManagement.setEffect(reflection);
        });

        btnSalaryManagement.setOnMouseEntered(event -> {
            mouseLocationLabel.setLayoutX(1460);
            mouseLocationLabel.setLayoutY(37);
            btnSalaryManagement.setEffect(null);
            mouseLocationLabel.setText("Salary Management -:)");
            mouseLocationLabel.setVisible(true);
        });
        btnSalaryManagement.setOnMouseExited(event ->{
            mouseLocationLabel.setVisible(false);
            btnSalaryManagement.setEffect(reflection);
        });
    }

    private TranslateTransition transition(AnchorPane anchorPane){
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), anchorPane);
        transition.setFromX(1921);
        transition.setToX(0);

        root1.getChildren().setAll(anchorPane);
        setButtonsOriginalStyle();
        return transition;
    }
}
