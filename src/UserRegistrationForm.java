import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class UserRegistrationForm {
    private JFrame frame;
    private JTextField nameField;
    private JTextField rollNoField;
    private JRadioButton studentRadioButton;
    private JRadioButton teacherRadioButton;
    private JTextField classField;
    private JButton registerButton;

    public UserRegistrationForm() {
        frame = new JFrame("User Registration");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new FlowLayout());

        JLabel nameLabel = new JLabel("Name:");
        JLabel rollNoLabel = new JLabel("Roll No:");
        JLabel typeLabel = new JLabel("User Type:");
        JLabel classLabel = new JLabel("Class (For Students):");

        nameField = new JTextField(20);
        rollNoField = new JTextField(20);

        studentRadioButton = new JRadioButton("Student");
        teacherRadioButton = new JRadioButton("Teacher");

        ButtonGroup userTypeGroup = new ButtonGroup();
        userTypeGroup.add(studentRadioButton);
        userTypeGroup.add(teacherRadioButton);

        classField = new JTextField(20);

        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                int rollNo = Integer.parseInt(rollNoField.getText());
                String userType = studentRadioButton.isSelected() ? "Student" : "Teacher";
                String userClass = classField.getText();
                try{  
            		Class.forName("com.mysql.jdbc.Driver");  
            		Connection con=DriverManager.getConnection(  
            		"jdbc:mysql://localhost:3306/class?useUnicode=true&characterEncoding=utf8","root","");
            		Statement stmt=con.createStatement();
            		int rs=0;
            		if(userType == "Student") {
            		rs=stmt.executeUpdate("INSERT INTO `student`(`class`, `roll_no`, `name`) VALUES ('"+userClass+"','"+rollNo+"','"+name+"')");
            		}
            		if(userType == "Teacher") {
                		rs=stmt.executeUpdate("INSERT INTO `teacher`(`roll_no`, `name`, `password`) VALUES ('"+rollNo+"','"+name+"','"+userClass+"')");
                		}
            		if(rs==1)
            		JOptionPane.showMessageDialog(frame, "User registered successfully."); 
            		con.close();  
            		}catch(Exception e1){ System.out.println(e1);
            		JOptionPane.showMessageDialog(frame, "User registered unsuccessfully.");
            		}
                
            }
        });

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(rollNoLabel);
        panel.add(rollNoField);
        panel.add(typeLabel);
        panel.add(studentRadioButton);
        panel.add(teacherRadioButton);
        panel.add(classLabel);
        panel.add(classField);
        panel.add(registerButton);

        frame.add(panel);
    }
   

    public void show() {
        frame.setVisible(true);
    }
}
