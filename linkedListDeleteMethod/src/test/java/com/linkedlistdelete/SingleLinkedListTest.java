package com.linkedlistdelete;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SingleLinkedListTest {
    SingleLinkedList list;

    @BeforeEach
    void setup() {
        list = new SingleLinkedList();
        list.createSingleLinkedlist(10);
        list.insertLinkedList(20, 1);
        list.insertLinkedList(30, 2);
    }

    @Test
    void deleteHead() {
        list.delete(0);
        assertEquals(2, list.size);
        assertEquals(20, list.head.value);
    }

    @Test
    void deleteMiddle() {
        list.delete(1);
        assertEquals(2, list.size);
        assertEquals(30, list.head.next.value);
    }

    @Test
    void deleteTail() {
        list.delete(list.size - 1);
        assertEquals(2, list.size);
        assertEquals(20, list.tail.value);
    }

    @Test
    void deleteInvalid() {
        list.delete(-1);
        list.delete(100);
        assertEquals(3, list.size);
    }
}
