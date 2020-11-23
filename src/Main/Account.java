package Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private String empID;
    private String empPassword;
    protected final static String empCredentialFile = "empCredential.txt";

    public Account(){}

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

    protected String getEmpID() {
        return empID;
    }

    protected void setEmpID(String empID) {
        this.empID = empID;
    }

    protected String getEmpPassword() {
        return empPassword;
    }

    protected void setEmpPassword(String empPassword) {
        this.empPassword = empPassword;
    }

    @Override
    public String toString() {
        return "ID: " + empID + '\n' +
                "Password: " + empPassword;
    }
}
