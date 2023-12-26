package lk.ijse.smartLibrary.dao.custom;

import lk.ijse.smartLibrary.dao.CrudDAO;
import lk.ijse.smartLibrary.dao.custom.impl.util.CrudUtil;
import lk.ijse.smartLibrary.dto.Salary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PaymentOfSalariesDAO extends CrudDAO<Salary, String> {
    ArrayList<Salary> getAll() throws SQLException;
}
