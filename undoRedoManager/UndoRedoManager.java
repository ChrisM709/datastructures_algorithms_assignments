public class UndoRedoManager<Snapshot> {
    private static class Node<Snapshot> {
        Snapshot state;
        Node<Snapshot> prev, next;
        Node(Snapshot s) { state = s; }
    }

    private Node<Snapshot> head;   
    private Node<Snapshot> tail;     
    private Node<Snapshot> current; 

    public void push(Snapshot snapshot) {
        Node<Snapshot> node = new Node<>(snapshot);

        if (head == null) {             
            head = tail = current = node;
            return;
        }

        if (current != tail) {           
            tail = current;
            tail.next = null;
        }

        tail.next = node;
        node.prev  = tail;
        tail       = current = node;
    }

    public Snapshot undo() {
        if (current != null && current.prev != null) {
            current = current.prev;
        }
        return current.state;
    }

    public Snapshot redo() {
        if (current != null && current.next != null) {
            current = current.next;
        }
        return current.state;
    }
}
