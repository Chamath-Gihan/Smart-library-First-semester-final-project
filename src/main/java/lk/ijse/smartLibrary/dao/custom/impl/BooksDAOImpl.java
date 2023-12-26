package lk.ijse.smartLibrary.dao.custom.impl;

import lk.ijse.smartLibrary.dao.custom.BooksDAO;
import lk.ijse.smartLibrary.dao.custom.impl.util.CrudUtil;
import lk.ijse.smartLibrary.dto.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BooksDAOImpl implements BooksDAO {
    @Override
    public ArrayList<Book> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean add(Book book) throws SQLException {
        return CrudUtil.execute("INSERT INTO Books(Book_Id, Book_Name, Book_Category, About, Place, Qty)" +
                "VALUES(?, ?, ?, ?, ?, ?)", book.getBookId(), book.getBookName(), book.getBookCategory(), book.getAbout(), book.getPlace(), book.getQty());
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("DELETE FROM Books WHERE Book_Id = ?", id);
    }

    @Override
    public Book search(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Books WHERE Book_Id = ?", id);
        if (rst.next()) {
            return new Book(rst.getString(1), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7));
        }
        return null;
    }

    @Override
    public boolean update(Book dto) throws SQLException {
        return CrudUtil.execute("UPDATE Books SET Book_Name = ?, Book_Category = ?, About = ?, Place = ?, Qty = ?  WHERE Book_Id = ?",
                dto.getBookName(), dto.getBookCategory(), dto.getAbout(), dto.getPlace(), dto.getQty(), dto.getBookId());
    }
}
