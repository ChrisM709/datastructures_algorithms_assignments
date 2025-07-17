import java.util.Scanner;

public class ShelterMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AnimalShelter shelter = new AnimalShelter();
        boolean running = true;

        while (running) {
            System.out.println("\n--- Animal Shelter ---");
            System.out.println("1. Admit a dog");
            System.out.println("2. Admit a cat");
            System.out.println("3. Adopt any animal");
            System.out.println("4. Adopt a dog");
            System.out.println("5. Adopt a cat");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> shelter.enqueue(Animal.Type.DOG);
                case 2 -> shelter.enqueue(Animal.Type.CAT);
                case 3 -> {
                    Animal animal = shelter.dequeueAny();
                    System.out.println("Adopted: " + animal.getType() + " #" + animal.getOrder());
                }
                case 4 -> {
                    Animal animal = shelter.dequeueDog();
                    System.out.println(animal != null
                        ? "Adopted dog #" + animal.getOrder()
                        : "No dogs available");
                }
                case 5 -> {
                    Animal animal = shelter.dequeueCat();
                    System.out.println(animal != null
                        ? "Adopted cat #" + animal.getOrder()
                        : "No cats available");
                }
                case 6 -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }
}
