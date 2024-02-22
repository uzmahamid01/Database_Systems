import java.sql.*;
import java.util.Map;

public class DataAccess {
    private static final String URL = "jdbc:mysql://localhost:3306/uzma";
    private static final String USER = "root";
    private static final String PASSWORD = "masternode";

    public static void createJobClass(JobModel job) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement statement = connection.prepareStatement("INSERT INTO JobClass (JobCLassID, JobName, Wage) VALUES (?, ?, ?)")){
            statement.setInt(1, job.getJobClassID());
            statement.setString(2, job.getJobName());
            statement.setDouble(3, job.getWage());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static JobModel readJobClass(int jobClassID) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM JobClass WHERE JobClassID = ?")) {
            statement.setInt(1, jobClassID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new JobModel(resultSet.getInt("JobClassID"), resultSet.getString("JobName"), resultSet.getDouble("Wage"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void updateJobClass(JobModel job) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("UPDATE JobClass SET JobName = ?, Wage = ? WHERE JobClassID = ?")) {
            statement.setString(1, job.getJobName());
            statement.setDouble(2, job.getWage());
            statement.setInt(3, job.getJobClassID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public static void deleteJobClass(int jobClassID) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement statement = connection.prepareStatement("DELETE FROM JobClass WHERE JobClassID = ?")){
            statement.setInt(1, jobClassID);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void createEmployee(EmployeeModel employee) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Employee (EmpNum, EmpName, JobClassID) VALUES (?, ?, ?)")) {
            statement.setInt(1, employee.getEmpNum());
            statement.setString(2, employee.getEmpName());
            statement.setInt(3, employee.getJob().getJobClassID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static EmployeeModel readEmployee(int empNum) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Employee WHERE EmpNum = ?")) {
            statement.setInt(1, empNum);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int jobClassID = resultSet.getInt("JobClassID");
                    JobModel job = readJobClass(jobClassID);
                    return new EmployeeModel(resultSet.getInt("EmpNum"), resultSet.getString("EmpName"), job);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateEmployee(EmployeeModel employee) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("UPDATE Employee SET EmpName = ?, JobClassID = ? WHERE EmpNum = ?")) {
            statement.setString(1, employee.getEmpName());
            statement.setInt(2, employee.getJob().getJobClassID());
            statement.setInt(3, employee.getEmpNum());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteEmployee(int empNum) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Employee WHERE EmpNum = ?")) {
            statement.setInt(1, empNum);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //For projectClass
    public static void createProject(ProjectModel project) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Project (ProjNum, ProjName, ProjLeader) VALUES (?, ?, ?)")) {
            statement.setInt(1, project.getProjNum());
            statement.setString(2, project.getProjName());
            statement.setInt(3, project.getProjLeader());
            statement.executeUpdate();


            //insert task assignment for the project
            for (Map.Entry<Integer, Double> entry : project.getEmployeeHoursBilled().entrySet()) {
                int empID = entry.getKey();
                double hoursBilled = entry.getValue();
                createAssignment(connection, project.getProjNum(), empID, hoursBilled);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ProjectModel readProject(int projNum) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Project WHERE ProjNum = ?")) {
            statement.setInt(1, projNum);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int projLeader = resultSet.getInt("ProjLeader");
                    ProjectModel project = new ProjectModel(projNum, resultSet.getString("ProjName"), projLeader);
                    // Load task assignments for the project
                    loadAssignments(connection, project);
                    return project;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateProject(ProjectModel project) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("UPDATE Project SET ProjName = ?, ProjLeader = ? WHERE ProjNum = ?")) {
                statement.setString(1, project.getProjName());
                statement.setInt(2, project.getProjLeader());
                statement.setInt(3, project.getProjNum());
                statement.executeUpdate();

                //update task assignments for the project
                deleteAssignments(connection, project.getProjNum());
                for (Map.Entry<Integer, Double> entry : project.getEmployeeHoursBilled().entrySet()) {
                    int empID = entry.getKey();
                    double hoursBilled = entry.getValue();
                    createAssignment(connection, project.getProjNum(), empID, hoursBilled);
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteProject(int projNum) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Project WHERE ProjNum = ?")) {
            statement.setInt(1, projNum);
            statement.executeUpdate();
            // Also delete task assignments for the project
            deleteAssignments(connection, projNum);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method to create task assignments for a project
    private static void createAssignment(Connection connection, int projNum, int empID, double hoursBilled) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO Assignment (EmpID, ProjID, HoursBilled) VALUES (?, ?, ?)")) {
            statement.setInt(1, empID);
            statement.setInt(2, projNum);
            statement.setDouble(3, hoursBilled);
            statement.executeUpdate();
        }
    }

    // Helper method to load task assignments for a project
    private static void loadAssignments(Connection connection, ProjectModel project) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Assignment WHERE ProjID = ?")) {
            statement.setInt(1, project.getProjNum());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int empID = resultSet.getInt("EmpID");
                    double hoursBilled = resultSet.getDouble("HoursBilled");
                    project.addEmployeeHours(empID, hoursBilled);
                }
            }
        }
    }

    // Helper method to delete task assignments for a project
    private static void deleteAssignments(Connection connection, int projNum) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Assignment WHERE ProjID = ?")) {
            statement.setInt(1, projNum);
            statement.executeUpdate();
        }
    }
    // Method to get employee name from the database based on empID
    public static String getEmployeeNameFromDatabase(int empID) {
        String employeeName = null;
        String query = "SELECT EmpName FROM Employee WHERE EmpNum = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, empID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    employeeName = resultSet.getString("EmpName");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeName;
    }

    
}
