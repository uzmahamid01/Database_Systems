import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Insert_data {
    private static final String URL = "jdbc:mysql://localhost:3306/uzma";
    private static final String USER = "root";
    private static final String PASSWORD = "masternode";

    private static Connection connection;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            insertData();
            System.out.println("Data inserted successfully.");

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

    private static void insertData() throws SQLException {
        // Insert data into Project table
        String insertProjectSQL = "INSERT INTO Project (ProjNum, ProjName, ProjLeader, TotalCharge) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertProjectSQL)) {
            statement.setInt(1, 15);
            statement.setString(2, "Evergreen");
            statement.setInt(3, 105);
            statement.executeUpdate();


            statement.setInt(1, 18);
            statement.setString(2, "Amber Wave");
            statement.setInt(3, 104);
            statement.executeUpdate();

            

            statement.setInt(1, 22);
            statement.setString(2, "Rolling Tide");
            statement.setInt(3, 113);
            statement.executeUpdate();

            statement.setInt(1, 25);
            statement.setString(2, "Starlight");
            statement.setInt(3, 101);
            statement.executeUpdate();
        }
        

        // Insert data into Employee table
        String insertEmployeeSQL = "INSERT INTO Employee (EmpNum, EmpName, JobClassID) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertEmployeeSQL)) {
            statement.setInt(1, 103);
            statement.setString(2, "June E. Arbough");
            statement.setInt(3, 1); //JobClassID for Elec. Engineer is 1
            statement.executeUpdate();

            statement.setInt(1, 101);
            statement.setString(2, "John G. News");
            statement.setInt(3, 2); //JobClassID for Database Engineer is 2
            statement.executeUpdate();

            statement.setInt(1, 105);
            statement.setString(2, "Alice K. Johnson");
            statement.setInt(3, 2); //JobClassID for Database Engineer is 2
            statement.executeUpdate();

            statement.setInt(1, 106);
            statement.setString(2, "William Smithfield");
            statement.setInt(3, 3); //JobClassID for programmer 3
            statement.executeUpdate();

            statement.setInt(1, 102);
            statement.setString(2, "David H. Senior");
            statement.setInt(3, 4); //JobClassID for system analyst 4
            statement.executeUpdate();

            statement.setInt(1, 114);
            statement.setString(2, "Annelise Jones");
            statement.setInt(3, 5); //JobClassID for application Designer 5
            statement.executeUpdate();


            statement.setInt(1, 118);
            statement.setString(2, "James J. Frommer");
            statement.setInt(3, 6); //JobClassID for general support 6
            statement.executeUpdate();


            statement.setInt(1, 104);
            statement.setString(2, "Anne K. Ramoras");
            statement.setInt(3, 4); 
            statement.executeUpdate();


            statement.setInt(1, 112);
            statement.setString(2, "Darlene M. Smithson");
            statement.setInt(3, 7); //JobClassID for DSS analyst 7
            statement.executeUpdate();

            statement.setInt(1, 113);
            statement.setString(2, "Delbert K. Joenbrood");
            statement.setInt(3, 5); 
            statement.executeUpdate();

            statement.setInt(1, 111);
            statement.setString(2, "Geoff B. Wabash");
            statement.setInt(3, 8); // JobClassID for Clerical Support 8
            statement.executeUpdate();

            statement.setInt(1, 107);
            statement.setString(2, "Maria D. Alonzo");
            statement.setInt(3, 3); 
            statement.executeUpdate();


            statement.setInt(1, 115);
            statement.setString(2, "Travis B. Bawangi");
            statement.setInt(3, 4); 
            statement.executeUpdate();


            statement.setInt(1, 108);
            statement.setString(2, "Ralph B. Washington");
            statement.setInt(3, 4);
            statement.executeUpdate();


        }

        // Insert data into JobClass table
        String insertJobClassSQL = "INSERT INTO JobClass (JobClassID, JobName, Wage) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertJobClassSQL)) {
            statement.setInt(1, 1);
            statement.setString(2, "Elec. Engineer");
            statement.setDouble(3, 84.50);
            statement.executeUpdate();

            statement.setInt(1, 2);
            statement.setString(2, "Database Designer");
            statement.setDouble(3, 105.00);
            statement.executeUpdate();

            statement.setInt(1, 3);
            statement.setString(2, "Programmer");
            statement.setDouble(3, 35.75);
            statement.executeUpdate();

            statement.setInt(1, 4);
            statement.setString(2, "System Analyst");
            statement.setDouble(3, 96.75);
            statement.executeUpdate();

            statement.setInt(1, 5);
            statement.setString(2, "Application Designer");
            statement.setDouble(3, 48.10);
            statement.executeUpdate();

            statement.setInt(1, 6);
            statement.setString(2, "General Support");
            statement.setDouble(3, 18.36);
            statement.executeUpdate();

            statement.setInt(1, 7);
            statement.setString(2, "DSS Analyst");
            statement.setDouble(3, 45.95);
            statement.executeUpdate();

            statement.setInt(1, 8);
            statement.setString(2, "Clerical Support");
            statement.setDouble(3, 26.85);
            statement.executeUpdate();

        }

        // Insert data into Assignment table
        String insertAssignmentSQL = "INSERT INTO Assignment (EmpID, ProjID, HoursBilled, TotalCharge) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertAssignmentSQL)) {
            //ProjNum 15
            statement.setInt(1, 103); 
            statement.setInt(2, 15); 
            statement.setDouble(3, 23.8); 
            statement.setDouble(4, 2011.10); 
            statement.executeUpdate();

            statement.setInt(1, 101); 
            statement.setInt(2, 15); 
            statement.setDouble(3, 19.4); 
            statement.setDouble(4, 2037.00); 
            statement.executeUpdate();


            statement.setInt(1, 105); 
            statement.setInt(2, 15); 
            statement.setDouble(3, 35.7); 
            statement.setDouble(4, 3748.50); 
            statement.executeUpdate();


            statement.setInt(1, 106); 
            statement.setInt(2, 15); 
            statement.setDouble(3, 12.6); 
            statement.setDouble(4, 450.45); 
            statement.executeUpdate();


            statement.setInt(1, 102); 
            statement.setInt(2, 15); 
            statement.setDouble(3, 23.8); 
            statement.setDouble(4, 2302.65); 
            statement.executeUpdate();


            //Project Num 18
            statement.setInt(1, 114); 
            statement.setInt(2, 18); 
            statement.setDouble(3, 24.6); 
            statement.setDouble(4, 1183.26); 
            statement.executeUpdate();

            statement.setInt(1, 118); 
            statement.setInt(2, 18); 
            statement.setDouble(3, 45.3); 
            statement.setDouble(4, 831.71); 
            statement.executeUpdate();


            statement.setInt(1, 104); 
            statement.setInt(2, 18); 
            statement.setDouble(3, 32.4); 
            statement.setDouble(4, 37135.70); 
            statement.executeUpdate();


            statement.setInt(1, 112); 
            statement.setInt(2, 18); 
            statement.setDouble(3, 44.0); 
            statement.setDouble(4, 2021.80); 
            statement.executeUpdate();

            //Proj Num 22
            statement.setInt(1, 105); 
            statement.setInt(2, 22); 
            statement.setDouble(3, 64.7); 
            statement.setDouble(4, 6793.50); 
            statement.executeUpdate();

            statement.setInt(1, 104); 
            statement.setInt(2, 22); 
            statement.setDouble(3, 48.4); 
            statement.setDouble(4, 4682.70); 
            statement.executeUpdate();

            statement.setInt(1, 113); 
            statement.setInt(2, 22); 
            statement.setDouble(3, 23.6); 
            statement.setDouble(4, 1135.16); 
            statement.executeUpdate();


            statement.setInt(1, 111); 
            statement.setInt(2, 22); 
            statement.setDouble(3, 22.0); 
            statement.setDouble(4, 591.14); 
            statement.executeUpdate();


            statement.setInt(1, 106); 
            statement.setInt(2, 22); 
            statement.setDouble(3, 12.8); 
            statement.setDouble(4, 457.60); 
            statement.executeUpdate();


            //ProjNum 25
            statement.setInt(1, 107); 
            statement.setInt(2, 25); 
            statement.setDouble(3, 24.6); 
            statement.setDouble(4, 879.45); 
            statement.executeUpdate();


            statement.setInt(1, 115); 
            statement.setInt(2, 25); 
            statement.setDouble(3, 45.8); 
            statement.setDouble(4, 4431.15); 
            statement.executeUpdate();

            statement.setInt(1, 101); 
            statement.setInt(2, 25); 
            statement.setDouble(3, 56.3); 
            statement.setDouble(4, 5811.50); 
            statement.executeUpdate();


            statement.setInt(1, 114); 
            statement.setInt(2, 25); 
            statement.setDouble(3, 33.1); 
            statement.setDouble(4, 1592.11); 
            statement.executeUpdate();


            statement.setInt(1, 108); 
            statement.setInt(2, 25); 
            statement.setDouble(3, 23.6); 
            statement.setDouble(4, 2283.30); 
            statement.executeUpdate();


            statement.setInt(1, 118); 
            statement.setInt(2, 25); 
            statement.setDouble(3, 30.5); 
            statement.setDouble(4, 559.98); 
            statement.executeUpdate();

            statement.setInt(1, 112); 
            statement.setInt(2, 25); 
            statement.setDouble(3, 41.4); 
            statement.setDouble(4, 1902.33); 
            statement.executeUpdate();

        }

    }

    
    
}
