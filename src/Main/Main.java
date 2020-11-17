package Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static java.util.Scanner Scanner = new Scanner(System.in);

    private static void loginCLI(){
        System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println("X     VEKA-GURUBARAN    X");
        System.out.println("X     OODJ Assignment   X");
        System.out.println("X-----------------------X");
        System.out.println("X       [1] Login       X");
        System.out.println("X       [2] Exit        X");
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.print("--> ");
        String userChoice = Scanner.next();
        int intUserChoice = userChoiceVerification(userChoice, 1, 2);
        switch(intUserChoice){
            case 1:
                System.out.print("ID: ");
                String empID = Scanner.next();
                System.out.print("Password: ");
                String empPassword = Scanner.next();
                boolean credentialExist = Employee.login(empID, empPassword);
                if (credentialExist){
                    if(empID.contains("MS")){
                        managingStaffMainCLI(empID);
                    }
                    else if(empID.contains("DS")){
                        deliveryStaffMainCLI(empID);
                    }
                }
                loginCLI();
                break;
            case 2:
                System.out.println("Program exiting...");
                break;
            default:
                loginCLI();
                break;
        }
    }

/*

    Managing Staff CLI Section

 */

    private static void managingStaffMainCLI(String empID){
        System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println("X     Managing Staff    X");
        System.out.printf("X         %s        X\n", empID);
        System.out.println("X-----------------------X");
        System.out.println("X       [1] Order       X");
        System.out.println("X       [2] Feedback    X");
        System.out.println("X       [3] Report      X");
        System.out.println("X       [4] Account     X");
        System.out.println("X       [5] Logout      X");
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.print("--> ");
        String userChoice = Scanner.next();
        int intUserChoice = userChoiceVerification(userChoice, 1, 5);
        switch(intUserChoice){
            case 1:
                managingStaffMainCLI(empID);
                break;
            case 2:
                managingStaffMainCLI(empID);
                break;
            case 3:
                managingStaffMainCLI(empID);
                break;
            case 4:
                managingStaffAccountCLI(empID);
                managingStaffMainCLI(empID);
                break;
            case 5:
                break;
            default:
                managingStaffMainCLI(empID);
                break;
        }
    }

    private static void managingStaffAccountCLI(String empID){
        System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println("X         Account       X");
        System.out.println("X          Menu         X");
        System.out.println("X-----------------------X");
        System.out.println("X     [1] Self          X");
        System.out.println("X     [2] Emp Account   X");
        System.out.println("X     [3] Emp Details   X");
        System.out.println("X     [4] Back          X");
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.print("--> ");
        String userChoice = Scanner.next();
        int intUserChoice = userChoiceVerification(userChoice, 1, 5);
        switch(intUserChoice){
            case 1:
                staffAccountSelfCLI(empID);
                managingStaffAccountCLI(empID);
                break;
            case 2:
                managingStaffAccountEmpAccountCLI();
                managingStaffAccountCLI(empID);
                break;
            case 3:
                managingStaffAccountEmpDetailsCLI();
                managingStaffAccountCLI(empID);
                break;
            case 4:
                break;
            default:
                managingStaffAccountCLI(empID);
                break;
        }
    }


    private static void staffAccountSelfCLI(String empID){
        System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println("X         Self          X");
        System.out.println("X        Details        X");
        System.out.println("X-----------------------X");
        System.out.println("X       [1] View        X");
        System.out.println("X       [2] Edit        X");
        System.out.println("X       [3] Back        X");
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.print("--> ");
        String userChoice = Scanner.next();
        int intUserChoice = userChoiceVerification(userChoice, 1, 5);
        switch(intUserChoice){
            case 1:
                if(empID.contains("MS")){
                    Employee employee = new ManagingStaff();
                    employee.viewStaffDetails(empID);
                }
                else if(empID.contains("DS")){
                    Employee employee = new DeliveryStaff();
                    employee.viewStaffDetails(empID);
                }
                staffAccountSelfCLI(empID);
                break;
            case 2:
                if(empID.contains("MS")){
                    Employee employee = new ManagingStaff();
                    employee.viewStaffDetails(empID);
                    System.out.println("-----------------------");
                    Scanner.nextLine();
                    List<String> managingStaffNewDetails = new ArrayList<>();
                    managingStaffNewDetails.add(empID);
                    String[] managingStaffEditableDetails = {"Name", "Age", "Gender", "Email"};
                    for (String managingStaffEditableDetail : managingStaffEditableDetails) {
                        System.out.printf("New %s: ", managingStaffEditableDetail);
                        String userInput = Scanner.nextLine();
                        if (userInput.equals("")){
                            managingStaffNewDetails.add("");
                        } else {
                            managingStaffNewDetails.add(userInput);
                        }
                    }
                    employee.editStaffDetails(empID, managingStaffNewDetails);
                }
                else if(empID.contains("DS")){
                    Employee employee = new DeliveryStaff();
                    employee.viewStaffDetails(empID);
                    System.out.println("-----------------------");
                    Scanner.nextLine();
                    List<String> deliveryStaffNewDetails = new ArrayList<>();
                    deliveryStaffNewDetails.add(empID);
                    String[] deliveryStaffEditableDetails = {"Name", "Age", "Gender", "Email", "Car Brand", "Car Plate NO"};
                    for (String deliveryStaffEditableDetail : deliveryStaffEditableDetails) {
                        System.out.printf("New %s: ", deliveryStaffEditableDetail);
                        String userInput = Scanner.nextLine();
                        if (userInput.equals("")){
                            deliveryStaffNewDetails.add("");
                        } else {
                            deliveryStaffNewDetails.add(userInput);
                        }
                    }
                    employee.editStaffDetails(empID, deliveryStaffNewDetails);
                }
                staffAccountSelfCLI(empID);
                break;
            case 3:
                break;
            default:
                staffAccountSelfCLI(empID);
                break;
        }
    }

    private static void managingStaffAccountEmpAccountCLI(){
        System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println("X        Employee       X");
        System.out.println("X        Account        X");
        System.out.println("X-----------------------X");
        System.out.println("X       [1] Search      X");
        System.out.println("X       [2] Add         X");
        System.out.println("X       [3] Edit        X");
        System.out.println("X       [4] Remove      X");
        System.out.println("X       [5] Back        X");
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.print("--> ");
        String userChoice = Scanner.next();
        int intUserChoice = userChoiceVerification(userChoice, 1, 5);
        switch(intUserChoice){
            case 1:
                System.out.print("Search ID: ");
                String searchEmpID = Scanner.next();
                Employee.account.viewSelfAccount(searchEmpID);
                managingStaffAccountEmpAccountCLI();
                break;
            case 2:
                managingStaffAccountEmpAccountCLI();
                break;
            case 3:
                managingStaffAccountEmpAccountCLI();
                break;
            case 4:
                managingStaffAccountEmpAccountCLI();
                break;
            case 5:
                break;
            default:
                managingStaffAccountEmpAccountCLI();
                break;
        }
    }


    private static void managingStaffAccountEmpDetailsCLI(){
        System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println("X        Employee       X");
        System.out.println("X        Details        X");
        System.out.println("X-----------------------X");
        System.out.println("X       [1] Search      X");
        System.out.println("X       [2] Add         X");
        System.out.println("X       [3] Edit        X");
        System.out.println("X       [4] Remove      X");
        System.out.println("X       [5] Back        X");
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.print("--> ");
        String userChoice = Scanner.next();
        int intUserChoice = userChoiceVerification(userChoice, 1, 5);
        switch(intUserChoice){
            case 1:
                System.out.print("Search ID: ");
                String searchEmpID = Scanner.next();
                if(searchEmpID.contains("MS")){
                    Employee employee = new ManagingStaff();
                    employee.viewStaffDetails(searchEmpID);
                }
                else if(searchEmpID.contains("DS")){
                    Employee employee = new DeliveryStaff();
                    employee.viewStaffDetails(searchEmpID);
                }
                managingStaffAccountEmpDetailsCLI();
                break;
            case 2:

                managingStaffAccountEmpDetailsCLI();
                break;
            case 3:
                System.out.print("Edit ID: ");
                String empID = Scanner.next();
                if(empID.contains("MS")){
                    Employee employee = new ManagingStaff();
                    employee.viewStaffDetails(empID);
                    System.out.println("-----------------------");
                    Scanner.nextLine();
                    List<String> managingStaffNewDetails = new ArrayList<>();
                    managingStaffNewDetails.add(empID);
                    String[] managingStaffEditableDetails = {"Name", "Age", "Gender", "Email"};
                    for (String managingStaffEditableDetail : managingStaffEditableDetails) {
                        System.out.printf("New %s: ", managingStaffEditableDetail);
                        String userInput = Scanner.nextLine();
                        if (userInput.equals("")){
                            managingStaffNewDetails.add("");
                        } else {
                            managingStaffNewDetails.add(userInput);
                        }
                    }
                    employee.editStaffDetails(empID, managingStaffNewDetails);
                }
                else if(empID.contains("DS")){
                    Employee employee = new DeliveryStaff();
                    employee.viewStaffDetails(empID);
                    System.out.println("-----------------------");
                    Scanner.nextLine();
                    List<String> deliveryStaffNewDetails = new ArrayList<>();
                    deliveryStaffNewDetails.add(empID);
                    String[] deliveryStaffEditableDetails = {"Name", "Age", "Gender", "Email", "Car Brand", "Car Plate NO"};
                    for (String deliveryStaffEditableDetail : deliveryStaffEditableDetails) {
                        System.out.printf("New %s: ", deliveryStaffEditableDetail);
                        String userInput = Scanner.nextLine();
                        if (userInput.equals("")){
                            deliveryStaffNewDetails.add("");
                        } else {
                            deliveryStaffNewDetails.add(userInput);
                        }
                    }
                    employee.editStaffDetails(empID, deliveryStaffNewDetails);
                }

                managingStaffAccountEmpDetailsCLI();
                break;
            case 4:

                managingStaffAccountEmpDetailsCLI();
                break;
            case 5:
                break;
            default:
                managingStaffAccountEmpDetailsCLI();
                break;
        }
    }


    public static void managingStaffOrderCLI(){
        System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println("X         Order         X");
        System.out.println("X       Management      X");
        System.out.println("X-----------------------X");
        System.out.println("X       [1] Search      X");
        System.out.println("X       [2] Add         X");
        System.out.println("X       [3] Edit        X");
        System.out.println("X       [4] Remove      X");
        System.out.println("X       [5] Back        X");
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.print("--> ");
        String userChoice = Scanner.next();
        int intUserChoice = userChoiceVerification(userChoice, 1, 5);
        switch(intUserChoice){
            case 1:
                managingStaffOrderCLI();
                break;
            case 2:
                managingStaffOrderCLI();
                break;
            case 3:
                managingStaffOrderCLI();
                break;
            case 4:
                managingStaffOrderCLI();
                break;
            case 5:
                break;
            default:
                managingStaffOrderCLI();
                break;
        }
    }

