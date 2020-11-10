package Main;

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
                        // call Managing Staff UI
                        managingStaffMainCLI(empID);
                    }else{
                        // call Delivery Staff UI
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
                managingStaffMainCLI(empID);
                break;
            case 5:
                break;
            default:
                managingStaffMainCLI(empID);
                break;
        }

    }

    private static int userChoiceVerification(String userInput, int initialInput, int finalInput){
        try {
            int intUserInput = Integer.parseInt(userInput);
            while (initialInput <= finalInput){
                if (initialInput == intUserInput){
                    return intUserInput;
                }
                initialInput++;
            }
            System.out.println("Warning: Please provide a valid input value!");
            return 0;
        } catch (NumberFormatException e){
            System.out.println("Warning: Input is not an integer!");
            return 0;
        }
    }

    public static void main(String[] args) {
        loginCLI();
    }
}
