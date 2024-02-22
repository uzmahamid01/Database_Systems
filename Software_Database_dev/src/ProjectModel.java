import java.util.HashMap;
import java.util.Map;

public class ProjectModel {
    private int projNum;
    private String projName;
    private int projLeader;
    private Map<Integer, Double> employeeHoursBilled; // Map to store employee ID and billed hours

    public ProjectModel(int projNum, String projName, int projLeader) {
        this.projNum = projNum;
        this.projName = projName;
        this.projLeader = projLeader;
        this.employeeHoursBilled = new HashMap<>();
    }

    // Getters and Setters
    public int getProjNum() {
        return projNum;
    }

    public void setProjNum(int projNum) {
        this.projNum = projNum;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public int getProjLeader() {
        return projLeader;
    }

    public void setProjLeader(int projLeader) {
        this.projLeader = projLeader;
    }

    public Map<Integer, Double> getEmployeeHoursBilled() {
        return employeeHoursBilled;
    }

    public void setEmployeeHoursBilled(Map<Integer, Double> employeeHoursBilled) {
        this.employeeHoursBilled = employeeHoursBilled;
    }

    // Method to add employee and billed hours to the project
    public void addEmployeeHours(int empID, double hoursBilled) {
        employeeHoursBilled.put(empID, hoursBilled);
    }

    // Method to remove employee from the project
    public void removeEmployee(int empID) {
        employeeHoursBilled.remove(empID);
    }
}
