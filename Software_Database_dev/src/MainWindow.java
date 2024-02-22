import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Main Window");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create buttons
        JButton jobClassButton = new JButton("Job Class");
        JButton employeeButton = new JButton("Employee Class");
        JButton projectButton = new JButton("Project Class");

        // Add action listeners to buttons
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





// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// public class MainWindow extends JFrame {
//     public MainWindow() {
//         setTitle("Main Window");
//         setSize(300, 200);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLocationRelativeTo(null);

//         JPanel panel = new JPanel();
//         panel.setLayout(new GridLayout(2, 1));

//         JButton jobClassButton = new JButton("JobClass");
//         jobClassButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 JobClassView jobClassView = new JobClassView();
//                 jobClassView.setVisible(true);
//             }
//         });

//         JButton employeeButton = new JButton("Employee");
//         employeeButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 EmployeeClassView employeeView = new EmployeeClassView();
//                 employeeView.setVisible(true);
//             }
//         });

//         panel.add(jobClassButton);
//         panel.add(employeeButton);

//         add(panel);
//         setVisible(true);

        
//     }

    

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(new Runnable() {
//             public void run() {
//                 new MainWindow();
//             }
//         });
//     }
// }
