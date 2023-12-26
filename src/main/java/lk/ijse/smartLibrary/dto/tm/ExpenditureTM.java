package lk.ijse.smartLibrary.dto.tm;

public class ExpenditureTM {
    private String expenditureId;
    private String detail;
    private double price;

    @Override
    public String toString() {
        return "FinanceTM{" +
                "expenditureId='" + expenditureId + '\'' +
                ", detail='" + detail + '\'' +
                ", price=" + price +
                '}';
    }

    public ExpenditureTM() {
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

    public ExpenditureTM(String expenditureId, String detail, double price) {
        this.expenditureId = expenditureId;
        this.detail = detail;
        this.price = price;
    }
}
