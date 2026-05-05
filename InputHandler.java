import java.util.Scanner;

public class InputHandler {
    private Scanner scanner;
    
    public InputHandler(Scanner scanner) {
        this.scanner = scanner;
    }
    
    public int getIntInput(int min, int max) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.print("Please enter a number between " + min + " and " + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }
    
    public String getStringInput() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                if (!input.isEmpty()) {
                    return input;
                }
                System.out.print("Input cannot be empty. Please try again: ");
            } catch (Exception e) {
                System.out.print("Invalid input. Please try again: ");
            }
        }
    }
    
    public void waitForEnter() {
        try {
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Error reading input.");
        }
    }
}