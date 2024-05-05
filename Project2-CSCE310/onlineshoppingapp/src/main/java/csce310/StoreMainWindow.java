package csce310;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoreMainWindow extends JFrame {
    private DataAdapter dataAdapter;
    private UserView userView;
    private ProductView productView;

    public StoreMainWindow(DataAdapter dataAdapter) {
        this.dataAdapter = dataAdapter;

        setTitle("Online Shopping System");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout()); 
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); 
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        // Add a heading label
        JLabel headingLabel = new JLabel("Welcome to DBMS Online Shopping");
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(headingLabel, BorderLayout.NORTH); 

        JPanel buttonPanel = new JPanel(); 
        buttonPanel.setLayout(new GridLayout(2, 1, 20, 20)); 
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 50, 70));

        panel.add(buttonPanel, BorderLayout.CENTER); 


        // Buy Products button
        JButton userManagementButton = new JButton("Buy Products");
        Dimension buttonSize1 = new Dimension(100, 30);
        userManagementButton.setPreferredSize(buttonSize1);
        buttonPanel.add(userManagementButton);

        // List Products for Sale button
        JButton productManagementButton = new JButton("List Products for Sale");
        Dimension buttonSize2 = new Dimension(100, 30);
        productManagementButton.setPreferredSize(buttonSize2);
        buttonPanel.add(productManagementButton);

        userManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUserManagement();
            }
        });

        productManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openProductManagement();
            }
        });
    }

    private void openUserManagement() {
        userView = new UserView(dataAdapter);
    }

    private void openProductManagement() {
        productView = new ProductView(dataAdapter);
    }

    public static void main(String[] args) {
        DataAdapter dataAdapter = new DataAdapter();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StoreMainWindow(dataAdapter);
            }
        });
    }
}
