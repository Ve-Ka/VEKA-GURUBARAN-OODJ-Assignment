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
                boolean credentialExist = Employee.login("MS0001", "MSP0001");
                if (credentialExist){
                    //call respective employee

            }
                break;
            case 2:
                System.out.println("Program exiting...");
                break;
            default:
                loginCLI();
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
            System.out.println("Please provide a valid input value!");
            return 0;
        } catch (NumberFormatException e){
            System.out.println("Input is not an integer!");
            return 0;
        }
    }

    public static void main(String[] args) {
        loginCLI();
    }
}
