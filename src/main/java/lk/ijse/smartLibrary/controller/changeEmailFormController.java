package lk.ijse.smartLibrary.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.smartLibrary.email.Email;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;

public class changeEmailFormController {
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
    private Label lblCheck;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtEmail;

    @FXML
    void txtEmailOnKeyPressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            if (!validateInput()) {
                return;
            }
            show();
        }
    }

    @FXML
    void btnConfirmOnAction(ActionEvent event) throws IOException {
        String email1 = txtEmail.getText();

        if(!validateInput()){
            return;
        }

        int randomNumber = new Random().nextInt(900000) + 100000;
        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "UPDATE Librarian SET Librarian_Email = ?, otp = ? WHERE Librarian_Id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email1);
            stmt.setInt(2, randomNumber);
            stmt.setInt(3, 1); // Assuming you want to update the row with Librarian_Id = 1
            stmt.executeUpdate();


        }catch (SQLException e) {
            notification.title("Smart Library")
                    .text("email changed unsuccess !").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            e.printStackTrace();
        }

        Email.firstOtp(randomNumber);
        show();
    }

    private boolean validateInput() {
        if (!Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", txtEmail.getText())) {
            Platform.runLater(() -> printLabel("Please enter a valid Email Address"));
            return false;
        }
        return true;
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
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/otpConfirmation_form.fxml"));

        Scene scene = new Scene(anchorPane);

        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Confirm");
        stage.centerOnScreen();
    }
}
