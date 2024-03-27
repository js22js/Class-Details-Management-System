import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentResultView {
    private static JFrame frame;
    private JTable resultTable;
    private JTextField classNameField; 

    public StudentResultView() {
        frame = new JFrame("Result View");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);

        // Create a table model to hold result data
        DefaultTableModel tableModel = new DefaultTableModel();
        resultTable = new JTable(tableModel);
        classNameField = new JTextField(20);
        classNameField.setEditable(true);

        int classNameInput = JOptionPane.showConfirmDialog(frame, classNameField, "Enter Class Name", JOptionPane.OK_CANCEL_OPTION);
        
        if (classNameInput != JOptionPane.OK_OPTION) {
            // Teacher canceled or closed the input dialog
            return;
        }
        String className = classNameField.getText();


        // Add columns to the table model
        tableModel.addColumn("Roll No");
        tableModel.addColumn("Subject 1");
        tableModel.addColumn("Subject 2");
        tableModel.addColumn("Subject 3");
        tableModel.addColumn("Subject 4");
        tableModel.addColumn("Total");
        tableModel.addColumn("Result");

        // Fetch result data from the database and populate the table
        fetchResultData(className, tableModel);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        frame.add(scrollPane);

        frame.setVisible(true);
    }

    private void fetchResultData(String className, DefaultTableModel tableModel) {
        try {
            // Assuming you have a database connection
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/class?useUnicode=true&characterEncoding=utf8", "root", "");

            String selectQuery = "SELECT roll_no, subject1, subject2, subject3, subject4, total, result FROM " + className + "result";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String rollNo = resultSet.getString("roll_no");
                String subject1 = resultSet.getString("subject1");
                String subject2 = resultSet.getString("subject2");
                String subject3 = resultSet.getString("subject3");
                String subject4 = resultSet.getString("subject4");
                int total = resultSet.getInt("total");
                String result = resultSet.getString("result");

                // Add a row to the table model
                tableModel.addRow(new Object[]{rollNo, subject1, subject2, subject3, subject4, total, result});
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error fetching result data.");
        }
    }

    public static void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentResultView(); // Replace with the actual class name
        });
    }
}
