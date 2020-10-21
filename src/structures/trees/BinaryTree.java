package structures.trees;

import java.util.ArrayList;

/**
 * Binary Tree
 * Parent Class
 */
public class BinaryTree<T> {
    Node root;

    /**
     * Node
     * Child Class
     */
    public static class Node<T> {
        Node left, right;
        int key;
        T data;

        /**
         * Constructor for the Binary Tree Node
         *
         * @param key  Key identifies the Node (Primary Key)
         * @param data Data is stored in the Node itself
         */
        public Node(int key, T data) {
            this.key = key;
            this.data = data;
        }

        /**
         * Get the key of the Node
         *
         * @return int key
         */
        public int getKey() {
            return this.key;
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
         * Get the left Node of the Node
         *
         * @return Node left
         */
        public Node getLeft() {
            return this.left;
        }

        /**
         * Get the right Node of the Node
         *
         * @return Node right
         */
        public Node getRight() {
            return this.right;
        }

        /**
         * Concatenates the key of the Node and the data within the Node
         *
         * @return String output
         */
        @Override
        public String toString() {
            return this.key + ": " + this.data.toString();
        }
    }

    /**
     * Inserts a Node into the Binary Tree
     *
     * @param node Node that gets inserted into the tree
     */
    public void insert(Node node) {
        this.root = this.insert(this.root, node);
    }

    /**
     * Inserts an array of Nodes into the Binary Tree
     *
     * @param nodes Array of Nodes to be inserted into the tree
     */
    public void insert(Node[] nodes) {
        if (nodes.length < 1) {
            return;
        }

        for (int i = 0; i < nodes.length; i++) {
            this.insert(nodes[i]);
        }
    }

    /**
     * Deletes a Node from the Binary Tree
     *
     * @param node Node to delete from the tree
     */
    public void delete(Node node) {
        this.root = this.delete(this.root, node);
    }

    /**
     * Deletes an array of Nodes from the Binary Tree
     *
     * @param nodes Nodes to delete from the tree
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
     * Clears the entire Binary Tree
     */
    public void clear() {
        this.clear(this.root);
    }

    /**
     * Find the Node corresponding to the specified key
     *
     * @param key Key the identifies the Node
     * @return Node found
     */
    public Node find(int key) {
        return this.find(this.root, key);
    }

    /**
     * Finds the smallest key in the Binary Tree
     *
     * @return int key
     */
    public int min() {
        return this.min(this.root);
    }

    /**
     * Finds the largest key in the Binary Tree
     *
     * @return int key
     */
    public int max() {
        return this.max(this.root);
    }

    /**
     * Traverse the Binary Tree in pre-order
     *
     * @return ArrayList<Node> nodes
     */
    public ArrayList<Node> preOrder() {
        return this.preOrder(this.root);
    }

    /**
     * Traverse the Binary Tree in in-order
     *
     * @return ArrayList<Node> nodes
     */
    public ArrayList<Node> inOrder() {
        return this.inOrder(this.root);
    }

    /**
     * Traverse the Binary Tree in post-order
     *
     * @return ArrayList<Node> nodes
     */
    public ArrayList<Node> postOrder() {
        return this.postOrder(this.root);
    }

    /**
     * Return the root Node
     *
     * @return Node root
     */
    public Node getRoot() {
        return this.root;
    }

    private Node insert(Node focused, Node insert) {
        if (focused == null) {
            return insert;
        }

        if (insert.key < focused.key) {
            focused.left = this.insert(focused.left, insert);
        } else if (insert.key > focused.key) {
            focused.right = this.insert(focused.right, insert);
        }

        return focused;
    }

    private Node delete(Node focused, Node delete) {
        if (focused == null) {
            return null;
        }

        if (delete.key < focused.key) {
            focused.left = this.delete(focused.left, delete);
        } else if (delete.key > focused.key) {
            focused.right = this.delete(focused.right, delete);
        } else {
            if (focused.left == null) {
                return focused.right;
            }
            if (focused.right == null) {
                return focused.left;
            }

            focused.key = min(focused.right);
            focused.right = this.delete(focused.right, focused);
        }

        return focused;
    }

    private void clear(Node focused) {
        this.delete(new BinaryTree.Node[]{
                focused,
                focused.left,
                focused.right
        });
        if (this.root != null) {
            this.clear(this.root);
        }
    }

    private int min(Node node) {
        if (node.left != null) {
            return this.min(node.left);
        }
        return node.key;
    }

    private int max(Node node) {
        if (node.right != null) {
            return this.max(node.right);
        }
        return node.key;
    }

    private Node find(Node focused, int key) {
        if (focused.key == key) {
            return focused;
        }

        if (key < focused.key) {
            return this.find(focused.left, key);
        } else if (key > focused.key) {
            return this.find(focused.right, key);
        }

        return null;
    }

    private ArrayList<Node> preOrder(Node focused) {
        ArrayList<Node> list = new ArrayList<Node>();

        if (focused != null) {
            list.add(focused);
            list.addAll(this.preOrder(focused.left));
            list.addAll(this.preOrder(focused.right));
        }

        return list;
    }

    private ArrayList<Node> inOrder(Node focused) {
        ArrayList<Node> list = new ArrayList<Node>();

        if (focused != null) {
            list.addAll(this.inOrder(focused.left));
            list.add(focused);
            list.addAll(this.inOrder(focused.right));
        }

        return list;
    }

    private ArrayList<Node> postOrder(Node focused) {
        ArrayList<Node> list = new ArrayList<Node>();

        if (focused != null) {
            list.addAll(this.postOrder(focused.left));
            list.addAll(this.postOrder(focused.right));
            list.add(focused);
        }

        return list;
    }
}