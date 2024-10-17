// EvaluateRuleServlet.java
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import org.json.JSONObject;

@WebServlet("/api/evaluateRule")
public class EvaluateRuleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Read the input JSON from the request body
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String jsonInput = stringBuilder.toString();

        // Parse JSON and get rule ID and user data
        JSONObject inputObject = new JSONObject(jsonInput);
        long ruleId = inputObject.getLong("ruleId");
        JSONObject userData = inputObject.getJSONObject("userData");

        // Evaluate the rule against user data
        boolean result = RuleEngine.evaluateRule(ruleId, userData.toMap());

        // Respond with the result
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("result", result);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();
    }
}
