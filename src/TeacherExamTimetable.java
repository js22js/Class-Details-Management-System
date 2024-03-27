import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

public class TeacherExamTimetable {
    private JFrame frame;
    private JTextField classNameField;
    private JTextField[] subjectFields;
    private JTextField[] dateFields;

    public TeacherExamTimetable() {
        frame = new JFrame("Exam Timetable");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);

        JPanel panel = new JPanel(new BorderLayout());

        // Add an input dialog to prompt for class name
        classNameField = new JTextField(20);
        classNameField.setEditable(true);

        int classNameInput = JOptionPane.showConfirmDialog(frame, classNameField, "Enter Class Name", JOptionPane.OK_CANCEL_OPTION);
        
        if (classNameInput != JOptionPane.OK_OPTION) {
            // Teacher canceled or closed the input dialog
            return;
        }
        String className = classNameField.getText();
        // Create text fields for subjects and dates
        int numSubjects = 4; // Change this to the number of subjects you have
        subjectFields = new JTextField[numSubjects];
        dateFields = new JTextField[numSubjects];

        JPanel inputPanel = new JPanel(new GridLayout(numSubjects, 3)); // 3 columns: Subject, Date, Empty Label

        for (int i = 0; i < numSubjects; i++) {
            subjectFields[i] = new JTextField(20);
            subjectFields[i].setEditable(true);
            dateFields[i] = new JTextField(20);
            dateFields[i].setEditable(true);

            inputPanel.add(new JLabel("Subject " + (i + 1) + ": "));
            inputPanel.add(subjectFields[i]);
            inputPanel.add(new JLabel("Date " + (i + 1) + ": "));
            inputPanel.add(dateFields[i]);
            inputPanel.add(new JLabel()); // Empty label for spacing
            inputPanel.add(new JLabel()); // Empty label for spacing
        }

        // Add a "Save" button to store the exam timetable data in the database
        JButton saveButton = new JButton("Save Timetable");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    saveTimetableToDatabase(className);
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(saveButton, BorderLayout.SOUTH);

        frame.add(panel);
    }

    private void saveTimetableToDatabase(String className) throws ClassNotFoundException {
        // Assuming you have a MySQL database connection
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/class?useUnicode=true&characterEncoding=utf8", "root", "");
            Statement stmt=con.createStatement(); 
            boolean rs= stmt.execute("CREATE TABLE IF NOT EXISTS "+className+"exam(subject VARCHAR(25), exam_date DATE);");
            String insertQuery = "INSERT INTO "+className+"exam(subject, exam_date) VALUES (?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);

            
            for (int i = 0; i < subjectFields.length; i++) {
                String subject = subjectFields[i].getText();
                String date = dateFields[i].getText();
                
                if (!subject.isEmpty() && !date.isEmpty()) {

                    preparedStatement.setString(1, subject);
                    preparedStatement.setString(2, date);
                    
                    preparedStatement.executeUpdate();
                }
            }

            JOptionPane.showMessageDialog(frame, "Exam Timetable saved ");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error saving Exam Timetable contact admin");
        }
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TeacherExamTimetable().show();
            }
        });
    }
}
