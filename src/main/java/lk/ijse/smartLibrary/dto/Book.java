package lk.ijse.smartLibrary.dto;

public class Book {
    private String bookId;
    private String bookName;
    private String bookCategory;
    private String about;
    private String place;
    private String qty;

    @Override
    public String toString() {
        return "Book{" +
                "bookId='" + bookId + '\'' +
                ", bookName='" + bookName + '\'' +
                ", bookCategory='" + bookCategory + '\'' +
                ", about='" + about + '\'' +
                ", place='" + place + '\'' +
                ", qty='" + qty + '\'' +
                '}';
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public Book() {
    }

    public Book(String bookId, String bookName, String bookCategory, String about, String place, String qty) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookCategory = bookCategory;
        this.about = about;
        this.place = place;
        this.qty = qty;
    }
}
