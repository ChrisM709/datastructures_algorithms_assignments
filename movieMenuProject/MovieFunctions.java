public class MovieFunctions {
    private String[][] seats;

    // starts the 10 x 10 seat layout and fills all seats as available 
    public MovieFunctions() {
        seats = new String[10][10];
        initializeSeats();
    }

    // method used above to mark seats available
    public void initializeSeats() {
        for (int rowIndex = 0; rowIndex < seats.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < seats[rowIndex].length; columnIndex++) {
                seats[rowIndex][columnIndex] = "A";
            }
        }
    }

    // method to display seats based on them being available or not 
    public void displaySeats() {
        System.out.println("\nSeating Chart (A = Available, X = Reserved):");
        for (int rowIndex = 0; rowIndex < seats.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < seats[rowIndex].length; columnIndex++) {
                System.out.print(seats[rowIndex][columnIndex] + " ");
            }
            System.out.println();
        }
    }

    // method to display a ticket once a seat is reserved 
    public void reserveSeat(int rowIndex, int columnIndex) {
        if (isValid(rowIndex, columnIndex)) {
            if (seats[rowIndex][columnIndex].equals("A")) {
                seats[rowIndex][columnIndex] = "X";

                // Show mock ticket
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
                System.out.println("\nThat seat is already reserved.");
                findNextSeat();
            }
        } else {
            System.out.println("\nInvalid seat location.");
        }
    }

    // if a seat is already taken, this method will suggest a new seat for the user from the start of the array
    private void findNextSeat() {
        for (int rowIndex = 0; rowIndex < seats.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < seats[rowIndex].length; columnIndex++) {
                if (seats[rowIndex][columnIndex].equals("A")) {
                    System.out.println("Suggested seat: Row " + (rowIndex + 1) + ", Seat " + (columnIndex + 1));
                    return;
                }
            }
        }
        System.out.println("No Seats Found");
    }

    // method used to cancel a reservation 
    public void cancelSeat(int rowIndex, int columnIndex) {
        if (isValid(rowIndex, columnIndex)) {
            if (seats[rowIndex][columnIndex].equals("X")) {
                seats[rowIndex][columnIndex] = "A";
                System.out.println("\nReservation cancelled.");
            } else {
                System.out.println("\nThat seat is not reserved.");
            }
        } else {
            System.out.println("\nInvalid seat location.");
        }
    }

    // checks to make sure a seat is within the array
    private boolean isValid(int rowIndex, int columnIndex) {
        return rowIndex >= 0 && rowIndex < seats.length &&
                columnIndex >= 0 && columnIndex < seats[0].length;
    }
}
