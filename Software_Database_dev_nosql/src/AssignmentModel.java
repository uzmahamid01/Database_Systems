public class AssignmentModel {
    private int empID;
    private int projID;
    private double hoursBilled;
    private double totalCharge;

    public AssignmentModel(int empID, int projID, double hoursBilled,  double totalCharge) {
        this.empID = empID;
        this.projID = projID;
        this.hoursBilled = hoursBilled;
        this.totalCharge = totalCharge;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public int getProjID() {
        return projID;
    }

    public void setProjID(int projID) {
        this.projID = projID;
    }

    public double getHoursBilled() {
        return hoursBilled;
    }

    public void setHoursBilled(double hoursBilled) {
        this.hoursBilled = hoursBilled;
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(double totalCharge) {
        this.totalCharge = totalCharge;
    }

    @Override
    public String toString() {
        return "AssignmentModel{" +
                "empID=" + empID +
                ", projID=" + projID +
                ", hoursBilled=" + hoursBilled +
                ", totalCharge=" + totalCharge +
                '}';
    }
}
