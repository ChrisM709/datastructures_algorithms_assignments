public class MovieFunctions {
    private String[][] seats;

    public MovieFunctions() {
        seats = new String[10][10];
        initializeSeats();
    }

    public void initializeSeats() {
        for (int rowIndex = 0; rowIndex < seats.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < seats[rowIndex].length; columnIndex++) {
                seats[rowIndex][columnIndex] = "A";
            }
        }
    }

    public void displaySeats() {
        System.out.println("\nSeating Chart (A = Available, X = Reserved):");
        for (int rowIndex = 0; rowIndex < seats.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < seats[rowIndex].length; columnIndex++) {
                System.out.print(seats[rowIndex][columnIndex] + " ");
            }
            System.out.println();
        }
    }

    public void reserveSeat(int rowIndex, int columnIndex) {
        if (isValid(rowIndex, columnIndex)) {
            if (seats[rowIndex][columnIndex].equals("A")) {
                seats[rowIndex][columnIndex] = "X";
                System.out.println("Seat reserved.");
            } else {
                System.out.println("That seat is already reserved.");
            }
        } else {
            System.out.println("Invalid seat location.");
        }
    }

    public void cancelSeat(int rowIndex, int columnIndex) {
        if (isValid(rowIndex, columnIndex)) {
            if (seats[rowIndex][columnIndex].equals("X")) {
                seats[rowIndex][columnIndex] = "A";
                System.out.println("Reservation cancelled.");
            } else {
                System.out.println("That seat is not reserved.");
            }
        } else {
            System.out.println("Invalid seat location.");
        }
    }

    private boolean isValid(int rowIndex, int columnIndex) {
        return rowIndex >= 0 && rowIndex < seats.length &&
               columnIndex >= 0 && columnIndex < seats[0].length;
    }
}
