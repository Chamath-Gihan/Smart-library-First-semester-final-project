package lk.ijse.smartLibrary.dto.tm;

public class SalaryTM {
    private String salaryId;
    private String employeeId;
    private double salaryForMonth;

    @Override
    public String toString() {
        return "SalaryTM{" +
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

    public SalaryTM() {
    }

    public SalaryTM(String salaryId, String employeeId, double salaryForMonth) {
        this.salaryId = salaryId;
        this.employeeId = employeeId;
        this.salaryForMonth = salaryForMonth;
    }
}
