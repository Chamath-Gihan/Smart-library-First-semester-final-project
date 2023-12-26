package lk.ijse.smartLibrary.report;

public class ReservationObject {
    private int reservationId;
    private int memberId;
    private int bookId;
    private String memberName;
    private String bookName;
    private String bookCategory;
    private String about;
    private String reserveDate;
    private String bringDate;

    public ReservationObject(int reservationId, int memberId, int bookId, String memberName, String bookName, String bookCategory, String about, String reserveDate, String bringDate) {
        this.reservationId = reservationId;
        this.memberId = memberId;
        this.bookId = bookId;
        this.memberName = memberName;
        this.bookName = bookName;
        this.bookCategory = bookCategory;
        this.about = about;
        this.reserveDate = reserveDate;
        this.bringDate = bringDate;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
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

    public String getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(String reserveDate) {
        this.reserveDate = reserveDate;
    }

    public String getBringDate() {
        return bringDate;
    }

    public void setBringDate(String bringDate) {
        this.bringDate = bringDate;
    }
}
