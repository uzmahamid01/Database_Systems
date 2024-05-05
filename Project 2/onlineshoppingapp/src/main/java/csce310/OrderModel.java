package csce310;

public class OrderModel {
    private int orderId;
    private int productId;
    private int userId;
    private double price;
    private String cardHolderName;
    private String cardNumber;
    private int cvv;

    // Constructor
    public OrderModel(int orderId, int productId, int userId, double price, String cardHolderName, String cardNumber, int cvv) {
        this.orderId = orderId;
        this.productId = productId;
        this.userId = userId;
        this.price = price;
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    // Getters and setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    // toString method
    @Override
    public String toString() {
        return "OrderModel{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", userId=" + userId +
                ", price=" + price +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", cvv=" + cvv +
                '}';
    }
}
