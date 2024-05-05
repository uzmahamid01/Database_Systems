public class CustomerModel {
    private int CustomerID;
    private String CustomerName;
    private String Address;
    private String Phone;
    private String Email;

    // Constructors, getters, and setters
    public CustomerModel(int CustomerID, String CustomerName, String Address, String Phone, String Email) {
        this.CustomerID = CustomerID;
        this.CustomerName = CustomerName;
        this.Address = Address;
        this.Phone = Phone;
        this.Email = Email;
    }

    // Getters and setters
    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int CustomerID) {
        this.CustomerID = CustomerID;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
}