import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentLogin {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public StudentLogin() {
        frame = new JFrame("Student Login");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel(new FlowLayout());

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                // Implement student authentication against admin-registered students
                boolean authenticated = authenticateStudent(username, password);

                if (authenticated) {
                    StudentDashboard studentDashboard = new StudentDashboard();
                    studentDashboard.show();
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid credentials. Please try again.");
                }
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        frame.add(panel);
    }

    public void show() {
        frame.setVisible(true);
    }

    private boolean authenticateStudent(String username, String password) {
        // Implement student authentication logic here.
        // You should check the "student" table in your database and return true if authenticated, false otherwise.
        return DatabaseManager.getInstance().validateStudentLogin(username, password);
    }
}
