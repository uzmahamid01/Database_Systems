import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DataAccess {
    private static final String URL = "jdbc:mysql://localhost:3306/uzma";
    private static final String USER = "root";
    private static final String PASSWORD = "masternode";

    //JobModel
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

    //EMployee Model
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

    public EmployeeModel readEmployee(int empNum) {
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

    //ProjectModel
    public void createProject(ProjectModel project) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Project (ProjNum, ProjName, ProjLeader) VALUES (?, ?, ?)")) {
            statement.setInt(1, project.getProjNum());
            statement.setString(2, project.getProjName());
            statement.setInt(3, project.getProjLeader());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public ProjectModel readProject(int projNum) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Project WHERE ProjNum = ?")) {
            statement.setInt(1, projNum);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new ProjectModel(resultSet.getInt("ProjNum"), resultSet.getString("ProjName"), resultSet.getInt("ProjLeader"));
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteProject(int projNum) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Project WHERE ProjNum = ?")) {
            statement.setInt(1, projNum);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static double getHoursBilledForEmployeeOnProject(int empNum, int projNum) {
        double hoursBilled = 0.0;
        String query = "SELECT HoursBilled FROM Assignment WHERE EmpID = ? AND ProjID = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, empNum);
            statement.setInt(2, projNum);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    hoursBilled = resultSet.getDouble("HoursBilled");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hoursBilled;
    }

    public static List<EmployeeModel> getEmployeesForProject(int projNum) {
        List<EmployeeModel> employees = new ArrayList<>();
        String query = "SELECT * FROM Employee INNER JOIN Assignment ON Employee.EmpNum = Assignment.EmpID WHERE Assignment.ProjID = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, projNum);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int empNum = resultSet.getInt("EmpNum");
                    String empName = resultSet.getString("EmpName");
                    int jobClassID = resultSet.getInt("JobClassID");
                    JobModel job = readJobClass(jobClassID);
                    employees.add(new EmployeeModel(empNum, empName, job));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public List<AssignmentModel> getAssignmentsForProject(int projNum) {
        List<AssignmentModel> assignments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Assignment WHERE ProjID = ?")) {
            statement.setInt(1, projNum);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int empID = resultSet.getInt("EmpID");
                    double hoursBilled = resultSet.getDouble("HoursBilled");
                    double totalCharge = resultSet.getDouble("TotalCharge");
                    assignments.add(new AssignmentModel(empID, projNum, hoursBilled, totalCharge));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignments;
    }

    public void createAssignment(AssignmentModel assignment) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Assignment (EmpID, ProjID, HoursBilled) VALUES (?, ?, ?)")) {
            statement.setInt(1, assignment.getEmpID());
            statement.setInt(2, assignment.getProjID());
            statement.setDouble(3, assignment.getHoursBilled());
    
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<ProjectModel> getAllProjects() {
        List<ProjectModel> projects = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM projects");

            while (resultSet.next()) {
                int projNum = resultSet.getInt("projNum");
                String projName = resultSet.getString("projName");
                int projLeader = resultSet.getInt("projLeader");

                ProjectModel project = new ProjectModel(projNum, projName, projLeader);
                projects.add(project);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;
    }
    
}
