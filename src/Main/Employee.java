package Main;

import java.util.List;

public abstract class Employee {
    protected String empID;
    protected String empName;
    protected int empAge;
    protected String empGender;
    protected String empEmail;

    static Account account = new Account();

    public static boolean login(String empID, String empPassword){
        List<Account> empAccounts = account.getAllEmpCredential();
        for (Account account: empAccounts){
            if(account.getEmpID().equals(empID) && account.getEmpPassword().equals(empPassword)){
                System.out.println("Login Successful!");
                return true;
            }
        }
        System.out.println("Warning: Wrong Credential!");
        return false;
    }

    public abstract void viewStaffDetails(String empID);
    public abstract void editStaffDetails(String empID);


    protected String getEmpID() {
        return empID;
    }

    protected void setEmpID(String empID) {
        this.empID = empID;
    }

    protected String getEmpName() {
        return empName;
    }

    protected void setEmpName(String empName) {
        this.empName = empName;
    }

    protected int getEmpAge() {
        return empAge;
    }

    protected void setEmpAge(int empAge) {
        this.empAge = empAge;
    }

    protected String getEmpGender() {
        return empGender;
    }

    protected void setEmpGender(String empGender) {
        this.empGender = empGender;
    }

    protected String getEmpEmail() {
        return empEmail;
    }

    protected void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }
}
