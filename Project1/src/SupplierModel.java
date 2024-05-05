
public class SupplierModel {
    private int SupplierID;
    private String SupplierName;
    private String Address;
    private String Phone;
    private String Email;

    // Constructors, getters, and setters
    public SupplierModel(int SupplierID, String SupplierName, String Address, String Phone, String Email) {
        this.SupplierID = SupplierID;
        this.SupplierName = SupplierName;
        this.Address = Address;
        this.Phone = Phone;
        this.Email = Email;
    }

    public void  setSupplierID(int SupplierID) {
        this.SupplierID=SupplierID;
    }

    public int getSupplierID() {
        return SupplierID;
    }

    public void  setSupplierName(String SupplierName) {
        this.SupplierName=SupplierName;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public String getAddress() {
        return Address;
    }

    public void  setAddress(String Address) {
        this.Address=Address;
    }

    public String getPhone() {
        return Phone;
    }

    public void  setPhone(String Phone) {
        this.Phone=Phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String  Email){    
        this.Email = Email;  
    }

}
