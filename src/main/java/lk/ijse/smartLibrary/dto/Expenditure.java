package lk.ijse.smartLibrary.dto;

public class Expenditure {
    private String expenditureId;
    private String detail;
    private double price;

    @Override
    public String toString() {
        return "Expenditure{" +
                "expenditureId='" + expenditureId + '\'' +
                ", detail='" + detail + '\'' +
                ", price=" + price +
                '}';
    }

    public Expenditure() {
    }

    public Expenditure(String expenditureId, String detail, double price) {
        this.expenditureId = expenditureId;
        this.detail = detail;
        this.price = price;
    }

    public String getExpenditureId() {
        return expenditureId;
    }

    public void setExpenditureId(String expenditureId) {
        this.expenditureId = expenditureId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
