public class JobModel {
    public int jobClassID;
    public String jobName;
    public double wage;
    
    //constructor:
    public JobModel(int jobClassID, String jobName, double wage) {
        this.jobClassID = jobClassID;
        this.jobName = jobName;
        this.wage = wage;

    }
    
    public int getJobClassID() {
        return jobClassID;
    }

    public void setJobClassID(int jobClassID) {
        this.jobClassID = jobClassID;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public double getWage() {
        return wage;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }

}
