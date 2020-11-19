package Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    public String custID;
    public String custName;
    public String custEmail;
    public String custPhoneNo;
    public String custAddress;

    protected final static String custDetailsFile = "custDetails.txt";

    protected List<Customer> getAllCustDetails(){
        List<Customer> custList = new ArrayList();
        try {
            List<String> custDetailsList = Files.readAllLines(Paths.get(custDetailsFile));
            for (String record : custDetailsList){
                String[] rec = record.split("\\|");
                Customer customer = new Customer();
                customer.setCustID(rec[0]);
                customer.setCustName(rec[1]);
                customer.setCustEmail(rec[2]);
                customer.setCustPhoneNo(rec[3]);
                customer.setCustAddress(rec[4]);
                custList.add(customer);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return custList;
    }

    protected void viewCustDetails(String custID){
        List<Customer> customers = getAllCustDetails();
        for (Customer customer: customers){
            if (customer.getCustID().equals(custID)){
                System.out.println(customer.toString());
            }
        }
    }

    public String getCustID() {
        return custID;
    }

    private void setCustID(String custID) {
        this.custID = custID;
    }

    public String getCustName() {
        return custName;
    }

    private void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustEmail() {
        return custEmail;
    }

    private void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getCustPhoneNo() {
        return custPhoneNo;
    }

    private void setCustPhoneNo(String custPhoneNo) {
        this.custPhoneNo = custPhoneNo;
    }

    public String getCustAddress() {
        return custAddress;
    }

    private void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    @Override
    public String toString() {
        return "ID: " + custID + '\n' +
                "Name: " + custName + '\n' +
                "Email: " + custEmail + '\n' +
                "Phone NO.: " + custPhoneNo + '\n' +
                "Home Address: " + custAddress + '\n';
    }
}
