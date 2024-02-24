import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeClassView extends JFrame {
    private JTextField empNumField;
    private JTextField empNameField;
    private JTextField jobClassIdField;

    public EmployeeClassView() {
        setTitle("Add or Update Employee");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel empNumLabel = new JLabel("Employee Number:");
        empNumField = new JTextField();
        JLabel empNameLabel = new JLabel("Employee Name:");
        empNameField = new JTextField();
        JLabel jobClassIdLabel = new JLabel("JobClass ID:");
        jobClassIdField = new JTextField();

        JButton addButton = new JButton("Add/Update");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int empNum = Integer.parseInt(empNumField.getText());
                String empName = empNameField.getText();
                int jobClassId = Integer.parseInt(jobClassIdField.getText());

                JobModel job = DataAccess.readJobClass(jobClassId);
                if (job != null) {
                    EmployeeModel employee = new EmployeeModel(empNum, empName, job);
                    DataAccess.createEmployee(employee);
                    JOptionPane.showMessageDialog(EmployeeClassView.this, "Employee added/updated successfully!");

                    // Clear text fields
                    empNumField.setText("");
                    empNameField.setText("");
                    jobClassIdField.setText("");
                } else {
                    JOptionPane.showMessageDialog(EmployeeClassView.this, "Invalid JobClass ID!");
                }
            }
        });

        panel.add(empNumLabel);
        panel.add(empNumField);
        panel.add(empNameLabel);
        panel.add(empNameField);
        panel.add(jobClassIdLabel);
        panel.add(jobClassIdField);
        panel.add(addButton);

        add(panel);
        setVisible(true);
    }
    
}
