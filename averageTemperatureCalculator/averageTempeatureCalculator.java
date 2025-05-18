import java.util.Scanner;

public class averageTempeatureCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Number of days with recorded temperature: ");
        int numberOfDays = scanner.nextInt();

        double[] temperatures = new double[numberOfDays];

        for (int i = 0; i < numberOfDays; i++){
            System.out.print("Day " + (i + 1) + " temperature: ");
            temperatures[i] = scanner.nextDouble();
        }

        double sum = 0;
        for (int i = 0; i < temperatures.length; i++) {
            sum += temperatures[i];
        }

        double average = sum / numberOfDays;
        System.out.printf("Average temperature: %.2f\n", average);

        int numberAboveAverage = 0;
        for (int i = 0; i < temperatures.length; i++) {
            if (temperatures[i] > average) {
                numberAboveAverage++;
            }
        }

        System.out.println(numberAboveAverage + " days above average temperature");

        scanner.close();
    }
}