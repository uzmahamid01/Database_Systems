import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomerView extends JFrame {
    private JTextField idField, nameField, addressField, phoneField, emailField;
    private JButton addButton, updateButton;
    private DataAccess dataAccess;

    public CustomerView(DataAccess dataAccess) {
        this.dataAccess = dataAccess;

        setTitle("Customer Management");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2));

        panel.add(new JLabel("CustomerID:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("CustomerName:"));
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
                addCustomer();
            }
        });
        panel.add(addButton);

        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCustomer();
            }
        });
        panel.add(updateButton);

        add(panel);
        setVisible(true);
    }

    private void addCustomer() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();

            CustomerModel customer = new CustomerModel(id, name, address, phone, email);
            dataAccess.addCustomer(customer);
            JOptionPane.showMessageDialog(this, "Customer added successfully.");
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID. Please enter a valid integer.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding customer: " + ex.getMessage());
        }
    }

    private void updateCustomer() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();

            CustomerModel customer = new CustomerModel(id, name, address, phone, email);
            // Assuming customerDAO.updateCustomer(customer) method is implemented
            dataAccess.updateCustomer(customer);
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
