import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataAccess {
    private Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/storemanagement";
    private static final String USER = "root";
    private static final String PASSWORD = "masternode";

    public DataAccess(Connection connection) {
        this.connection = connection;
    }
    public Connection getConnection() {
        return connection;
    }

    // Method to add a new customer to the database
    public void addCustomer(CustomerModel customer) throws SQLException {
        String sql = "INSERT INTO Customer (CustomerID, CustomerName, Address, Phone, Email) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, customer.getCustomerID());
            statement.setString(2, customer.getCustomerName());
            statement.setString(3, customer.getAddress());
            statement.setString(4, customer.getPhone());
            statement.setString(5, customer.getEmail());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update an existing customer in the database
    public void updateCustomer(CustomerModel customer) throws SQLException {
        String sql = "UPDATE Customer SET CustomerName=?, Address=?, Phone=?, Email=? WHERE CustomerID=?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getAddress());
            statement.setString(3, customer.getPhone());
            statement.setString(4, customer.getEmail());
            statement.setInt(5, customer.getCustomerID());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Method to add a new supplier to the database
    public void addSupplier(SupplierModel supplier) throws SQLException {
        String sql = "INSERT INTO Suppliers (SupplierID, SupplierName, Address, Phone, Email) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, supplier.getSupplierID());
            statement.setString(2, supplier.getSupplierName());
            statement.setString(3, supplier.getAddress());
            statement.setString(4, supplier.getPhone());
            statement.setString(5, supplier.getEmail());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update an existing supplier in the database
    public void updateSupplier(SupplierModel supplier) throws SQLException {
        String sql = "UPDATE Suppliers SET SupplierName=?, Address=?, Phone=?, Email=? WHERE SupplierID=?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, supplier.getSupplierName());
            statement.setString(2, supplier.getAddress());
            statement.setString(3, supplier.getPhone());
            statement.setString(4, supplier.getEmail());
            statement.setInt(5, supplier.getSupplierID()); 
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to add a new product to the database
    public void addProduct(ProductModel product) throws SQLException {
        String sql = "INSERT INTO Products (ProductID, ProductName, Description, Price, Quantity) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, product.getProductID());
            statement.setString(2, product.getProductName());
            statement.setString(3, product.getDescription());
            statement.setDouble(4, product.getPrice());
            statement.setInt(5, product.getQuantity());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update an existing product in the database
    public void updateProduct(ProductModel product) throws SQLException {
        String sql = "UPDATE Products SET ProductName=?, Description=?, Price=?, Quantity=? WHERE ProductID=?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getProductName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getQuantity());
            statement.setInt(5, product.getProductID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to add a new order to the database
    public void addOrder(OrderModel order) throws SQLException {
        String sql = "INSERT INTO Orders (OrderID, CustomerID, OrderDate, TotalAmount) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, order.getOrderID());
            statement.setInt(2, order.getCustomerID());
            statement.setString(3, order.getOrderDate());
            statement.setDouble(4, order.getTotalAmount());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update an existing order in the database
    public void updateOrder(OrderModel order) throws SQLException {
        String sql = "UPDATE Orders SET CustomerID=?, OrderDate=?, TotalAmount=? WHERE OrderID=?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order.getCustomerID());
            statement.setString(2, order.getOrderDate());
            statement.setDouble(3, order.getTotalAmount());
            statement.setInt(4, order.getOrderID());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to add a new payment to the database
    public void addPayment(PaymentModel payment) throws SQLException {
        String sql = "INSERT INTO Payments (PaymentID, OrderID, Amount, PaymentDate, cardNumber, cardHolderName, CVV) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, payment.getPaymentID());
            statement.setInt(2, payment.getOrderID());
            statement.setDouble(3, payment.getAmount());
            statement.setDate(4, payment.getPaymentDate());
            statement.setString(5, payment.getCardNumber()); 
            statement.setString(6, payment.getCardHolderName());
            statement.setInt(7, payment.getCVV());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update an existing payment in the database
    public void updatePayment(PaymentModel payment) throws SQLException {
        String sql = "UPDATE Payments SET OrderID=?, Amount=?, PaymentDate=?, cardNumber = ?, cardHolderName = ?, CVV= ? WHERE PaymentID=?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, payment.getOrderID());
            statement.setDouble(2, payment.getAmount());
            statement.setDate(3, payment.getPaymentDate());
            statement.setString(4, payment.getCardNumber());
            statement.setString(5, payment.getCardHolderName());
            statement.setInt(6, payment.getCVV());
            statement.setInt(7, payment.getPaymentID()); 
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    
}
