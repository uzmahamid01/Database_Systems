import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Create_db {
    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/uzma";
    private static final String USER = "root";
    private static final String PASSWORD = "masternode";

    private static Connection connection;
    private static Statement statement;

    public static void main(String[] args) {
        try {
            // Load and register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            //statement object
            statement = connection.createStatement();

            //SQL queries to create tables
            createProjectTable();
            createEmployeeTable();
            createJobClassTable();
            createAssignmentTable();

            System.out.println("Tables created successfully.");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createProjectTable() throws SQLException {
        String createTableSQL = "CREATE TABLE Project ("
                + "ProjNum INT PRIMARY KEY,"
                + "ProjName VARCHAR(50),"
                + "ProjLeader INT REFERENCES Employee(EmpNum)"
                + ")";
        statement.execute(createTableSQL);
    }

    private static void createEmployeeTable() throws SQLException {
        String createTableSQL = "CREATE TABLE Employee ("
                + "EmpNum INT PRIMARY KEY,"
                + "EmpName VARCHAR(50),"
                + "JobClassID INT REFERENCES JobClass(JobClassID)"
                + ")";
        statement.execute(createTableSQL);
    }

    private static void createJobClassTable() throws SQLException {
        String createTableSQL = "CREATE TABLE JobClass ("
                + "JobClassID INT PRIMARY KEY,"
                + "JobName VARCHAR(50),"
                + "Wage DOUBLE"
                + ")";
        statement.execute(createTableSQL);
    }

    private static void createAssignmentTable() throws SQLException {
        String createTableSQL = "CREATE TABLE Assignment ("
                + "EmpID INT REFERENCES Employee(EmpNum),"
                + "ProjID INT REFERENCES Project(ProjNum),"
                + "HoursBilled DOUBLE,"
                + "TotalCharge DOUBLE,"
                + "PRIMARY KEY (EmpID, ProjID)"
                + ")";
        statement.execute(createTableSQL);
    }


}
