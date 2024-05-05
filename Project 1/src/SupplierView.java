import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SupplierView extends JFrame {
    private JTextField idField, nameField, addressField, phoneField, emailField;
    private JButton addButton, updateButton;
    private DataAccess dataAccess;

    public SupplierView(DataAccess dataAccess) {
        this.dataAccess = dataAccess;

        setTitle("Supplier Management");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2));

        panel.add(new JLabel("SupplierID:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("SupplierName:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Address:"));
        addressField = new JTextField();
        panel.add(addressField);

        panel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        panel.add(phoneField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addSupplier();
            }
        });
        panel.add(addButton);

        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateSupplier();
            }
        });
        panel.add(updateButton);

        add(panel);
        setVisible(true);
    }

    private void addSupplier() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();

            SupplierModel supplier = new SupplierModel(id, name, address, phone, email);
            dataAccess.addSupplier(supplier);
            JOptionPane.showMessageDialog(this, "Customer added successfully.");
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID. Please enter a valid integer.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding customer: " + ex.getMessage());
        }
    }

    private void updateSupplier() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();

            SupplierModel supplier = new SupplierModel(id, name, address, phone, email);
            // Assuming customerDAO.updateCustomer(customer) method is implemented
            dataAccess.updateSupplier(supplier);
            JOptionPane.showMessageDialog(this, "Customer updated successfully.");
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID. Please enter a valid integer.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating customer: " + ex.getMessage());
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        addressField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }
}
