import java.util.LinkedList;
import java.util.Queue;

public class AnimalShelter {
    private final Queue<Animal> dogs   = new LinkedList<>();
    private final Queue<Animal> cats   = new LinkedList<>();
    private int orderCounter = 0;  

    public void enqueue(Animal.Type type) {
        Animal animal = new Animal(type, orderCounter++);
        if (type == Animal.Type.DOG)   dogs.add(animal);
        else                            cats.add(animal);
    }

    public Animal dequeueAny() {
        if (dogs.isEmpty())   return cats.poll();
        if (cats.isEmpty())   return dogs.poll();
        Animal oldestDog = dogs.peek();
        Animal oldestCat = cats.peek();
        return (oldestDog.getOrder() < oldestCat.getOrder()
                ? dogs.poll()
                : cats.poll());
    }

    public Animal dequeueDog() {
        return dogs.poll(); 
    }

    public Animal dequeueCat() {
        return cats.poll();
    }
}
