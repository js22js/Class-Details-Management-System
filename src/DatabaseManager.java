import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/class";
    private static final String DB_USER = "your_database_user";
    private static final String DB_PASSWORD = "your_database_password";
    private static Connection connection;
    
    // Singleton pattern to ensure one connection instance
    private static DatabaseManager instance;

    private DatabaseManager() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public boolean validateTeacherLogin(String username, String password) {
        // Implement teacher authentication logic using the "teacher" table
    	try{  
    	    		Class.forName("com.mysql.jdbc.Driver");  
    	    		Connection con=DriverManager.getConnection(  
    	    		"jdbc:mysql://localhost:3306/class?useUnicode=true&characterEncoding=utf8","root","");
    	    		Statement stmt=con.createStatement();  
    	    		ResultSet rs=stmt.executeQuery("SELECT * FROM `teacher` WHERE name='"+username+"' AND password='"+password+"';");
    	    		if(rs.next())
    	    		return true; 
    	    		con.close();  
    	    		}catch(Exception e){ System.out.println(e); }
		return false;
    }

    public boolean validateStudentLogin(String username, String password) {
    	try{  
    		Class.forName("com.mysql.jdbc.Driver");  
    		Connection con=DriverManager.getConnection(  
    		"jdbc:mysql://localhost:3306/class?useUnicode=true&characterEncoding=utf8","root","");
    		Statement stmt=con.createStatement();  
    		ResultSet rs=stmt.executeQuery("SELECT * FROM `student` WHERE name='"+username+"' AND roll_no='"+password+"';");
    		if(rs.next())
    		return true; 
    		con.close();  
    		}catch(Exception e){ System.out.println(e); }
return false;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
