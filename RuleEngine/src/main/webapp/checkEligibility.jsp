<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Check Eligibility and Add Rule</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 20px; }
        .container { max-width: 600px; margin: 0 auto; }
        .form-group { margin-bottom: 10px; }
        label { display: block; margin-bottom: 5px; }
        input[type="text"], input[type="number"] { width: 100%; padding: 8px; }
        button { padding: 10px 15px; background-color: #4CAF50; color: white; border: none; cursor: pointer; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Check Candidate Eligibility</h1>

        <!-- Display eligibility result -->
        <div id="result" style="color: green;">
            <%
                if (request.getAttribute("result") != null) {
                    out.print(request.getAttribute("result"));
                }
            %>
        </div>

        <div id="error" style="color: red;">
            <%
                if (request.getAttribute("error") != null) {
                    out.print(request.getAttribute("error"));
                }
            %>
        </div>

        <!-- Candidate form -->
        <form action="checkEligibilityServlet" method="post">
            <div class="form-group">
                <label for="age">Age:</label>
                <input type="number" id="age" name="age" required>
            </div>
            <div class="form-group">
                <label for="department">Department:</label>
                <input type="text" id="department" name="department" required>
            </div>
            <div class="form-group">
                <label for="salary">Salary:</label>
                <input type="number" id="salary" name="salary" required>
            </div>
            <button type="submit">Check Eligibility</button>
        </form>

        <h2>Add New Rule</h2>

        <!-- Form to add a new rule -->
        <form action="index.jsp">
            
            <button type="submit">Add Rule</button>
        </form>
    </div>
</body>
</html>
