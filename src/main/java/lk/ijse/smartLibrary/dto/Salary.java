package lk.ijse.smartLibrary.dto;

public class Salary {
    private String salaryId;
    private String employeeId;
    private double salaryForMonth;

    @Override
    public String toString() {
        return "Salary{" +
                "salaryId='" + salaryId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", salaryForMonth=" + salaryForMonth +
                '}';
    }

    public String getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(String salaryId) {
        this.salaryId = salaryId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public double getSalaryForMonth() {
        return salaryForMonth;
    }

    public void setSalaryForMonth(double salaryForMonth) {
        this.salaryForMonth = salaryForMonth;
    }

    public Salary() {
    }

    public Salary(String salaryId, String employeeId, double salaryForMonth) {
        this.salaryId = salaryId;
        this.employeeId = employeeId;
        this.salaryForMonth = salaryForMonth;
    }
}
