// RuleEngine.java

import java.util.Map;

public class RuleEngine {

    public static Node createRule(String ruleString) {
        // Create AST
        Node root = null;
        if (ruleString.contains("AND")) {
            String[] parts = ruleString.split("AND");
            root = new Node("operator", "AND");
            root.left = new Node("operand", parts[0].trim());
            root.right = new Node("operand", parts[1].trim());
        } else {
            root = new Node("operand", ruleString.trim());
        }

        // Save rule in database
        RuleRepository.save(root, ruleString);

        return root;
    }

    public static boolean evaluateRule(long ruleId, Map<String, Object> userData) {
        // Load rule from database by ID
        Node rule = RuleRepository.findById(ruleId);
        return evaluate(rule, userData);
    }

    static boolean evaluate(Node node, Map<String, Object> userData) {
        if (node.type.equals("operator")) {
            boolean leftEval = evaluate(node.left, userData);
            boolean rightEval = evaluate(node.right, userData);
            return node.value.equals("AND") ? leftEval && rightEval : leftEval || rightEval;
        } else {
            return evaluateCondition(node.value, userData);
        }
    }

    private static boolean evaluateCondition(String condition, Map<String, Object> userData) {
        // Parse the condition and compare with user data


String[] parts = condition.split(" ");
        String attribute = parts[0];
        String operator = parts[1];
        String value = parts[2];
        Object userValue = userData.get(attribute);

        if (userValue instanceof Integer) {
            int userVal = (int) userValue;
            int ruleVal = Integer.parseInt(value);
            return operator.equals(">") ? userVal > ruleVal : userVal < ruleVal;
        } else if (userValue instanceof String) {
            return operator.equals("=") && userValue.equals(value.replaceAll("'", ""));
        }

        return false;
    }
}
