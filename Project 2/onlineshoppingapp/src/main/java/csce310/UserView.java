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

public class UserView extends JFrame {
    private DataAdapter dataAdapter;
    private JTable productTable;
    private DefaultTableModel tableModel;

    public UserView(DataAdapter dataAdapter) {
        this.dataAdapter = dataAdapter;

        setTitle("User Management");
        setSize(730, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 10, 700, 500);
        panel.add(scrollPane);

        productTable = new JTable();
        scrollPane.setViewportView(productTable);

        JButton viewProductsButton = new JButton("Refresh Products");
        viewProductsButton.setBounds(10, 520, 200, 25);
        panel.add(viewProductsButton);

        JLabel productIdLabel = new JLabel("Product ID:");
        productIdLabel.setBounds(10, 550, 80, 25);
        panel.add(productIdLabel);

        JTextField productIdField = new JTextField();
        productIdField.setBounds(100, 550, 165, 25);
        panel.add(productIdField);

        JButton buyButton = new JButton("Buy Now");
        buyButton.setBounds(290, 550, 100, 25);
        panel.add(buyButton);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Product ID");
        tableModel.addColumn("Product Name");
        tableModel.addColumn("Description");
        tableModel.addColumn("Price");
        tableModel.addColumn("Quantity");
        productTable.setModel(tableModel);

        displayProducts();

        viewProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Loading Products");
                displayProducts();
                productIdField.setText("");
            }
        });

        //update productIdField
        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    String productId = (String) productTable.getValueAt(selectedRow, 0);
                    productIdField.setText(productId); // Update productIdField with selected product ID
                }
            }
        });

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row index
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the product ID from the selected row
                    String productId = (String) productTable.getValueAt(selectedRow, 0);
                    
                    // Open OrderView and pass the productId
                    OrderView orderView = new OrderView(dataAdapter, productId);
                    orderView.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a product to buy.");
                }
            }
        });
        
    }

    private void displayProducts() {
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
