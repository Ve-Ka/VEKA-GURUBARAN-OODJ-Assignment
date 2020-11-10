package Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DeliveryStaff extends Employee{
    private String carBrand;
    private String carPlateNo;
    private final static String deliveryStaffDetailsFile = "deliveryStaffDetails.txt";

    @Override
    public void viewStaffDetails(String empID) {
        List<DeliveryStaff> deliveryStaffDetailsList = getAllDeliveryStaffDetails();
        for (DeliveryStaff deliveryStaff: deliveryStaffDetailsList){
            if(deliveryStaff.getEmpID().equals(empID)){
                System.out.println("ID: " + deliveryStaff.getEmpID());
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
    public void editStaffDetails(String empID) {

    }

    private List<DeliveryStaff> getAllDeliveryStaffDetails(){
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

    private String getCarBrand() {
        return carBrand;
    }

    private void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    private String getCarPlateNo() {
        return carPlateNo;
    }

    private void setCarPlateNo(String carPlateNo) {
        this.carPlateNo = carPlateNo;
    }
}
