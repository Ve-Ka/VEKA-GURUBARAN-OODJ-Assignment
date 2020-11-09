package Main;

public abstract class Employee {
    protected String empName;
    protected int empAge;
    protected String empGender;
    protected String empEmail;
    protected String empJob;

    @Override
    public String toString() {
        return "Employee{" +
                "empName='" + empName + '\'' +
                ", empAge=" + empAge +
                ", empGender='" + empGender + '\'' +
                ", empEmail='" + empEmail + '\'' +
                ", empJob='" + empJob + '\'' +
                '}';
    }
}
