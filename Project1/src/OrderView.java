import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class OrderView extends JFrame {
    private JTextField idField, customerIDField, orderDateField, totalAmountField;
    private JButton addButton, updateButton;
    private DataAccess dataAccess;

    public OrderView(DataAccess dataAccess) {
        this.dataAccess = dataAccess;

        setTitle("Order Management");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2));

        panel.add(new JLabel("OrderID:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("CustomerID:"));
        customerIDField = new JTextField();
        panel.add(customerIDField);

        panel.add(new JLabel("OrderDate:"));
        orderDateField = new JTextField();
        panel.add(orderDateField);

        panel.add(new JLabel("TotalAmount:"));
        totalAmountField = new JTextField();
        panel.add(totalAmountField);

        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addOrder();
            }
        });
        panel.add(addButton);

        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateOrder();
            }
        });
        panel.add(updateButton);

        add(panel);
        setVisible(true);
    }

    private void addOrder() {
    try {
        int id = Integer.parseInt(idField.getText());
        int customerID = Integer.parseInt(customerIDField.getText());
        // Assuming you handle date input appropriately, format it to 'YYYY-MM-DD'
        String orderDate = orderDateField.getText();
        double totalAmount = Double.parseDouble(totalAmountField.getText());

        // Format orderDate to 'YYYY-MM-DD'
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        java.util.Date parsedDate = dateFormat.parse(orderDate);
        dateFormat.applyPattern("yyyy-MM-dd");
        orderDate = dateFormat.format(parsedDate);

        OrderModel order = new OrderModel(id, customerID, orderDate, totalAmount);
        dataAccess.addOrder(order);
        JOptionPane.showMessageDialog(this, "Order added successfully.");
        clearFields();
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid values.");
    } catch (ParseException ex) {
        JOptionPane.showMessageDialog(this, "Error parsing date: " + ex.getMessage());
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error adding order: " + ex.getMessage());
    }
}


private void updateOrder() {
    try {
        int id = Integer.parseInt(idField.getText());
        int customerID = Integer.parseInt(customerIDField.getText());
        // Assuming you handle date input appropriately, format it to 'YYYY-MM-DD'
        String orderDate = orderDateField.getText();
        double totalAmount = Double.parseDouble(totalAmountField.getText());

        // Format orderDate to 'YYYY-MM-DD'
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        java.util.Date parsedDate = dateFormat.parse(orderDate);
        dateFormat.applyPattern("yyyy-MM-dd");
        orderDate = dateFormat.format(parsedDate);

        OrderModel order = new OrderModel(id, customerID, orderDate, totalAmount);
        dataAccess.updateOrder(order);
        JOptionPane.showMessageDialog(this, "Order updated successfully.");
        clearFields();
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid values.");
    } catch (ParseException ex) {
        JOptionPane.showMessageDialog(this, "Error parsing date: " + ex.getMessage());
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error updating order: " + ex.getMessage());
    }
}


    private void clearFields() {
        idField.setText("");
        customerIDField.setText("");
        orderDateField.setText("");
        totalAmountField.setText("");
    }
}
