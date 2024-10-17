// DatabaseConnection.java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/rule_engine_db";
    private static final String USER = "root";  // Use your MySQL username
    private static final String PASSWORD = "root";  // Use your MySQL password

    public static Connection getConnection() throws SQLException {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return DriverManager.getConnection(URL, USER, PASSWORD);
        
    }


}