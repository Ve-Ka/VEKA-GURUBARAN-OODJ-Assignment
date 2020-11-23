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

                if(empID.contains("MS")){
                    employee = new ManagingStaff(empID, empPassword);
                    if (employee.login()) {
                        managingStaffMainCLI(empID);
                    }
                }
                else if(empID.contains("DS")){
                    employee = new DeliveryStaff(empID, empPassword);
                    if (employee.login()) {
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
        System.out.println("X      [1] Order        X");
        System.out.println("X      [2] Feedback     X");
        System.out.println("X      [3] Item         X");
        System.out.println("X      [4] Report       X");
        System.out.println("X      [5] Account      X");
        System.out.println("X      [6] Logout       X");
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.print("--> ");
        String userChoice = Scanner.next();
        int intUserChoice = userChoiceVerification(userChoice, 1, 6);
        switch(intUserChoice){
            case 1:
                managingStaffMainCLI(empID);
                break;
            case 2:
                managingStaffMainCLI(empID);
                break;
            case 3:
                managingStaffManagementCLI(idType.ITEM);
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
        int intUserChoice = userChoiceVerification(userChoice, 1, 4);
        switch(intUserChoice){
            case 1:
                staffAccountSelfCLI(empID);
                managingStaffAccountCLI(empID);
                break;
            case 2:
                managingStaffManagementCLI(idType.STAFF);
                managingStaffAccountCLI(empID);
                break;
            case 3:
                managingStaffManagementCLI(idType.CUSTOMER);
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
        int intUserChoice = userChoiceVerification(userChoice, 1, 3);
        switch(intUserChoice){
            case 1:
                if(empID.contains("MS")){
                    employee.displayStaffDetails(empID);
                }
                else if(empID.contains("DS")){
                    employee.displayStaffDetails(empID);
                }
                staffAccountSelfCLI(empID);
                break;
            case 2:
                if(empID.contains("MS")){
                    employee = new ManagingStaff();
                    employee.displayStaffDetails(empID);
                    System.out.println("-----------------------");
                    Scanner.nextLine();

                    List<String> defaultStaffDetails = employee.defaultStaffDetails(empID);

                    String[] editableDetails = {"Name", "Age", "Gender", "Email"};
                    for (int a = 0; a < editableDetails.length; a ++){
                        System.out.printf("New %s: ", editableDetails[a]);
                        String userInput = Scanner.nextLine();
                        if (userInput.equals("")){
                            editableDetails[a] = defaultStaffDetails.get(a + 1);
                        }else{
                            editableDetails[a] = userInput;
                        }
                    }

                    employee = new ManagingStaff(empID, editableDetails[0], Integer.parseInt(editableDetails[1]),
                            editableDetails[2], editableDetails[3]);
                    ManagingStaff managingStaff = (ManagingStaff) employee;
                    managingStaff.editStaffDetails(managingStaff);
                }
                else if(empID.contains("DS")){
                    employee = new DeliveryStaff();
                    employee.displayStaffDetails(empID);
                    System.out.println("-----------------------");
                    Scanner.nextLine();

                    List<String> defaultStaffDetails = employee.defaultStaffDetails(empID);

                    String[] staffEditableDetails = {"Name", "Age", "Gender", "Email", "Vehicle Brand",
                            "Vehicle Plate No"};
                    for (int a = 0; a < staffEditableDetails.length; a ++){
                        System.out.printf("New %s: ", staffEditableDetails[a]);
                        String userInput = Scanner.nextLine();
                        if (userInput.equals("")){
                            staffEditableDetails[a] = defaultStaffDetails.get(a + 1);
                        }else{
                            staffEditableDetails[a] = userInput;
                        }
                    }

                    employee = new DeliveryStaff(empID, staffEditableDetails[0],
                            Integer.parseInt(staffEditableDetails[1]), staffEditableDetails[2],
                            staffEditableDetails[3], staffEditableDetails[4], staffEditableDetails[5]);
                    DeliveryStaff deliveryStaff = (DeliveryStaff) employee;
                    deliveryStaff.editStaffDetails(deliveryStaff);
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

    private static void managingStaffManagementCLI(idType idType){
        System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXX");
        switch(idType) {
            case STAFF:
                System.out.println("X        Staff          X");
                break;
            case CUSTOMER:
                System.out.println("X        Customer       X");
                break;
            case ITEM:
                System.out.println("X        Item           X");
                break;
        }
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
                System.out.print("Search ID: ");
                String searchID = Scanner.next();

                if(!idExistVerification(searchID, idType)){
                    System.out.println("Alert: ID does not exist!");
                    managingStaffManagementCLI(idType);
                    break;
                }else if(searchID.contains("MS") || searchID.contains("DS")){
                    employee.viewSelfAccount(searchID);
                    System.out.println("-----------------------");
                    employee.displayStaffDetails(searchID);
                }else if(searchID.contains("CS")){
                    Customer customer = new Customer();
                    System.out.println("ID: " + searchID);
                    System.out.println("-----------------------");
                    customer.viewCustDetails(searchID);
                }else if(searchID.contains("IT")){
                    Item item = new Item();
                    System.out.println("ID: " + searchID);
                    System.out.println("-----------------------");
                    item.viewItemDetails(searchID);
                }

                managingStaffManagementCLI(idType);
                break;
            case 2:
                // Add new Account
                System.out.print("New ID: ");
                String addID = Scanner.next();

                String newPassword = "";
                String[] addableDetails = {};


                if ((addID.contains("MS") || addID.contains("DS")) && !idExistVerification(addID, idType)){
                    employee = new ManagingStaff();
                    System.out.print("New Password: ");
                    newPassword = Scanner.next();
                    employee = new ManagingStaff();

                    if(addID.contains("MS")){
                        addableDetails = new String[]{"Name", "Age", "Gender", "Email"};
                    }
                    else if(addID.contains("DS")){
                        addableDetails = new String[]{"Name", "Age", "Gender", "Email", "Vehicle Brand",
                                "Vehicle Plate NO"};
                    }

                }else if (addID.contains("CS") && !idExistVerification(addID, idType)){
                    addableDetails = new String[]{"Name", "Email", "Phone NO", "Address"};
                }else if (addID.contains("IT") && !idExistVerification(addID, idType)){
                    addableDetails = new String[]{"Name", "Quantity", "Price", "Supplier", "Description"};
                }else{
                    System.out.println("Alert: ID input not valid or exist!");
                    managingStaffManagementCLI(idType);
                    break;
                }


                System.out.println("-----------------------");
                Scanner.nextLine();
                List<String> defaultStaffDetails = employee.defaultStaffDetails(addID);

                for (int a = 0; a < addableDetails.length; a ++){
                    System.out.printf("New %s: ", addableDetails[a]);
                    String userInput = Scanner.nextLine();
                    if (userInput.equals("")){
                        addableDetails[a] = defaultStaffDetails.get(a + 1);
                    }else{
                        addableDetails[a] = userInput;
                    }
                }


                // pass values
                switch(idType) {
                    case STAFF:
                        if (addID.contains("MS")){
                            employee = new ManagingStaff(addID, addableDetails[0],
                                    Integer.parseInt(addableDetails[1]), addableDetails[2],
                                    addableDetails[3]);
                            ManagingStaff managingStaff = (ManagingStaff) employee;
                            managingStaff.addEmpAccount(addID, newPassword, managingStaff);
                        } else if(addID.contains("DS")){
                            employee = new DeliveryStaff(addID, addableDetails[0],
                                    Integer.parseInt(addableDetails[1]), addableDetails[2], addableDetails[3],
                                    addableDetails[4], addableDetails[5]);
                            DeliveryStaff deliveryStaff = (DeliveryStaff) employee;

                            // instantiate to access addEmpAccount method
                            employee = new ManagingStaff();
                            ManagingStaff managingStaff = (ManagingStaff) employee;
                            managingStaff.addEmpAccount(addID, newPassword, deliveryStaff);
                        }
                        break;
                    case CUSTOMER:
                        //managingStaff.addCustDetails(newDetails);
                        break;
                    case ITEM:
                        //managingStaff.addItemDetails(newDetails);
                        break;
                }

                managingStaffManagementCLI(idType);
                break;
            case 3:
                System.out.print("Edit ID: ");
                String editID = Scanner.next();
                String[] staffEditableDetails = {};
                List<String> defaultDetails = new ArrayList<>();

                if(!idExistVerification(editID, idType)){
                    System.out.println("Alert: ID does not exist!");
                    managingStaffManagementCLI(idType);
                    break;
                }else if(editID.contains("MS")){
                    staffEditableDetails = new String[]{"Name", "Age", "Gender", "Email"};
                    employee = new ManagingStaff();
                    defaultDetails = employee.defaultStaffDetails(editID);
                    employee.displayStaffDetails(editID);
                }else if(editID.contains("DS")){
                    staffEditableDetails = new String[]{"Name", "Age", "Gender", "Email", "Vehicle Brand",
                            "Vehicle Plate NO"};
                    employee = new DeliveryStaff();
                    defaultDetails = employee.defaultStaffDetails(editID);
                    employee.displayStaffDetails(editID);
                }else if(editID.contains("CS")){
                    staffEditableDetails = new String[]{"Name", "Email", "Phone NO", "Address"};
                    Customer customer = new Customer();
                    defaultDetails = customer.defaultCustomerDetails(editID);
                    customer.viewCustDetails(editID);
                }else if(editID.contains("IT")){
                    staffEditableDetails = new String[]{"Name", "Quantity", "Price", "Supplier", "Description"};
                    Item item = new Item();
                    defaultDetails = item.defaultItemDetails(editID);
                    item.viewItemDetails(editID);
                }

                System.out.println("-----------------------");
                Scanner.nextLine();

                for (int a = 0; a < staffEditableDetails.length; a ++){
                    System.out.printf("New %s: ", staffEditableDetails[a]);
                    String userInput = Scanner.nextLine();
                    if (userInput.equals("")){
                        staffEditableDetails[a] = defaultDetails.get(a + 1);
                    }else{
                        staffEditableDetails[a] = userInput;
                    }
                }

                switch(idType) {
                    case STAFF:
                        if (editID.contains("MS")){
                            employee = new ManagingStaff(editID, staffEditableDetails[0],
                                    Integer.parseInt(staffEditableDetails[1]), staffEditableDetails[2],
                                    staffEditableDetails[3]);
                            ManagingStaff managingStaff1 = (ManagingStaff) employee;
                            managingStaff1.editStaffDetails(managingStaff1);
                        }else if (editID.contains("DS")){
                            employee = new DeliveryStaff(editID, staffEditableDetails[0],
                                    Integer.parseInt(staffEditableDetails[1]), staffEditableDetails[2],
                                    staffEditableDetails[3], staffEditableDetails[4], staffEditableDetails[5]);
                            DeliveryStaff deliveryStaff = (DeliveryStaff) employee;
                            deliveryStaff.editStaffDetails(deliveryStaff);
                        }
                        break;
                    case CUSTOMER:
                        Customer customer = new Customer(editID, staffEditableDetails[0], staffEditableDetails[1],
                                staffEditableDetails[2], staffEditableDetails[3]);

                        employee = new ManagingStaff();
                        ManagingStaff managingStaff1 = (ManagingStaff) employee;
                        managingStaff1.editCustDetails(customer);
                        break;
                    case ITEM:
                        Item item = new Item(editID, staffEditableDetails[0], Integer.parseInt(staffEditableDetails[1]),
                                Double.parseDouble(staffEditableDetails[2]), staffEditableDetails[3],
                                staffEditableDetails[4]);

                        employee = new ManagingStaff();
                        ManagingStaff managingStaff2 = (ManagingStaff) employee;
                        managingStaff2.editItemDetails(item);
                        break;
                }

                managingStaffManagementCLI(idType);
                break;
            case 4:
                System.out.print("Remove ID: ");
                String removeID = Scanner.next();

                employee = new ManagingStaff();
                ManagingStaff managingStaff1 = (ManagingStaff) employee;

                if(!idExistVerification(removeID, idType)){
                    System.out.println("Alert: Employee ID does not exist!");
                } else if(removeID.contains("MS") || removeID.contains("DS")){
                    managingStaff1.removeEmpAccount(removeID);
                } else if(removeID.contains("CS")){
                    managingStaff1.removeCustDetails(removeID);
                } else if(removeID.contains("IT")){
                    managingStaff1.removeItemDetails(removeID);
                }

                managingStaffManagementCLI(idType);
                break;
            case 5:
                break;
            default:
                managingStaffManagementCLI(idType);
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
        int intUserChoice = userChoiceVerification(userChoice, 1, 3);
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

    private static Boolean idExistVerification(String ID, idType idType){
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
                List<Customer> customers = customer.getAllCustDetails();
                for (Customer customer1: customers){
                    if(customer1.getCustID().equals(ID)){
                        return true;
                    }
                }
                break;
            case ITEM:
                Item item = new Item();
                List<Item> items = item.getAllItemDetails();
                for (Item item1: items){
                    if(item1.getItemID().equals(ID)){
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    public static void main(String[] args) {
        loginCLI();
    }
}
