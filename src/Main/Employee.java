package Main;

import java.util.List;

public abstract class Employee {
    protected String empName;
    protected int empAge;
    protected String empGender;
    protected String empEmail;
    protected String empJob;

    static Account account = new Account();
    public static boolean login(String empID, String empPassword){
        List<Account> empAccounts = account.getAllEmpCredential();
        for (Account account: empAccounts){
            if(account.getEmpID().equals(empID) && account.getEmpPassword().equals(empPassword)){
                System.out.println("Employee Exist!");
                return true;
            }
            else{
                System.out.println(account.getEmpID() + account.getEmpPassword());
            }
        }
        return false;
    }


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
