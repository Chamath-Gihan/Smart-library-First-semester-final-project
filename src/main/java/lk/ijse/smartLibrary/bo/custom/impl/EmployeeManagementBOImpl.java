package lk.ijse.smartLibrary.bo.custom.impl;

import lk.ijse.smartLibrary.bo.custom.EmployeeManagementBO;
import lk.ijse.smartLibrary.dao.DAOFactory;
import lk.ijse.smartLibrary.dao.custom.EmployeeDAO;
import lk.ijse.smartLibrary.dao.custom.QueryDAO;
import lk.ijse.smartLibrary.dao.custom.impl.util.CrudUtil;
import lk.ijse.smartLibrary.dto.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeManagementBOImpl implements EmployeeManagementBO {
    EmployeeDAO employeeDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    QueryDAO queryDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.QUERY);

    @Override
    public ArrayList<Employee> getAllEmployers() throws SQLException {
        return employeeDAO.getAll();
    }

    @Override
    public boolean addEmployers(Employee employee) throws SQLException {
        return employeeDAO.add(employee);
    }

    @Override
    public boolean deleteEmployers(String id) throws SQLException {
        return employeeDAO.delete(id);
    }

    @Override
    public Employee searchEmployers(String id) throws SQLException {
        return employeeDAO.search(id);
    }

    @Override
    public boolean updateEmployers(Employee employee) throws SQLException {
        return employeeDAO.update(employee);
    }

    @Override
    public int getNextEmployeeID() throws SQLException {
        return queryDAO.getNextEmployeeId();
    }
}
