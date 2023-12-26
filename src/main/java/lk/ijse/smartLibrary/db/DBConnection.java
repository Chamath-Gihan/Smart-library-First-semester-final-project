package lk.ijse.smartLibrary.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static lk.ijse.smartLibrary.db.DBConnection dbConnection;
    private Connection con;

    private DBConnection() throws SQLException {
        con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/SmartLibrary",
                "root",
                "Chamath2005."
        );
    }

    public static lk.ijse.smartLibrary.db.DBConnection getInstance() throws SQLException {
        return (null == dbConnection) ? dbConnection = new lk.ijse.smartLibrary.db.DBConnection()
                : dbConnection;
    }
    public Connection getConnection() {
        return con;
    }
}
