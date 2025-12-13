package htw.webtech.myapp.persistence.entity;

import jakarta.persistence.*;

@Entity
public class AdEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String size;
    private String price;
    private String imagePath;


    @Column(nullable = false)
    private String ownerEmail;

    @Column(nullable = false)
    private boolean sold = false;

    private String buyerEmail;

    public AdEntry() {}

    public AdEntry(String brand, String size, String price, String ownerEmail) {
        this.brand = brand;
        this.size = size;
        this.price = price;
        this.ownerEmail = ownerEmail;
        this.sold = false;
    }

    public Long getId() { return id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getOwnerEmail() { return ownerEmail; }
    public void setOwnerEmail(String ownerEmail) { this.ownerEmail = ownerEmail; }

    public boolean isSold() { return sold; }
    public void setSold(boolean sold) { this.sold = sold; }

    public String getBuyerEmail() {return buyerEmail; }
    public void setBuyerEmail(String buyerEmail) { this.buyerEmail = buyerEmail; }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
