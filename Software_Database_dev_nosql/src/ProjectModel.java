import java.util.HashMap;
import java.util.Map;

public class ProjectModel {
    private int projNum;
    private String projName;
    private int projLeader;
    private Map<Integer, Double> assignments; // Map<EmployeeID, HoursBilled>

    public ProjectModel(int projNum, String projName, int projLeader) {
        this.projNum = projNum;
        this.projName = projName;
        this.projLeader = projLeader;
        this.assignments = new HashMap<>();
    }

    // Getters and setters
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

    //to add assignment
    public void addAssignment(int employeeId, double hoursBilled) {
        assignments.put(employeeId, hoursBilled);
    }

    //to remove assignment
    public void removeAssignment(int employeeId) {
        assignments.remove(employeeId);
    }

    //to get total billed hours for a project
    public double getTotalBilledHours() {
        double totalHours = 0;
        for (double hours : assignments.values()) {
            totalHours += hours;
        }
        return totalHours;
    }

    //to get total charge for a project
    public double getTotalCharge(double wage) {
        return getTotalBilledHours() * wage;
    }

    //to get assignments map
    public Map<Integer, Double> getAssignments() {
        return assignments;
    }

    //to set assignments map
    public void setAssignments(Map<Integer, Double> assignments) {
        this.assignments = assignments;
    }

    @Override
    public String toString() {
        return "ProjectModel{" +
                "projNum=" + projNum +
                ", projName='" + projName + '\'' +
                ", projLeader=" + projLeader +
                ", assignments=" + assignments +
                '}';
    }
}


