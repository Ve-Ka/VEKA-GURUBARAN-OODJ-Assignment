package Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private String empID;
    private String empPassword;
    private final static String empCredentialFile = "empCredential.txt";
/*
    public static boolean login(String empID, String empPassword){
        List<Account> empAccounts = getAllEmpCredential();
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
*/
    protected List<Account> getAllEmpCredential(){
        List<Account> accountList = new ArrayList();
        try {
            List<String> empCredentialList = Files.readAllLines(Paths.get(empCredentialFile));
            for (String record : empCredentialList){
                String[] rec = record.split("\\|");
                Account account = new Account();
                account.setEmpID(rec[0]);
                account.setEmpPassword(rec[1]);
                accountList.add(account);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return accountList;
    }

    public String viewSelfAccount(){
        return "";
    }
    public void editSelfAccount(){

    }

    protected String getEmpID() {
        return empID;
    }

    private void setEmpID(String empID) {
        this.empID = empID;
    }

    protected String getEmpPassword() {
        return empPassword;
    }

    private void setEmpPassword(String empPassword) {
        this.empPassword = empPassword;
    }
}
