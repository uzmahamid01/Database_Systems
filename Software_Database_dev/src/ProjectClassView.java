import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Vector;

public class ProjectClassView extends JFrame {
    private JTextField projNumField;
    private JTextField projNameField;
    private JTextField projLeaderField;
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton saveButton;

    public ProjectClassView() {
        setTitle("Project Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        initComponents();
        setLayout(new BorderLayout());
        add(createInputPanel(), BorderLayout.NORTH);
        add(new JScrollPane(employeeTable), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private void initComponents() {
        projNumField = new JTextField(10);
        projNameField = new JTextField(20);
        projLeaderField = new JTextField(10);
        String[] columnNames = {"Employee ID", "Employee Name", "Billed Hours"};
        tableModel = new DefaultTableModel(columnNames, 0);
        employeeTable = new JTable(tableModel);
        addButton = new JButton("Add Employee");
        saveButton = new JButton("Save");
        saveButton.setEnabled(false); // Disable save button initially
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Project Number:"));
        inputPanel.add(projNumField);
        inputPanel.add(new JLabel("Project Name:"));
        inputPanel.add(projNameField);
        inputPanel.add(new JLabel("Project Leader:"));
        inputPanel.add(projLeaderField);
        return inputPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(saveButton);
        return buttonPanel;
    }

    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void addSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }
    

    public void enableSaveButton(boolean enable) {
        saveButton.setEnabled(enable);
    }

    // Method to add a row to the employee table
    public void addEmployeeRow(Vector<Object> row) {
        tableModel.addRow(row);
        JOptionPane.showMessageDialog(this, "Employee added successfully.");
        // Reset text boxes
        projNumField.setText("");
        projNameField.setText("");
        projLeaderField.setText("");
    }


    // Method to clear all rows from the employee table
    public void clearEmployeeTable() {
        tableModel.setRowCount(0);
    }

    // Method to get project number from the input field
    public int getProjectNumber() {
        return Integer.parseInt(projNumField.getText());
    }

    // Method to get project name from the input field
    public String getProjectName() {
        return projNameField.getText();
    }

    // Method to get project leader from the input field
    public int getProjectLeader() {
        return Integer.parseInt(projLeaderField.getText());
    }

    // Method to get selected employee ID from the table
    public int getSelectedEmployeeID() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow != -1) {
            return (int) tableModel.getValueAt(selectedRow, 0);
        }
        return -1;
    }

    // Method to get billed hours entered by the user
    public double getBilledHours() {
        while (true) {
            String input = JOptionPane.showInputDialog(this, "Enter billed hours:");
            if (input != null) {
                try {
                    double billedHours = Double.parseDouble(input);
                    if (billedHours >= 0) {
                        return billedHours;
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid input. Billed hours must be a non-negative number.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.");
                }
            } else {
                // User clicked cancel, return a negative value to indicate cancel
                return -1;
            }
        }
    }

    /// Method to display assigned employees with their billed hours
    public void displayAssignedEmployees(Map<Integer, Double> employeeHoursMap) {
        clearEmployeeTable();
        for (Map.Entry<Integer, Double> entry : employeeHoursMap.entrySet()) {
            int empID = entry.getKey();
            double billedHours = entry.getValue();
            // Fetch employee details from the database based on empID
            // Here, you should query the database to get the employee name based on empID
            String employeeName = DataAccess.getEmployeeNameFromDatabase(empID);
            // Add employee details to the table
            Vector<Object> row = new Vector<>();
            row.add(empID);
            row.add(employeeName);
            row.add(billedHours);
            addEmployeeRow(row);
        }
    }

}





// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// public class ProjectClassView extends JFrame {
//     private JTextField projNumField;
//     private JTextField projNameField;
//     private JTextField projLeaderField;
//     private JButton addButton;
//     private JButton updateButton;

//     public ProjectClassView() {
//         setTitle("Project Management");
//         setSize(400, 200);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//         JPanel panel = new JPanel();
//         panel.setLayout(new GridLayout(4, 2));

//         JLabel projNumLabel = new JLabel("Project Number:");
//         projNumField = new JTextField();
//         panel.add(projNumLabel);
//         panel.add(projNumField);

//         JLabel projNameLabel = new JLabel("Project Name:");
//         projNameField = new JTextField();
//         panel.add(projNameLabel);
//         panel.add(projNameField);

//         JLabel projLeaderLabel = new JLabel("Project Leader:");
//         projLeaderField = new JTextField();
//         panel.add(projLeaderLabel);
//         panel.add(projLeaderField);

//         addButton = new JButton("Add");
//         updateButton = new JButton("Update");

//         panel.add(addButton);
//         panel.add(updateButton);

//         addButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 // Call controller method to add project
//                 addProject();
//             }
//         });

//         updateButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 // Call controller method to update project
//                 updateProject();
//             }
//         });

//         add(panel);
//         setVisible(true);
//     }

//     private void addProject() {
//         // Get data from text fields and pass it to controller to add project
//         int projNum = Integer.parseInt(projNumField.getText());
//         String projName = projNameField.getText();
//         int projLeader = Integer.parseInt(projLeaderField.getText());
//         // Call controller method to add project with provided data
//         // Controller.addProject(projNum, projName, projLeader);
//     }

//     private void updateProject() {
//         // Get data from text fields and pass it to controller to update project
//         int projNum = Integer.parseInt(projNumField.getText());
//         String projName = projNameField.getText();
//         int projLeader = Integer.parseInt(projLeaderField.getText());
//         // Call controller method to update project with provided data
//         // Controller.updateProject(projNum, projName, projLeader);
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(new Runnable() {
//             @Override
//             public void run() {
//                 new ProjectClassView();
//             }
//         });
//     }
// }
