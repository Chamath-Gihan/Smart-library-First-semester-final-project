package lk.ijse.smartLibrary.dao.custom;

import lk.ijse.smartLibrary.dao.CrudDAO;
import lk.ijse.smartLibrary.dto.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeDAO extends CrudDAO<Employee, String> {
    ArrayList<Employee> getAll() throws SQLException;

    boolean add(Employee employee) throws SQLException;

    boolean delete(String id) throws SQLException;

    Employee search(String id) throws SQLException;

    boolean update(Employee employee) throws SQLException;
}
