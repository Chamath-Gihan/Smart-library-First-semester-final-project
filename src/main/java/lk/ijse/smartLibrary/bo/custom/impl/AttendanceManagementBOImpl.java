package lk.ijse.smartLibrary.bo.custom.impl;

import lk.ijse.smartLibrary.bo.custom.AttendanceManagementBO;
import lk.ijse.smartLibrary.dao.DAOFactory;
import lk.ijse.smartLibrary.dao.custom.AttendanceDAO;
import lk.ijse.smartLibrary.dao.custom.QueryDAO;
import lk.ijse.smartLibrary.dto.Attendance;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AttendanceManagementBOImpl implements AttendanceManagementBO {
    AttendanceDAO attendanceDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ATTENDANCE);
    QueryDAO queryDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.QUERY);

    @Override
    public boolean attendEmployers(String id, LocalDate date) throws SQLException {
        return queryDAO.attend(id, String.valueOf(date));
    }

    @Override
    public int searchEmployers(String id) throws SQLException {
        return queryDAO.search(id);
    }

    @Override
    public ArrayList<Attendance> getAllEmployeesAttendance() throws SQLException {
        return attendanceDAO.getAll();
    }
}
