import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentDashboard {
    private JFrame frame;

    public StudentDashboard() {
        frame = new JFrame("Student Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel(new FlowLayout());

        JButton classTimetableButton = new JButton("Class Timetable");
        JButton examTimetableButton = new JButton("Exam Timetable");
        JButton resultsButton = new JButton("Results");

        classTimetableButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the class timetable view
                StudentClassTimetable.show();
            }
        });

        examTimetableButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the exam timetable view
                StudentExamTimetable.show();
            }
        });

        resultsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the results view
                StudentResultView.show();
            }
        });

        panel.add(classTimetableButton);
        panel.add(examTimetableButton);
        panel.add(resultsButton);

        frame.add(panel);
    }

    public void show() {
        frame.setVisible(true);
    }
}
