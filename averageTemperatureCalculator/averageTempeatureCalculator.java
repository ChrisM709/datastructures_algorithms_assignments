import java.util.Scanner;

public class averageTempeatureCalculator {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter how many days of temperatures they’ll record
        System.out.print("Number of days with recorded temperature: ");
        int numberOfDays = scanner.nextInt();

        // Create an array to store the temperature for each day
        double[] temperatures = new double[numberOfDays];

        // Loop to read each day’s temperature and store it in the array
        for (int i = 0; i < numberOfDays; i++) {
            System.out.print("Day " + (i + 1) + " temperature: ");
            temperatures[i] = scanner.nextDouble();
        }

        // Sum up all recorded temperatures
        double sum = 0;
        for (int i = 0; i < temperatures.length; i++) {
            sum += temperatures[i];
        }

        // Calculate the average by dividing total sum by number of days
        double average = sum / numberOfDays;
        // Print the average, formatted to two decimal places
        System.out.printf("Average temperature: %.2f\n", average);

        // Count how many days had a temperature above the average
        int numberAboveAverage = 0;
        for (int i = 0; i < temperatures.length; i++) {
            if (temperatures[i] > average) {
                numberAboveAverage++;
            }
        }

        // Output the count of days above average
        System.out.println(numberAboveAverage + " days above average temperature");

        // Close the scanner to free up resources
        scanner.close();
    }
}
