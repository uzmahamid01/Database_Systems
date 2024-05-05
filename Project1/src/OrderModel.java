
public class OrderModel {
    private  int OrderID;
    private  int CustomerID;
    private  String OrderDate;
    private  double TotalAmount;

    public  OrderModel(int OrderID, int CustomerID, String OrderDate,double TotalAmount) {
        this.OrderID = OrderID;
        this.CustomerID=CustomerID;
        this.OrderDate=OrderDate;  
        this.TotalAmount=TotalAmount;
    }
    //getters and setters for the order model class
    public int getOrderID()   { return OrderID; }
    public void setOrderID(int OrderID) { this.OrderID = OrderID; }

    public int getCustomerID()     {return CustomerID ;}
    public void setCustomerID(int CustomerID ) {this.CustomerID = CustomerID;}

    public String getOrderDate()           { return OrderDate ; }
    public void setOrderDate(String OrderDate) { this.OrderDate = OrderDate ; }

    public  double getTotalAmount()      { return TotalAmount; }
    public  void setTotalAmount(double TotalAmount) { this.TotalAmount = TotalAmount ; }

}
