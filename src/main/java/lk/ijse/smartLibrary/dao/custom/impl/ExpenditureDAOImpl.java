package lk.ijse.smartLibrary.dao.custom.impl;

import lk.ijse.smartLibrary.dao.custom.ExpenditureDAO;
import lk.ijse.smartLibrary.dao.custom.impl.util.CrudUtil;
import lk.ijse.smartLibrary.dto.Employee;
import lk.ijse.smartLibrary.dto.Expenditure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExpenditureDAOImpl implements ExpenditureDAO {

    @Override
    public ArrayList<Expenditure> getAll() throws SQLException {
        ArrayList<Expenditure> data = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Expenditure");
        while (resultSet.next()) {
            data.add(new Expenditure(
                    resultSet.getString(1),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            ));
        }
        return data;
    }

    @Override
    public boolean add(Expenditure dto) throws SQLException {
        return CrudUtil.execute("INSERT INTO Expenditure(Expenditure_Id, Expenditure_Detail, Expenditure_Price)" +
                "VALUES(?, ?, ?)", dto.getExpenditureId(), dto.getDetail(), dto.getPrice());
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public Expenditure search(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Expenditure WHERE Expenditure_Id = ?", id);
        if(rst.next()){
            return new Expenditure(rst.getString(1), rst.getString(4), rst.getDouble(5));
        }
        return null;
    }

    @Override
    public boolean update(Expenditure dto) throws SQLException {
        return false;
    }

}
