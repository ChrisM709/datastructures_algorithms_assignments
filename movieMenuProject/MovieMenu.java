import java.util.Scanner;

public class MovieMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MovieFunctions theater = new MovieFunctions();
        boolean running = true;

        while (running) {
            System.out.println("\n--- Movie Theater Menu ---");
            System.out.println("1. Show seats");
            System.out.println("2. Reserve a seat");
            System.out.println("3. Cancel a reservation");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    theater.displaySeats();
                    break;

                case 2:
                    System.out.print("\nPlease enter the row you would like to sit in (1 - 10): ");
                    int reserveRow = scanner.nextInt() - 1;
                    System.out.print("Please enter the column you would like to sit in (1 -10): ");
                    int reserveCol = scanner.nextInt() - 1;
                    theater.reserveSeat(reserveRow, reserveCol);
                    break;

                case 3:
                    System.out.print("\nPlease enter the row on your ticket (1 - 10): ");
                    int cancelRow = scanner.nextInt() - 1;
                    System.out.print("Please enter the column on your ticket (1 - 10): ");
                    int cancelCol = scanner.nextInt() - 1;
                    theater.cancelSeat(cancelRow, cancelCol);
                    break;

                case 4:
                    running = false;
                    System.out.println("\nGoodbye!");
                    break;

                default:
                    System.out.println("\nInvalid option.");
                    break;
            }
        }

        scanner.close();
    }
}
