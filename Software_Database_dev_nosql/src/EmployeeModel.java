
public class EmployeeModel {
    public int empNum;
    public String empName;
    public JobModel job;

    //constructor:
    public EmployeeModel(int empNum, String empName, JobModel job) {
        this.empNum = empNum;
        this.empName = empName;
        this.job = job;
    }
    //getters and setters:
    public int getEmpNum() { 
        return empNum; 
    }
    public void setEmpNum(int empNum) { 
        this.empNum = empNum; 
    }
    public String getEmpName() { 
        return empName; 
    }
    public void setEmpName(String empName){ 
        this.empName = empName; 
    }
    public JobModel getJob(){
        return job;
    }
    public void setJob(JobModel job){
        this.job = job;
    }
    public static double getTotalCharge(double hoursBilled, double wage) {
        return hoursBilled * JobModel.wage; 
    }

}
