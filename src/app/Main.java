package app;

import java.io.*;
import java.text.*;

public class Main {
    private final String PROGRAM_NAME = "DEV DEKXI'S EUCLIDEAN ALGO CALCULATOR";
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private boolean isContinueProgram = true;
    private int[] numbers = {69, 420};
    private String[] programActions = {
            "Terminate Program",
            "ABOUT GCD",
            "ABOUT EUCLIDEAN ALGORITHM",
            "SET VALUES FOR NUMBERS",
            "PERFORM EUCLIDEAN ALGORITHM"
    };
    /*===============================
     * ENTRY POINT
     *===============================*/
    public static void main(String[] args) {
        try{
            new Main().run();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /*===============================
     * PROGRAM RUNNER
     *===============================*/
    private void run(){
        while(isContinueProgram){
            showBanner(PROGRAM_NAME);
            showMenu(programActions);
            showEnterPrompt("your choice");
            performActionFromMenu(getInt(0, programActions.length));
        }
    }
    /*===============================
     * ACTION MENU
     *===============================*/
    private void performActionFromMenu(int choice){
        switch(choice){
            case 1:
                showMessage(ConsoleTag.INFO, """
                        -----------------------------------------------------------------
                        What is Greatest Common Divisor?
                         
                        Formula:
                                gcd(a, b) = |a| if b = 0
                                gcd(a, b) = gcd(b, a mod b) if b ≠ 0
                        
                            Description:
                                The gcd of two integers is the largest positive integer
                                that divides both numbers without leaving a remainder.
                                
                        Examples:
                        
                        1. Positive + Positive
                        𝑔𝑐𝑑(24,18)=6
                        Both numbers are positive, so it’s just the usual gcd.
                        
                        2. Negative + Negative
                        𝑔𝑐𝑑(-42,-30)=6
                        We take absolute values first, result is still positive.
                        
                        3. Positive + Negative
                        𝑔𝑐𝑑(48,-36)=12
                        Again, the sign doesn’t matter — gcd is always non-negative.
                        -----------------------------------------------------------------
                        """);
                break;
            case 2:
                showMessage(ConsoleTag.INFO, """
                        -----------------------------------------------------------------
                        What is Euclidean Algorithm?
                        Description:
                                The Euclidean algorithm is an efficient method for
                                finding the gcd of two integers by repeatedly
                                replacing the larger number with the remainder
                                until one becomes zero.
                        -----------------------------------------------------------------
                        """);
                break;
            case 3:
                showMessage(ConsoleTag.INFO, "Preparing setting of numbers...");
                showBanner("SETTING NUMBERS");
                setNumbers();
                break;
            case 4:
                showMessage(ConsoleTag.INFO, "Preparing euclidean algorithm computation...");
                showBanner("EUCLIDEAN ALGORITHM");
                printAbsEquation(numbers[0], numbers[1]);
                performEuclideanAlgorithm(getAbs(numbers[0]), getAbs(numbers[1]));
                break;
            case 0:
                setContinueProgram();
                break;
        }
    }
    /*===============================
     * PROGRAM ACTIONS
     *===============================*/

    private void setNumbers(){
        for(int i = 0; i < numbers.length; i++){
            printf("Change first number (" + numbers[i] +") to? ");
            numbers[i] = getInt();
        }
    }
    private int performEuclideanAlgorithm(int a, int b){
        if(a == b){
            printNumbers(a,b);
            printf("%nGCD = %d", a);
            return a;
        }else if(a<b){
            printNumbers(a,b);
            return performEuclideanAlgorithm(a, b-a);
        }else if(a>b){
            printNumbers(a,b);
            return performEuclideanAlgorithm(a-b, b);
        }
        throw unexpectedErrorOccurred();
    }
    private void setContinueProgram(){
        showMessage(ConsoleTag.SYSTEM, "Quit Program? (y/n) ");
        isContinueProgram = !getBoolean();
        if(!isContinueProgram)
            showMessage(ConsoleTag.SYSTEM, "Thank you for using the program! - dekxi");
    }

    /*===============================
     * CONSOLE RENDERING METHODS
     *===============================*/
    private void showBanner(String title){
        println();
        printBorder(title.length() + 6);
        printf("%n{| %s |}", title);
        printBorder(title.length() + 6);
        println();
    }
    private void printBorder(int length){
        printf("%n%s", "=".repeat(length));
    }
    private void showMenu(String[] options){
        for(int i=1; i < options.length; i++){
            printRow(i, options[i]);
        }
        printRow(0, options[0]);
    }
    private void printRow(int order, String label){
        printf("%n[%s] %s", order, label);
    }
    private void printNumbers(int a, int b){
        printf("%n%s - %s", a, b);
    }
    private void printAbsEquation(int a, int b){
        printf("%n|%s| - |%s|", a, b);
    }
    /*===============================
     * VALID GETTER METHOD
     *===============================*/
    private int getInt(){
        while(true){
            try{
                return parseInt(readLine());
            }catch(IllegalArgumentException e){
                showMessage(ConsoleTag.ERROR, e.getMessage());
                showEnterPrompt();
            }
        }
    }
    private int getInt(int min, int max){
        int value;
        while(true){
            value = getInt();
            if(value >=min && value <= max){
                return value;
            }
            showMessage(ConsoleTag.ERROR, "Must be within " + min + " - " + max + ". ");
        }
    }
    private boolean getBoolean(){
        while(true){
            try{
                return parseBoolean(readLine());
            }catch(IllegalArgumentException e){
                showMessage(ConsoleTag.ERROR, e.getMessage());
                showEnterPrompt();
            }
        }
    }
    private int getAbs(int num) {
        return num < 0 ? -num : num;
    }
    /*===============================
     * PARSER METHODS
     *===============================*/
    private int parseInt(String input) throws IllegalArgumentException{
        try{
            return Integer.parseInt(input);
        }catch(NumberFormatException e){
            throw unableToParseInt();
        }
    }
    private Boolean parseBoolean(String input) throws IllegalArgumentException{
        if("0".equals(input) || "no".equalsIgnoreCase(input) || "n".equalsIgnoreCase(input)) return false;
        if("1".equals(input) || "yes".equalsIgnoreCase(input) || "y".equalsIgnoreCase(input)) return true;
        throw unableToParseBoolean();
    }

    /*===============================
     * CONSOLE READER METHODS
     *===============================*/
    private String readLine() throws IllegalArgumentException{
        try{
            return reader.readLine().trim();
        }catch(IOException e){
            throw unableToParseString();
        }
    }
    /*===============================
     * PROGRAM EXCEPTIONS
     *===============================*/
    private IllegalArgumentException unableToParseInt(){
        return new IllegalArgumentException("Unable to parse integer. Try again");
    }
    private IllegalArgumentException unableToParseString(){
        return new IllegalArgumentException("Unable to parse string. Try again");
    }
    private IllegalArgumentException unableToParseBoolean(){
        return new IllegalArgumentException("Unable to parse boolean. Try again");
    }
    private IllegalStateException unexpectedErrorOccurred(){
        return new IllegalStateException("Unexpected error");
    }
    /*===============================
     * PRINT UTILITY METHODS
     *===============================*/
    private void showEnterPrompt(){
        showEnterPrompt("");
    }
    private void showEnterPrompt(String valNameToInput){
        printf("%nEnter %s: ", valNameToInput);
    }
    private void showMessage(ConsoleTag tag, String message){
        printf("%n%s %s", tag.label(),message );
    }
    private void println(){System.out.println();}
    private void printf(String format, Object... args){
        System.out.printf(format, args);
    }
    /*===============================
     * CONSOLE TAG
     *===============================*/
    private enum ConsoleTag{
        ERROR("[ERROR]"),
        SYSTEM("[SYSTEM]"),
        INFO("[INFO]");

        private String label;
        ConsoleTag(String label){this.label = label;}
        String label(){return this.label;}
    }
}