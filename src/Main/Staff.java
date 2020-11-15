package Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Staff implements Task{
    java.util.Scanner Scanner = new Scanner(System.in);


    @Override
    public void search() {
        System.out.print("EmpID: ");
        String userInput = Scanner.nextLine();
        if(userInput.contains("MS")){
            ManagingStaff managingStaff = new ManagingStaff();
            managingStaff.viewStaffDetails(userInput);
        }
        else if(userInput.contains("DS")){
            DeliveryStaff deliveryStaff = new DeliveryStaff();
            deliveryStaff.viewStaffDetails(userInput);
        }
    }

    @Override
    public void add() {

    }

    @Override
    public void modify() {

    }

    @Override
    public void remove() {

    }

    /*

    public void addStaff(){
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

        List<String> newManagingStaffDetails = new ArrayList<>();
        String[] managingStaffEditableDetails = {"EmpID","Name", "Age", "Gender", "Email"};
        for (String managingStaffEditableDetail : managingStaffEditableDetails) {
            System.out.printf("%s: ", managingStaffEditableDetail);
            String userInput = Scanner.nextLine();
            while (userInput.equals("")) {
                System.out.println("Warning: Entry cannot be empty!");
            }
            newManagingStaffDetails.add(userInput);
        }

        managingStaffDefaultDetails.addAll(newManagingStaffDetails);

        for (ManagingStaff managingStaff : managingStaffDetails) {
            managingStaff.setEmpID(managingStaffDefaultDetails.get(0));
            managingStaff.setEmpName(managingStaffDefaultDetails.get(1));
            managingStaff.setEmpAge(Integer.parseInt(managingStaffDefaultDetails.get(2)));
            managingStaff.setEmpGender(managingStaffDefaultDetails.get(3));
            managingStaff.setEmpEmail(managingStaffDefaultDetails.get(4));
        }

    }
*/
}
