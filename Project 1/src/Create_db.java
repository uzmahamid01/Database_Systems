import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


public class Create_db {
    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/storemanagement";
    private static final String USER = "root";
    private static final String PASSWORD = "masternode";

    private static Connection connection;
    private static Statement statement;

    public static void main(String[] args) {
        try {
            // Load and register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement();
    
            //drop the tables before running this else it will through tables already existing error
            deleteTable("Payments"); 
            deleteTable("Orders");   
            deleteTable("Customer");
            deleteTable("Suppliers");
            deleteTable("Products");

            //create tables
            createCustomerTable();
            createSuppliersTable();
            createProductsTable();
            createOrdersTable();
            createPaymentsTable();

            //populate tables
            populateCustomerTable(300);
            populateSuppliersTable(20);
            populateElectronicProducts(100);
            populateOrdersTable(500);
            populatePaymentsTable(500);
    
            System.out.println("Tables created and populated successfully.");
    
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void deleteTable(String tableName) throws SQLException {
        if ("Orders".equals(tableName)) {
            String deletePaymentsTableSQL = "DROP TABLE IF EXISTS Payments";
            statement.execute(deletePaymentsTableSQL);
            System.out.println("Table Payments deleted successfully.");
        }
        
        String deleteTableSQL = "DROP TABLE IF EXISTS " + tableName;
        statement.execute(deleteTableSQL);
        System.out.println("Table " + tableName + " deleted successfully.");
    }
    
    
    private static void createCustomerTable() throws SQLException {
        String createTableSQL = "CREATE TABLE Customer ("
                + "CustomerID INT PRIMARY KEY,"
                + "CustomerName VARCHAR(255),"
                + "Address VARCHAR(255),"
                + "Phone VARCHAR(15),"
                + "Email VARCHAR(255)"
                + ")";
        statement.execute(createTableSQL);
    }

    private static void createSuppliersTable() throws SQLException {
        String createTableSQL = "CREATE TABLE Suppliers ("
                + "SupplierID INT PRIMARY KEY,"
                + "SupplierName VARCHAR(255),"
                + "Address VARCHAR(255),"
                + "Phone VARCHAR(15),"
                + "Email VARCHAR(255)"
                + ")";
        statement.execute(createTableSQL);
    }

    private static void createProductsTable() throws SQLException {
        String createTableSQL = "CREATE TABLE Products ("
                + "ProductID INT PRIMARY KEY,"
                + "ProductName VARCHAR(50),"
                + "Description TEXT,"
                + "Price DECIMAL(10,2),"
                + "Quantity INT"
                + ")";
        statement.execute(createTableSQL);
    }

    private static void populateCustomerTable(int count) throws SQLException {
        for (int i = 1; i <= count; i++) {
            String name = generateRandomName();
            String address = generateRandomAddress();
            String phone = generateRandomPhoneNumber();
            String email = generateRandomEmail(name);

            String insertSQL = String.format("INSERT INTO Customer (CustomerID, CustomerName, Address, Phone, Email) " +
                    "VALUES (%d, '%s', '%s', '%s', '%s')", i, name, address, phone, email);

            statement.executeUpdate(insertSQL);
        }
    }

    private static void populateSuppliersTable(int count) throws SQLException {
        for (int i = 1; i <= count; i++) {
            String name = generateRandomName();
            String address = generateRandomAddress();
            String phone = generateRandomPhoneNumber();
            String email = generateRandomEmail(name);

            String insertSQL = String.format("INSERT INTO Suppliers (SupplierID, SupplierName, Address, Phone, Email) " +
                    "VALUES (%d, '%s', '%s', '%s', '%s')", i, name, address, phone, email);

            statement.executeUpdate(insertSQL);
        }
    }

    private static void populateElectronicProducts(int count) throws SQLException {
        //random list of products 
        String[] productNames = {
            "Smartphone", "Laptop", "Tablet", "Smartwatch", "Wireless Headphones",
            "Gaming Console", "Camera", "Drone", "Fitness Tracker", "Virtual Reality Headset",
            "Smart Speaker", "E-book Reader", "Action Camera", "Robot Vacuum", "Wireless Earbuds",
            "Graphics Tablet", "Portable Projector", "Home Security Camera", "GPS Tracker", "Wireless Charger",
            "Bluetooth Speaker", "Noise Cancelling Headphones", "Smart Doorbell", "Electric Scooter", "Fitness Smart Scale",
            "Wireless Security System", "Smart Light Bulbs", "Smart Thermostat", "Wireless Keyboard", "Wireless Mouse",
            "Solar Power Bank", "Car Dash Cam", "Portable Bluetooth Printer", "Robot Lawn Mower", "Bluetooth FM Transmitter",
            "Digital Voice Recorder", "Bluetooth Sport Earphones", "Smart Plug", "Mini Drone", "VR Gaming Headset",
            "Wireless Webcam", "Foldable Bluetooth Keyboard", "Smart Water Bottle", "Smart Car Charger", "Portable Power Generator",
            "Bluetooth Trackers", "Wireless Bike Computer", "Portable Photo Printer", "Solar Outdoor Lights", "Smart LED Strip Lights"
        };
        String[] descriptions = {
            "Advanced technology packed into a compact design.",
            "Powerful computing performance for work and play.",
            "Portable device for on-the-go entertainment.",
            "Stay connected and track your activities with style.",
            "Immersive sound quality without the hassle of wires.",
            "Next-level gaming experience for enthusiasts.",
            "Capture your memories in stunning detail.",
            "Explore the skies and capture breathtaking aerial footage.",
            "Monitor your health and fitness goals effortlessly.",
            "Step into a new world of immersive experiences.",
            "Voice-activated assistant for smart home control.",
            "Read your favorite books on a high-resolution display.",
            "Record your adventures in crisp and clear video.",
            "Automatically clean your floors with intelligent navigation.",
            "Enjoy music and calls without tangled cables.",
            "Create digital art with precision and ease.",
            "Project your presentations anywhere you go.",
            "Keep an eye on your home from anywhere.",
            "Track your outdoor activities with precision.",
            "Charge your devices without plugging in.",
            "Portable speaker for parties and outdoor adventures.",
            "Block out external noise for immersive listening.",
            "See who's at your door from your smartphone.",
            "Effortless transportation for urban commuters.",
            "Track your weight and body composition accurately.",
            "Protect your home with advanced surveillance.",
            "Control your lights with voice commands.",
            "Manage your home temperature from anywhere.",
            "Type comfortably without wires cluttering your desk.",
            "Navigate your computer without cords limiting movement.",
            "Charge your devices using solar energy.",
            "Record your drives for safety and insurance purposes.",
            "Print photos directly from your smartphone.",
            "Effortlessly maintain your lawn with robotic assistance.",
            "Stream music from your phone to your car stereo.",
            "Capture audio notes and lectures with ease.",
            "Listen to music while working out without wires getting in the way.",
            "Automate your home appliances for convenience.",
            "Capture aerial footage with a compact drone.",
            "Immerse yourself in virtual reality gaming experiences.",
            "Stay connected with friends and family via video calls.",
            "Type on the go with a foldable, portable keyboard.",
            "Stay hydrated and track your water intake with smart features.",
            "Charge your devices faster in your car.",
            "Have backup power during emergencies with a portable generator.",
            "Keep track of your belongings with Bluetooth trackers.",
            "Monitor your biking activities and performance metrics.",
            "Print photos instantly with a pocket-sized printer.",
            "Illuminate your outdoor spaces with solar-powered lights.",
            "Add ambiance to your room with customizable LED lighting."
        };
    
        Random random = new Random();
        String insertSQL = "INSERT INTO Products (ProductID, ProductName, Description, Price, Quantity) " +
                        "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            for (int i = 1; i <= count; i++) {
                int nameIndex = random.nextInt(productNames.length);
                int descriptionIndex = random.nextInt(descriptions.length);

                String productName = productNames[nameIndex];
                String description = descriptions[descriptionIndex];
                double price = 100 + random.nextDouble() * 900; 
                int quantity = 10 + random.nextInt(91);

                preparedStatement.setInt(1, i);
                preparedStatement.setString(2, productName);
                preparedStatement.setString(3, description);
                preparedStatement.setDouble(4, price);
                preparedStatement.setInt(5, quantity);

                preparedStatement.executeUpdate();
            }
            System.out.println("Successfully populated products.");
        }
    }
    


