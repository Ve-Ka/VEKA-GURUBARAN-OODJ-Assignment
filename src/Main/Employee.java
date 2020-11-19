package Main;

import java.util.List;

public abstract class Employee {
    private String empID;
    protected String empName;
    protected int empAge;
    protected String empGender;
    protected String empEmail;

    protected static Account account;

    public static boolean login(String empID, String empPassword){
        account = new Account();
        List<Account> accounts = account.getAllEmpCredential();
        for (Account account: accounts){
            if(account.getEmpID().equals(empID) && account.getEmpPassword().equals(empPassword)){
                System.out.println("Login Successful!");
                return true;
            }
        }
        System.out.println("Warning: Wrong Credential!");
        return false;
    }

    protected abstract void viewStaffDetails(String empID);
    protected abstract void editStaffDetails(String empID, List<String> empDetails);


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
