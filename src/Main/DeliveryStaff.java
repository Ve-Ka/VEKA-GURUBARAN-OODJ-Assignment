package Main;

import javax.management.DescriptorKey;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DeliveryStaff extends Employee{
    private String vehicleBrand;
    private String vehiclePlateNo;
    protected final static String deliveryStaffDetailsFile = "deliveryStaffDetails.txt";

    public DeliveryStaff(){}

    public DeliveryStaff(String empID){
        super(empID);
    }

    public DeliveryStaff(String empID, String empPassword) {
        super(empID, empPassword);
    }

    public DeliveryStaff(String empID, String empName, int empAge, String empGender, String empEmail,
                         String vehicleBrand, String vehiclePlateNo) {
        super(empID, empName, empAge, empGender, empEmail);
        this.vehicleBrand = vehicleBrand;
        this.vehiclePlateNo = vehiclePlateNo;
    }

    @Override
    protected void displayStaffDetails(String empID) {
        List<DeliveryStaff> deliveryStaffDetailsList = getAllDeliveryStaffDetails();
        for (DeliveryStaff deliveryStaff: deliveryStaffDetailsList){
            if(deliveryStaff.getEmpID().equals(empID)){
                System.out.println(deliveryStaff.toString());
            }
        }
    }

    @Override
    protected List<String> defaultStaffDetails(String empID) {
        List<DeliveryStaff> originalDetails = getAllDeliveryStaffDetails();
        List<String> defaultDetails = new ArrayList<>();
        for (DeliveryStaff detail : originalDetails) {
            if (detail.getEmpID().equals(empID)) {
                defaultDetails.add(detail.getEmpID());
                defaultDetails.add(detail.getEmpName());
                defaultDetails.add(Integer.toString(detail.getEmpAge()));
                defaultDetails.add(detail.getEmpGender());
                defaultDetails.add(detail.getEmpEmail());
                defaultDetails.add(detail.getVehicleBrand());
                defaultDetails.add(detail.getVehiclePlateNo());
            }
        }
        return defaultDetails;
    }

    @Override
    public String generateID(){
        List<DeliveryStaff> defaultList = getAllDeliveryStaffDetails();
        String newEmpID;
        try{
            newEmpID = String.format("DS%04d", ((Integer.parseInt(defaultList.get(defaultList.size()
                    - 1).getEmpID().replaceAll("DS", ""))) + 1));
        }catch(IndexOutOfBoundsException e){
            newEmpID = "DS0001";
        }
        return newEmpID;
    }

    protected void displayLimitedDeliveryStaffDetails(){
        List<DeliveryStaff> deliveryStaffs = getAllDeliveryStaffDetails();
        System.out.println("\nAll Delivery Staff details");
        for (DeliveryStaff deliveryStaff: deliveryStaffs){
            System.out.printf("%s|%s|%s|%s\n", deliveryStaff.getEmpID(), deliveryStaff.getEmpName(),
                    deliveryStaff.getVehicleBrand(), deliveryStaff.getVehicleBrand());
        }
        System.out.println("");
    }

    protected void editStaffDetails(DeliveryStaff deliveryStaff) {
        // Overwrite Original List with new data
        List<DeliveryStaff> originalDetails = getAllDeliveryStaffDetails();
        int position = 0;
        for (DeliveryStaff detail: originalDetails){
            if (detail.getEmpID().equals(deliveryStaff.getEmpID())) {
                break;
            }else{
                position ++;
            }
        }
        originalDetails.set(position, deliveryStaff);

        // Write to file
        try{
            FileWriter WriteData = new FileWriter(deliveryStaffDetailsFile);
            for (DeliveryStaff detail: originalDetails){
                WriteData.write(String.format("%s|%s|%d|%s|%s|%s|%s\n", detail.getEmpID(), detail.getEmpName(),
                        detail.getEmpAge(), detail.getEmpGender(), detail.getEmpEmail(), detail.getVehicleBrand(),
                        detail.getVehiclePlateNo()));
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
                deliveryStaff.setVehicleBrand(rec[5]);
                deliveryStaff.setVehiclePlateNo(rec[6]);
                deliveryStaffDetailsList.add(deliveryStaff);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return deliveryStaffDetailsList;
    }

    protected String getVehicleBrand() {
        return vehicleBrand;
    }

    private void setVehicleBrand(String VehicleBrand) {
        this.vehicleBrand = VehicleBrand;
    }

    protected String getVehiclePlateNo() {
        return vehiclePlateNo;
    }

    private void setVehiclePlateNo(String VehiclePlateNo) {
        this.vehiclePlateNo = VehiclePlateNo;
    }

    @Override
    public String toString() {
        return  super.toString() + '\n' +
                "Vehicle Brand: " + vehicleBrand + '\n' +
                "Vehicle Plate NO.: " + vehiclePlateNo;
    }
}
