package csce310;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

public class OrderView extends JFrame {
    private DataAdapter dataAdapter;
    private String productId;

    public OrderView(DataAdapter dataAdapter, String productId) {
        this.dataAdapter = dataAdapter;
        this.productId = productId;

        setTitle("Order Management");
        setSize(400, 280);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        // Add window listener to handle window closing event
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cancelOrder();
            }
        });

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel titleLabel = new JLabel("Enter Details to Place an Order");
        titleLabel.setBounds(70, 10, 400, 25);
        panel.add(titleLabel);

        JLabel productIdLabel = new JLabel("Product ID:");
        productIdLabel.setBounds(30, 40, 100, 25);
        panel.add(productIdLabel);

        JTextField productIdField = new JTextField(productId);
        productIdField.setEditable(false);
        productIdField.setBounds(160, 40, 165, 25);
        panel.add(productIdField);

        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setBounds(30, 70, 100, 25);
        panel.add(userIdLabel);

        JTextField userIdField = new JTextField();
        userIdField.setBounds(160, 70, 165, 25);
        panel.add(userIdField);

        JLabel cardHolderLabel = new JLabel("Card Holder's Name:");
        cardHolderLabel.setBounds(30, 100, 150, 25);
        panel.add(cardHolderLabel);

        JTextField cardHolderField = new JTextField();
        cardHolderField.setBounds(160, 100, 165, 25);
        panel.add(cardHolderField);

        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberLabel.setBounds(30, 130, 120, 25);
        panel.add(cardNumberLabel);

        JTextField cardNumberField = new JTextField();
        cardNumberField.setBounds(160, 130, 165, 25);
        panel.add(cardNumberField);

        JLabel cvvLabel = new JLabel("CVV:");
        cvvLabel.setBounds(30, 160, 80, 25);
        panel.add(cvvLabel);

        JTextField cvvField = new JTextField();
        cvvField.setBounds(160, 160, 165, 25);
        panel.add(cvvField);

        JButton orderButton = new JButton("Order");
        orderButton.setBounds(10, 200, 340, 40);
        orderButton.setBackground(Color.GREEN);
        panel.add(orderButton);

        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = userIdField.getText();
                String cardHolderName = cardHolderField.getText();
                String cardNumber = cardNumberField.getText();
                String cvvText = cvvField.getText();

                // Check for empty fields
                if (userId.isEmpty() || cardHolderName.isEmpty() || cardNumber.isEmpty() || cvvText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check card number length
                if (cardNumber.length() != 16) {
                    JOptionPane.showMessageDialog(null, "Card number must be 16 digits long", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check CVV length
                if (cvvText.length() != 3) {
                    JOptionPane.showMessageDialog(null, "CVV must be 3 digits long", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Parse CVV
                int cvv;
                try {
                    cvv = Integer.parseInt(cvvText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "CVV must be a number", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if user ID already exists
                if (!dataAdapter.isUserExists(userId)) {
                    // If user ID doesn't exist, prompt to add user details
                    showAddUserDialog(userId, cardHolderName, cardNumber, cvv);
                } else {
                    // User ID already exists, proceed with the order
                    placeOrder(userId, cardHolderName, cardNumber, cvv);
                }
            }
        });
    }

    // Method to cancel the order
    private void cancelOrder() {
        JOptionPane.showMessageDialog(null, "Order Cancelled");
        dispose();
    }

    // Method to show add user dialog
    private void showAddUserDialog(String userId, String cardHolderName, String cardNumber, int cvv) {
        JPanel addUserPanel = new JPanel();
        addUserPanel.setLayout(new GridLayout(6, 2));
    
        // Add heading label
        JLabel headingLabel = new JLabel("User Doesn't Exist, Add User Details");
        headingLabel.setForeground(Color.BLACK); // Set font color
        headingLabel.setFont(headingLabel.getFont().deriveFont(Font.BOLD, 16)); // Set font size and style
        addUserPanel.add(headingLabel);
        addUserPanel.add(new JLabel());
    
        // Add user details fields
        addUserPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField(20);
        addUserPanel.add(nameField);
        addUserPanel.add(new JLabel("Address:"));
        JTextField addressField = new JTextField(20);
        addUserPanel.add(addressField);
        addUserPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField(20);
        addUserPanel.add(emailField);
        addUserPanel.add(new JLabel("Phone:"));
        JTextField phoneField = new JTextField(20);
        addUserPanel.add(phoneField);
    
        int result = showCustomDialog(addUserPanel, "Enter User Details", "Add User", "Cancel");
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String address = addressField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
    
            // Add user
            dataAdapter.addUser(userId, name, address, phone, email);
            placeOrder(userId, cardHolderName, cardNumber, cvv); // Proceed with the order after adding the user
        }
    }
    
    
    // Method to show custom JOptionPane with OK and Cancel buttons
    private int showCustomDialog(JPanel panel, String title, String okButtonText, String cancelButtonText) {
        Object[] options = {okButtonText, cancelButtonText};
        return JOptionPane.showOptionDialog(null, panel, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }

    // Method to place the order
    private void placeOrder(String userId, String cardHolderName, String cardNumber, int cvv) {
        // Get product price
        Map<String, String> productData = dataAdapter.getProductData("Product:" + productId);
        double price = Double.parseDouble(productData.get("Price"));

        // Add the order
        dataAdapter.addOrder(productId, userId, price, cardHolderName, cardNumber, cvv);

        // Update product quantity
        int quantity = Integer.parseInt(productData.get("Quantity")) - 1;
        dataAdapter.updateProduct(productId, productData.get("ProductName"), productData.get("Description"), price, quantity);

        dispose();

        JOptionPane.showMessageDialog(null, "Purchase Successful");
    }
}
