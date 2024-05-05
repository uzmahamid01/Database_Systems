import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JTextArea;


public class SalesReports {

    private final Connection connection;

    public SalesReports(Connection connection) {
        this.connection = connection;
    }

    public void generateTotalSalePerMonthReport(String month, Date startDate, Date endDate, boolean descending, JTextArea reportTextArea) {
        Map<String, Double> monthlySales = new HashMap<>();
    
        String sql = "SELECT DATE_FORMAT(OrderDate, '%Y-%m') AS Month, SUM(TotalAmount) AS TotalSale " +
                "FROM Orders " +
                "WHERE OrderDate BETWEEN ? AND ? AND DATE_FORMAT(OrderDate, '%Y-%m') = ? " +
                "GROUP BY Month " ;
                // "ORDER BY TotalSale " + (descending ? "DESC" : "ASC") +
                // " LIMIT 100";
    
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            statement.setString(1, dateFormat.format(startDate));
            statement.setString(2, dateFormat.format(endDate));
            statement.setString(3, month);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String monthLabel = resultSet.getString("Month");
                    double totalSale = resultSet.getDouble("TotalSale");
                    monthlySales.put(monthLabel, totalSale);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    
        reportTextArea.setText("");
        reportTextArea.append("Month\tTotal Sale\n");
        for (Map.Entry<String, Double> entry : monthlySales.entrySet()) {
            reportTextArea.append(entry.getKey() + "\t" + entry.getValue() + "\n");
        }
    }
    public void generateTotalSalePerProductReport(Date startDate, Date endDate, boolean descending, JTextArea reportTextArea) {
        Map<String, Double> productSales = new HashMap<>();
        String sql = "SELECT p.ProductID, p.ProductName, SUM(pm.Amount) AS TotalSale " +
             "FROM Products p " +
             "LEFT JOIN Orders o ON p.ProductID = o.OrderID " +
             "LEFT JOIN Payments pm ON o.OrderID = pm.OrderID " +
             "WHERE o.OrderDate BETWEEN ? AND ? " +
             "GROUP BY p.ProductID, p.ProductName "; 
            //  "ORDER BY TotalSale DESC " +  
            //  "LIMIT 10;";
    
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, (java.sql.Date) startDate);
            statement.setDate(2, (java.sql.Date) endDate);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String productName = resultSet.getString("ProductName");
                    double totalSale = resultSet.getDouble("TotalSale");
                    productSales.put( productName, totalSale);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        reportTextArea.setText("");
        reportTextArea.append("Product\tTotal Sale\n");
        for (Map.Entry<String, Double> entry : productSales.entrySet()) {
            reportTextArea.append(entry.getKey() + "\t" + entry.getValue() + "\n");
        }
    }
    

    public void generateTotalSalePerCustomerReport(java.sql.Date startDate, java.sql.Date endDate, boolean b, JTextArea reportTextArea) {
        Map<String, Double> customerSales = new HashMap<>();
    
        String sql = "SELECT o.CustomerID, c.CustomerName, SUM(p.Amount) AS TotalSale " +
             "FROM Orders o " +
             "JOIN Payments p ON o.OrderID = p.OrderID " +
             "JOIN Customer c ON o.CustomerID = c.CustomerID " +
             "WHERE o.OrderDate BETWEEN ? AND ? " +
             "GROUP BY o.CustomerID, c.CustomerName";
    
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, startDate);
            preparedStatement.setDate(2, endDate);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String customerName = resultSet.getString("CustomerName");
                    double totalSale = resultSet.getDouble("TotalSale");
                    customerSales.put(customerName, totalSale);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    
        reportTextArea.setText("");
        reportTextArea.append("Customer Name\tTotal Sale\n");
        for (Map.Entry<String, Double> entry : customerSales.entrySet()) {
            reportTextArea.append(entry.getKey() + "\t" + entry.getValue() + "\n");
        }
    }
    

}
