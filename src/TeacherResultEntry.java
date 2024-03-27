import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class TeacherResultEntry {
    private JFrame frame;
    private DefaultTableModel tableModel;
    private JTable resultTable;
    private JTextField classNameField;

    public TeacherResultEntry() {
        frame = new JFrame("Result Entry");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);
        

        JPanel panel = new JPanel(new BorderLayout());
        
        classNameField = new JTextField(20);
        classNameField.setEditable(true);

        int classNameInput = JOptionPane.showConfirmDialog(frame, classNameField, "Enter Class Name", JOptionPane.OK_CANCEL_OPTION);
        
        if (classNameInput != JOptionPane.OK_OPTION) {
            // Teacher canceled or closed the input dialog
            return;
        }
        String className = classNameField.getText();
        
        // Create a table with text fields to display and enter results (Roll No, Marks for 4 subjects, Total, Result)
        String[] columnNames = {"Roll No", "Subject 1", "Subject 2", "Subject 3", "Subject 4", "Total", "Result"};
        Object[][] data = {
                {"Roll001", "", "", "", "", "", ""},
                {"Roll002", "", "", "", "", "", ""}
                // Add more rows as needed
        };
        tableModel = new DefaultTableModel(data, columnNames);
        resultTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add an "Add Row" button to add a new row for entering results
        JButton addRowButton = new JButton("Add Row");
        addRowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addRowToTable();
            }
        });

        // Add a "Save" button to store the result data in the database
        JButton saveButton = new JButton("Save Results");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveResultsToDatabase(className);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addRowButton);
        buttonPanel.add(saveButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
    }

    private void addRowToTable() {
        tableModel.addRow(new Object[]{"", "", "", "", "", "", ""});
    }

    private void saveResultsToDatabase(String className) {
        // Assuming you have a MySQL database connection
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/class?useUnicode=true&characterEncoding=utf8", "root", "");
            Statement stmt=con.createStatement(); 
            boolean rs= stmt.execute("CREATE TABLE IF NOT EXISTS "+className+"result(roll_no VARCHAR(25),subject1 VARCHAR(25),subject2 VARCHAR(25),subject3 VARCHAR(25),subject4 VARCHAR(25),total INT, result VARCHAR(25));");
            String insertQuery = "INSERT INTO "+className+"result(roll_no, subject1, subject2, subject3, subject4, total, result) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);

            for (int row = 0; row < tableModel.getRowCount(); row++) {
                String rollNo = (String) tableModel.getValueAt(row, 0);
                String subject1 = (String) tableModel.getValueAt(row, 1);
                String subject2 = (String) tableModel.getValueAt(row, 2);
                String subject3 = (String) tableModel.getValueAt(row, 3);
                String subject4 = (String) tableModel.getValueAt(row, 4);

                // Calculate Total and Result as needed
                int total = 0;
                String result = "";

                // Insert the values into the database
                preparedStatement.setString(1, rollNo);
                preparedStatement.setString(2, subject1);
                preparedStatement.setString(3, subject2);
                preparedStatement.setString(4, subject3);
                preparedStatement.setString(5, subject4);
                preparedStatement.setInt(6, total);
                preparedStatement.setString(7, result);

                preparedStatement.executeUpdate();
            }

            JOptionPane.showMessageDialog(frame, "Results saved to the database.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error saving results to the database.");
        }
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TeacherResultEntry().show();
            }
        });
    }
}
