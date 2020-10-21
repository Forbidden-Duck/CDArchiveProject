package structures.lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Doubly Linked List
 * Parent Class
 */
public class DoublyLinkedList<T> {
    Node head, tail;

    /**
     * Node
     * Child Class
     *
     * @param <T> The data to be stored
     */
    public static class Node<T> {
        Node next, previous;
        T data;

        /**
         * Constructor for the Doubly Linked List Node
         *
         * @param data Data is stored in the Node itself
         */
        public Node(T data) {
            this.data = data;
        }

        /**
         * Get the data stored in the Node
         *
         * @return T data
         */
        public T getData() {
            return this.data;
        }

        /**
         * Set/Update the data stored in the Node
         *
         * @param data The new data to be placed in the Node
         */
        public void setData(T data) {
            this.data = data;
        }

        /**
         * Get the next Node of the Node
         *
         * @return Node next
         */
        public Node getNext() {
            return this.next;
        }

        /**
         * Get the previous Node of the Node
         *
         * @return Node previous
         */
        public Node getPrevious() {
            return this.previous;
        }
    }

    /**
     * Prepend a singular Node to the list
     * Returns the head Node
     *
     * @param node Node that is being prepended
     * @returns Node head
     */
    public Node prepend(Node node) {
        // If there is no head or tail
        // Set the head and tail as the Node
        if (this.head == null || this.tail == null) {
            this.head = node;
            this.tail = node;
        } else {
            node.next = this.head;
            this.head.previous = node;
            this.head = node;
        }
        return this.head;
    }

    /**
     * Prepend an array of Nodes to the list
     * Returns the head Node
     *
     * @param nodes Nodes that are being prepended
     * @returns Node head
     */
    public Node prepend(Node[] nodes) {
        if (nodes.length < 1) {
            return this.head;
        }

        for (int i = 0; i < nodes.length; i++) {
            this.prepend(nodes[i]);
        }
        return this.head;
    }

    /**
     * Append a singular Node to the end of the list
     * Returns the tail Node
     *
     * @param node Node that is being appended
     * @return Node tail
     */
    public Node append(Node node) {
        // If there is no head or tail
        // Set the head and tail as the Node
        if (this.head == null || this.tail == null) {
            this.head = node;
            this.tail = node;
        } else {
            node.previous = this.tail;
            this.tail.next = node;
            this.tail = node;
        }
        return this.tail;
    }

    /**
     * Append an array of Nodes to the list
     * Returns the tail Node
     *
     * @param nodes Nodes that are being appened
     * @return Node tail
     */
    public Node append(Node[] nodes) {
        if (nodes.length < 1) {
            return this.tail;
        }

        for (int i = nodes.length - 1; i >= 0; i--) {
            this.append(nodes[i]);
        }

        return this.tail;
    }

    /**
     * Insert a Node before the specified Node
     *
     * @param toInsert Node that gets inserted
     * @param after    Node to insert before
     * @return Node after.previous
     */
    public Node insertBefore(Node toInsert, Node after) {
        if (after == null) {
            return null;
        }

        if (this.head == after) {
            this.head = toInsert;
        }

        toInsert.previous = after.previous;
        after.previous = toInsert;
        toInsert.next = after;
        if (toInsert.previous != null) {
            toInsert.previous.next = toInsert;
        }
        return after.previous;
    }

    /**
     * Insert an array of Nodes before the specified Node
     *
     * @param toInserts Nodes that get inserted
     * @param after     Node to insert before
     * @return Node after.previous
     */
    public Node insertBefore(Node[] toInserts, Node after) {
        if (toInserts.length < 1) {
            return after.previous;
        }
        if (after == null) {
            return null;
        }

        for (int i = 0; i < toInserts.length; i++) {
            this.insertBefore(toInserts[i], after);
        }

        return after.previous;
    }

    /**
     * Insert a Node after the specified Node
     *
     * @param toInsert Node that gets inserted
     * @param before   Node to insert after
     * @return Node before.next
     */
    public Node insertAfter(Node toInsert, Node before) {
        if (before == null) {
            return null;
        }

        if (this.tail == before) {
            this.tail = toInsert;
        }

        toInsert.next = before.next;
        before.next = toInsert;
        toInsert.previous = before;
        if (toInsert.next != null) {
            toInsert.next.previous = toInsert;
        }

        return before.next;
    }

    /**
     * Insert an array of Nodes after the specified Node
     *
     * @param toInserts Nodes that get inserted
     * @param before    Node to insert after
     * @return Node before.next
     */
    public Node insertAfter(Node[] toInserts, Node before) {
        if (toInserts.length < 1) {
            return before.next;
        }
        if (before == null) {
            return null;
        }

        for (int i = toInserts.length - 1; i >= 0; i--) {
            this.insertAfter(toInserts[i], before);
        }

        return before.next;
    }

    /**
     * Delete a Node from the list
     *
     * @param node Node to delete
     */
    public void delete(Node node) {
        if (this.head.equals(node)) {
            if (node.next != null) {
                this.head = node.next;
            } else {
                this.head = null;
            }
        }
        if (this.tail.equals(node)) {
            if (node.previous != null) {
                this.tail = node.previous;
            } else {
                this.tail = null;
            }
        }
        if (node.previous != null) {
            node.previous.next = node.next;
        }
        if (node.next != null) {
            node.next.previous = node.previous;
        }
    }

