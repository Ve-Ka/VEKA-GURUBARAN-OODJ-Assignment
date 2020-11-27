package Main;

import java.util.List;

public abstract class Employee {
    private String empID;
    private String empName;
    private int empAge;
    private String empGender;
    private String empEmail;

    private Account account;

    public Employee(){}

    public Employee(String empID){
        this.empID = empID;
    }

    // Composition of employee and account class
    public Employee(String empID, String empPassword){
        this.account = new Account();
        account.setEmpID(empID);
        account.setEmpPassword(empPassword);
    }

    public Employee(String empID, String empName, int empAge, String empGender, String empEmail){
        this.empID = empID;
        this.empName = empName;
        this.empAge = empAge;
        this.empGender = empGender;
        this.empEmail = empEmail;
    }

    protected boolean login(){
        List<Account> accountList = account.getAllEmpCredential();
        for (Account item: accountList){
            if(item.getEmpID().equals(account.getEmpID()) &&
                    item.getEmpPassword().equals(account.getEmpPassword())){
                System.out.println("Login Successful!");
                return true;
            }
        }
        System.out.println("Warning: Invalid Credential!");
        return false;
    }

    protected void viewSelfAccount(String empID){
        Account account = new Account();
        List<Account> accounts = account.getAllEmpCredential();
        for (Account account1: accounts){
            if (account1.getEmpID().equals(empID)){
                System.out.println(account1.toString());
            }
        }
    }

    protected abstract void displayStaffDetails(String empID);
    protected abstract List<String> defaultDetails(String ID);

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

    @Override
    public String toString() {
        return  "Name: " + empName + '\n' +
                "Age: " + empAge + '\n' +
                "Gender: " + empGender + '\n' +
                "Email: " + empEmail;
    }

}
