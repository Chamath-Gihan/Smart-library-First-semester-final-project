package lk.ijse.smartLibrary.bo.custom;

import lk.ijse.smartLibrary.bo.SuperBO;
import lk.ijse.smartLibrary.dto.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeManagementBO extends SuperBO {
    ArrayList<Employee> getAllEmployers() throws SQLException;

    boolean addEmployers(Employee employee) throws SQLException;

    boolean deleteEmployers(String id) throws SQLException;

    Employee searchEmployers(String id) throws SQLException;

    boolean updateEmployers(Employee employee) throws SQLException;

    int getNextEmployeeID() throws SQLException;
}
