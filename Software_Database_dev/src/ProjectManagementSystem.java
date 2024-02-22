import java.sql.*;

public class ProjectManagementSystem {
    private static final String URL = "jdbc:mysql://localhost:3306/uzma";
    private static final String USER = "root";
    private static final String PASSWORD = "masternode";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            while (true) {
                System.out.println("\nAttributes:");
                System.out.println("1. Add new job class");
                System.out.println("2. Add new employee");
                System.out.println("3. Add new project");
                System.out.println("4. Add new charge");
                System.out.println("5. Display information for the project");
                System.out.println("6. Exit");
                System.out.print("Choose an option: ");

                int choice = Integer.parseInt(System.console().readLine());

                switch (choice) {
                    case 1:
                        addJobClass(statement);
                        break;
                    case 2:
                        addEmployee(statement);
                        break;
                    case 3:
                        addProject(statement);
                        break;
                    case 4:
                        addCharge(statement);
                        break;
                    case 5:
                        displayProjectInformation(statement);
                        break;
                    case 6:
                        System.out.println("Program Ending...");
                        return;
                    default:
                        System.out.println("Bad Input. Please choose again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addJobClass(Statement statement) throws SQLException {
        System.out.println("\nAdding a new job class:");
        int jobClassID = Integer.parseInt(System.console().readLine("Enter Job Class ID: "));
        String jobTitle = System.console().readLine("Enter Job Name: ");
        double hourlyWage = Double.parseDouble(System.console().readLine("Enter hourly wages: "));

        String sql = String.format("INSERT INTO JobClass VALUES (%d, '%s', %f)", jobClassID, jobTitle, hourlyWage);
        statement.executeUpdate(sql);

        System.out.println("Job class added successfully.");
    }

    private static void addEmployee(Statement statement) throws SQLException {
        System.out.println("\nAdding a new employee:");
        int employeeID = Integer.parseInt(System.console().readLine("Enter Employee ID: "));
        String employeeName = System.console().readLine("Enter Employee name: ");
        int jobClassID = Integer.parseInt(System.console().readLine("Enter Job Class ID: "));

        String sql = String.format("INSERT INTO Employee VALUES (%d, '%s', %d)", employeeID, employeeName, jobClassID);
        statement.executeUpdate(sql);

        System.out.println("Employee added successfully.");
    }

    private static void addProject(Statement statement) throws SQLException {
        System.out.println("\nAdding a new project:");
        int projectID = Integer.parseInt(System.console().readLine("Enter Project ID: "));
        String projectName = System.console().readLine("Enter Project Name: ");
        int projectLeaderID = Integer.parseInt(System.console().readLine("Enter Project Leader ID: "));

        String sql = String.format("INSERT INTO Project VALUES (%d, '%s', %d)", projectID, projectName, projectLeaderID);
        statement.executeUpdate(sql);

        System.out.println("Project added successfully.");
    }

    private static void addCharge(Statement statement) throws SQLException {
        System.out.println("\nAdding a new charge:");
        int projectID = Integer.parseInt(System.console().readLine("Enter Project ID: "));
        int employeeID = Integer.parseInt(System.console().readLine("Enter Employee ID: "));
        double billedHours = Double.parseDouble(System.console().readLine("Enter Billed Hours: "));

        // You need to compute the charge and update the total charge for the project here
        // This is just a placeholder
        double totalCharge = billedHours * getHourlyWageForEmployee(employeeID, statement);
        String sql = String.format("INSERT INTO Assignment VALUES (%d, %d, %f, %f)", employeeID, projectID, billedHours, totalCharge);
        statement.executeUpdate(sql);

        System.out.println("Charges added successfully. Total Charge: $" + totalCharge);
    }

    private static double getHourlyWageForEmployee(int employeeID, Statement statement) throws SQLException {
        String sql = String.format("SELECT Wage FROM JobClass WHERE JobClassID = (SELECT JobClassID FROM Employee WHERE EmpNum = %d)", employeeID);
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            return resultSet.getDouble("Wage");
        } else {
            return 0; 
        }
    }

    private static void displayProjectInformation(Statement statement) throws SQLException {
        System.out.println("\nDisplaying Information on the Project:");
        String projectName = System.console().readLine("Enter Project Name: ");

        String sql = String.format("SELECT * FROM Assignment WHERE ProjID = (SELECT ProjNum FROM Project WHERE ProjName = '%s')", projectName);
        ResultSet resultSet = statement.executeQuery(sql);

        double SubTotal = 0.0;
        System.out.println("Project information for project '" + projectName + "':");
        while (resultSet.next()) {
            int employeeID = resultSet.getInt("EmpID");
            double billedHours = resultSet.getDouble("HoursBilled");
            double totalCharge = resultSet.getDouble("TotalCharge");

            SubTotal += totalCharge;

            System.out.println("Employee ID: " + employeeID + ", Billed Hours: " + billedHours + ", Total Charge: " + totalCharge);
        }
        System.out.println("Total Charge for the Whole Project: " + SubTotal);
    }


}
