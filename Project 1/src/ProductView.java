import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ProductView extends JFrame {
    private JTextField idField, nameField, descriptionField, priceField, quantityField;
    private JButton addButton, updateButton;
    private DataAccess dataAccess;

    public ProductView(DataAccess dataAccess) {
        this.dataAccess = dataAccess;

        setTitle("Product Management");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2));

        panel.add(new JLabel("ProductID:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("ProductName:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        panel.add(descriptionField);

        panel.add(new JLabel("Price:"));
        priceField = new JTextField();
        panel.add(priceField);

        panel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        panel.add(quantityField);

        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });
        panel.add(addButton);

        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateProduct();
            }
        });
        panel.add(updateButton);

        add(panel);
        setVisible(true);
    }

    private void addProduct() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String description = descriptionField.getText();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            ProductModel product = new ProductModel(id, name, price, description, quantity);
            dataAccess.addProduct(product);
            JOptionPane.showMessageDialog(this, "Product added successfully.");
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid values for price and quantity.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding product: " + ex.getMessage());
        }
    }

    private void updateProduct() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String description = descriptionField.getText();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            ProductModel product = new ProductModel(id, name, price, description, quantity);
            dataAccess.updateProduct(product);
            JOptionPane.showMessageDialog(this, "Product updated successfully.");
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid values for price and quantity.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating product: " + ex.getMessage());
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        descriptionField.setText("");
        priceField.setText("");
        quantityField.setText("");
    }
}
