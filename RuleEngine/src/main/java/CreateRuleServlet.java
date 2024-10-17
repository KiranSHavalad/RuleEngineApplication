import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

// Assuming you have this servlet mapped to "/createRuleServlet"
@WebServlet("/createRuleServlet")
public class CreateRuleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ruleString = request.getParameter("rule");

        // Check if the input is valid
        if (ruleString == null || ruleString.trim().isEmpty()) {
            request.setAttribute("error", "Please enter a valid rule.");
            request.getRequestDispatcher("/createRule.jsp").forward(request, response);
            return;
        }

        try {
            // Create Abstract Syntax Tree (AST) and save the rule
            Node rule = RuleEngine.createRule(ruleString);
            RuleRepository.save(rule, ruleString);

            // Set success message and forward back to JSP
            request.setAttribute("result", "Rule Created Successfully: " + ruleString);
        } catch (Exception e) {
            // Handle any errors and set error message
            request.setAttribute("error", "Error: Failed to create rule.");
            e.printStackTrace();  // For debugging purposes
        }

        // Forward back to the JSP page
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
