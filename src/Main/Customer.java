package Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String custID;
    private String custName;
    private String custEmail;
    private String custPhoneNo;
    private String custAddress;

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

    protected String getCustID() {
        return custID;
    }

    protected void setCustID(String custID) {
        this.custID = custID;
    }

    protected String getCustName() {
        return custName;
    }

    protected void setCustName(String custName) {
        this.custName = custName;
    }

    protected String getCustEmail() {
        return custEmail;
    }

    protected void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    protected String getCustPhoneNo() {
        return custPhoneNo;
    }

    protected void setCustPhoneNo(String custPhoneNo) {
        this.custPhoneNo = custPhoneNo;
    }

    protected String getCustAddress() {
        return custAddress;
    }

    protected void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    @Override
    public String toString() {
        return "ID: " + custID + '\n' +
                "Name: " + custName + '\n' +
                "Email: " + custEmail + '\n' +
                "Phone NO.: " + custPhoneNo + '\n' +
                "Home Address: " + custAddress;
    }
}
