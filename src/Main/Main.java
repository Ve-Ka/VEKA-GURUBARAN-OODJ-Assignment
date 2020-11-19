package Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static java.util.Scanner Scanner = new Scanner(System.in);
    static Employee employee;

    private enum idType{
        STAFF, CUSTOMER, ITEM
    }

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
        System.out.println("X       [3] Stock       X");
        System.out.println("X       [4] Report      X");
        System.out.println("X       [5] Account     X");
        System.out.println("X       [6] Logout      X");
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
                managingStaffMainCLI(empID);
                break;
            case 5:
                managingStaffAccountCLI(empID);
                managingStaffMainCLI(empID);
                break;
            case 6:
                break;
            default:
                managingStaffMainCLI(empID);
                break;
        }
    }

    private static void managingStaffAccountCLI(String empID){
        System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println("X        Account        X");
        System.out.println("X         Menu          X");
        System.out.println("X-----------------------X");
        System.out.println("X      [1] Self         X");
        System.out.println("X      [2] Staff        X");
        System.out.println("X      [3] Customer     X");
        System.out.println("X      [4] Back         X");
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
                managingStaffAccountStaffCLI();
                managingStaffAccountCLI(empID);
                break;
            case 3:
                managingStaffAccountCustomerCLI();
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

    private static void managingStaffAccountStaffCLI(){
        System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println("X        Staff          X");
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

                if(!accountExistVerification(searchEmpID, idType.STAFF)){
                    System.out.println("Alert: Employee ID does not exist!");
                    managingStaffAccountStaffCLI();
                    break;
                }else if(searchEmpID.contains("MS")){
                    employee = new ManagingStaff();
                }else if(searchEmpID.contains("DS")){
                    employee = new DeliveryStaff();
                }

                Employee.account.viewSelfAccount(searchEmpID);
                System.out.println("-----------------------");
                employee.viewStaffDetails(searchEmpID);

                managingStaffAccountStaffCLI();
                break;
            case 2:
                // Add new Account
                System.out.print("New ID: ");
                String newEmpID = Scanner.next();

                if ((newEmpID.contains("MS") || newEmpID.contains("DS")) && !accountExistVerification(newEmpID, idType.STAFF)){
                    employee = new ManagingStaff();
                }else{
                    System.out.println("Alert: Employee ID input not valid or exist!");
                    managingStaffAccountStaffCLI();
                    break;
                }

                System.out.print("New Password: ");
                String newEmpPassword = Scanner.next();

                // Add new Details based on Account
                String[] staffAddableDetails = {};
                if(newEmpID.contains("MS")){
                    employee = new ManagingStaff();
                    staffAddableDetails = new String[]{"Name", "Age", "Gender", "Email"};
                }
                else if(newEmpID.contains("DS")){
                    employee = new DeliveryStaff();
                    staffAddableDetails = new String[]{"Name", "Age", "Gender", "Email", "Car Brand", "Car Plate NO"};
                }


                System.out.println("-----------------------");
                Scanner.nextLine();
                List<String> newStaffDetails = new ArrayList<>();
                newStaffDetails.add(newEmpID);
                for (String staffAddableDetail : staffAddableDetails) {
                    System.out.printf("New %s: ", staffAddableDetail);
                    String userInput = Scanner.nextLine();
                    while (userInput.equals("")){
                        System.out.println("Warning: Field cannot be empty!");
                        System.out.printf("New %s: ", staffAddableDetail);
                        userInput = Scanner.nextLine();
                    }
                    newStaffDetails.add(userInput);
                }

                ManagingStaff managingStaff = new ManagingStaff();
                managingStaff.addEmpAccount(newEmpID, newEmpPassword, newStaffDetails);

                managingStaffAccountStaffCLI();
                break;
            case 3:
                String[] staffEditableDetails = {};
                System.out.print("Edit ID: ");
                String editEmpID = Scanner.next();

                if(!accountExistVerification(editEmpID, idType.STAFF)){
                    System.out.println("Alert: Employee ID does not exist!");
                    managingStaffAccountStaffCLI();
                    break;
                }else if(editEmpID.contains("MS")){
                    employee = new ManagingStaff();
                    staffEditableDetails = new String[]{"Name", "Age", "Gender", "Email"};
                }
                else if(editEmpID.contains("DS")){
                    employee = new DeliveryStaff();
                    staffEditableDetails = new String[]{"Name", "Age", "Gender", "Email", "Car Brand", "Car Plate NO"};
                }

                employee.viewStaffDetails(editEmpID);
                System.out.println("-----------------------");
                Scanner.nextLine();
                List<String> staffNewDetails = new ArrayList<>();
                staffNewDetails.add(editEmpID);
                for (String StaffEditableDetail : staffEditableDetails) {
                    System.out.printf("New %s: ", StaffEditableDetail);
                    String userInput = Scanner.nextLine();
                    if (userInput.equals("")){
                        staffNewDetails.add("");
                    } else {
                        staffNewDetails.add(userInput);
                    }
                }
                employee.editStaffDetails(editEmpID, staffNewDetails);

                managingStaffAccountStaffCLI();
                break;
            case 4:
                System.out.print("Remove ID: ");
                String removeEmpID = Scanner.next();

                if(!accountExistVerification(removeEmpID, idType.STAFF)){
                    System.out.println("Alert: Employee ID does not exist!");
                } else{
                    employee = new ManagingStaff();
                    ManagingStaff managingStaff1 = (ManagingStaff) employee;
                    managingStaff1.removeEmpAccount(removeEmpID);
                }

                managingStaffAccountStaffCLI();
                break;
            case 5:
                break;
            default:
                managingStaffAccountStaffCLI();
                break;
        }
    }

    private static void managingStaffAccountCustomerCLI() {
        System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println("X        Customer       X");
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
        switch (intUserChoice) {
            case 1:
                /*
                System.out.print("Search ID: ");
                String searchCustID = Scanner.next();

                if(!accountExistVerification(searchCustID, idType.CUSTOMER)){
                    System.out.println("Alert: Employee ID does not exist!");
                    managingStaffAccountStaffCLI();
                    break;
                }else if(searchCustID.contains("MS")){
                    employee = new ManagingStaff();
                }else if(searchCustID.contains("DS")){
                    employee = new DeliveryStaff();
                }

                Employee.account.viewSelfAccount(searchCustID);
                System.out.println("-----------------------");
                employee.viewStaffDetails(searchCustID);
*/
                managingStaffAccountCustomerCLI();
                break;
            case 2:
                managingStaffAccountCustomerCLI();
                break;
            case 3:
                managingStaffAccountCustomerCLI();
                break;
            case 4:
                managingStaffAccountCustomerCLI();
                break;
            case 5:
                break;
            default:
                managingStaffAccountCustomerCLI();
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

    /*

        Data Validation Section

     */

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

    private static Boolean accountExistVerification(String ID, idType idType){
        switch(idType){
            case STAFF:
                Account account = new Account();
                List<Account> accounts = account.getAllEmpCredential();
                for (Account account1: accounts) {
                    if (account1.getEmpID().equals(ID)) {
                        return true;
                    }
                }
                break;
            case CUSTOMER:
                Customer customer = new Customer();
                List<Customer> customers = customer.getAllCustCredential();
                for (Customer customer1: customers){
                    if(customer1.getCustID().equals(ID)){
                        return true;
                    }
                }
                break;
            case ITEM:
                break;
        }
        return false;
    }

    public static void main(String[] args) {
        loginCLI();
    }
}
