import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Player objPlayer = new Player();

        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        objPlayer.setName(name);

        objPlayer.showInfo();
        scanner.close();
    }