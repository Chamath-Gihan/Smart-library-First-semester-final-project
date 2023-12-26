package lk.ijse.smartLibrary.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.smartLibrary.email.Email;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;

import static javafx.scene.input.KeyCode.ENTER;

public class ReActivateFormController {
    private static final String URL = "jdbc:mysql://localhost:3306/SmartLibrary";
    private static final Properties props = new Properties();
    private static final Notifications notification = Notifications.create();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "Chamath2005.");
    }

    @FXML
    private Button btnConfirm;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtOtp;

    @FXML
    void btnConfirmOnAction(ActionEvent event) throws IOException {
        int otpFromDB = 0;
        int otpFromDB2 = getOtp(otpFromDB);

        if (!validateInput(otpFromDB2)) {
            return;
        }
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

    private boolean validateInput(int otpFromDB2) throws IOException {
        String otp1 = String.valueOf(otpFromDB2);

        if (!Pattern.matches("^\\d{6}$", txtOtp.getText())) {
            notification.title("Smart Library")
                    .text("Please enter a 6 digit OTP !").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return false;
        }else if(!otp1.equals(txtOtp.getText())){
            notification.title("Smart Library")
                    .text("Please enter correct digit OTP !").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return false;
        }else if(otp1.equals("")){
            notification.title("Smart Library")
                    .text("Please enter a 6 digit OTP !").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            return false;
        }else{

            try (Connection con = DriverManager.getConnection(URL, props)) {
                String sql = "UPDATE Librarian SET status = ?  WHERE Librarian_Id = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setBoolean(1, true);
                stmt.setInt(2, 1);
                stmt.executeUpdate();


            }catch (SQLException e) {
                notification.title("Smart Library")
                        .text("eSystem activate unsuccess !").darkStyle()
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT);
                notification.show();
                e.printStackTrace();
            }
            show();
            return true;
        }
    }

    public void show() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Re Activate");
        stage.centerOnScreen();

    }
}
