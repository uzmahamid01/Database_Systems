import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class delete_column {
    private static final String URL = "jdbc:mysql://localhost:3306/uzma";
    private static final String USER = "root";
    private static final String PASSWORD = "masternode";

    private static Connection connection;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            deleteTotalChargeAttribute();
            System.out.println("Column deleted successfully.");

            

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private static void deleteTotalChargeAttribute() throws SQLException {
        // Alter the Project table to drop the TotalCharge attribute
        String alterTableSQL = "ALTER TABLE Project DROP COLUMN TotalCharge";
        try (PreparedStatement statement = connection.prepareStatement(alterTableSQL)) {
            statement.execute(alterTableSQL);
        }
    } 
}
