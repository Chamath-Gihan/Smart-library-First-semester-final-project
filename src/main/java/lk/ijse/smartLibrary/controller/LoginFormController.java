package lk.ijse.smartLibrary.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.smartLibrary.email.Email;
import lk.ijse.smartLibrary.webCam.WebCamConnect;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.File;
import java.util.Random;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeBodyPart;

import static javafx.scene.paint.Color.BLUE;

public  class LoginFormController{
    private static final String URL = "jdbc:mysql://localhost:3306/SmartLibrary";
    private static final Properties props = new Properties();
    private static final Notifications notification = Notifications.create();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "Chamath2005.");
    }

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblError1;

    @FXML
    private Label lblError2;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private Label lblExit;

    @FXML
    private AnchorPane root;

    @FXML
    private Label lblActivate;

    private int loginAttempts = 5;
    private boolean isLockedOut = false;

    private boolean isCredentialsValid(String userName, String password) {
        if (userName.equals(UserName) && password.equals(Password)) {
            return true;
        } else {
            return false;
        }
    }

    @FXML
    void lblExitOnMouseClicked(MouseEvent event) {
        Stage stage = (Stage) lblExit.getScene().getWindow();
        stage.close();
    }

    @FXML
    void lblExitOnMouseEnterd(MouseEvent event) {
        lblExit.setStyle("-fx-cursor: hand;");
        lblExit.setBorder(new Border(new BorderStroke(BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

    }

    @FXML
    void lblExitOnMouseExited(MouseEvent event) {
        lblExit.setStyle("-fx-cursor: default;");
        lblExit.setStyle("-fx-background-radius: 30; -fx-border-radius: 30; -fx-border-color: black;");

    }

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {

        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        if (txtPassword.getText().isBlank() && txtUserName.getText().isBlank()) {
            lblError1.setText("Please provide User Name and Password .          ");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
                lblError1.setText("");
            }));
            timeline.play();
            return;
        }

        if (txtUserName.getText().isBlank()) {
            lblError2.setText("Please provide User name on User name Field .    ");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
                lblError2.setText("");
            }));
            timeline.play();
            return;
        }

        if (txtPassword.getText().isBlank()) {
            lblError1.setText("Please provide Password on Password Field .      ");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
                lblError1.setText("");
            }));
            timeline.play();
            return;
        }

        if (isCredentialsValid(userName, password)) {
            System.out.println("Login successful!");
            loginAttempts = 5;
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/homepage_form.fxml"));
            Scene scene = new Scene(anchorPane);

            Stage stage = (Stage)root.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Home");
            stage.centerOnScreen();

        } else {
            loginAttempts--;
            if (loginAttempts == 0 || loginAttempts < 0) {
                isLockedOut = true;
                txtUserName.clear();
                txtPassword.clear();

                WebCamConnect webCamConnect = new WebCamConnect();
                webCamConnect.webCamUse();

                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(30), e -> {
                    isLockedOut = false;
                    lblError1.setText("");
                    loginAttempts = 5;
                }));
                timeline.play();
                txtUserName.setDisable(true);
                txtPassword.setDisable(true);
                btnLogin.setDisable(true);

                try (Connection con = DriverManager.getConnection(URL, props)) {
                    String sql = "UPDATE Librarian SET status = ?  WHERE Librarian_Id = ?";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    stmt.setBoolean(1, false);
                    stmt.setInt(2, 1);
                    stmt.executeUpdate();


                }catch (SQLException e) {
                    notification.title("Smart Library")
                            .text("System de activate unsuccess !").darkStyle()
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.BOTTOM_RIGHT);
                    notification.show();
                    e.printStackTrace();
                }


                lblActivate.setText("System was disabled . click to re activate !");

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("System Disabled !");
                alert.setContentText("Smart Library System was disabled ! ");

                alert.show();
                lblExit.setDisable(true);


                //String to = "spchandanipushpakumari@gmail.com";
                String from = "tashhtechnologies@gmail.com";
                String host = "smtp.gmail.com";

                Properties properties = System.getProperties();
                properties.put("mail.smtp.host", host);
                properties.put("mail.smtp.port", "465");
                properties.put("mail.smtp.ssl.enable", "true");
                properties.put("mail.smtp.auth", "true");

                Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("tashhtechnologies@gmail.com", "mafmowhxsnejxbwx");
                    }
                });

                try {
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(from));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                    message.setSubject("The Smart Library System was Disabled !");

                    Multipart multipart = new MimeMultipart();
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    MimeBodyPart textPart = new MimeBodyPart();

                    try {
                        File f =new File("firstCapture.jpg");

                        attachmentPart.attachFile(f);
                        textPart.setText("The system has taken a photo of the user when the system was disabled and sent it with this .");
                        multipart.addBodyPart(textPart);
                        multipart.addBodyPart(attachmentPart);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    message.setContent(multipart);
                    System.out.println("sending...");
                    Transport.send(message);
                    System.out.println("Sent message successfully....");
                } catch (MessagingException mex) {
                    mex.printStackTrace();
                }
                lblActivate.setDisable(false);
                lblExit.setDisable(false);

            } else {
                lblError1.setText("Incorrect username or password! " + loginAttempts + " attempts remaining.");

                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
                    lblError1.setText("");
                }));
                timeline.play();
                txtPassword.clear();
                txtUserName.clear();
            }
        }
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {
    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) {

    }

    @FXML
    void txtPasswordOnKeyPressed(KeyEvent event) {
        validate(event);
    }

    @FXML
    void txtUserNameOnKeyPressed(KeyEvent event) {
        validate(event);
    }

    @FXML
    void lblActivateOnMouseClicked(MouseEvent event) throws IOException {
        int randomNumber = new Random().nextInt(900000) + 100000;
        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "UPDATE Librarian SET  otp = ? WHERE Librarian_Id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, randomNumber);
            stmt.setInt(2, 1); // Assuming you want to update the row with Librarian_Id = 1
            stmt.executeUpdate();


        }catch (SQLException e) {
            notification.title("Smart Library")
                    .text("OTP send unsuccess !").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            e.printStackTrace();
        }

        Email.firstOtp(randomNumber);
        show();

    }

    public void show() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/reActivate_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Re Activate");
        stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - stage.getWidth()) / 2);
        stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - stage.getHeight()) / 2);
    }

    public void getStatus() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            PreparedStatement ps = con.prepareStatement("SELECT status FROM Librarian WHERE Librarian_Id = ?");
            ps.setString(1, "1");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                boolean status = rs.getBoolean(1);
                if (status) {
                    // Status column of id 1 is true
                } else {
                    txtPassword.setDisable(true);
                    txtUserName.setDisable(true);
                    btnLogin.setDisable(true);
                    lblActivate.setDisable(false);
                    lblActivate.setText("System was disabled . click to re activate !");
                }
            } else {
                // No row with id 1 exists
            }
        }
    }

    String UserName;
    String Password;
    String to;

    public void initialize(){
        try {
            getLibrarianCredentials();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        lblActivate.setDisable(true);
        try {
            getStatus();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getLibrarianCredentials() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "SELECT Librarian_Name, Librarian_Password , Librarian_Email FROM Librarian LIMIT 1";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                UserName = rs.getString("Librarian_Name");
                Password = rs.getString("Librarian_Password");
                to = rs.getString("Librarian_Email");
                System.out.println(UserName);
                System.out.println(Password);
                System.out.println(to);
            }
        }
    }

    private void validate(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER) {
            if (txtPassword.getText().isBlank() && txtUserName.getText().isBlank()) {
                lblError1.setText("Please provide User Name and Password .          ");
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
                    lblError1.setText("");
                }));
                timeline.play();
                return;
            }

            if (txtUserName.getText().isBlank()) {
                lblError2.setText("Please provide User name on User name Field .    ");
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
                    lblError2.setText("");
                }));
                timeline.play();
                return;
            }

            if (txtPassword.getText().isBlank()) {
                lblError1.setText("Please provide Password on Password Field .      ");
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
                    lblError1.setText("");
                }));
                timeline.play();
                return;
            }
            btnLogin.fire();
        }
    }




}