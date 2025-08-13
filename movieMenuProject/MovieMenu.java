import java.util.Scanner;

public class MovieMenu {
    public static void main(String[] args) {
        // Create a Scanner to read user input from the console
        Scanner scanner = new Scanner(System.in);

        // Instantiate our MovieFunctions helper which manages the seating state
        MovieFunctions theater = new MovieFunctions();

        // A flag to keep the menu loop running until the user chooses to exit
        boolean running = true;

        // Main menu loop: continues until running is set to false
        while (running) {
            // Display the menu options each time through the loop
            System.out.println("\n--- Movie Theater Menu ---");
            System.out.println("1. Show seats");
            System.out.println("2. Reserve a seat");
            System.out.println("3. Cancel a reservation");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            // Read the user's menu choice as an integer
            int choice = scanner.nextInt();

            // Decide what to do based on the menu choice
            switch (choice) {
                case 1:
                    // Option 1: Display the current seating chart (available vs. reserved)
                    theater.displaySeats();
                    break;

                case 2:
                    // Option 2: Reserve a seat
                    // Prompt for the row (1–10), convert to zero-based index by subtracting 1
                    System.out.print("\nPlease enter the row you would like to sit in (1 - 10): ");
                    int reserveRow = scanner.nextInt() - 1;

                    // Prompt for the column (1–10), convert to zero-based index
                    System.out.print("Please enter the column you would like to sit in (1 - 10): ");
                    int reserveCol = scanner.nextInt() - 1;

                    // Attempt to reserve the specified seat
                    theater.reserveSeat(reserveRow, reserveCol);
                    break;

                case 3:
                    // Option 3: Cancel an existing reservation
                    // Prompt for the row on the ticket, convert to zero-based index
                    System.out.print("\nPlease enter the row on your ticket (1 - 10): ");
                    int cancelRow = scanner.nextInt() - 1;

                    // Prompt for the column on the ticket, convert to zero-based index
                    System.out.print("Please enter the column on your ticket (1 - 10): ");
                    int cancelCol = scanner.nextInt() - 1;

                    // Attempt to cancel the specified reservation
                    theater.cancelSeat(cancelRow, cancelCol);
                    break;

                case 4:
                    // Option 4: Exit the program
                    running = false;               // Break out of the loop
                    System.out.println("\nGoodbye!");
                    break;

                default:
                    // Any other number: inform the user the choice was invalid
                    System.out.println("\nInvalid option.");
                    break;
            }
        }

        // Close the Scanner to release system resources
        scanner.close();
    }
}
