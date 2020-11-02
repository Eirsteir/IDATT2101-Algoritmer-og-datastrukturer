public class BinarySearchTree {

    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();

        for (String s : args)
            tree.insert(s);

        tree.print();
    }

    private static final int COUNT = 10;

    private Node root;

    public BinarySearchTree() {
        this.root = null;
    }

    public void insert(String key) {
        this.root = insertRecursive(root, key);
    }

    // Use the word as a sorting key and insert recursively
    private Node insertRecursive(Node root, String key) {
        if (root == null)
            return new Node(key);

        if (root.key.compareTo(key) > 0)
            root.left = insertRecursive(root.left, key);
        else
            root.right = insertRecursive(root.right, key);

        return root;
    }

    public void print() {
        // Pass initial space count as 0
        printRecursive(this.root, 0);
    }

    private void printRecursive(Node root, int space) {
        if (root == null)
            return;

        // Increase distance between levels
        space += COUNT;

        // Process right child first
        printRecursive(root.right, space);

        // Print current node after space
        // count
        System.out.print("\n");
        for (int i = COUNT; i < space; i++)
            System.out.print(" ");
        System.out.print(root.key + "\n");

        // Process left child
        printRecursive(root.left, space);
    }

    static class Node {
        String key;
        Node left;
        Node right;

        public Node(String item) {
            this.key = item;
            this.left = this.right = null;
        }
    }
}
