package lk.ijse.smartLibrary.dto;

public class Attendance {
    private String employeeID;
    private String monthlyAttendanceCount;
    private String date;

    @Override
    public String toString() {
        return "Attendance{" +
                "employeeID='" + employeeID + '\'' +
                ", monthlyAttendanceCount='" + monthlyAttendanceCount + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getMonthlyAttendanceCount() {
        return monthlyAttendanceCount;
    }

    public void setMonthlyAttendanceCount(String monthlyAttendanceCount) {
        this.monthlyAttendanceCount = monthlyAttendanceCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Attendance(String employeeID, String monthlyAttendanceCount, String date) {
        this.employeeID = employeeID;
        this.monthlyAttendanceCount = monthlyAttendanceCount;
        this.date = date;
    }

    public Attendance() {
    }
}
