package Main;

import java.util.EnumMap;
import java.util.List;

public abstract class Employee {
    private String empID;
    protected String empName;
    protected int empAge;
    protected String empGender;
    protected String empEmail;

    private Account account;

    // Static polymorphism (constructor overloading)
    public Employee(){}

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
        System.out.println("Warning: Wrong Credential!");
        return false;
    }

    protected void viewSelfAccount(String empID){
        List<Account> accounts = account.getAllEmpCredential();
        for (Account account: accounts){
            if (account.getEmpID().equals(empID)){
                System.out.println(account.toString());
            }
        }
    }

    protected abstract void displayStaffDetails(String empID);
    protected abstract List<String> defaultStaffDetails(String empID);


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