    private static String generateRandomName() {
        String[] names = {"John", "Emily", "Michael", "Sophia", "Jacob", "Emma", "Olivia", "Matthew", "Daniel", "Ethan"};
        Random random = new Random();
        return names[random.nextInt(names.length)] + " " + names[random.nextInt(names.length)];
    }

    private static String generateRandomAddress() {
        String[] addresses = {"123 Main St", "456 Oak Ave", "789 Elm St", "321 Pine Ave", "567 Maple St"};
        Random random = new Random();
        return addresses[random.nextInt(addresses.length)];
    }

    private static String generateRandomPhoneNumber() {
        Random random = new Random();
        StringBuilder phoneNumber = new StringBuilder("+1 ");
        for (int i = 0; i < 10; i++) {
            phoneNumber.append(random.nextInt(10));
        }
        return phoneNumber.toString();
    }

    private static String generateRandomEmail(String name) {
        String[] domains = {"gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "aol.com"};
        Random random = new Random();
        return name.replaceAll("\\s+", "").toLowerCase() + "@" + domains[random.nextInt(domains.length)];
    }

    private static void createOrdersTable() throws SQLException {
        String createTableSQL = "CREATE TABLE Orders ("
                + "OrderID INT PRIMARY KEY,"
                + "CustomerID INT,"
                + "OrderDate DATE,"
                + "TotalAmount DOUBLE,"
                + "FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID)"
                + ")";
        statement.execute(createTableSQL);
    }
    private static void populateOrdersTable(int orderCount) throws SQLException {
        Random random = new Random();
        
        //generating orders for 6 months (September 2023 to March 2024)
        Calendar startDate = Calendar.getInstance();
        startDate.set(2023, Calendar.SEPTEMBER, 1);
        long startMillis = startDate.getTimeInMillis();
        
        Calendar endDate = Calendar.getInstance();
        endDate.set(2024, Calendar.MARCH, 31);
        long endMillis = endDate.getTimeInMillis();
        
        long interval = endMillis - startMillis;

        for (int i = 1; i <= orderCount; i++) {
            long randomMillisOffset = (long) (random.nextDouble() * interval);
            long randomMillis = startMillis + randomMillisOffset;
            Date orderDate = new Date(randomMillis);

            int customerId = random.nextInt(300) + 1;
            double totalAmount = 50 + random.nextDouble() * 950; 

            String insertSQL = String.format("INSERT INTO Orders (OrderID, CustomerID, OrderDate, TotalAmount) " +
                    "VALUES (%d, %d, '%s', %.2f)", i, customerId, orderDate, totalAmount);

            statement.executeUpdate(insertSQL);
        }
    }

    private static void createPaymentsTable() throws SQLException {
        String createTableSQL = "CREATE TABLE Payments ("
                + "PaymentID INT PRIMARY KEY,"
                + "OrderID INT,"
                + "Amount DOUBLE,"
                + "PaymentDate DATE,"
                + "CardNumber VARCHAR(255),"
                + "CardHolderName VARCHAR(255),"
                + "CVV INT,"
                + "FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)"
                + ")";
        statement.execute(createTableSQL);
    }
    
    private static void populatePaymentsTable(int orderCount) throws SQLException {
        Random random = new Random();
    
        //query existing Orders table
        List<Integer> existingOrderIDs = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("SELECT OrderID FROM Orders");
        while (resultSet.next()) {
            existingOrderIDs.add(resultSet.getInt("OrderID"));
        }

        if (existingOrderIDs.isEmpty()) {
            System.out.println("No existing orders found. Skipping payment population.");
            return; 
        }
    
        Calendar startDate = Calendar.getInstance();
        startDate.set(2023, Calendar.SEPTEMBER, 1);
        long startMillis = startDate.getTimeInMillis();
    
        Calendar endDate = Calendar.getInstance();
        endDate.set(2024, Calendar.MARCH, 31);
        long endMillis = endDate.getTimeInMillis();
    
        long interval = endMillis - startMillis;
    
        for (int i = 101; i <= orderCount; i++) {
            long randomMillisOffset = (long) (random.nextDouble() * interval);
            long randomMillis = startMillis + randomMillisOffset;
    
            Date paymentDate = new Date(randomMillis);
            double amount = 50 + random.nextDouble() * 950;
    
            // Generate random index only if the existingOrderIDs list is not empty
            int randomOrderIndex = random.nextInt(existingOrderIDs.size());
            int orderId = existingOrderIDs.get(randomOrderIndex);
    
            String cardNumber = generateRandomCardNumber();
            String cardHolderName = generateRandomCardHolderName();
            int cvv = random.nextInt(900) + 100;
    
            String insertSQL = String.format("INSERT INTO Payments (PaymentID, OrderID, Amount, PaymentDate, CardNumber, CardHolderName, CVV) " +
                    "VALUES (%d, %d, %.2f, '%s', '%s', '%s', %d)", i, orderId, amount, paymentDate, cardNumber, cardHolderName, cvv);
    
            statement.executeUpdate(insertSQL);
        }
    }
    
    //helper method to generate a random card number
    private static String generateRandomCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }
        return cardNumber.toString();
    }
    //helper function
    private static String generateRandomCardHolderName() {
        String[] firstNames = {"John", "Emma", "Michael", "Sophia", "William", "Olivia", "James", "Isabella", "Alexander", "Mia"};
        String[] lastNames = {"Smith", "Johnson", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor", "Anderson", "Thomas"};
        Random random = new Random();
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];
        return firstName + " " + lastName;
    }
}
