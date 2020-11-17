package Main;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DeliveryStaff extends Employee{
    private String carBrand;
    private String carPlateNo;
    protected final static String deliveryStaffDetailsFile = "deliveryStaffDetails.txt";

    @Override
    public void viewStaffDetails(String empID) {
        List<DeliveryStaff> deliveryStaffDetailsList = getAllDeliveryStaffDetails();
        for (DeliveryStaff deliveryStaff: deliveryStaffDetailsList){
            if(deliveryStaff.getEmpID().equals(empID)){
                System.out.println("Name: " + deliveryStaff.getEmpName());
                System.out.println("Age: " + deliveryStaff.getEmpAge());
                System.out.println("Gender: " + deliveryStaff.getEmpGender());
                System.out.println("Email: " + deliveryStaff.getEmpEmail());
                System.out.println("Car Brand: " + deliveryStaff.getCarBrand());
                System.out.println("Car Plate NO: " + deliveryStaff.getCarPlateNo());
            }
        }
    }

    @Override
    public void editStaffDetails(String empID, List<String> empDetails) {
        // load data in to default list
        List<DeliveryStaff> deliveryStaffDetails = getAllDeliveryStaffDetails();
        List<String> deliveryStaffDefaultDetails = new ArrayList<>();
        for (DeliveryStaff deliveryStaff : deliveryStaffDetails) {
            if (deliveryStaff.getEmpID().equals(empID)) {
                deliveryStaffDefaultDetails.add(deliveryStaff.getEmpID());
                deliveryStaffDefaultDetails.add(deliveryStaff.getEmpName());
                deliveryStaffDefaultDetails.add(Integer.toString(deliveryStaff.getEmpAge()));
                deliveryStaffDefaultDetails.add(deliveryStaff.getEmpGender());
                deliveryStaffDefaultDetails.add(deliveryStaff.getEmpEmail());
                deliveryStaffDefaultDetails.add(deliveryStaff.getCarBrand());
                deliveryStaffDefaultDetails.add(deliveryStaff.getCarPlateNo());
            }
        }

        // verify and replace empty value with default
        List<String> deliveryStaffVerifiedDetails = new ArrayList<>();
        for(int a = 0; a < empDetails.toArray().length; a ++){
            if(empDetails.get(a).equals("")){
                deliveryStaffVerifiedDetails.add(deliveryStaffDefaultDetails.get(a));
            } else{
                deliveryStaffVerifiedDetails.add(empDetails.get(a));
            }
        }

        // overwrite default list
        for (DeliveryStaff deliveryStaff : deliveryStaffDetails) {
            if (deliveryStaff.getEmpID().equals(empID)) {
                deliveryStaff.setEmpName(deliveryStaffVerifiedDetails.get(1));
                deliveryStaff.setEmpAge(Integer.parseInt(deliveryStaffVerifiedDetails.get(2)));
                deliveryStaff.setEmpGender(deliveryStaffVerifiedDetails.get(3));
                deliveryStaff.setEmpEmail(deliveryStaffVerifiedDetails.get(4));
                deliveryStaff.setCarBrand(deliveryStaffVerifiedDetails.get(5));
                deliveryStaff.setCarPlateNo(deliveryStaffVerifiedDetails.get(6));
            }
        }

        // write to file
        try{
            FileWriter WriteData = new FileWriter(deliveryStaffDetailsFile);
            for (DeliveryStaff deliveryStaff : deliveryStaffDetails) {
                String tempEmpID = deliveryStaff.getEmpID();
                String tempEmpName = deliveryStaff.getEmpName();
                int tempEmpAge = deliveryStaff.getEmpAge();
                String tempEmpGender = deliveryStaff.getEmpGender();
                String tempEmpEmail = deliveryStaff.getEmpEmail();
                String tempCarBrand = deliveryStaff.getCarBrand();
                String tempCarPlateNo = deliveryStaff.getCarPlateNo();
                WriteData.write(String.format("%s|%s|%d|%s|%s|%s|%s\n", tempEmpID, tempEmpName, tempEmpAge,
                        tempEmpGender, tempEmpEmail, tempCarBrand, tempCarPlateNo));
            }
            WriteData.close();
            System.out.println("Alert: Details Updated!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    protected static List<DeliveryStaff> getAllDeliveryStaffDetails(){
        List<DeliveryStaff> deliveryStaffDetailsList = new ArrayList();
        try {
            List<String> empDetailsList = Files.readAllLines(Paths.get(deliveryStaffDetailsFile));
            for (String record : empDetailsList){
                String[] rec = record.split("\\|");
                DeliveryStaff deliveryStaff = new DeliveryStaff();
                deliveryStaff.setEmpID(rec[0]);
                deliveryStaff.setEmpName(rec[1]);
                deliveryStaff.setEmpAge(Integer.parseInt(rec[2]));
                deliveryStaff.setEmpGender(rec[3]);
                deliveryStaff.setEmpEmail(rec[4]);
                deliveryStaff.setCarBrand(rec[5]);
                deliveryStaff.setCarPlateNo(rec[6]);
                deliveryStaffDetailsList.add(deliveryStaff);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return deliveryStaffDetailsList;
    }

    protected String getCarBrand() {
        return carBrand;
    }

    private void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    protected String getCarPlateNo() {
        return carPlateNo;
    }

    private void setCarPlateNo(String carPlateNo) {
        this.carPlateNo = carPlateNo;
    }
}