    /**
     * Delete an array of Nodes from the list
     *
     * @param nodes Nodes to delete
     */
    public void delete(Node[] nodes) {
        if (nodes.length < 1) {
            return;
        }

        for (int i = 0; i < nodes.length; i++) {
            this.delete(nodes[i]);
        }
    }

    /**
     * Delete the Node before the specified Node
     *
     * @param node Node to delete before
     */
    public void deleteBefore(Node node) {
        if (node == null) {
            return;
        }
        if (node.previous == null) {
            return;
        }
        this.delete(node.previous);
    }

    /**
     * Delete the Node before all Nodes in the array of Nodes
     *
     * @param nodes An array of Nodes to delete a Node before
     */
    public void deleteBefore(Node[] nodes) {
        if (nodes.length < 1) {
            return;
        }

        for (int i = 0; i < nodes.length; i++) {
            this.deleteBefore(nodes[i]);
        }
    }

    /**
     * Delete the Node after the specified Node
     *
     * @param node Node to delete after
     */
    public void deleteAfter(Node node) {
        if (node == null) {
            return;
        }
        if (node.next == null) {
            return;
        }
        this.delete(node.next);
    }

    /**
     * Delete the Node after all Nodes in the array of Nodes
     *
     * @param nodes An array of Nodes to delete a Node after
     */
    public void deleteAfter(Node[] nodes) {
        if (nodes.length < 1) {
            return;
        }

        for (int i = 0; i < nodes.length; i++) {
            this.deleteAfter(nodes[i]);
        }
    }

    /**
     * Clears the list of all Nodes
     */
    public void clear() {
        this.delete(this.toArray());
    }

    /**
     * Get a Node based on the index
     * Calculated on the head Node "0" and to tail Node "size"
     *
     * @param index Index to look for
     * @return Node found
     */
    public Node get(int index) {
        Node focused = this.head;
        int i = 0;
        if (index < 0 || index >= this.size()) {
            return focused;
        }

        while (focused != null) {
            if (i == index) {
                return focused;
            }
            focused = focused.next;
            i++;
        }

        return null;
    }

    /**
     * Finds a Node with the same data
     *
     * @param data Data of the Node that's being found
     * @return Node found | null
     */
    public Node find(T data) {
        Node focused = this.head;

        while (focused != null) {
            if ((focused.data.toString() != null && data.toString() != null)
                    && focused.data.toString().equals(data.toString())) {
                return focused;
            } else if (focused.data.equals(data)) {
                return focused;
            }

            focused = focused.next;
        }

        return null;
    }

    /**
     * Find an array of Nodes using an array of data
     * Returns a new array of new Nodes (will not interact with original list)
     *
     * @param datas Array of data to find the Nodes with
     * @return Node[] nodes
     */
    public Node[] find(T[] datas) {
        if (datas.length < 1) {
            return null;
        }
        DoublyLinkedList list = new DoublyLinkedList();

        for (int i = 0; i < datas.length; i++) {
            Node found = this.find(datas[i]);
            if (found == null) {
                list.append(new DoublyLinkedList.Node(null));
            } else {
                list.append(new DoublyLinkedList.Node(found.data));
            }
        }

        return list.toArray();
    }

    /**
     * Return the size of the list
     *
     * @return int size
     */
    public int size() {
        Node focused = this.head;
        int size = 0;

        while (focused != null) {
            size++;
            focused = focused.next;
        }

        return size;
    }

    /**
     * Convert the DoublyLinkedList into a List of data within the Nodes
     *
     * @return List T
     */
    public List<T> toList() {
        Node focused = this.head;
        List data = new ArrayList<T>();

        while (focused != null) {
            data.add(focused.getData());
            focused = focused.next;
        }

        return data;
    }

    /**
     * Converts the list into an array of Nodes
     *
     * @return Node[] nodes
     */
    public Node[] toArray() {
        Node focused = this.head;
        Node[] nodes = new DoublyLinkedList.Node[this.size()];
        int i = 0;

        while (focused != null) {
            nodes[i] = focused;
            focused = focused.next;
            i++;
        }

        return nodes;
    }

    /**
     * Concatenates the data within every Node
     *
     * @return String of data
     */
    @Override
    public String toString() {
        Node focused = this.head;
        String output = "";

        while (focused != null) {
            if (focused.data.toString() != null) {
                output += focused.data.toString();
            } else {
                output += focused.data;
            }
            if (focused.next != null) {
                output += " -> ";
            }
            focused = focused.next;
        }

        return output;
    }

    /**
     * Concatenates the data within every Node in reverse
     *
     * @return Reversed string of data
     */
    public String toStringReverse() {
        Node focused = this.tail;
        String output = "";

        while (focused != null) {
            if (focused.data.toString() != null) {
                output += focused.data.toString();
            } else {
                output += focused.data;
            }
            if (focused.previous != null) {
                output += " <- ";
            }
            focused = focused.next;
        }

        return output;
    }
}