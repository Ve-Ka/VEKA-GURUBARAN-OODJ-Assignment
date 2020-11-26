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

    public Customer(){}

    public Customer(String custID){
        this.custID = custID;
    }

    public Customer(String custID, String custName, String custPhoneNo){
        this.custID = custID;
        this.custName = custName;
        this.custPhoneNo = custPhoneNo;
    }

    public Customer(String custID, String custName, String custEmail, String custPhoneNo, String custAddress) {
        this.custID = custID;
        this.custName = custName;
        this.custEmail = custEmail;
        this.custPhoneNo = custPhoneNo;
        this.custAddress = custAddress;
    }

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

    protected void displayLimitedCustDetails(){
        List<Customer> customers = getAllCustDetails();
        System.out.println("\nAll customer details");
        for (Customer customer: customers){
            System.out.printf("%S|%S|%s\n", customer.getCustID(), customer.getCustName(), customer.getCustPhoneNo());
        }
        System.out.println("");
    }

    protected List<String> defaultCustomerDetails(String custID){
        List<Customer> originalDetails = getAllCustDetails();
        List<String> defaultDetails = new ArrayList<>();
        for (Customer detail : originalDetails) {
            if (detail.getCustID().equals(custID)) {
                defaultDetails.add(detail.getCustID());
                defaultDetails.add(detail.getCustName());
                defaultDetails.add(detail.getCustEmail());
                defaultDetails.add(detail.getCustPhoneNo());
                defaultDetails.add(detail.getCustAddress());
            }
        }
        return defaultDetails;
    }

    public String generateID() {
        List<Customer> defaultList = getAllCustDetails();
        String newCustID;
        try{
            newCustID = String.format("CS%04d", ((Integer.parseInt(defaultList.get(defaultList.size()
                    - 1).getCustID().replaceAll("CS", ""))) + 1));
        }catch(IndexOutOfBoundsException e){
            newCustID = "CS0001";
        }
        return newCustID;
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
        return  "Name: " + custName + '\n' +
                "Email: " + custEmail + '\n' +
                "Phone NO.: " + custPhoneNo + '\n' +
                "Home Address: " + custAddress;
    }
}