/*

    Delivery Staff Section

*/
    private static void deliveryStaffMainCLI(String empID){
        System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println("X     Delivery Staff    X");
        System.out.printf("X         %s        X\n", empID);
        System.out.println("X-----------------------X");
        System.out.println("X       [1] Delivery    X");
        System.out.println("X       [2] Account     X");
        System.out.println("X       [3] Logout      X");
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.print("--> ");
        String userChoice = Scanner.next();
        int intUserChoice = userChoiceVerification(userChoice, 1, 5);
        switch(intUserChoice) {
            case 1:
                deliveryStaffMainCLI(empID);
                break;
            case 2:
                staffAccountSelfCLI(empID);
                deliveryStaffMainCLI(empID);
                break;
            case 3:
                break;
            default:
                deliveryStaffMainCLI(empID);
                break;
        }
    }


    private static int userChoiceVerification(String userInput, int initialInput, int finalInput){
        try {
            int intUserInput = Integer.parseInt(userInput);
            if (intUserInput >= initialInput && intUserInput <= finalInput){
                return intUserInput;
            }
            System.out.println("Warning: Please provide a valid input value!");
        } catch (NumberFormatException e){
            System.out.println("Warning: Input is not an integer!");
        } return 0;
    }

    public static void main(String[] args) {
        loginCLI();
    }
}
