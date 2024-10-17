// RuleRepository.java
import java.sql.*;

public class RuleRepository {

    public static void save(Node rule, String ruleString) {
        String sql = "INSERT INTO rules (rule_string, left_node, right_node, operator, type) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ruleString);
            pstmt.setString(2, rule.left != null ? rule.left.value : null);  // Left node value
            pstmt.setString(3, rule.right != null ? rule.right.value : null);  // Right node value
            pstmt.setString(4, rule.value);  // Operator or operand value
            pstmt.setString(5, rule.type);   // Node type ("operator" or "operand")

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Node findById(long ruleId) {
        String sql = "SELECT * FROM rules WHERE id = ?";
        Node node = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, ruleId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String ruleString = rs.getString("rule_string");
                String leftNode = rs.getString("left_node");
                String rightNode = rs.getString("right_node");
                String operator = rs.getString("operator");
                String type = rs.getString("type");

                node = new Node(type, operator);
                if (leftNode != null) {
                    node.left = new Node("operand", leftNode);
                }
                if (rightNode != null) {
                    node.right = new Node("operand", rightNode);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return node;
    }
}
