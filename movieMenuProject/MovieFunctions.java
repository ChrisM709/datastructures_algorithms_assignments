public class MovieFunctions {
    // 2D array representing the seating layout.
    // Each element holds "A" for Available or "X" for Reserved.
    private String[][] seats;

    /**
     * Constructor: sets up the seating layout when a MovieFunctions object is created.
     * - Allocates a 10×10 array for seats.
     * - Calls initializeSeats() to mark every seat as available.
     */
    public MovieFunctions() {
        seats = new String[10][10];
        initializeSeats();  // Populate the array with "A"
    }

    /**
     * initializeSeats():
     * - Iterates over every row and column index in the seats array.
     * - Assigns "A" to each position, indicating all seats start as Available.
     *
     * Why?
     * We need a clear “starting state” before any reservations or cancellations happen.
     */
    public void initializeSeats() {
        for (int rowIndex = 0; rowIndex < seats.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < seats[rowIndex].length; columnIndex++) {
                seats[rowIndex][columnIndex] = "A";
            }
        }
    }

    /**
     * displaySeats():
     * - Prints a header explaining the meaning of "A" and "X".
     * - Loops through each row and column, printing the current status of every seat.
     * - After printing each row, moves to a new line for readability.
     *
     * Why?
     * Users need a visual map of which seats are free or taken when making a reservation.
     */
    public void displaySeats() {
        System.out.println("\nSeating Chart (A = Available, X = Reserved):");
        for (int rowIndex = 0; rowIndex < seats.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < seats[rowIndex].length; columnIndex++) {
                System.out.print(seats[rowIndex][columnIndex] + " ");
            }
            System.out.println();  // New line after each row
        }
    }

    /**
     * reserveSeat(int rowIndex, int columnIndex):
     * - First, checks if the requested indexes are within bounds using isValid().
     * - If valid and the seat is Available ("A"), marks it Reserved ("X") and prints a mock ticket.
     * - If it’s already reserved, informs the user and calls findNextSeat() to suggest an alternative.
     * - If the indexes are invalid, prints an error message.
     *
     * Why?
     * This encapsulates the whole “book a seat” logic: validation, state change, and confirmation.
     */
    public void reserveSeat(int rowIndex, int columnIndex) {
        if (isValid(rowIndex, columnIndex)) {  // Check array bounds
            if (seats[rowIndex][columnIndex].equals("A")) {
                seats[rowIndex][columnIndex] = "X";  // Mark as reserved

                // Print a simple ticket confirmation
                System.out.println("\nTicket Confirmation");
                System.out.println("----------------------------");
                System.out.println("Movie: Forest Gump");
                System.out.println("Date: 2025-05-18");
                System.out.println("Time: 7:45 PM");
                System.out.println("Theater: 1");
                System.out.println("Seat: Row " + (rowIndex + 1) + ", Seat " + (columnIndex + 1));
                System.out.println("Enjoy the show!");
                System.out.println("----------------------------\n");

            } else {
                // Seat already taken: suggest the next available one
                System.out.println("\nThat seat is already reserved.");
                findNextSeat();
            }
        } else {
            // Indexes out of range
            System.out.println("\nInvalid seat location.");
        }
    }

    /**
     * findNextSeat():
     * - Scans the seats array from the beginning.
     * - When it finds the first Available seat ("A"), it suggests that seat and returns.
     * - If no seats remain available, it notifies the user.
     *
     * Why?
     * Improves user experience by offering an immediate alternative without restarting the search.
     */
    private void findNextSeat() {
        for (int rowIndex = 0; rowIndex < seats.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < seats[rowIndex].length; columnIndex++) {
                if (seats[rowIndex][columnIndex].equals("A")) {
                    System.out.println("Suggested seat: Row " + (rowIndex + 1) + ", Seat " + (columnIndex + 1));
                    return;  // Stop after the first suggestion
                }
            }
        }
        System.out.println("No Seats Found");  // All seats are reserved
    }

    /**
     * cancelSeat(int rowIndex, int columnIndex):
     * - Validates the requested seat position with isValid().
     * - If the seat is currently Reserved ("X"), flips it back to Available ("A") and confirms the cancellation.
     * - If the seat wasn’t reserved, informs the user.
     * - If indexes are invalid, prints an error.
     *
     * Why?
     * Provides a way to reverse a reservation, updating the seating state.
     */
    public void cancelSeat(int rowIndex, int columnIndex) {
        if (isValid(rowIndex, columnIndex)) {
            if (seats[rowIndex][columnIndex].equals("X")) {
                seats[rowIndex][columnIndex] = "A";  // Free up the seat
                System.out.println("\nReservation cancelled.");
            } else {
                // Seat wasn’t booked in the first place
                System.out.println("\nThat seat is not reserved.");
            }
        } else {
            System.out.println("\nInvalid seat location.");
        }
    }

    /**
     * isValid(int rowIndex, int columnIndex):
     * - Returns true only if rowIndex and columnIndex fall within the array bounds.
     * - Prevents ArrayIndexOutOfBoundsException when accessing seats[].
     *
     * Why?
     * Centralizes boundary checks so other methods stay cleaner and safer.
     */
    private boolean isValid(int rowIndex, int columnIndex) {
        return rowIndex >= 0 && rowIndex < seats.length &&
               columnIndex >= 0 && columnIndex < seats[0].length;
    }
}
