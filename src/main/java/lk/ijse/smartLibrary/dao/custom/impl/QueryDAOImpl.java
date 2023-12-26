package lk.ijse.smartLibrary.dao.custom.impl;

import lk.ijse.smartLibrary.dao.custom.QueryDAO;
import lk.ijse.smartLibrary.dao.custom.impl.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public boolean attend(String id, String date) throws SQLException {
        return CrudUtil.execute("INSERT IGNORE INTO Attendance(Employee_Id, Monthly_Attendance_Count, Date) " +
                "VALUES(?, ?, ?)" , id, 1, Date.valueOf(date));
    }

    @Override
    public int search(String id) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT SUM(Monthly_Attendance_Count) FROM Attendance WHERE Employee_Id = ? AND MONTH(Date) = MONTH(CURRENT_DATE())", id);
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public int getNextEmployeeId() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT MAX(Employee_Id) FROM Employee");
        if (rs.next()) {
            int maxId = rs.getInt(1);
            return maxId + 1;
        } else {
            return 1;
        }
    }

    @Override
    public int getNextExpenditureId() throws SQLException {
        ResultSet rst = CrudUtil.execute( "SELECT MAX(Expenditure_Id) FROM Expenditure");
        int nextId = 0;
        if (rst.next()) {
            nextId = rst.getInt(1) + 1;
        } else {
            nextId = 1;
        }
        return nextId;
    }

    @Override
    public double getTotalExpenditurePrice() throws SQLException {
        double totalExpenditurePrice = 0.0;
        ResultSet rst = CrudUtil.execute("SELECT SUM(Expenditure_Price) AS Expenditure_Price FROM Expenditure");

        if (rst.next()) {
            totalExpenditurePrice = rst.getDouble("Expenditure_Price");
        }
        return totalExpenditurePrice;
    }

    @Override
    public int getNextBookId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT MAX(Book_Id) FROM Books");
        int nextId = 0;
        if (rst.next()) {
            nextId = rst.getInt(1) + 1;
        }
        return nextId;
    }

    @Override
    public int reservationId() throws SQLException {
        int nextReservationId = 0;
        ResultSet rst = CrudUtil.execute("SELECT MAX(Reservation_Id) FROM Reservation");
        nextReservationId = rst.next() ? rst.getInt(1) + 1 : 1;
        return nextReservationId;
    }

    @Override
    public int getNextMemberId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT MAX(Member_Id) FROM Members");
        int nextId = 1;

        if (rst.next()) {
            nextId = rst.getInt(1) + 1;
        }
        return nextId;
    }

    @Override
    public boolean addReservation(String reservationId1, String memberId, String date) throws SQLException {
        return CrudUtil.execute("INSERT INTO Reservation (Reservation_Id , Member_Id, Bring_Date) VALUES (?, ?, ?)", reservationId1, memberId, date);
    }

    @Override
    public boolean addBookReservation(String reservationId, String bookId) throws SQLException {
        return CrudUtil.execute("INSERT INTO BookReservation (Reservation_Id, Book_Id) VALUES (?, ?)", reservationId, bookId);
    }

    @Override
    public int getReservations() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT MAX(Member_Id) FROM Reservation");
        int nextId = 1;

        if (rst.next()) {
            nextId = rst.getInt(1) + 1;
        }
        return nextId;
    }
}
