import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MenuFunctions menufunctions = new MenuFunctions();
        boolean running = true;

        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. View current menu");
            System.out.println("2. Add an item");
            System.out.println("3. Undo last change");
            System.out.println("4. Redo");
            System.out.println("5. Exit");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> System.out.println("Current items: " + menufunctions.viewMenu());
                case 2 -> {
                    System.out.print("Enter new item: ");
                    String item = scanner.nextLine();
                    menufunctions.addItem(item);
                }
                case 3 -> menufunctions.undo();
                case 4 -> menufunctions.redo();
                case 5 -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }
        scanner.close();
    }
}
