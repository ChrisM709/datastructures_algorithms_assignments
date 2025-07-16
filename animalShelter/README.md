# Animal Shelter (FIFO Queue)

A **console application** that simulates an animal shelter that holds only dogs and cats,
operating on a strictly “first in, first out” basis. Adopters can choose to adopt
the oldest animal of any type, or specifically the oldest dog or cat.

```
Animal.java           ── the Animal data class (type + arrival order)
AnimalShelter.java    ── queue-based shelter logic with enqueue/dequeue
ShelterMenu.java      ── user-facing menu loop (console UI)
```

## Features

- **Enqueue** (admit) a new dog or cat.
- **DequeueAny**: adopt the oldest animal (dog or cat).
- **DequeueDog**: adopt the oldest dog.
- **DequeueCat**: adopt the oldest cat.

## How it works

1. **Animal**  
   - Holds an enum `Type { DOG, CAT }` and an `order` integer (lower = older).
2. **AnimalShelter**  
   - Two `Queue<Animal>` instances (for dogs and cats).  
   - `enqueue(Type)` stamps and adds to the appropriate queue.  
   - `dequeueAny()` peeks at both queues’ heads and removes the one with the smallest `order`.  
   - `dequeueDog()` / `dequeueCat()` remove from the front of the respective queue.
3. **ShelterMenu**  
   - Simple console menu to admit or adopt animals.

## Requirements

- JDK 17 (or any recent LTS)
- A terminal or IDE with Java support (e.g., VS Code)

## Compile & Run

```bash
# from the project root (where your .java files are)
javac *.java
java ShelterMenu
```

## Quick Demo

```
--- Animal Shelter ---
1. Admit a dog
2. Admit a cat
3. Adopt any animal
4. Adopt a dog
5. Adopt a cat
6. Exit
Choose an option: 1
Admitted a dog.

Choose an option: 2
Admitted a cat.

Choose an option: 3
Adopted: DOG #0

Choose an option: 4
Adopted dog #?  # if any remain

Choose an option: 6
```
