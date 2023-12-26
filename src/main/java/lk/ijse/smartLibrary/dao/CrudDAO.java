package lk.ijse.smartLibrary.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T, t> extends SuperDAO{
    ArrayList<T> getAll() throws SQLException;

    boolean add(T dto) throws SQLException;

    boolean delete(t id) throws SQLException;

    T search(t id) throws SQLException;

    boolean update(T dto) throws SQLException;

}
