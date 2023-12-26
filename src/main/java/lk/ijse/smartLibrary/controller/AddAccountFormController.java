package lk.ijse.smartLibrary.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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

public class AddAccountFormController {
    private static final String URL = "jdbc:mysql://localhost:3306/SmartLibrary";
    private static final Properties props = new Properties();
    private static final Notifications notification = Notifications.create();
    public static String email;

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "Chamath2005.");
    }

    @FXML
    private Button btnLetsGo;

    @FXML
    private Label lblCheck;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtReEnterPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private AnchorPane root;

    public int randomNumber;

    @FXML
    void btnLetsGoOnAction(ActionEvent event) {
        if (!validateInput()) {
            return;
        }

        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        email = txtEmail.getText();
        randomNumber = new Random().nextInt(900000) + 100000;

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "INSERT INTO Librarian(Librarian_Id, Librarian_Name, Librarian_Password, Librarian_Email, status,otp) VALUES (?, ?, ?, ?, ?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, String.valueOf('1')); // Generate the Librarian Id
            stmt.setString(2, userName);
            stmt.setString(3, password);
            stmt.setString(4, email);
            stmt.setBoolean(5, true); // Set status to true by default
            stmt.setInt(6, randomNumber);
            stmt.executeUpdate();


        }catch (SQLException e) {
            notification.title("Smart Library")
                    .text("Stepl 1 not complete !").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            e.printStackTrace();
        }
        try {
            Email.firstOtp(randomNumber);
            show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void txtEmailOnKeyPressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            check();
        }
    }

    @FXML
    void txtNameOnKeyPressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            check();
        }
    }

    @FXML
    void txtPasswordOnKeyPressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            check();
        }
    }

    @FXML
    void txtReEnterPasswordOnKeyPressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            check();
        }
    }

    @FXML
    void txtUsernameOnKeyPressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                check();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void check() throws IOException {
        if (!validateInput()) {
            return;
        }
    }

    private boolean validateInput() {
        if (txtName.getText().isEmpty() || txtUserName.getText().isEmpty() || txtPassword.getText().isEmpty()
                || txtReEnterPassword.getText().isEmpty() || txtEmail.getText().isEmpty()) {
            Platform.runLater(() -> printLabel("Please fill all fields correctly"));
            return false;
        }
        else if (!txtPassword.getText().equals(txtReEnterPassword.getText())) {
            Platform.runLater(() -> printLabel("Passwords do not match"));
            return false;
        }

        else if (!Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", txtEmail.getText())) {
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
        stage.setTitle("Verify");
        stage.centerOnScreen();
    }
}



