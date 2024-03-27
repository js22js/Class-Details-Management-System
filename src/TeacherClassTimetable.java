import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;

public class TeacherClassTimetable {
    private JFrame frame;
    private JTextField[] dayFields;
    private JTextField[][] subjectFields;
    private JTextField classNameField; // Add a field for class name input

    public TeacherClassTimetable() {
        frame = new JFrame("Class Timetable");
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

        // Create text fields for input and display the class timetable (5 days x 4 subjects)
        dayFields = new JTextField[5];
        subjectFields = new JTextField[5][4];
        JPanel timetablePanel = new JPanel(new GridLayout(6, 5)); // 6 rows (5 days + header) and 5 columns (4 subjects + header)

        String[] dayNames = {"Day", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String[] subjectNames = {"Subject 1", "Subject 2", "Subject 3", "Subject 4"};

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == 0) {
                    // Header row
                    if (j == 0) {
                        JTextField emptyField = new JTextField();
                        emptyField.setEditable(false);
                        timetablePanel.add(emptyField);
                    } else {
                        JTextField headerField = new JTextField(subjectNames[j - 1]); // Adjust index
                        headerField.setEditable(false);
                        timetablePanel.add(headerField);
                    }
                } else {
                    // Data rows
                    if (j == 0) {
                        JTextField dayField = new JTextField(dayNames[i]);
                        dayField.setEditable(false);
                        timetablePanel.add(dayField);
                        dayFields[i - 1] = dayField;
                    } else {
                        JTextField textField = new JTextField();
                        subjectFields[i - 1][j - 1] = textField;
                        timetablePanel.add(textField);
                    }
                }
            }
        }

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

        panel.add(timetablePanel, BorderLayout.CENTER);
        panel.add(saveButton, BorderLayout.SOUTH);

        frame.add(panel);
    }

    private void saveTimetableToDatabase(String className) throws ClassNotFoundException {
        // Assuming you have a MySQL database connection
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/class?useUnicode=true&characterEncoding=utf8", "root", "");
            Statement stmt=con.createStatement(); 
            boolean rs= stmt.execute("CREATE TABLE IF NOT EXISTS "+className+"(day VARCHAR(25),subject1 VARCHAR(25),subject2 VARCHAR(25),subject3 VARCHAR(25),subject4 VARCHAR(25));");
            String insertQuery = "INSERT INTO "+className+" (day, subject1, subject2, subject3, subject4) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);

            for (int i = 0; i < 5; i++) {
            	 preparedStatement.setString(1, dayFields[i].getText());
                 preparedStatement.setString(2, subjectFields[i][0].getText());
                 preparedStatement.setString(3, subjectFields[i][1].getText());
                 preparedStatement.setString(4, subjectFields[i][2].getText());
                 preparedStatement.setString(5, subjectFields[i][3].getText());

                 

                preparedStatement.executeUpdate();
            }

            JOptionPane.showMessageDialog(frame, "Timetable saved");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error saving timetable contact admin");
        }
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TeacherClassTimetable().show();
            }
        });
    }
}
