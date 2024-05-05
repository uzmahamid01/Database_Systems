package csce310;

public class UserModel {
    private String userID;
    private String userName;
    private String address;
    private String phone;
    private String email;

    public UserModel(String userID, String userName, String address, String phone, String email) {
        this.userID = userID;
        this.userName = userName;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    // Getters and setters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
