package lk.ijse.smartLibrary.bo.custom;

import lk.ijse.smartLibrary.bo.SuperBO;
import lk.ijse.smartLibrary.dao.custom.impl.util.CrudUtil;
import lk.ijse.smartLibrary.dto.Salary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SalaryManagementBO extends SuperBO {
    ArrayList<Salary> getAllSalaryPayments() throws SQLException;
}
