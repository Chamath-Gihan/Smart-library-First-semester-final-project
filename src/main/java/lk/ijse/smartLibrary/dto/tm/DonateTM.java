package lk.ijse.smartLibrary.dto.tm;

public class DonateTM {
    private String donateId;
    private String bookName;
    private String quantity;

    @Override
    public String toString() {
        return "DonateTM{" +
                "donateId='" + donateId + '\'' +
                ", bookName='" + bookName + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }

    public String getDonateId() {
        return donateId;
    }

    public void setDonateId(String donateId) {
        this.donateId = donateId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public DonateTM(String donateId, String bookName, String quantity) {
        this.donateId = donateId;
        this.bookName = bookName;
        this.quantity = quantity;
    }

    public DonateTM() {
    }
}
