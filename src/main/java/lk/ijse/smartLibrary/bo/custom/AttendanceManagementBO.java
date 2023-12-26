package lk.ijse.smartLibrary.bo.custom;

import lk.ijse.smartLibrary.bo.SuperBO;
import lk.ijse.smartLibrary.dto.Attendance;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface AttendanceManagementBO extends SuperBO {
    boolean attendEmployers(String id, LocalDate date) throws SQLException;
    int searchEmployers(String id) throws SQLException;
    ArrayList<Attendance> getAllEmployeesAttendance() throws SQLException;
}
