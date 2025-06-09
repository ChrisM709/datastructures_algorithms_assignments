# DataStructures-Algorithms: Linked List Delete Method

## Overview

This project demonstrates a simple Java implementation of a singly linked list with the ability to insert and delete nodes at any position. It includes:

- **`SingleLinkedList`**: A class managing the linked list operations.
- **`Node`**: An inner class representing each list element.
- **JUnit Tests**: Automated tests to verify insertion and deletion behaviors.

## Features

1. **Create List**: Start a new list with a single node using `createSingleLinkedlist(int value)`.
2. **Insert Node**: Add a node at the front, end, or middle using `insertLinkedList(int value, int position)`.
3. **Delete Node**: Remove a node by index (0-based) with `delete(int position)`, handling:
   - Invalid positions (prints an error).
   - Deletion at the head (first node).
   - Deletion at the tail (last node).
   - Deletion in the middle.

## Project Structure

```
Datastructures-Algorithms
└── linkedListDeleteMethod
    ├── src
    │   ├── main/java/com/linkedlistdelete/SingleLinkedList.java
    │   └── test/java/com/linkedlistdelete/SingleLinkedListTest.java
    ├── .gitignore
    ├── pom.xml
    └── README.md  ← this file
```

- **`SingleLinkedList.java`**: Contains the implementation of the list and delete logic.
- **`SingleLinkedListTest.java`**: JUnit tests covering all deletion scenarios.
- **`.gitignore`**: Configured to ignore compiled files and PDFs (`*.pdf`).
- **`pom.xml`**: Maven configuration (JUnit 5 dependency).

## Getting Started

### Prerequisites

- Java JDK 8 or higher
- Maven 3.6+

### Build & Run Tests

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/Datastructures-Algorithms.git
   cd Datastructures-Algorithms/linkedListDeleteMethod
   ```
2. Build the project and run tests:
   ```bash
   mvn clean test
   ```
All tests should pass, showing that insertion and deletion work as expected.

## How It Works

### `SingleLinkedList` Highlights

- **Fields**:
  - `head`: points to the first node.
  - `tail`: points to the last node.
  - `size`: tracks number of nodes.

- **`createSingleLinkedlist(int value)`**:
  - Initializes an empty list with one node.

- **`insertLinkedList(int value, int position)`**:
  - Inserts at front, end, or specified middle index.
  - Adjusts `head`, `tail`, and increments `size`.

- **`delete(int position)`**:
  - Checks for invalid index (prints error).
  - For `position == 0`: moves `head` forward.
  - For `position == size - 1`: updates `tail` after traversal.
  - For middle positions: skips over the target node.
  - Decrements `size` and updates pointers accordingly.

### Tests Overview

- **`deleteHead`**: Remove the first node.
- **`deleteMiddle`**: Remove a node in the middle.
- **`deleteTail`**: Remove the last node.
- **`deleteInvalid`**: Attempt out-of-bounds deletions (list remains unchanged).