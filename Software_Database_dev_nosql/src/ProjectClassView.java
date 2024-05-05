import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProjectClassView extends JFrame {
    private JLabel lblProjNum, lblProjName, lblProjLeader;
    private JTextField txtProjNum, txtProjName, txtProjLeader;
    private JButton btnAddUpdate, btnViewEdit, btnAddEmployee;
    private JTable tblEmployees;
    private DefaultTableModel tblModel;
    private DataAccess dataAccess;

    public ProjectClassView() {
        setTitle("Project Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        lblProjNum = new JLabel("Project Number:");
        lblProjName = new JLabel("Project Name:");
        lblProjLeader = new JLabel("Project Leader:");

        txtProjNum = new JTextField(10);
        txtProjName = new JTextField(10);
        txtProjLeader = new JTextField(10);

        btnAddUpdate = new JButton("Add/Update Project");
        btnViewEdit = new JButton("View/Edit Project");
        btnAddEmployee = new JButton("Add Employee to Project");

        tblModel = new DefaultTableModel();
        tblModel.addColumn("Employee ID");
        tblModel.addColumn("Employee Name");
        tblModel.addColumn("Hours Billed");
        tblModel.addColumn("Wages");
        //tblModel.addColumn("Calculated Wages");
        

        tblEmployees = new JTable(tblModel);

        JPanel pnlInput = new JPanel(new GridLayout(3, 2));
        pnlInput.add(lblProjNum);
        pnlInput.add(txtProjNum);
        pnlInput.add(lblProjName);
        pnlInput.add(txtProjName);
        pnlInput.add(lblProjLeader);
        pnlInput.add(txtProjLeader);

        JPanel pnlButtons = new JPanel(new FlowLayout());
        pnlButtons.add(btnAddUpdate);
        pnlButtons.add(btnViewEdit);
        pnlButtons.add(btnAddEmployee);

        add(pnlInput, BorderLayout.NORTH);
        add(new JScrollPane(tblEmployees), BorderLayout.CENTER);
        add(pnlButtons, BorderLayout.SOUTH);

        dataAccess = new DataAccess();

        btnAddUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int projNum = Integer.parseInt(txtProjNum.getText());
                String projName = txtProjName.getText();
                int projLeader = Integer.parseInt(txtProjLeader.getText());
                ProjectModel project = new ProjectModel(projNum, projName, projLeader);
                dataAccess.createProject(project);
                
                refreshTable();
            }
        });

        btnViewEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int projNum = Integer.parseInt(txtProjNum.getText());
                ProjectModel project = dataAccess.readProject(projNum);
                if (project != null) {
                    
                    txtProjName.setText(project.getProjName());
                    txtProjLeader.setText(Integer.toString(project.getProjLeader()));

                    
                    tblModel.setRowCount(0);

                    
                    List<AssignmentModel> assignments = dataAccess.getAssignmentsForProject(project.getProjNum());
                    for (AssignmentModel assignment : assignments) {
                        EmployeeModel employee = dataAccess.readEmployee(assignment.getEmpID());
                        double wage = employee != null ? employee.getJob().getWage() : 0.0;
                        tblModel.addRow(new Object[]{
                                assignment.getEmpID(),
                                employee != null ? employee.getEmpName() : "",
                                assignment.getHoursBilled(),
                                wage,
                                assignment.getTotalCharge(),
                                assignment.getHoursBilled() * wage
                        });
                    }

                } else {
                    JOptionPane.showMessageDialog(ProjectClassView.this, "Failed to retrieve project details.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnAddEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEmployeeDialog addEmployeeDialog = new AddEmployeeDialog(ProjectClassView.this);
                addEmployeeDialog.setVisible(true);
            }
        });

    }

    private void refreshTable() {
        
        tblModel.setRowCount(0);

        
        int projNum = Integer.parseInt(txtProjNum.getText());
        List<AssignmentModel> assignments = dataAccess.getAssignmentsForProject(projNum);
        for (AssignmentModel assignment : assignments) {
            EmployeeModel employee = dataAccess.readEmployee(assignment.getEmpID());
            double wage = employee != null ? employee.getJob().getWage() : 0.0;
            tblModel.addRow(new Object[]{
                    assignment.getEmpID(),
                    employee != null ? employee.getEmpName() : "",
                    assignment.getHoursBilled(),
                    wage,
                    assignment.getTotalCharge(),
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ProjectClassView().setVisible(true);
            }
        });
    }

    private class AddEmployeeDialog extends JDialog {
        private JLabel lblEmpNum, lblEmpName, lblProjNum, lblHoursBilled;
        private JTextField txtEmpNum, txtEmpName, txtProjNum, txtHoursBilled;
        private JButton btnSave, btnCancel;

        public AddEmployeeDialog(JFrame parent) {
            super(parent, "Add Employee to Project", true);
            setSize(300, 200);
            setLocationRelativeTo(parent);
            setLayout(new GridLayout(5, 2));

            lblEmpNum = new JLabel("Employee Number:");
            lblEmpName = new JLabel("Employee Name:");
            lblProjNum = new JLabel("Project Number:");
            lblHoursBilled = new JLabel("Hours Billed:");

            txtEmpNum = new JTextField();
            txtEmpName = new JTextField();
            txtProjNum = new JTextField();
            txtHoursBilled = new JTextField();

            btnSave = new JButton("Save");
            btnCancel = new JButton("Cancel");

            add(lblEmpNum);
            add(txtEmpNum);
            add(lblEmpName);
            add(txtEmpName);
            add(lblProjNum);
            add(txtProjNum);
            add(lblHoursBilled);
            add(txtHoursBilled);
            add(btnSave);
            add(btnCancel);

            btnSave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int empNum = Integer.parseInt(txtEmpNum.getText());
                    //String empName = txtEmpName.getText();
                    int projNum = Integer.parseInt(txtProjNum.getText());
                    double hoursBilled = Double.parseDouble(txtHoursBilled.getText());
                    
                    
                    
                    EmployeeModel employee = dataAccess.readEmployee(empNum);
                    if (employee != null) {
                        double wage = employee.getJob().getWage();
                        double totalCharge = hoursBilled * wage; 
                    
                        tblModel.addRow(new Object[]{
                                empNum,
                                employee.getEmpName(),
                                hoursBilled,
                                wage,
                                hoursBilled * wage
                        });
            
                        AssignmentModel assignment = new AssignmentModel(empNum, projNum, hoursBilled, totalCharge);
                        dataAccess.createAssignment(assignment);

                        
                        
                        refreshTable();
                    } else {
                        
                        JOptionPane.showMessageDialog(AddEmployeeDialog.this, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
            
                    dispose(); 
                }
            });
            

            btnCancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose(); 
                }
            });
        }
    }
    
}


