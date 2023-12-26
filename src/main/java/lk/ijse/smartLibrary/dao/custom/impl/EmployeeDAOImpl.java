package lk.ijse.smartLibrary.dao.custom.impl;

import lk.ijse.smartLibrary.dao.custom.EmployeeDAO;
import lk.ijse.smartLibrary.dao.custom.impl.util.CrudUtil;
import lk.ijse.smartLibrary.dto.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public ArrayList<Employee> getAll() throws SQLException {
        ArrayList<Employee> data = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Employee");
        while (resultSet.next()) {
            data.add(new Employee(
                    resultSet.getString(1),
                    resultSet.getString(3),
                    resultSet.getString(5),
                    resultSet.getString(4),
                    resultSet.getDouble(6)
            ));
        }
        return data;
    }

    @Override
    public boolean add(Employee employee) throws SQLException {
        return CrudUtil.execute( "INSERT INTO Employee(Employee_Id, Employee_Name, Employee_Contact, Employee_Address, Salary_For_Day)" +
                "VALUES(?, ?, ?, ?, ?)", employee.getEmployeeId(), employee.getName(), employee.getContact(), employee.getAddress(), employee.getSalary());
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("DELETE FROM Employee WHERE Employee_Id = ?", id);
    }

    @Override
    public Employee search(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Employee WHERE Employee_Id = ?", id);
        if (rst.next()) {
            return new Employee(rst.getString(1), rst.getString(3), rst.getString(4), rst.getString(5), rst.getDouble(6));
        }
        return null;
    }

    @Override
    public boolean update(Employee employee) throws SQLException {
        return CrudUtil.execute("UPDATE Employee SET Employee_Name = ?, Employee_Address = ?, Employee_Contact = ?, Salary_For_Day = ? WHERE Employee_Id = ?",
                employee.getName(), employee.getAddress(), employee.getContact(), employee.getSalary(), employee.getEmployeeId());
    }
}
