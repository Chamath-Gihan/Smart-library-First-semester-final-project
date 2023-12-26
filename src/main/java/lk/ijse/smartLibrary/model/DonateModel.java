package lk.ijse.smartLibrary.model;

import javafx.geometry.Pos;
import javafx.util.Duration;
import lk.ijse.smartLibrary.dto.Book;
import lk.ijse.smartLibrary.dto.Donate;
import lk.ijse.smartLibrary.dao.custom.impl.util.CrudUtil;
import org.controlsfx.control.Notifications;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DonateModel {
    private static final String URL = "jdbc:mysql://localhost:3306/SmartLibrary";
    private static final Properties props = new Properties();
    private static final Notifications notification = Notifications.create();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "Chamath2005.");
    }

    public static List<Donate> getAll() throws SQLException {
        List<Donate> data = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Donate");
        while (resultSet.next()) {
            data.add(new Donate(
                    resultSet.getString(1),
                    resultSet.getString(3),
                    resultSet.getString(2)
            ));
        }
        return data;
    }

    public static void addDonate(String memberId, String bookId, String quantity, String bookName1){
        try (Connection con = DriverManager.getConnection(URL, props)) {
            con.setAutoCommit(false);

            try {

                String memberSql = "SELECT * FROM Members WHERE Member_Id = ?";
                PreparedStatement memberPstm = con.prepareStatement(memberSql);
                memberPstm.setString(1, memberId);
                ResultSet memberResult = memberPstm.executeQuery();
                if (!memberResult.next()) {
                    notification.title("Smart Library")
                            .text("The entered Member ID does not exist.").darkStyle()
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.BOTTOM_RIGHT);
                    notification.show();
                    return;

                }else {
                    // Check if Book_Id exists in Books table
                    String bookSql = "SELECT * FROM Books WHERE Book_Id = ?";
                    PreparedStatement bookPstm = con.prepareStatement(bookSql);
                    bookPstm.setString(1, bookId);
                    ResultSet bookResult = bookPstm.executeQuery();
                    if (!bookResult.next()) {
                        notification.title("Smart Library")
                                .text("The entered Book ID does not exist.").darkStyle()
                                .hideAfter(Duration.seconds(5))
                                .position(Pos.BOTTOM_RIGHT);
                        notification.show();
                        return;
                    }
                }
                String donateSql = "INSERT INTO Donate (Qty, Book_Name) VALUES (?, ?)";
                PreparedStatement donatePstm = con.prepareStatement(donateSql, Statement.RETURN_GENERATED_KEYS);
                donatePstm.setString(1, quantity);
                donatePstm.setString(2, bookName1);
                int affectedRows = donatePstm.executeUpdate();

                ResultSet generatedKeys = donatePstm.getGeneratedKeys();
                generatedKeys.next();
                int donateId = generatedKeys.getInt(1);

                String updateSql = "UPDATE Books SET Qty = Qty + ? WHERE Book_Id = ?";
                PreparedStatement updatePstm = con.prepareStatement(updateSql);
                updatePstm.setString(1, quantity);
                updatePstm.setString(2, bookId);
                int affectedRows1 = updatePstm.executeUpdate();


                String memberDonateSql = "INSERT INTO MemberDonations (Donate_Id, Member_Id) VALUES (?, ?)";
                PreparedStatement memberDonatePstm = con.prepareStatement(memberDonateSql);
                memberDonatePstm.setInt(1, donateId);
                memberDonatePstm.setString(2, memberId);
                int affectedRows2 = memberDonatePstm.executeUpdate();

            }catch (SQLException ex){
                ex.printStackTrace();
                con.rollback();
                return;
            }
            notification.title("Smart Library")
                    .text("Donation Added Success !").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();

            con.commit();
            getAll();
        }catch (SQLException ex) {

            notification.title("Smart Library")
                    .text("Book Donation Added Unsuccss !").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            ex.printStackTrace();
        }
    }

    public static Book search(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Books WHERE Book_Id = ?", id);
        if (rst.next()) {
            return new Book(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6));
        }
        return null;
    }
}
