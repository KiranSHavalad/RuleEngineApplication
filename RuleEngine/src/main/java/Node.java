// Node.java
public class Node {
    String type;   // "operator" or "operand"
    String value;  // Condition or operator value (e.g., "age > 30", "AND")
    Node left, right;

    public Node(String type, String value) {
        this.type = type;
        this.value = value;
        this.left = null;
        this.right = null;
    }
}
