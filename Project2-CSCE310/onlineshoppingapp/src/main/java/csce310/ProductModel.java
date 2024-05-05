package csce310;

public class ProductModel {
    private String productID;
    private String productName;
    private String description;
    private double price;
    private int quantity;

    public ProductModel(String productID, String productName, String description, double price, int quantity) {
        this.productID = productID;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and setters
    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
