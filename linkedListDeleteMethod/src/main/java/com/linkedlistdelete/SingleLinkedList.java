package com.linkedlistdelete;

public class SingleLinkedList {
    // The first node in the list (or null if the list is empty)
    public Node head;
    // The last node in the list (or null if the list is empty)
    public Node tail;
    // The number of nodes currently in the list
    public int size;

    /**
     * Inner class representing each node in the linked list.
     * Holds an integer value and a reference to the next node.
     */
    public static class Node {
        public int value;   // The data stored in this node
        public Node next;   // Reference to the next node in the list

        // Constructor to create a node with a specific value
        public Node(int value) {
            this.value = value;
            this.next = null;  // Next is null until we link it in the list
        }

        // No-arg constructor defaults to value = 0
        public Node() {
            this(0);
        }
    }

    /**
     * createSingleLinkedList(int nodeValue):
     * – Called when the list is empty and we want to start a new list.
     * – Creates a new node with the given value.
     * – Sets both head and tail to this new node, since it's the only element.
     * – Initializes size to 1.
     * – Returns the new head node.
     *
     * Why? We need a clear way to bootstrap an empty list before performing
     * insertions or deletions.
     */
    public Node createSingleLinkedlist(int nodeValue) {
        Node node = new Node(nodeValue);
        head = node;   // First element
        tail = node;   // Also last element
        size = 1;      // List now has one node
        return head;
    }

    /**
     * insertLinkedList(int nodeValue, int location):
     * – Adds a new node with the specified value at the given position:
     *   * If the list is empty, delegates to createSingleLinkedlist().
     *   * If location ≤ 0, inserts at the front (new head).
     *   * If location ≥ size, appends at the end (new tail).
     *   * Otherwise, walks to the node just before the desired index,
     *     splices the new node in between.
     * – Increments size after insertion.
     *
     * Why? Provides flexible insertion at any point, including front, middle, or end,
     * keeping head, tail, and size up to date.
     */
    public void insertLinkedList(int nodeValue, int location) {
        Node node = new Node(nodeValue);

        // Empty list: create initial node
        if (head == null) {
            createSingleLinkedlist(nodeValue);
            return;
        }

        // Insert at the front
        if (location <= 0) {
            node.next = head;  // New node points to old head
            head = node;       // Update head
        }
        // Insert at the end
        else if (location >= size) {
            tail.next = node;  // Old tail points to new node
            tail = node;       // Update tail
        }
        // Insert in the middle
        else {
            Node prev = head;
            // Walk to the node just before the insertion point
            for (int i = 0; i < location - 1; i++) {
                prev = prev.next;
            }
            node.next = prev.next;  // New node points to next node
            prev.next = node;       // Previous node now points to new node
        }

        size++;  // One more node in the list
    }

    /**
     * delete(int position):
     * – Removes the node at the given 0-based index.
     * – Handles several cases:
     *   * Invalid position (negative or ≥ size): prints an error, does nothing.
     *   * position == 0: removes the head by moving head to head.next.
     *     If the list becomes empty, also clears tail.
     *   * Otherwise: walks to the node before the target, bypasses the target,
     *     and if the target was the tail, updates tail to the previous node.
     * – Decrements size after a successful deletion.
     *
     * Why? Allows users to remove elements from anywhere in the list,
     * correctly maintaining head, tail, and size.
     */
    public void delete(int position) {
        // Check for invalid indices
        if (position < 0 || position >= size) {
            System.out.println("Error: position out of bounds.");
            return;
        }

        // Remove the head node
        if (position == 0) {
            head = head.next;  // Skip over the old head
            size--;
            // If list is now empty, clear tail as well
            if (head == null) {
                tail = null;
            }
            return;
        }

        // Walk to the node just before the one we want to delete
        Node prev = head;
        for (int i = 0; i < position - 1; i++) {
            prev = prev.next;
        }

        Node toDelete = prev.next;         // The node we're removing
        prev.next = toDelete.next;         // Bypass it
        // If we removed the tail, update the tail reference
        if (toDelete == tail) {
            tail = prev;
        }
        size--;  // One fewer node
    }

}
