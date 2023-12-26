package lk.ijse.smartLibrary.bo.custom;

import lk.ijse.smartLibrary.bo.SuperBO;
import lk.ijse.smartLibrary.dao.custom.impl.util.CrudUtil;
import lk.ijse.smartLibrary.dto.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface BookManagementBO extends SuperBO {

    boolean addBook(Book book) throws SQLException;
    boolean deleteBook(String id) throws SQLException;
    Book searchBook(String id) throws SQLException;
    boolean updateBook(Book dto) throws SQLException;
    int getNextBookId() throws SQLException;
    int reservationId() throws SQLException;
    boolean addReservation(String bookId, String memberId, String reservationId1, String date) throws SQLException;
}
