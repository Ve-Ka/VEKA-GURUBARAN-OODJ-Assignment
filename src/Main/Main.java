package Main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static java.util.Scanner Scanner = new Scanner(System.in);
    private static Employee employee;

    private enum idType{
        STAFF, CUSTOMER, ORDER, ITEM, DELIVERY, FEEDBACK
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

                if(empID.startsWith("MS")){
                    employee = new ManagingStaff(empID, empPassword);
                    if (employee.login()) {
                        managingStaffMainCLI(empID);
                    }
                }
                else if(empID.startsWith("DS")){
                    employee = new DeliveryStaff(empID, empPassword);
                    if (employee.login()) {
                        deliveryStaffMainCLI(empID);
                    }
                }else{
                    System.out.println("Warning: Login ID or Password invalid!");
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
        System.out.println("X      [2] Delivery     X");
        System.out.println("X      [3] Feedback     X");
        System.out.println("X      [4] Item         X");
        System.out.println("X      [5] Report       X");
        System.out.println("X      [6] Account      X");
        System.out.println("X      [7] Logout       X");
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.print("--> ");
        String userChoice = Scanner.next();
        int intUserChoice = userChoiceVerification(userChoice, 1, 7);
        switch(intUserChoice){
            case 1:
                managingStaffManagementCLI(empID, idType.ORDER);
                managingStaffMainCLI(empID);
                break;
            case 2:
                managingStaffManagementCLI(empID, idType.DELIVERY);
                managingStaffMainCLI(empID);
                break;
            case 3:
                managingStaffManagementCLI(empID, idType.FEEDBACK);
                managingStaffMainCLI(empID);
                break;
            case 4:
                managingStaffManagementCLI(empID, idType.ITEM);
                managingStaffMainCLI(empID);
                break;
            case 5:
                managingStaffReportCLI();
                managingStaffMainCLI(empID);
                break;
            case 6:
                managingStaffAccountCLI(empID);
                managingStaffMainCLI(empID);
                break;
            case 7:
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
                managingStaffManagementCLI(empID, idType.STAFF);
                managingStaffAccountCLI(empID);
                break;
            case 3:
                managingStaffManagementCLI(empID, idType.CUSTOMER);
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
                if(empID.startsWith("MS")){
                    employee.displayStaffDetails(empID);
                }
                else if(empID.startsWith("DS")){
                    employee.displayStaffDetails(empID);
                }
                staffAccountSelfCLI(empID);
                break;
            case 2:
                if(empID.startsWith("MS")){
                    employee = new ManagingStaff();
                    employee.displayStaffDetails(empID);
                    System.out.println("-----------------------");
                    Scanner.nextLine();

                    List<String> defaultDetails = employee.defaultDetails(empID);

                    String[] editableDetails = {"Name", "Age", "Gender", "Email"};
                    for (int a = 0; a < editableDetails.length; a ++){
                        System.out.printf("New %s: ", editableDetails[a]);
                        String userInput = Scanner.nextLine();
                        if (userInput.equals("")){
                            editableDetails[a] = defaultDetails.get(a + 1);
                        }else{
                            editableDetails[a] = userInput;
                        }
                    }

                    employee = new ManagingStaff(empID, editableDetails[0], Integer.parseInt(editableDetails[1]),
                            editableDetails[2], editableDetails[3]);
                    ManagingStaff managingStaff = (ManagingStaff) employee;
                    managingStaff.editStaffDetails(managingStaff);
                }
                else if(empID.startsWith("DS")){
                    employee = new DeliveryStaff();
                    employee.displayStaffDetails(empID);
                    System.out.println("-----------------------");
                    Scanner.nextLine();

                    List<String> defaultDetails = employee.defaultDetails(empID);

                    String[] staffEditableDetails = {"Name", "Age", "Gender", "Email", "Vehicle Brand",
                            "Vehicle Plate No"};
                    for (int a = 0; a < staffEditableDetails.length; a ++){
                        System.out.printf("New %s: ", staffEditableDetails[a]);
                        String userInput = Scanner.nextLine();
                        if (userInput.equals("")){
                            staffEditableDetails[a] = defaultDetails.get(a + 1);
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

    private static void managingStaffManagementCLI(String empID, idType idType){
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
            case ORDER:
                System.out.println("X        Order          X");
                break;
            case DELIVERY:
                System.out.println("X        Delivery       X");
                break;
            case FEEDBACK:
                System.out.println("X        Feedback       X");
                break;
        }
        System.out.println("X       Management      X");
        System.out.println("X-----------------------X");
        System.out.println("X       [1] Search      X");
        if(idType.equals(idType.DELIVERY)){
            System.out.println("X       [3] Edit        X");
            System.out.println("X       [5] Back        X");
        }else{
            System.out.println("X       [2] Add         X");
            System.out.println("X       [3] Edit        X");
            System.out.println("X       [4] Remove      X");
            System.out.println("X       [5] Back        X");
        }
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.print("--> ");
        String userChoice = Scanner.next();

        if(idType.equals(idType.DELIVERY)){
            if(!((userChoice.equals("1")) || (userChoice.equals("3")) || (userChoice.equals("5")))){
                userChoice ="0";
            }
        }

        int intUserChoice = userChoiceVerification(userChoice, 1, 5);
        switch(intUserChoice){
            case 1:
                System.out.print("Search ID: ");
                String searchID = Scanner.next();

                if(!idExistVerification(searchID, idType)){
                    System.out.println("Alert: ID does not exist!");
                    managingStaffManagementCLI(empID, idType);
                    break;
                }else if(searchID.startsWith("MS")){
                    employee.viewSelfAccount(searchID);
                    System.out.println("-----------------------");
                    employee = new ManagingStaff();
                    ManagingStaff managingStaff = (ManagingStaff) employee;
                    managingStaff.displayStaffDetails(searchID);
                }else if (searchID.startsWith("DS")){
                    employee.viewSelfAccount(searchID);
                    System.out.println("-----------------------");
                    employee = new DeliveryStaff();
                    DeliveryStaff deliveryStaff = (DeliveryStaff) employee;
                    deliveryStaff.displayStaffDetails(searchID);
                }else if(searchID.startsWith("CS")){
                    Customer customer = new Customer();
                    System.out.println("ID: " + searchID);
                    System.out.println("-----------------------");
                    customer.viewCustDetails(searchID);
                }else if(searchID.startsWith("IT")){
                    Item item = new Item();
                    System.out.println("ID: " + searchID);
                    System.out.println("-----------------------");
                    item.viewItemDetails(searchID);
                }else if(searchID.startsWith("OD")){
                    Order order = new Order();
                    System.out.println("-----------------------");
                    order.search(searchID);
                }else if(searchID.startsWith("DE")){
                    Delivery delivery = new Delivery();
                    System.out.println("-----------------------");
                    delivery.search(searchID);
                }else if(searchID.startsWith("FE")){
                    Feedback feedback = new Feedback();
                    System.out.println("-----------------------");
                    feedback.search(searchID);
                }

                managingStaffManagementCLI(empID, idType);
                break;
            case 2:
                // Add new Account
                String addID = "";
                String newPassword = "";
                String[] addableDetails = {};

                // Order ID will be auto generated so there is not a need to input a value
                // Check for any other id that have potential to be auto generated
                if(!idType.equals(idType.ORDER)) {
                    while ((!addID.toUpperCase().contains("MS") && !addID.toUpperCase().contains("DS")) && idType.equals(idType.STAFF)){
                        System.out.print("Managing Staff | Delivery Staff [MS/DS]: ");
                        addID = Scanner.next();
                    }

                    if (addID.toUpperCase().startsWith("MS")) {
                        addableDetails = new String[]{"Name", "Age", "Gender", "Email"};
                        employee = new ManagingStaff();
                        ManagingStaff managingStaff = (ManagingStaff) employee;
                        addID = managingStaff.generateID();
                        System.out.println("\nNew ID: " + addID);
                        System.out.print("New Password: ");
                        newPassword = Scanner.next();
                    } else if (addID.toUpperCase().startsWith("DS")) {
                        addableDetails = new String[]{"Name", "Age", "Gender", "Email", "Vehicle Brand",
                                "Vehicle Plate NO"};
                        employee = new ManagingStaff();
                        DeliveryStaff deliveryStaff = (DeliveryStaff) employee;
                        addID = deliveryStaff.generateID();
                        System.out.println("\nNew ID: " + addID);
                        System.out.print("New Password: ");
                        newPassword = Scanner.next();
                    } else if (idType.equals(idType.CUSTOMER)) {
                        addableDetails = new String[]{"Name", "Email", "Phone NO", "Address"};
                        Customer customer = new Customer();
                        addID = customer.generateID();
                        System.out.println("\nNew ID: " + addID);
                    } else if (idType.equals(idType.ITEM)) {
                        addableDetails = new String[]{"Name", "Quantity", "Price", "Supplier", "Description"};
                        Item item = new Item();
                        addID = item.generateID();
                        System.out.println("\nNew ID: " + addID);
                    } else if (idType.equals(idType.FEEDBACK)) {
                        addableDetails = new String[]{"Feedback Title", "Feedback Content", "Customer ID"};
                        Feedback feedback = new Feedback();
                        addID = feedback.generateID();
                        System.out.println("\nNew ID: " + addID);
                    }else {
                        System.out.println("Alert: ID input Invalid or exist!");
                        managingStaffManagementCLI(empID, idType);
                        break;
                    }


                    System.out.println("-----------------------");
                    Scanner.nextLine();
                    List<String> defaultDetails = employee.defaultDetails(addID);

                    for (int a = 0; a < addableDetails.length; a++) {
                        System.out.printf("New %s: ", addableDetails[a]);
                        String userInput = Scanner.nextLine();
                        if (userInput.equals("")) {
                            addableDetails[a] = defaultDetails.get(a + 1);
                        } else {
                            addableDetails[a] = userInput;
                        }
                    }
                }

                // pass values
                switch(idType) {
                    case STAFF:
                        if (addID.startsWith("MS")){
                            employee = new ManagingStaff(addID, addableDetails[0],
                                    Integer.parseInt(addableDetails[1]), addableDetails[2],
                                    addableDetails[3]);
                            ManagingStaff managingStaff = (ManagingStaff) employee;
                            managingStaff.addEmpAccount(addID, newPassword, managingStaff);
                        } else if(addID.startsWith("DS")){
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
                        Customer customer = new Customer(addID, addableDetails[0], addableDetails[1],
                                addableDetails[2], addableDetails[3]);

                        employee = new ManagingStaff();
                        ManagingStaff managingStaff = (ManagingStaff) employee;
                        managingStaff.addCustDetails(customer);
                        break;
                    case ITEM:
                        Item item = new Item(addID, addableDetails[0], Integer.parseInt(addableDetails[1]),
                                Double.parseDouble(addableDetails[2]), addableDetails[3], addableDetails[4]);

                        employee = new ManagingStaff();
                        ManagingStaff managingStaff1 = (ManagingStaff) employee;
                        managingStaff1.addItemDetails(item);
                        break;
                    case ORDER:
                        Order order = new Order();
                        String newOrderID = order.generateID();
                        boolean exit = false;

                        // Retrieve Local Date Time
                        String currentLocalDateTime = customDateTimePrompt();

                        // Customer ID Input and Verification
                        // Display customer ID and name for selection purpose
                        Customer customer1 = new Customer();
                        customer1.displayLimitedCustDetails();
                        System.out.print("Customer ID: ");
                        String customerID = Scanner.next();
                        while((!idExistVerification(customerID, idType.CUSTOMER)) || !(customerID.startsWith("CS")) || (customerID.isBlank())){
                            System.out.println("Warning: Customer ID does not Exist or Invalid!");
                            System.out.print("Customer ID: ");
                            customerID = Scanner.next();
                        }
                        customer1 = new Customer(customerID);

                        // Item ID Input and Verification
                        Item item1 = new Item();
                        item1.displayLimitedItemDetails();
                        System.out.print("Item ID: ");
                        String itemID = Scanner.next();
                        int itemQuantity = 0;
                        while((!idExistVerification(itemID, idType.ITEM)) || !(itemID.startsWith("IT")) || (itemID.isBlank()) || !(item1.modifyItemQuantity(itemID, itemQuantity))){
                            System.out.println("Warning: Item ID does not Exist or out of stock!");
                            System.out.print("Item ID: ");
                            itemID = Scanner.next();
                        }

                        // Item Quantity Input and Verification
                        while(!exit || !(item1.modifyItemQuantity(itemID, itemQuantity))){
                            try{
                                Scanner.nextLine();
                                System.out.print("Item Quantity: ");
                                itemQuantity = Scanner.nextInt();
                                if(itemQuantity <= 0){
                                    System.out.println("Warning: Quantity cannot be less than 1!");
                                }else{
                                    exit = true;
                                }
                            } catch (InputMismatchException e){
                                System.out.println("Warning: Quantity Must be an Integer value!");
                            }
                        }exit = false;
                        item1 = new Item(itemID, itemQuantity);

                        // Delivery ID Input and Verification // Association between Delivery and Order
                        // Auto generated
                        System.out.print("Create a delivery [y/n]? ");
                        String deliveryNeeded = Scanner.next();
                        Delivery delivery;
                        String DeliveryID = "";
                        while(!exit){
                            switch(deliveryNeeded.toLowerCase()){
                                case "y":
                                    DeliveryStaff deliveryStaff = new DeliveryStaff();
                                    deliveryStaff.displayLimitedDeliveryStaffDetails();
                                    System.out.print("Delivery Staff ID: ");
                                    String deliveryStaffID = Scanner.next();
                                    while((!idExistVerification(deliveryStaffID, idType.STAFF)) || !(deliveryStaffID.startsWith("DS")) || (deliveryStaffID.isBlank())){
                                        System.out.println("Warning: Delivery Staff ID does not Exist or Invalid!");
                                        System.out.print("Delivery Staff ID: ");
                                        deliveryStaffID = Scanner.next();
                                    }
                                    deliveryStaff = new DeliveryStaff(deliveryStaffID);

                                    delivery = new Delivery();
                                    DeliveryID = delivery.generateID();
                                    delivery = new Delivery(DeliveryID, currentLocalDateTime, deliveryStaff.getEmpID(),
                                            customerID, item1);
                                    delivery.add();

                                    exit = true;
                                    break;
                                case "n":
                                    exit = true;
                                    System.out.println("Alert: No delivery is created!\n");
                                    break;
                                default:
                                    System.out.println("Warning: Kindly Provide valid Input!");
                                    break;
                            }
                        }

                        // Association between managingStaff and order
                        ManagingStaff managingStaff2 = new ManagingStaff(empID);

                        switch(deliveryNeeded.toLowerCase()){
                            case "y":
                                order = new Order(newOrderID, currentLocalDateTime, customer1, DeliveryID, item1,
                                        managingStaff2.getEmpID(), false);
                                break;
                            case "n":
                                order = new Order(newOrderID, currentLocalDateTime, customer1, item1, managingStaff2.getEmpID(),
                                        false);
                                break;
                        }
                        order.add();
                        break;

                    case FEEDBACK:
                        Customer customer2 = new Customer(addableDetails[2]);
                        Feedback feedback = new Feedback(addID, customDateTimePrompt(), addableDetails[0],
                                addableDetails[1], customer2, empID);
                        feedback.add();
                        break;
                }

                managingStaffManagementCLI(empID, idType);
                break;
            case 3:
                String[] staffEditableDetails = {};
                int pointerOffset = 1;
                List<String> defaultDetails = new ArrayList<>();

                System.out.print("Edit ID: ");
                String editID = Scanner.next();

                if(!idExistVerification(editID, idType)){
                    System.out.println("Alert: ID does not exist!");
                    managingStaffManagementCLI(empID, idType);
                    break;
                }else if(editID.startsWith("MS")){
                    staffEditableDetails = new String[]{"Name", "Age", "Gender", "Email"};
                    employee = new ManagingStaff();
                    defaultDetails = employee.defaultDetails(editID);
                    employee.displayStaffDetails(editID);
                }else if(editID.startsWith("DS")){
                    staffEditableDetails = new String[]{"Name", "Age", "Gender", "Email", "Vehicle Brand",
                            "Vehicle Plate NO"};
                    employee = new DeliveryStaff();
                    defaultDetails = employee.defaultDetails(editID);
                    employee.displayStaffDetails(editID);
                }else if(editID.startsWith("CS")){
                    staffEditableDetails = new String[]{"Name", "Email", "Phone NO", "Address"};
                    Customer customer = new Customer();
                    defaultDetails = customer.defaultDetails(editID);
                    customer.viewCustDetails(editID);
                }else if(editID.startsWith("IT")){
                    staffEditableDetails = new String[]{"Name", "Quantity", "Price", "Supplier", "Description"};
                    Item item = new Item();
                    defaultDetails = item.defaultDetails(editID);
                    item.viewItemDetails(editID);
                }else if(editID.startsWith("OD")){
                    staffEditableDetails = new String[]{"Order Completed"};
                    pointerOffset = 8;
                    Order order = new Order();
                    defaultDetails = order.defaultDetails(editID);
                    order.search(editID);
                }else if(editID.startsWith("DE")){
                    staffEditableDetails = new String[]{"Delivery Staff"};
                    pointerOffset = 2;
                    Delivery delivery = new Delivery();
                    defaultDetails = delivery.defaultDetails(editID);
                    delivery.search(editID);
                } else if(editID.startsWith("FE")) {
                    staffEditableDetails = new String[]{"Feedback Title", "Feedback Content"};
                    pointerOffset = 2;
                    Feedback feedback = new Feedback();
                    defaultDetails = feedback.defaultDetails(editID);
                    feedback.search(editID);
                }

                System.out.println("-----------------------");
                Scanner.nextLine();

                for (int a = 0; a < staffEditableDetails.length; a ++){
                    System.out.printf("New %s: ", staffEditableDetails[a]);
                    String userInput = Scanner.nextLine();
                    if (userInput.equals("")){
                        staffEditableDetails[a] = defaultDetails.get(a + pointerOffset);
                    }else{
                        staffEditableDetails[a] = userInput;
                    }
                }

                switch(idType) {
                    case STAFF:
                        if (editID.startsWith("MS")){
                            employee = new ManagingStaff(editID, staffEditableDetails[0],
                                    Integer.parseInt(staffEditableDetails[1]), staffEditableDetails[2],
                                    staffEditableDetails[3]);
                            ManagingStaff managingStaff1 = (ManagingStaff) employee;
                            managingStaff1.editStaffDetails(managingStaff1);
                        }else if (editID.startsWith("DS")){
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
                    case ORDER:
                        Order order = new Order(defaultDetails.get(0), defaultDetails.get(1), defaultDetails.get(2),
                                defaultDetails.get(3), Integer.parseInt(defaultDetails.get(4)), defaultDetails.get(5),
                                defaultDetails.get(6), Boolean.parseBoolean(staffEditableDetails[0]));
                        order.modify(order);
                        break;
                    case DELIVERY:
                        Delivery delivery = new Delivery(defaultDetails.get(0), defaultDetails.get(1),
                                staffEditableDetails[0], defaultDetails.get(3), defaultDetails.get(4),
                                defaultDetails.get(5), defaultDetails.get(6), Integer.parseInt(defaultDetails.get(7)));
                        delivery.modify(delivery);
                        break;
                    case FEEDBACK:
                        Feedback feedback1 = new Feedback(defaultDetails.get(0), defaultDetails.get(1),
                                staffEditableDetails[0], staffEditableDetails[1], defaultDetails.get(4),
                                defaultDetails.get(5), defaultDetails.get(6), empID);
                        feedback1.modify(feedback1);
                        break;
                }

                managingStaffManagementCLI(empID, idType);
                break;
            case 4:
                System.out.print("Remove ID: ");
                String removeID = Scanner.next();

                employee = new ManagingStaff();
                ManagingStaff managingStaff1 = (ManagingStaff) employee;

                if(!idExistVerification(removeID, idType)){
                    System.out.println("Alert: ID does not exist!");
                } else if(removeID.startsWith("MS") || removeID.startsWith("DS")){
                    managingStaff1.removeEmpAccount(removeID);
                } else if(removeID.startsWith("CS")){
                    managingStaff1.removeCustDetails(removeID);
                } else if(removeID.startsWith("IT")){
                    managingStaff1.removeItemDetails(removeID);
                } else if(removeID.startsWith("OD")){
                    Order order = new Order();
                    order.remove(removeID);
                    Delivery delivery = new Delivery();
                    delivery.remove(order.getDeliveryID());
                } else if(removeID.startsWith("FE")) {
                    Feedback feedback = new Feedback();
                    feedback.remove(removeID);
                }

                managingStaffManagementCLI(empID, idType);
                break;
            case 5:
                break;
            default:
                managingStaffManagementCLI(empID, idType);
                break;
        }
    }


    private static void managingStaffReportCLI(){
        System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println("X       Generate        X");
        System.out.println("X        Report         X");
        System.out.println("X-----------------------X");
        System.out.println("X  [1] Account          X");
        System.out.println("X  [2] Managing Staff   X");
        System.out.println("X  [3] Delivery Staff   X");
        System.out.println("X  [4] Feedback         X");
        System.out.println("X  [5] Item             X");
        System.out.println("X  [6] Order            X");
        System.out.println("X  [7] Delivery         X");
        System.out.println("X  [8] Back             X");
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.print("--> ");
        String userChoice = Scanner.next();
        int intUserChoice = userChoiceVerification(userChoice, 1, 8);
        switch(intUserChoice){
            case 1:
                Account account = new Account();
                account.generateReport();
                System.out.println("Alert: Report Generated!");
                managingStaffReportCLI();
                break;
            case 2:
                ManagingStaff managingStaff = new ManagingStaff();
                managingStaff.generateReport();
                System.out.println("Alert: Report Generated!");
                managingStaffReportCLI();
                break;
            case 3:
                DeliveryStaff deliveryStaff = new DeliveryStaff();
                deliveryStaff.generateReport();
                System.out.println("Alert: Report Generated!");
                managingStaffReportCLI();
                break;
            case 4:
                Feedback feedback = new Feedback();
                feedback.generateReport();
                System.out.println("Alert: Report Generated!");
                managingStaffReportCLI();
                break;
            case 5:
                Item item = new Item();
                item.generateReport();
                System.out.println("Alert: Report Generated!");
                managingStaffReportCLI();
                break;
            case 6:
                Order order = new Order();
                order.generateReport();
                System.out.println("Alert: Report Generated!");
                managingStaffReportCLI();
                break;
            case 7:
                Delivery delivery = new Delivery();
                delivery.generateReport();
                System.out.println("Alert: Report Generated!");
                managingStaffReportCLI();
                break;
            case 8:
                break;
            default:
                managingStaffReportCLI();
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
        System.out.println("X      [1] Delivery     X");
        System.out.println("X      [2] Account      X");
        System.out.println("X      [3] Logout       X");
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.print("--> ");
        String userChoice = Scanner.next();
        int intUserChoice = userChoiceVerification(userChoice, 1, 3);
        switch(intUserChoice) {
            case 1:
                deliveryStaffDeliveryCLI(empID);
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

    private static void deliveryStaffDeliveryCLI(String empID){
        System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println("X        Delivery       X");
        System.out.println("X       Management      X");
        System.out.println("X-----------------------X");
        System.out.println("X       [1] View        X");
        System.out.println("X       [2] Update      X");
        System.out.println("X       [3] Back        X");
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.print("--> ");
        String userChoice = Scanner.next();
        int intUserChoice = userChoiceVerification(userChoice, 1, 3);
        switch(intUserChoice) {
            case 1:
                Delivery delivery = new Delivery();
                delivery.viewSelfDelivery(empID);
                deliveryStaffDeliveryCLI(empID);
                break;
            case 2:
                Delivery delivery1 = new Delivery();
                delivery1.viewSelfDelivery(empID);
                System.out.print("Delivery ID: ");
                String deliveryID = Scanner.next();
                if((!idExistVerification(deliveryID, idType.DELIVERY)) || !(deliveryID.startsWith("DE")) ||
                        (deliveryID.isBlank()) || !(delivery1.selfDeliveryVerification(empID))){
                    System.out.println("Warning: Delivery ID does not Exist or Invalid!");
                    deliveryStaffDeliveryCLI(empID);
                    break;
                }

                boolean exit = false;
                while(!exit){
                    System.out.print("Confirm update order status to completed [y/n]? ");
                    String userConfirmation = Scanner.next();
                    switch(userConfirmation.toLowerCase()){
                        case "y":
                            Order order = new Order();
                            order.updateOrder(deliveryID);
                            System.out.println("Alert: Order status updated!");
                            exit = true;
                            break;
                        case "n":
                            System.out.println("Alert: Order status not updated!");
                            exit = true;
                            break;
                        default:
                            System.out.println("Warning: Kindly provide valid input!");
                            break;
                    }
                }

                deliveryStaffDeliveryCLI(empID);
                break;
            case 3:
                break;
            default:
                deliveryStaffDeliveryCLI(empID);
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
            case ORDER:
                Order order = new Order();
                List<Order> orders = order.getAllOrder();
                for (Order order1: orders){
                    if(order1.getOrderID().equals(ID)){
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
            case DELIVERY:
                Order order1 = new Order();
                List<Order> orderList = order1.getAllOrder();
                Delivery delivery = new Delivery();
                List<Delivery> deliveries = delivery.getAllDelivery();
                int position = 0;
                for (Delivery delivery1: deliveries){
                    if(delivery1.getDeliveryStaffID().equals(ID) && !orderList.get(position).getOrderCompletion()){
                        return true;
                    }
                }
                break;
            case FEEDBACK:
                Feedback feedback = new Feedback();
                List<Feedback> feedbacks = feedback.getAllFeedbackDetails();
                for(Feedback feedback1: feedbacks){
                    if(feedback1.getFeedbackID().equals(ID)){
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    private static String customDateTimePrompt(){
        boolean exit = false;
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        System.out.println("Current Date Time: " + dateTimeFormatter.format(currentLocalDateTime));
        while(!exit) {
            System.out.print("Custom Date Time [y/n]? ");
            String userRespond = Scanner.next();
            switch (userRespond.toLowerCase()) {
                case "y":
                    try {
                        System.out.print("Date (dd/MM/yyyy): ");
                        String customDate = Scanner.next();
                        System.out.print("Time (HH:mm:ss): ");
                        String customTime = Scanner.next();
                        String customDateTime = customDate + " " + customTime;
                        currentLocalDateTime = LocalDateTime.parse(customDateTime, dateTimeFormatter);
                        System.out.println("Custom Date Time: " + dateTimeFormatter.format(currentLocalDateTime));
                        exit = true;
                    } catch (DateTimeParseException e) {
                        System.out.println("Warning: Date Time input format error!");
                        //e.printStackTrace();
                    }
                    break;
                case "n":
                    System.out.println("Alert: Default Date Time used!");
                    exit = true;
                    break;
                default:
                    System.out.println("Warning: Kindly Provide valid Input!");
                    break;
            }
        }
        return dateTimeFormatter.format(currentLocalDateTime);
    }

    public static void main(String[] args) {
        loginCLI();
    }
}
