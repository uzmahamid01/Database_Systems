import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PaymentView extends JFrame {
    private JTextField paymentIDField, orderIDField, amountField, paymentDateField, cardNumberField, cardHolderNameField, CVVField;
    private JButton addButton, updateButton;
    private DataAccess dataAccess;

    public PaymentView(DataAccess dataAccess) {
        this.dataAccess = dataAccess;

        setTitle("Payment Management");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(8, 2));

        panel.add(new JLabel("PaymentID:"));
        paymentIDField = new JTextField();
        panel.add(paymentIDField);

        panel.add(new JLabel("OrderID:"));
        orderIDField = new JTextField();
        panel.add(orderIDField);

        panel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        panel.add(amountField);

        panel.add(new JLabel("Payment Date:"));
        paymentDateField = new JTextField();
        panel.add(paymentDateField);

        panel.add(new JLabel("Card Number:")); 
        cardNumberField = new JTextField(); 
        panel.add(cardNumberField);

        panel.add(new JLabel("Card Holder Name:")); 
        cardHolderNameField = new JTextField();
        panel.add(cardHolderNameField);

        panel.add(new JLabel("CVV:"));
        CVVField = new JTextField();
        panel.add(CVVField);

        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addPayment();
            }
        });
        panel.add(addButton);

        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updatePayment();
            }
        });
        panel.add(updateButton);

        add(panel);
        setVisible(true);
    }

    private void addPayment() {
        try {
            int paymentID = Integer.parseInt(paymentIDField.getText());
            int orderID = Integer.parseInt(orderIDField.getText());
            double amount = Double.parseDouble(amountField.getText());
            String cardNumber = cardNumberField.getText();
            String cardHolderName = cardHolderNameField.getText();
            int CVV = Integer.parseInt(CVVField.getText());
        
            java.sql.Date paymentDate = parseDate(paymentDateField.getText());
        
            PaymentModel payment = new PaymentModel(paymentID, orderID, amount, paymentDate, cardNumber, cardHolderName, CVV);
            dataAccess.addPayment(payment);
            JOptionPane.showMessageDialog(this, "Payment added successfully.");
            clearFields();
        } catch (NumberFormatException ex) {
            ex.printStackTrace(); 
            String errorMessage = "Invalid input. Please enter valid values for:\n";
            if (!paymentIDField.getText().matches("\\d+")) {
                errorMessage += "Payment ID\n";
            }
            if (!orderIDField.getText().matches("\\d+")) {
                errorMessage += "Order ID\n";
            }
            if (!amountField.getText().matches("\\d+(\\.\\d+)?")) {
                errorMessage += "Amount\n";
            }
            if (!paymentDateField.getText().matches("\\d{8}")) {
                errorMessage += "Payment Date\n";
            }
            // if (!cardNumberField.getText().matches("\\d{16}")) {
            //     errorMessage += "Card Number\n";
            // }
            
            if (cardHolderNameField.getText().isEmpty()) {
                errorMessage += "Card Holder Name\n";
            }
            if (!CVVField.getText().matches("\\d{3}")) {
                errorMessage += "CVV\n";
            }
            JOptionPane.showMessageDialog(this, errorMessage);
        } catch (Exception ex) {
            ex.printStackTrace(); // Add this line
            JOptionPane.showMessageDialog(this, "Error adding payment: " + ex.getMessage());
        }
    }

    private void updatePayment() {
        try {
            int paymentID = Integer.parseInt(paymentIDField.getText());
            int orderID = Integer.parseInt(orderIDField.getText());
            double amount = Double.parseDouble(amountField.getText());
            String cardNumber = cardNumberField.getText();
            String cardHolderName = cardHolderNameField.getText();
            int CVV = Integer.parseInt(CVVField.getText());
        
            java.sql.Date paymentDate = parseDate(paymentDateField.getText());
        
            PaymentModel payment = new PaymentModel(paymentID, orderID, amount, paymentDate, cardNumber, cardHolderName, CVV);
            dataAccess.updatePayment(payment);
            JOptionPane.showMessageDialog(this, "Payment added successfully.");
            clearFields();
        } catch (NumberFormatException ex) {
            ex.printStackTrace(); 
            String errorMessage = "Invalid input. Please enter valid values for:\n";
            if (!paymentIDField.getText().matches("\\d+")) {
                errorMessage += "Payment ID\n";
            }
            if (!orderIDField.getText().matches("\\d+")) {
                errorMessage += "Order ID\n";
            }
            if (!amountField.getText().matches("\\d+(\\.\\d+)?")) {
                errorMessage += "Amount\n";
            }
            
            if (!paymentDateField.getText().matches("\\d{8}")) {
                errorMessage += "Payment Date\n";
            }
            // if (!cardNumberField.getText().matches("\\d{16}")) {
            //     errorMessage += "Card Number\n";
            // }
            
            if (cardHolderNameField.getText().isEmpty()) {
                errorMessage += "Card Holder Name\n";
            }
            if (!CVVField.getText().matches("\\d{3}")) {
                errorMessage += "CVV\n";
            }
            JOptionPane.showMessageDialog(this, errorMessage);
        } catch (Exception ex) {
            ex.printStackTrace(); // Add this line
            JOptionPane.showMessageDialog(this, "Error adding payment: " + ex.getMessage());
        }
    }

    private java.sql.Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        java.util.Date parsedDate = dateFormat.parse(dateString);
        return new java.sql.Date(parsedDate.getTime());
    }    

    private void clearFields() {
        paymentIDField.setText("");
        orderIDField.setText("");
        amountField.setText("");
        paymentDateField.setText("");
        cardNumberField.setText(""); 
        cardHolderNameField.setText(""); 
        CVVField.setText(""); 
    }
}
