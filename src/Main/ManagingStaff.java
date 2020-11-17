package Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class ManagingStaff extends Employee{
    private final static String managingStaffDetailsFile = "managingStaffDetails.txt";

    @Override
    public void viewStaffDetails(String empID) {
        List<ManagingStaff> managingStaffDetailsList = getAllManagingStaffDetails();
        for (ManagingStaff managingStaff: managingStaffDetailsList){
            if(managingStaff.getEmpID().equals(empID)){
                System.out.println("ID: " + managingStaff.getEmpID());
                System.out.println("Name: " + managingStaff.getEmpName());
                System.out.println("Age: " + managingStaff.getEmpAge());
                System.out.println("Gender: " + managingStaff.getEmpGender());
                System.out.println("Email: " + managingStaff.getEmpEmail());
            }
        }
    }

    @Override
    public void editStaffDetails(String empID, List<String> empDetails) {
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

        // overwrite default class list
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
                String tempEmpID = managingStaff.getEmpID();
                String tempEmpName = managingStaff.getEmpName();
                int tempEmpAge = managingStaff.getEmpAge();
                String tempEmpGender = managingStaff.getEmpGender();
                String tempEmpEmail = managingStaff.getEmpEmail();
                WriteData.write(String.format("%s|%s|%d|%s|%s\n", tempEmpID, tempEmpName, tempEmpAge, tempEmpGender
                        , tempEmpEmail));
            }
            WriteData.close();
            System.out.println("Alert: Details Updated!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

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


    @Override
    public String toString() {
        return "ManagingStaff{" +
                "empID='" + empID + '\'' +
                ", empName='" + empName + '\'' +
                ", empAge=" + empAge +
                ", empGender='" + empGender + '\'' +
                ", empEmail='" + empEmail + '\'' +
                '}';
    }
}
