import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentClassTimetable {
    private static JFrame frame;
    private JTable timetableTable;
    private JTextField classNameField; 

    public StudentClassTimetable() {
        frame = new JFrame("Class Timetable");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);
        
        classNameField = new JTextField(20);
        classNameField.setEditable(true);

        int classNameInput = JOptionPane.showConfirmDialog(frame, classNameField, "Enter Class Name", JOptionPane.OK_CANCEL_OPTION);
        
        if (classNameInput != JOptionPane.OK_OPTION) {
            // Teacher canceled or closed the input dialog
            return;
        }
        String className = classNameField.getText();
        // Create a table model to hold timetable data
        DefaultTableModel tableModel = new DefaultTableModel();
        timetableTable = new JTable(tableModel);

        // Add columns to the table model
        tableModel.addColumn("Day");
        tableModel.addColumn("Subject 1");
        tableModel.addColumn("Subject 2");
        tableModel.addColumn("Subject 3");
        tableModel.addColumn("Subject 4");

        // Fetch class timetable data from the database and populate the table
        fetchTimetableData(className, tableModel);

        JScrollPane scrollPane = new JScrollPane(timetableTable);
        frame.add(scrollPane);
        frame.setVisible(true);
        
    }

    private void fetchTimetableData(String className, DefaultTableModel tableModel) {
        try {
            // Assuming you have a database connection
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/class?useUnicode=true&characterEncoding=utf8", "root", "");
            
            String selectQuery = "SELECT day, subject1, subject2, subject3, subject4 FROM "+className;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String day = resultSet.getString("day");
                String subject1 = resultSet.getString("subject1");
                String subject2 = resultSet.getString("subject2");
                String subject3 = resultSet.getString("subject3");
                String subject4 = resultSet.getString("subject4");

                // Add a row to the table model
                tableModel.addRow(new Object[]{day, subject1, subject2, subject3, subject4});
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error fetching timetable data.");
        }
    }public static void show() {
		// TODO Auto-generated method stub
		frame.setVisible(true);
		
	}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentClassTimetable(); // Replace with the actual class name
        });
    }
    
	
}
