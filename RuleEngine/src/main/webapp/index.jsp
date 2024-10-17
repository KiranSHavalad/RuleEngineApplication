<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Rule Engine - Create Rule</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 20px; }
        .container { max-width: 600px; margin: 0 auto; }
        .form-group { margin-bottom: 10px; }
        label { display: block; margin-bottom: 5px; }
        input[type="text"] { width: 100%; padding: 8px; }
        button { padding: 10px 15px; background-color: #4CAF50; color: white; border: none; cursor: pointer; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Create Rule</h1>

        <!-- Display success or error messages -->
        <div id="result" style="color:green;">
            <%
                if (request.getAttribute("result") != null) {
                    out.print(request.getAttribute("result"));
                }
            %>
        </div>
        <div id="error" style="color:red;">
            <%
                if (request.getAttribute("error") != null) {
                    out.print(request.getAttribute("error"));
                }
            %>
        </div>

        <!-- Rule form, sends data to the servlet using POST method -->
        <form action="createRuleServlet" method="post">
            <div class="form-group">
                <label for="rule">Enter Rule (e.g., age > 30 AND department = 'Sales'):</label>
                <input type="text" id="rule" name="rule" required>
            </div>
            <button type="submit">Submit Rule</button>
        </form>
        
         <h2>Check Eligibility</h2>
          <form action="checkEligibility.jsp">
            
            <button type="submit">Check</button>
    </div>
</body>
</html>
