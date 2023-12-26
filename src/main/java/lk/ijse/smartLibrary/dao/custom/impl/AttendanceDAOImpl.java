package lk.ijse.smartLibrary.dao.custom.impl;

import lk.ijse.smartLibrary.dao.custom.AttendanceDAO;
import lk.ijse.smartLibrary.dao.custom.impl.util.CrudUtil;
import lk.ijse.smartLibrary.dto.Attendance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttendanceDAOImpl implements AttendanceDAO{

    @Override
    public ArrayList<Attendance> getAll() throws SQLException {
        ArrayList<Attendance> data = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Attendance");
        while (resultSet.next()) {
            data.add(new Attendance(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            ));
        }
        return data;
    }

    @Override
    public boolean add(Attendance dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public Attendance search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean update(Attendance dto) throws SQLException {
        return false;
    }
}
