import java.sql.Date;

public class PaymentModel {
    private  int PaymentID;
    private int OrderID;
    private  double Amount;
    private Date PaymentDate;

    private String cardNumber;
    private String cardHolderName;
    private int CVV;

    public PaymentModel(int PaymentID, int OrderID, double Amount, Date PaymentDate, String cardNumber2, String cardHolderName, int CVV) {
        this.PaymentID = PaymentID;
        this.OrderID = OrderID;
        this.Amount = Amount;
        this.PaymentDate = PaymentDate;
        this.cardNumber = cardNumber2;
        this.cardHolderName = cardHolderName;
        this.CVV = CVV;
    }
    
    // Getter and Setters for the variables in the Payment Model Class
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public int getCVV() {
        return CVV;
    }

    public void setCVV(int CVV) {
        this.CVV = CVV;
    }

    public int getPaymentID() { return PaymentID;}

    public void setPaymentID(int paymentID) { PaymentID = paymentID; }

    public int getOrderID() {return OrderID ;}

    public void setOrderID(int orderID) { OrderID = orderID; }

    public double getAmount() { return Amount; }

    public void setAmount(double amount ) { Amount = amount; }

    public  Date getPaymentDate() { return PaymentDate; } 
    public  void setPaymentDate(Date paymentDate){ PaymentDate=paymentDate; }

}
