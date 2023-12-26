package lk.ijse.smartLibrary.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.regex.Pattern;

public class otpConfirmationFormController {
    private static final String URL = "jdbc:mysql://localhost:3306/SmartLibrary";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "Chamath2005.");
    }

    @FXML
    private Button btnConfirm;

    @FXML
    private Label lblClickHere;

    @FXML
    private TextField txtOtp;

    @FXML
    private AnchorPane root;

    @FXML
    private Label lblCheck;

    @FXML
    void btnConfirmOnAction(ActionEvent event) throws IOException {
        int otpFromDB = 0;
        int otpFromDB2 = getOtp(otpFromDB);
        if (!validateInput(otpFromDB2)) {
            return;
        }
    }

    @FXML
    void lblClickHereOnMouseClicked(MouseEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/changeEmail_form.fxml"));

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Change Email");
        stage.centerOnScreen();
    }

    private boolean validateInput(int otpFromDB2) throws IOException {

        String otp1 = String.valueOf(otpFromDB2);

        if (!Pattern.matches("^\\d{6}$", txtOtp.getText())) {
            Platform.runLater(() -> printLabel("Please enter a 6 digit OTP"));
            return false;
        }else if(!otp1.equals(txtOtp.getText())){
            Platform.runLater(() -> printLabel("Please enter correct OTP"));
            return false;
        }else if(otp1.equals("")){
            Platform.runLater(() -> printLabel("Please enter a 6 digit OTP"));
            return false;
        }else{
            show();
            return true;
        }
    }

    private void printLabel(String text) {
        lblCheck.setText(text);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> lblCheck.setText(""));
                    }
                },
                2000
        );
    }

    private void show() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/setupRunning_form.fxml"));

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Wait");
        stage.centerOnScreen();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            try {
                AnchorPane loginAnchorPane = FXMLLoader.load(getClass().getResource("/view/homepage_form.fxml"));
                Scene loginScene = new Scene(loginAnchorPane);
                stage.setScene(loginScene);
                stage.setTitle("Smart Library");
                stage.centerOnScreen();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }));
        timeline.play();
    }

    private int getOtp(int otpFromDB){
        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "SELECT otp FROM Librarian WHERE Librarian_Id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, 1); // Set the librarian ID to 1
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                otpFromDB = rs.getInt("otp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return otpFromDB;
    }
}
