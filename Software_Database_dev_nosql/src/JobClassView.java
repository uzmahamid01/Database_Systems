import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JobClassView extends JFrame {
    private JTextField jobClassIdField;
    private JTextField jobNameField;
    private JTextField wageField;

    public JobClassView() {
        setTitle("Add or Update JobClass");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel jobClassIdLabel = new JLabel("JobClass ID:");
        jobClassIdField = new JTextField();
        JLabel jobNameLabel = new JLabel("Job Name:");
        jobNameField = new JTextField();
        JLabel wageLabel = new JLabel("Wage:");
        wageField = new JTextField();

        JButton addButton = new JButton("Add/Update");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int jobClassId = Integer.parseInt(jobClassIdField.getText());
                String jobName = jobNameField.getText();
                double wage = Double.parseDouble(wageField.getText());

                JobModel job = new JobModel(jobClassId, jobName, wage);
                DataAccess.createJobClass(job);
                JOptionPane.showMessageDialog(JobClassView.this, "JobClass added/updated successfully!");

                // Clear text fields
                jobClassIdField.setText("");
                jobNameField.setText("");
                wageField.setText("");
            }
        });

        panel.add(jobClassIdLabel);
        panel.add(jobClassIdField);
        panel.add(jobNameLabel);
        panel.add(jobNameField);
        panel.add(wageLabel);
        panel.add(wageField);
        panel.add(addButton);

        add(panel);
        setVisible(true);
    }
}
