import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/checkEligibilityServlet")
public class CheckEligibilityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the candidate's details from the form submission
        int age = Integer.parseInt(request.getParameter("age"));
        String department = request.getParameter("department");
        int salary = Integer.parseInt(request.getParameter("salary"));

        try {
            // Fetch all rules from the database
            List<String> rules = fetchRulesFromDatabase();

            if (rules.isEmpty()) {
                request.setAttribute("error", "No rules found in the database.");
                request.getRequestDispatcher("/checkEligibility.jsp").forward(request, response);
                return;
            }

            // Evaluate each rule against the candidate's data
            boolean isEligible = false;  // Initially, assume not eligible
            for (String ruleString : rules) {
                Node rule = RuleEngine.createRule(ruleString);
                boolean ruleResult = RuleEngine.evaluate(rule, Map.of(
                    "age", age,
                    "department", department,
                    "salary", salary
                ));
                System.out.println("Evaluating rule: " + ruleString + " - Result: " + ruleResult);  // Log the rule and result

                // If any rule is satisfied, the candidate is eligible
                if (ruleResult) {
                    isEligible = true;
                    break;  // No need to check further rules
                }
            }

            // Set the result based on eligibility and forward back to JSP
            String result = isEligible ? "Eligible" : "Not Eligible";
            request.setAttribute("result", result);

        } catch (Exception e) {
            // Handle any errors and set error message
            request.setAttribute("error", "Error: Failed to check eligibility.");
            e.printStackTrace();  // For debugging
        }

        // Forward back to the JSP page
        request.getRequestDispatcher("/checkEligibility.jsp").forward(request, response);
    }

    // Helper method to fetch all rules from the database
    private List<String> fetchRulesFromDatabase() throws SQLException {
        List<String> rules = new ArrayList<>();
        String sql = "SELECT rule_string FROM rules";  // Get all rules from the database

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                rules.add(rs.getString("rule_string"));
            }
        }
        return rules;
    }
}
