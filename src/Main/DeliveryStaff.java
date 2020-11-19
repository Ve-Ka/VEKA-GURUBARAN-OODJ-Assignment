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
    protected void viewStaffDetails(String empID) {
        List<DeliveryStaff> deliveryStaffDetailsList = getAllDeliveryStaffDetails();
        for (DeliveryStaff deliveryStaff: deliveryStaffDetailsList){
            if(deliveryStaff.getEmpID().equals(empID)){
                System.out.println(deliveryStaff.toString());
            }
        }
    }

    @Override
    protected void editStaffDetails(String empID, List<String> details) {
        // load data in to default list
        List<DeliveryStaff> originalDetails = getAllDeliveryStaffDetails();
        List<String> defaultDetails = new ArrayList<>();
        for (DeliveryStaff detail : originalDetails) {
            if (detail.getEmpID().equals(empID)) {
                defaultDetails.add(detail.getEmpID());
                defaultDetails.add(detail.getEmpName());
                defaultDetails.add(Integer.toString(detail.getEmpAge()));
                defaultDetails.add(detail.getEmpGender());
                defaultDetails.add(detail.getEmpEmail());
                defaultDetails.add(detail.getCarBrand());
                defaultDetails.add(detail.getCarPlateNo());
            }
        }

        // verify and replace empty value with default
        List<String> verifiedDetails = new ArrayList<>();
        for(int a = 0; a < details.toArray().length; a ++){
            if(details.get(a).equals("")){
                verifiedDetails.add(defaultDetails.get(a));
            } else{
                verifiedDetails.add(details.get(a));
            }
        }

        // overwrite default object list
        for (DeliveryStaff detail : originalDetails) {
            if (detail.getEmpID().equals(empID)) {
                detail.setEmpName(verifiedDetails.get(1));
                detail.setEmpAge(Integer.parseInt(verifiedDetails.get(2)));
                detail.setEmpGender(verifiedDetails.get(3));
                detail.setEmpEmail(verifiedDetails.get(4));
                detail.setCarBrand(verifiedDetails.get(5));
                detail.setCarPlateNo(verifiedDetails.get(6));
            }
        }

        // write to file
        try{
            FileWriter WriteData = new FileWriter(deliveryStaffDetailsFile);
            for (DeliveryStaff detail : originalDetails) {
                WriteData.write(String.format("%s|%s|%d|%s|%s|%s|%s\n", detail.getEmpID(),
                        detail.getEmpName(), detail.getEmpAge(), detail.getEmpGender(),
                        detail.getEmpEmail(), detail.getCarBrand(), detail.getCarPlateNo()));
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


    @Override
    public String toString() {
        return  super.toString() + '\n' +
                "Car Brand: " + carBrand + '\n' +
                "Car Plate NO.: " + carPlateNo;
    }
}
