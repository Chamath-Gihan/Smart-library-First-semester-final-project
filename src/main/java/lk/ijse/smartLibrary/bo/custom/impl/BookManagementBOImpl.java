package lk.ijse.smartLibrary.bo.custom.impl;

import javafx.geometry.Pos;
import javafx.util.Duration;
import lk.ijse.smartLibrary.bo.custom.BookManagementBO;
import lk.ijse.smartLibrary.dao.DAOFactory;
import lk.ijse.smartLibrary.dao.custom.BooksDAO;
import lk.ijse.smartLibrary.dao.custom.MembersDAO;
import lk.ijse.smartLibrary.dao.custom.QueryDAO;
import lk.ijse.smartLibrary.db.DBConnection;
import lk.ijse.smartLibrary.dto.Book;
import lk.ijse.smartLibrary.dto.Member;

import java.sql.*;

public class BookManagementBOImpl implements BookManagementBO {
    BooksDAO booksDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.BOOKS);
    QueryDAO queryDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.QUERY);
    MembersDAO membersDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.MEMBERS);

    @Override
    public boolean addBook(Book book) throws SQLException {
        return booksDAO.add(book);
    }

    @Override
    public boolean deleteBook(String id) throws SQLException {
        return booksDAO.delete(id);
    }

    @Override
    public Book searchBook(String id) throws SQLException {
        return booksDAO.search(id);
    }

    @Override
    public boolean updateBook(Book dto) throws SQLException {
        return booksDAO.update(dto);
    }

    @Override
    public int getNextBookId() throws SQLException {
        return queryDAO.getNextBookId();
    }

    @Override
    public int reservationId() throws SQLException {
        return queryDAO.reservationId();
    }

    @Override
    public boolean addReservation(String bookId, String memberId, String reservationId1, String date) throws SQLException {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            Member memberResult = membersDAO.search(memberId);
            if ( memberResult == null) {
                return false;
            }

            Book bookResult = booksDAO.search(bookId);
            if (bookResult == null) {
                return false;
            }

            if (queryDAO.addReservation(reservationId1, memberId, date)) {
                queryDAO.addBookReservation(String.valueOf(queryDAO.getReservations()), memberId);
            }
            con.commit();

        } catch (SQLException ex) {
            ex.printStackTrace();
            con.rollback();
            return false;
        }

        return true;
    }
}
