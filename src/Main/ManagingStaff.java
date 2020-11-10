package Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ManagingStaff extends Employee{
    private final static String managingStaffDetailsFile = "managingStaffDetails.txt";


    public boolean searchStaff(){
        return true;
    }

    public boolean addStaff(){

        return true;
    }

    public boolean modifyStaff(){
        return true;
    }

    public boolean removeStaff(){
        return true;
    }

    Order order = new Order();

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
    public void editStaffDetails(String empID) {

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
}
