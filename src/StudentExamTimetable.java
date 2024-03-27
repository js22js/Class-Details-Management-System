import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentExamTimetable {
    private static JFrame frame;
    private JTable examTimetableTable;
    private JTextField classNameField; 

    public StudentExamTimetable() {
        frame = new JFrame("Exam Timetable");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);

        // Create a table model to hold exam timetable data
        DefaultTableModel tableModel = new DefaultTableModel();
        examTimetableTable = new JTable(tableModel);
        classNameField = new JTextField(20);
        classNameField.setEditable(true);

        int classNameInput = JOptionPane.showConfirmDialog(frame, classNameField, "Enter Class Name", JOptionPane.OK_CANCEL_OPTION);
        
        if (classNameInput != JOptionPane.OK_OPTION) {
            // Teacher canceled or closed the input dialog
            return;
        }
        String className = classNameField.getText();

        // Add columns to the table model
        tableModel.addColumn("Subject");
        tableModel.addColumn("Exam Date");

        // Fetch exam timetable data from the database and populate the table
        fetchExamTimetableData(className, tableModel);

        JScrollPane scrollPane = new JScrollPane(examTimetableTable);
        frame.add(scrollPane);

        frame.setVisible(true);
    }

    private void fetchExamTimetableData(String className, DefaultTableModel tableModel) {
        try {
            // Assuming you have a database connection
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/class?useUnicode=true&characterEncoding=utf8", "root", "");

            String selectQuery = "SELECT subject, exam_date FROM " + className + "exam";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String subject = resultSet.getString("subject");
                String examDate = resultSet.getString("exam_date");

                // Add a row to the table model
                tableModel.addRow(new Object[]{subject, examDate});
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error fetching exam timetable data.");
        }
    }

    public static void show() {
    	frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentExamTimetable(); // Replace with the actual class name
        });
    }
}
