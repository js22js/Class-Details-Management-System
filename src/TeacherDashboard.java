import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherDashboard {
    private JFrame frame;

    public TeacherDashboard() {
        frame = new JFrame("Teacher Dashboard");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);

        // Create teacher dashboard with buttons for Class Timetable, Exam Timetable, Result Entry

        JPanel panel = new JPanel(new FlowLayout());
        JButton classTimetableButton = new JButton("Class Timetable");
        JButton examTimetableButton = new JButton("Exam Timetable");
        JButton resultEntryButton = new JButton("Result Entry");

        classTimetableButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TeacherClassTimetable teacherClassTimetable=new TeacherClassTimetable();
                teacherClassTimetable.show();
            }
        });

        examTimetableButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	TeacherExamTimetable teacherExamTimetable=new TeacherExamTimetable();
            	teacherExamTimetable.show();
            }
        });

        resultEntryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	TeacherResultEntry teacherResultEntry=new TeacherResultEntry();
            	teacherResultEntry.show();
            }
        });

        panel.add(classTimetableButton);
        panel.add(examTimetableButton);
        panel.add(resultEntryButton);

        frame.add(panel);
    }

    public void show() {
        frame.setVisible(true);
    }
}