import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class StoreMain extends JFrame {
    private static final String URL = "jdbc:mysql://localhost:3306/storemanagement";
    private static final String USER = "root";
    private static final String PASSWORD = "masternode";
    private DataAccess dataAccess;

    public StoreMain(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
        setTitle("Store Management Dashboard");
        setSize(800, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel headerLabel = new JLabel("Store Management", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        Border emptyBorder = BorderFactory.createEmptyBorder(20, 0, 0, 0);
        headerLabel.setBorder(emptyBorder);
        add(headerLabel, BorderLayout.NORTH);

        JButton customerButton = createStyledButton("Customer Class");
        JButton supplierButton = createStyledButton("Suppliers Class");
        JButton productButton = createStyledButton("Product Class");
        JButton orderButton = createStyledButton("Orders Class");
        JButton paymentButton = createStyledButton("Payments Class");
        JButton totalSalePerMonthButton = createStyledButton("Total Sale Per Month");
        JButton totalSalePerProductButton = createStyledButton("Total Sale Per Product");
        JButton totalSalePerCustomerButton = createStyledButton("Total Sale Per Customer");

        //action listeners to buttons
        customerButton.addActionListener(e -> openCustomerView());
        supplierButton.addActionListener(e -> openSupplierView());
        productButton.addActionListener(e -> openProductView());
        orderButton.addActionListener(e -> openOrderView());
        paymentButton.addActionListener(e -> openPaymentView());
        totalSalePerMonthButton.addActionListener(e -> generateTotalSalePerMonthReport());
        totalSalePerProductButton.addActionListener(e -> generateTotalSalePerProductReport());
        totalSalePerCustomerButton.addActionListener(e -> generateTotalSalePerCustomerReport());


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 50, 30)); 

        panel.add(createSectionPanel("Manage Entities", customerButton, supplierButton, productButton));
        panel.add(createSectionPanel("Manage Orders & Payments", orderButton, paymentButton));
        panel.add(createSectionPanel("Generate Reports", totalSalePerMonthButton, totalSalePerProductButton, totalSalePerCustomerButton));

        add(panel);
        setVisible(true);
    }

    // Method to create a styled button with hover effect
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16)); 
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(150, 121, 105)); 
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(196, 164, 132));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(150, 121, 105)); 
            }
        });

        return button;
    }

    // Method to create a panel for a section with a title
    private JPanel createSectionPanel(String title, JButton... buttons) {
        JPanel sectionPanel = new JPanel(new BorderLayout());
        sectionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        sectionPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(buttons.length, 1, 100, 2)); 
        for (JButton button : buttons) {
            buttonPanel.add(button);
        }
        sectionPanel.add(buttonPanel, BorderLayout.CENTER);

        return sectionPanel;
    }

    // Method to open CustomerView
    private void openCustomerView() {
        CustomerView customerView = new CustomerView(dataAccess);
        customerView.setVisible(true);
    }

    // Method to open SupplierView
    private void openSupplierView() {
        SupplierView supplierView = new SupplierView(dataAccess);
        supplierView.setVisible(true);
    }

    // Method to open ProductView
    private void openProductView() {
        ProductView productView = new ProductView(dataAccess);
        productView.setVisible(true);
    }

    // Method to open OrderView
    private void openOrderView() {
        OrderView orderView = new OrderView(dataAccess);
        orderView.setVisible(true);
    }

    // Method to open PaymentView
    private void openPaymentView() {
        PaymentView paymentView = new PaymentView(dataAccess);
        paymentView.setVisible(true);
    }

    // Method to generate total sale per month report
    private void generateTotalSalePerMonthReport() {
        JDialog dialog = new JDialog(this, "Total Sales Per Month", true);
        dialog.setLayout(new BorderLayout());
    
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        JTextField monthField = new JTextField();
        JTextField fromDateField = new JTextField();
        JTextField toDateField = new JTextField();
        JButton viewButton = new JButton("View");
    
        inputPanel.add(new JLabel("Month:"));
        inputPanel.add(monthField);
        inputPanel.add(new JLabel("From:"));
        inputPanel.add(fromDateField);
        inputPanel.add(new JLabel("To:"));
        inputPanel.add(toDateField);
        inputPanel.add(viewButton);
    
        dialog.add(inputPanel, BorderLayout.NORTH);
    
        JTextArea reportTextArea = new JTextArea(20, 40);
        JScrollPane scrollPane = new JScrollPane(reportTextArea);
        dialog.add(scrollPane, BorderLayout.CENTER);
    
        JPanel buttonPanel = new JPanel();
        JButton sortButton = new JButton("Sort");
        JButton saveButton = new JButton("Save as Text");
    
        buttonPanel.add(sortButton);
        buttonPanel.add(saveButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
    
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String monthInput = monthField.getText();
                    SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
                    monthFormat.setLenient(false); 
                    java.util.Date utilDate = monthFormat.parse(monthInput);
                    java.sql.Date monthDate = new java.sql.Date(utilDate.getTime());
    
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(monthDate);
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
                    Date fromDate = new Date(calendar.getTimeInMillis()); 
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                    Date toDate = new Date(calendar.getTimeInMillis()); 
    
                    generateTotalSalePerMonthReport(monthInput, fromDate, toDate, reportTextArea);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(dialog, "Invalid month format. Please use yyyy-MM format.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    
        
        // sortButton.addActionListener(e -> {
            
        // });
    
        saveButton.addActionListener(e -> {
            System.out.println("Save Button Clicked!");
            String reportContent = reportTextArea.getText();
            String filename = "TotalSalePerMonthReport.txt"; 
            generateReportFile(filename, reportContent);
            //JOptionPane.showMessageDialog(null, "Report saved as TotalSalePerMonthReport.txt");
            System.out.println("Saved report into text file successfully");
        });
    
        dialog.pack();
        dialog.setVisible(true);
    }
    
    // Method to generate total sale per month report
    private void generateTotalSalePerMonthReport(String month, Date fromDate, Date toDate, JTextArea reportTextArea) {
        // Get the connection from DataAccess
        Connection conn = dataAccess.getConnection();
        SalesReports salesReports = new SalesReports(conn);
        System.out.println("Calling Sales Report class1");
        salesReports.generateTotalSalePerMonthReport(month, fromDate, toDate, true, reportTextArea);
    }

    // Method to generate total sale per product report
    private void generateTotalSalePerProductReport() {
        JDialog dialog = new JDialog(this, "Total Sales Per Product", true);
        dialog.setLayout(new BorderLayout());
    
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();
        JButton viewButton = new JButton("View");
    
        inputPanel.add(new JLabel("Start Date:"));
        inputPanel.add(startDateField);
        inputPanel.add(new JLabel("End Date:"));
        inputPanel.add(endDateField);
        inputPanel.add(new JLabel()); 
        inputPanel.add(viewButton);
    
        dialog.add(inputPanel, BorderLayout.NORTH);
    
        JTextArea reportTextArea = new JTextArea(20, 40);
        JScrollPane scrollPane = new JScrollPane(reportTextArea);
        dialog.add(scrollPane, BorderLayout.CENTER);
    
        JPanel buttonPanel = new JPanel();
        JButton sortButton = new JButton("Sort");
        JButton saveButton = new JButton("Save as Text");
    
        buttonPanel.add(sortButton);
        buttonPanel.add(saveButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
    
        viewButton.addActionListener(e -> {
            try {
                String startDateText = startDateField.getText();
                String endDateText = endDateField.getText();
    
               
                if (startDateText.isEmpty() || endDateText.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please provide both start and end dates.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = new Date(dateFormat.parse(startDateText).getTime());
                Date endDate = new Date(dateFormat.parse(endDateText).getTime());
    
                generateTotalSalePerProductReport(startDate, endDate, reportTextArea);
            } catch (ParseException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Invalid date format. Please use yyyy-MM-dd format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        // Add action listener to the Sort button
        sortButton.addActionListener(e -> {
            String reportContent = reportTextArea.getText();
            String[] lines = reportContent.split("\n");

            String[] header = Arrays.copyOfRange(lines, 0, 1);
            lines = Arrays.copyOfRange(lines, 1, lines.length);
    
            //sort in desc
            Arrays.sort(lines, (a, b) -> {
                String[] colsA = a.split("\t");
                String[] colsB = b.split("\t");
                double saleA = Double.parseDouble(colsA[1]);
                double saleB = Double.parseDouble(colsB[1]);
                return Double.compare(saleB, saleA);
            });
            
            //cut-off = 10
            StringBuilder sortedReport = new StringBuilder();
            sortedReport.append(String.join("\n", header)).append("\n"); 
            int cutoff = Math.min(10, lines.length); 
            for (int i = 0; i < cutoff; i++) {
                sortedReport.append(lines[i]).append("\n");
            }
            reportTextArea.setText(sortedReport.toString());
        });
    
       
        saveButton.addActionListener(e -> {
            
            String reportContent = reportTextArea.getText();
            String filename = "TotalSalePerProductReport.txt"; 
            generateReportFile(filename, reportContent);
        });
    
        dialog.pack();
        dialog.setVisible(true);
    }
    


    // Method to generate total sale per product report
    private void generateTotalSalePerProductReport(Date startDate, Date endDate, JTextArea reportTextArea) {
        Connection conn = dataAccess.getConnection();
        SalesReports salesReports = new SalesReports(conn);
        System.out.println("Calling Sales Report class2");
        salesReports.generateTotalSalePerProductReport(startDate, endDate, true, reportTextArea);
    }

    // Method to generate total sale per customer report
    private void generateTotalSalePerCustomerReport() {
        JDialog dialog = new JDialog(this, "Total Sales Per Customer", true);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();
        JButton viewButton = new JButton("View");
        JButton sortButton = new JButton("Sort"); 

        inputPanel.add(new JLabel("Start Date:"));
        inputPanel.add(startDateField);
        inputPanel.add(new JLabel("End Date:"));
        inputPanel.add(endDateField);
        inputPanel.add(new JLabel()); 
        inputPanel.add(viewButton);

        dialog.add(inputPanel, BorderLayout.NORTH);

        JTextArea reportTextArea = new JTextArea(20, 40);
        JScrollPane scrollPane = new JScrollPane(reportTextArea);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save as Text");

        buttonPanel.add(sortButton); 
        buttonPanel.add(saveButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        
        viewButton.addActionListener(e -> {
            try {
                String startDateText = startDateField.getText();
                String endDateText = endDateField.getText();

                if (startDateText.isEmpty() || endDateText.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please provide both start and end dates.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = new Date(dateFormat.parse(startDateText).getTime());
                Date endDate = new Date(dateFormat.parse(endDateText).getTime());

                generateTotalSalePerCustomerReport(startDate, endDate, reportTextArea);
            } catch (ParseException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Invalid date format. Please use yyyy-MM-dd format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        sortButton.addActionListener(e -> {
            String reportContent = reportTextArea.getText();
            String[] lines = reportContent.split("\n");
            
            String[] header = Arrays.copyOfRange(lines, 0, 1);
            lines = Arrays.copyOfRange(lines, 1, lines.length);
            
            Arrays.sort(lines, (a, b) -> {
                String[] colsA = a.split("\t");
                String[] colsB = b.split("\t");
                double saleA = Double.parseDouble(colsA[1]);
                double saleB = Double.parseDouble(colsB[1]);
                return Double.compare(saleB, saleA);
            });
        
            StringBuilder sortedReport = new StringBuilder();
            sortedReport.append(String.join("\n", header)).append("\n"); 
            int cutoff = Math.min(10, lines.length); 
            for (int i = 0; i < cutoff; i++) {
                sortedReport.append(lines[i]).append("\n");
            }
            reportTextArea.setText(sortedReport.toString());
        });
        
    
        saveButton.addActionListener(e -> {
            String reportContent = reportTextArea.getText();
            String filename = "TotalSalePerCustomerReport.txt"; 

            generateReportFile(filename, reportContent);
        });

        dialog.pack();
        dialog.setVisible(true);
    }

    // Method to generate total sale per customer report
    private void generateTotalSalePerCustomerReport(Date startDate, Date endDate, JTextArea reportTextArea) {
        Connection conn = dataAccess.getConnection();
        SalesReports salesReports = new SalesReports(conn);
        System.out.println("Calling Sales Report class3");
        salesReports.generateTotalSalePerCustomerReport(startDate, endDate, true, reportTextArea);
    }

    public void generateReportFile(String filename, String reportContent) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(reportContent);
            System.out.println("Report saved successfully to " + filename); 
            JOptionPane.showMessageDialog(null, "Report saved as " + filename);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while saving the report.");
        }
    }


    public static void main(String[] args) {
        // Establish a database connection
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            // Pass the connection to DataAccess
            DataAccess dataAccess = new DataAccess(connection);

            // Create an instance of StoreMain
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new StoreMain(dataAccess);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
