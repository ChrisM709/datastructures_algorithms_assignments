package com.linkedlistdelete;

public class SingleLinkedList {
    public Node head;
    public Node tail;
    public int size;

    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
            this.next = null;
        }

        public Node() {
            this(0);
        }
    }

    public Node createSingleLinkedlist(int nodeValue) {
        Node node = new Node(nodeValue);
        head = node;
        tail = node;
        size = 1;
        return head;
    }

    public void insertLinkedList(int nodeValue, int location) {
        Node node = new Node(nodeValue);
        if (head == null) {
            createSingleLinkedlist(nodeValue);
            return;
        }

        if (location <= 0) {
            node.next = head;
            head = node;
        } else if (location >= size) {
            tail.next = node;
            tail = node;
        } else {
            Node prev = head;
            for (int i = 0; i < location - 1; i++) {
                prev = prev.next;
            }
            node.next = prev.next;
            prev.next = node;
        }

        size++;
    }

    /**
     * Deletes the node at the specified 0-based position.
     * Handles invalid positions, head, tail, and middle removals.
     * @param position index of the node to delete
     */
    public void delete(int position) {
        if (position < 0 || position >= size) {
            System.out.println("Error: position out of bounds.");
            return;
        }

        if (position == 0) {
            head = head.next;
            size--;
            if (head == null) {
                tail = null;
            }
            return;
        }

        Node prev = head;
        for (int i = 0; i < position - 1; i++) {
            prev = prev.next;
        }

        Node toDelete = prev.next;
        prev.next = toDelete.next;
        if (toDelete == tail) {
            tail = prev;
        }
        size--;
    }

}

