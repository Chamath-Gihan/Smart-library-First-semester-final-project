package lk.ijse.smartLibrary.report;

public class BookObject {
    private int id;
    private String name;
    private String category;
    private String about;
    private String place;
    private String qty;

    public BookObject(int id, String name, String category, String about, String place, String qty) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.about = about;
        this.place = place;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getAbout() {
        return about;
    }

    public String getPlace() {
        return place;
    }

    public String getQty() {
        return qty;
    }
}
