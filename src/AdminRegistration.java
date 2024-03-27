import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminRegistration {
    private JFrame frame;
    private JTextField adminUsernameField;
    private JPasswordField adminPasswordField;
    private JButton adminLoginButton;

    public AdminRegistration() {
        frame = new JFrame("Admin Registration");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel(new FlowLayout());

        JLabel adminUsernameLabel = new JLabel("Admin Username:");
        JLabel adminPasswordLabel = new JLabel("Admin Password:");

        adminUsernameField = new JTextField(20);
        adminPasswordField = new JPasswordField(20);

        adminLoginButton = new JButton("Admin Login");
        adminLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String adminUsername = adminUsernameField.getText();
                char[] adminPasswordChars = adminPasswordField.getPassword();
                String adminPassword = new String(adminPasswordChars);

                // Perform admin login validation (e.g., check against a predefined admin username and password)
                if (isAdminAuthenticated(adminUsername, adminPassword)) {
                    openRegistrationForm();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid admin credentials. Please try again.");
                }
            }
        });

        panel.add(adminUsernameLabel);
        panel.add(adminUsernameField);
        panel.add(adminPasswordLabel);
        panel.add(adminPasswordField);
        panel.add(adminLoginButton);

        frame.add(panel);
    }

    public void show() {
        frame.setVisible(true);
    }

    private boolean isAdminAuthenticated(String username, String password) {
        
    	try{  
    		Class.forName("com.mysql.jdbc.Driver");  
    		Connection con=DriverManager.getConnection(  
    		"jdbc:mysql://localhost:3306/class?useUnicode=true&characterEncoding=utf8","root","");
    		Statement stmt=con.createStatement();  
    		ResultSet rs=stmt.executeQuery("SELECT * FROM `admin` WHERE username='"+username+"' AND password='"+password+"';");
    		if(rs.next())
    		return true; 
    		con.close();  
    		}catch(Exception e){ System.out.println(e); }  
    	
    	return false;
    }

    private void openRegistrationForm() {
        // Create and open the user registration form
        UserRegistrationForm userRegistrationForm = new UserRegistrationForm();
        userRegistrationForm.show();
        frame.dispose();
    }
}
