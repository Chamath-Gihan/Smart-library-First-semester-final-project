package lk.ijse.smartLibrary.dto;

public class Member {
    String Member_Id;
    String Member_Name;
    String Member_Address;
    String Month;

    public String getMonth() {
        return Month;
    }

    public Member(String memberId, String name, String address) {
        this.Member_Id = memberId;
        this.Member_Name = name;
        this.Member_Address = address;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public Member(String id, String name, String address, String month) {
        this.Member_Id = id;
        this.Member_Name = name;
        this.Month = month;
        this.Member_Address = address;

    }

    public void setMemberId(String memberId) {
        this.Member_Id = memberId;
    }

    public void setName(String name) {
        this.Member_Name = name;
    }

    public void setAddress(String address) {
        this.Member_Address = address;
    }

    public String getMemberId() {
        return Member_Id;
    }

    public String getName() {
        return Member_Name;
    }

    public String getAddress() {
        return Member_Address;
    }

    public Member() {
    }

    @Override
    public String toString() {
        return "member{" +
                "memberId='" + Member_Id + '\'' +
                ", name='" + Member_Name + '\'' +
                ", address='" + Member_Address + '\'' +
                '}';
    }
}
