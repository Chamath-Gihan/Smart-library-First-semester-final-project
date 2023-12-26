package lk.ijse.smartLibrary.dao.custom.impl;

import lk.ijse.smartLibrary.dao.custom.PaymentOfSalariesDAO;
import lk.ijse.smartLibrary.dao.custom.impl.util.CrudUtil;
import lk.ijse.smartLibrary.dto.Salary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentOfSalariesDAOImpl implements PaymentOfSalariesDAO {

    @Override
    public ArrayList<Salary> getAll() throws SQLException {
        ArrayList<Salary> data = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM PaymentOfSalaries");
        while (resultSet.next()) {
            data.add(new Salary(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3)
            ));
        }
        return data;
    }

    @Override
    public boolean add(Salary dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public Salary search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean update(Salary dto) throws SQLException {
        return false;
    }
}
