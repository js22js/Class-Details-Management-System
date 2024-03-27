import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage {
    private JFrame frame;

    public MainPage() {
        frame = new JFrame("Main Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        JPanel panel = new JPanel(new FlowLayout());

        JButton teacherButton = new JButton("Teacher");
        JButton studentButton = new JButton("Student");
        JButton adminButton = new JButton("Admin");

        teacherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TeacherLogin teacherLogin = new TeacherLogin();
                teacherLogin.show();
            }
        });

        studentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StudentLogin studentLogin = new StudentLogin();
                studentLogin.show();
            }
        });

        adminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AdminRegistration adminRegistration = new AdminRegistration();
                adminRegistration.show();
            }
        });

        panel.add(teacherButton);
        panel.add(studentButton);
        panel.add(adminButton);

        frame.add(panel);
    }

    public void show() {
        frame.setVisible(true);
    }
}