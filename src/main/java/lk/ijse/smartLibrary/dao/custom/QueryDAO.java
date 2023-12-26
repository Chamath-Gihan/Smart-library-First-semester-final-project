package lk.ijse.smartLibrary.dao.custom;

import lk.ijse.smartLibrary.dao.SuperDAO;
import lk.ijse.smartLibrary.dao.custom.impl.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface QueryDAO extends SuperDAO {
    boolean attend(String id, String date) throws SQLException;

    int search(String id) throws SQLException;

    int getNextEmployeeId() throws SQLException;

    int getNextExpenditureId() throws SQLException;

    double getTotalExpenditurePrice() throws SQLException;

    int getNextBookId() throws SQLException;

    int reservationId() throws SQLException;

    int getNextMemberId() throws SQLException;

    boolean addReservation(String reservationId1, String memberId, String date) throws SQLException;

    boolean addBookReservation(String reservationId, String bookId) throws SQLException;

    int getReservations() throws SQLException;
}
