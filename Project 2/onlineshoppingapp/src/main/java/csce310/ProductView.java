package csce310;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class ProductView extends JFrame {
    private DataAdapter dataAdapter;
    private JTable productTable; 

    public ProductView(DataAdapter dataAdapter) {
        this.dataAdapter = dataAdapter;

        setTitle("Product Management");
        setSize(730, 750); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Product ID");
        model.addColumn("Product Name");
        model.addColumn("Description");
        model.addColumn("Price");
        model.addColumn("Quantity");

        productTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBounds(10, 200, 700, 500);
        panel.add(scrollPane);

        JLabel idLabel = new JLabel("Product ID:");
        idLabel.setBounds(10, 10, 80, 25);
        panel.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(100, 10, 165, 25);
        panel.add(idField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 40, 80, 25);
        panel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(100, 40, 165, 25);
        panel.add(nameField);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(10, 70, 80, 25);
        panel.add(descriptionLabel);

        JTextField descriptionField = new JTextField();
        descriptionField.setBounds(100, 70, 165, 25);
        panel.add(descriptionField);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(10, 100, 100, 25);
        panel.add(priceLabel);

        JTextField priceField = new JTextField();
        priceField.setBounds(100, 100, 165, 25);
        panel.add(priceField);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(10, 130, 80, 25);
        panel.add(quantityLabel);

        JTextField quantityField = new JTextField();
        quantityField.setBounds(100, 130, 165, 25);
        panel.add(quantityField);

        JButton addButton = new JButton("Add Product");
        addButton.setBounds(10, 170, 120, 25);
        panel.add(addButton);

        JButton viewButton = new JButton("View Products");
        viewButton.setBounds(140, 170, 120, 25);
        panel.add(viewButton);

        JButton updateButton = new JButton("Update Product");
        updateButton.setBounds(270, 170, 120, 25);
        panel.add(updateButton);

        JButton deleteButton = new JButton("Delete Product");
        deleteButton.setBounds(400, 170, 120, 25);
        panel.add(deleteButton);

        // addButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         String productId = idField.getText();
        //         String name = nameField.getText();
        //         String description = descriptionField.getText();
        //         double price = Double.parseDouble(priceField.getText());
        //         int quantity = Integer.parseInt(quantityField.getText());
        //         dataAdapter.addProduct(productId, name, description, price, quantity);

        //         JOptionPane.showMessageDialog(null, "Product Added Successfully");
                

        //         idField.setText("");
        //         nameField.setText("");
        //         descriptionField.setText("");
        //         priceField.setText("");
        //         quantityField.setText("");
        //         displayProducts();
        //     }
        // });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String productId = idField.getText();
                    String name = nameField.getText();
                    String description = descriptionField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int quantity = Integer.parseInt(quantityField.getText());
                    
                    // Attempt to add the product
                    dataAdapter.addProduct(productId, name, description, price, quantity);
        
                    // Product added successfully, show a success message
                    JOptionPane.showMessageDialog(null, "Product Added Successfully");
        
                    // Clear text fields and display updated products
                    idField.setText("");
                    nameField.setText("");
                    descriptionField.setText("");
                    priceField.setText("");
                    quantityField.setText("");
                    displayProducts();
                } catch (IllegalArgumentException ex) {
                    // Product ID already exists, show an error message
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } 
                // catch (NumberFormatException ex) {
                //     // Invalid price or quantity, show an error message
                //     JOptionPane.showMessageDialog(null, "Please enter valid price and quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                // }
            }
        });
        

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Products Loading");
                // Display products in the JTable
                idField.setText("");
                nameField.setText("");
                descriptionField.setText("");
                priceField.setText("");
                quantityField.setText("");
                displayProducts();
            }
        });
        
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update product using data from text fields
                try {
                    String productId = idField.getText(); 
                    String name = nameField.getText();
                    String description = descriptionField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int quantity = Integer.parseInt(quantityField.getText());
                    dataAdapter.updateProduct(productId, name, description, price, quantity);
                    JOptionPane.showMessageDialog(null, "Product Updated Successfully");

                    idField.setText("");
                    nameField.setText("");
                    descriptionField.setText("");
                    priceField.setText("");
                    quantityField.setText("");
                    displayProducts();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid price and quantity.");
                }
            }
        });
        
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Delete selected product from the JTable
                int row = productTable.getSelectedRow();
                if (row != -1) {
                    String productId = productTable.getValueAt(row, 0).toString();
                    dataAdapter.deleteProduct(productId);
                    JOptionPane.showMessageDialog(null, "Product Deleted Successfully");

                    // Clear text fields
                    idField.setText("");
                    nameField.setText("");
                    descriptionField.setText("");
                    priceField.setText("");
                    quantityField.setText("");
                    displayProducts();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a product to delete.");
                }
            }
        });

        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // This condition ensures that this listener fires only once when the selection is finalized
                    int selectedRow = productTable.getSelectedRow();
                    if (selectedRow != -1) { // Check if a row is selected
                        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
                        String productId = model.getValueAt(selectedRow, 0).toString();
                        String productName = model.getValueAt(selectedRow, 1).toString();
                        String description = model.getValueAt(selectedRow, 2).toString();
                        String price = model.getValueAt(selectedRow, 3).toString();
                        String quantity = model.getValueAt(selectedRow, 4).toString();

                        // Populate text fields with the selected product information
                        idField.setText(productId);
                        nameField.setText(productName);
                        descriptionField.setText(description);
                        priceField.setText(price);
                        quantityField.setText(quantity);
                    }
                }
            }
        });

    }

    public void displayProducts() {
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        model.setRowCount(0);

        // Read all products using data adapter
        Set<String> productKeys = dataAdapter.getAllProductKeys();

        // Iterate over product keys and fetch product details
        for (String productKey : productKeys) {
            Map<String, String> productData = dataAdapter.getProductData(productKey);

            double price = Double.parseDouble(productData.get("Price"));
            String formattedPrice = String.format("%.2f", price);
            // Add product data to the table model
            Vector<Object> row = new Vector<>();
            row.add(productData.get("ProductID"));
            row.add(productData.get("ProductName"));
            row.add(productData.get("Description"));
            //row.add(productData.get("Price"));
            row.add(formattedPrice);
            row.add(productData.get("Quantity"));
            model.addRow(row);
        }
    }    
}
