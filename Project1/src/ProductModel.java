
public class ProductModel {
    private int ProductID;
    private String ProductName;
    private double Price;
    private String Description;
    private int Quantity;

    public ProductModel(int ProductID, String ProductName, double  Price, String Description, int Quantity) {
        this.ProductID = ProductID; 
        this.ProductName = ProductName;
        this.Price= Price;
        this.Description = Description;
        this.Quantity = Quantity;
    }
    
    // getters and setters for the attributes of the product model
    public int getProductID() { return ProductID;}
    public void setProductID(int id){this.ProductID = id;}

    public String getProductName(){return ProductName;}
    public void setProductName(String name){this.ProductName = name;}

    public double getPrice(){return Price;}
    public void setPrice(double price){this.Price = price;}

    public String getDescription(){return Description;}
    public void setDescription(String description){Description = description;}

    public int getQuantity(){return Quantity;}
    public void setQuantity(int quantity){this.Quantity = quantity;}

}
