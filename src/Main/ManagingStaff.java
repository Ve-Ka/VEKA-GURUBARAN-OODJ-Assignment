package Main;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class ManagingStaff extends Employee{
    private final static String managingStaffDetailsFile = "managingStaffDetails.txt";

    @Override
    protected void viewStaffDetails(String empID) {
        List<ManagingStaff> managingStaffDetailsList = getAllManagingStaffDetails();
        for (ManagingStaff managingStaff: managingStaffDetailsList){
            if(managingStaff.getEmpID().equals(empID)){
                System.out.println(managingStaff.toString());
            }
        }
    }

    @Override
    protected void editStaffDetails(String empID, List<String> empDetails) {
        // load data in to default list
        List<ManagingStaff> managingStaffDetails = getAllManagingStaffDetails();
        List<String> managingStaffDefaultDetails = new ArrayList<>();
        for (ManagingStaff managingStaff : managingStaffDetails) {
            if (managingStaff.getEmpID().equals(empID)) {
                managingStaffDefaultDetails.add(managingStaff.getEmpID());
                managingStaffDefaultDetails.add(managingStaff.getEmpName());
                managingStaffDefaultDetails.add(Integer.toString(managingStaff.getEmpAge()));
                managingStaffDefaultDetails.add(managingStaff.getEmpGender());
                managingStaffDefaultDetails.add(managingStaff.getEmpEmail());
            }
        }

        // verify and replace empty value with default
        List<String> managingStaffVerifiedDetails = new ArrayList<>();
        for(int a = 0; a < empDetails.toArray().length; a ++){
            if(empDetails.get(a).equals("")){
                managingStaffVerifiedDetails.add(managingStaffDefaultDetails.get(a));
            } else{
                managingStaffVerifiedDetails.add(empDetails.get(a));
            }
        }

        // overwrite default object list
        for (ManagingStaff managingStaff : managingStaffDetails) {
            if (managingStaff.getEmpID().equals(empID)) {
                managingStaff.setEmpName(managingStaffVerifiedDetails.get(1));
                managingStaff.setEmpAge(Integer.parseInt(managingStaffVerifiedDetails.get(2)));
                managingStaff.setEmpGender(managingStaffVerifiedDetails.get(3));
                managingStaff.setEmpEmail(managingStaffVerifiedDetails.get(4));
            }
        }

        // write to file
        try{
            FileWriter WriteData = new FileWriter(managingStaffDetailsFile);
            for (ManagingStaff managingStaff : managingStaffDetails) {
                WriteData.write(String.format("%s|%s|%d|%s|%s\n", managingStaff.getEmpID(), managingStaff.getEmpName(),
                        managingStaff.getEmpAge(), managingStaff.getEmpGender(), managingStaff.getEmpEmail()));
            }
            WriteData.close();
            System.out.println("Alert: Details Updated!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    // modify all staff section
    private List<ManagingStaff> getAllManagingStaffDetails(){
        List<ManagingStaff> managingStaffDetailsList = new ArrayList();
        try {
            List<String> empDetailsList = Files.readAllLines(Paths.get(managingStaffDetailsFile));
            for (String record : empDetailsList){
                String[] rec = record.split("\\|");
                ManagingStaff managingStaff = new ManagingStaff();
                managingStaff.setEmpID(rec[0]);
                managingStaff.setEmpName(rec[1]);
                managingStaff.setEmpAge(Integer.parseInt(rec[2]));
                managingStaff.setEmpGender(rec[3]);
                managingStaff.setEmpEmail(rec[4]);
                managingStaffDetailsList.add(managingStaff);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return managingStaffDetailsList;
    }

    protected void addEmpAccount(String newEmpID, String newEmpPassword, List<String> newStaffDetails){
        try{
            // Write to Credential File
            FileWriter WriteData = new FileWriter(Account.empCredentialFile, true);
            WriteData.write(String.format("%s|%s\n", newEmpID, newEmpPassword));
            WriteData.close();

            // Write to Staff Details File
            if(newEmpID.contains("MS")){
                WriteData = new FileWriter(managingStaffDetailsFile, true);
                WriteData.write(String.format("%s|%s|%d|%s|%s\n", newStaffDetails.get(0), newStaffDetails.get(1),
                        Integer.parseInt(newStaffDetails.get(2)), newStaffDetails.get(3), newStaffDetails.get(4)));
            }else if(newEmpID.contains("DS")){
                WriteData = new FileWriter(DeliveryStaff.deliveryStaffDetailsFile, true);
                WriteData.write(String.format("%s|%s|%d|%s|%s|%s|%s\n", newStaffDetails.get(0), newStaffDetails.get(1),
                        Integer.parseInt(newStaffDetails.get(2)), newStaffDetails.get(3), newStaffDetails.get(4),
                        newStaffDetails.get(5), newStaffDetails.get(6)));
            }

            WriteData.close();
            System.out.println("Alert: New Account and Details Added!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    protected void removeEmpAccount(String empID){
        // Remove Account from Account Object List
        List<Account> accounts = account.getAllEmpCredential();
        accounts.removeIf(account -> account.getEmpID().equals(empID));

        // Remove Details from Details Object List
        List<ManagingStaff> managingStaffs = getAllManagingStaffDetails();
        List<DeliveryStaff> deliveryStaffs = DeliveryStaff.getAllDeliveryStaffDetails();
        if (empID.contains("MS")){
            managingStaffs.removeIf(managingStaff -> managingStaff.getEmpID().equals(empID));
        }else if (empID.contains("DS")){
            deliveryStaffs.removeIf(deliveryStaff -> deliveryStaff.getEmpID().equals(empID));
        }

        // Write all data to file

        try {
            FileWriter WriteData = new FileWriter(Account.empCredentialFile);
            for (Account account: accounts){
                WriteData.write(String.format("%s|%s\n", account.getEmpID(), account.getEmpPassword()));
            }
            WriteData.close();

            if(empID.contains("MS")){
                WriteData = new FileWriter(managingStaffDetailsFile);
                for (ManagingStaff managingStaff: managingStaffs){
                    WriteData.write(String.format("%s|%s|%d|%s|%s\n", managingStaff.getEmpID(), managingStaff.getEmpName(),
                            managingStaff.getEmpAge(), managingStaff.getEmpGender(), managingStaff.getEmpEmail()));
                }
            }else if(empID.contains("DS")){
                WriteData = new FileWriter(DeliveryStaff.deliveryStaffDetailsFile);
                for (DeliveryStaff deliveryStaff: deliveryStaffs){
                    WriteData.write(String.format("%s|%s|%d|%s|%s|%s|%s\n", deliveryStaff.getEmpID(),
                            deliveryStaff.getEmpName(), deliveryStaff.getEmpAge(), deliveryStaff.getEmpGender(),
                            deliveryStaff.getEmpEmail(), deliveryStaff.getCarBrand(), deliveryStaff.getCarPlateNo()));
                }
            }

            WriteData.close();
            System.out.println("Alert: Employee Account and Details Removed!");
        }catch (IOException e){
            e.printStackTrace();
        }

    }


    // modify customer section
    protected void addCustDetails(List<String> newCustDetails){
        try{
            // Write to Customer File
            FileWriter WriteData = new FileWriter(Customer.custDetailsFile, true);
            WriteData.write(String.format("%s|%s|%s|%s|%s\n", newCustDetails.get(0), newCustDetails.get(1),
                    newCustDetails.get(2), newCustDetails.get(3), newCustDetails.get(4)));

            WriteData.close();
            System.out.println("Alert: New Account and Details Added!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    protected void editCustDetails(){

    }

    protected void removeCustDetails(){

    }

    // modify item section


    @Override
    public String toString() {
        return super.toString();
    }
}
