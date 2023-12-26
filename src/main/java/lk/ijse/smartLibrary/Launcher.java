package lk.ijse.smartLibrary;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.smartLibrary.db.DBConnection;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Launcher extends Application {
//    private static final String URL = "jdbc:mysql://localhost:3306/SmartLibrary";
//    private static final Properties props = new Properties();
    private static final Notifications notification = Notifications.create();

    static {
//        props.setProperty("user", "root");
//        props.setProperty("password", "Chamath2005.");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Connection con = DBConnection.getInstance().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Librarian");
            rs.next();
            int count = rs.getInt(1);
            if (count == 0) {
                addAccount(primaryStage);
            } else {
                PreparedStatement ps = con.prepareStatement("SELECT status FROM Librarian WHERE Librarian_Id = ?");
                ps.setString(1, "1");
                ResultSet rs2 = ps.executeQuery();
                if (rs2.next()) {
                    boolean status = rs2.getBoolean(1);
                    if (status) {
                        login(primaryStage);
                    } else {
                        login(primaryStage);
                    }
                } else {

                }
            }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void addAccount(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addAccount_form.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Create Account");
        stage.centerOnScreen();
        stage.show();
    }

    public void login(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.centerOnScreen();
        stage.show();
    }

}
