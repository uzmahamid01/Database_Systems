import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Main Window");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton jobClassButton = new JButton("Job Class");
        JButton employeeButton = new JButton("Employee Class");
        JButton projectButton = new JButton("Project Class");

        jobClassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openJobClassView();
            }
        });

        employeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openEmployeeView();
            }
        });

        projectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openProjectView();
            }
        });

        // Layout setup
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(jobClassButton);
        panel.add(employeeButton);
        panel.add(projectButton); 

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    // Method to open JobClassView
    private void openJobClassView() {
        JobClassView jobClassView = new JobClassView();
        jobClassView.setVisible(true);
    }

    // Method to open EmployeeClassView
    private void openEmployeeView() {
        EmployeeClassView employeeClassView = new EmployeeClassView();
        employeeClassView.setVisible(true);
    }

    // Method to open ProjectClassView
    private void openProjectView() {
        ProjectClassView projectClassView = new ProjectClassView();
        projectClassView.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainWindow();
            }
        });
    }
}



