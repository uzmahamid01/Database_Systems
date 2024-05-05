package csce310;

import redis.clients.jedis.Jedis;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class DataAdapter {
    private Jedis jedis;
    
    public DataAdapter() {
        // Establish connection to Redis server
        jedis = new Jedis("");
    }

    //add a user
    public void addUser(String userID, String userName, String address, String phone, String email) {
        // Set User data in Redis as a hash
        jedis.hset("User:" + userID, "UserID", userID);
        jedis.hset("User:" + userID, "UserName", userName);
        jedis.hset("User:" + userID, "Address", address);
        jedis.hset("User:" + userID, "Phone", phone);
        jedis.hset("User:" + userID, "Email", email);
    }

    //read all users
    public void readAllUsers() {
        // Get all keys matching the pattern "User:*"
        Set<String> userKeys = jedis.keys("User:*");

        // Iterate over user keys and fetch user details
        for (String userKey : userKeys) {
            Map<String, String> userData = jedis.hgetAll(userKey);
            System.out.println("User ID: " + userData.get("UserID"));
            System.out.println("User Name: " + userData.get("UserName"));
            System.out.println("Address: " + userData.get("Address"));
            System.out.println("Phone: " + userData.get("Phone"));
            System.out.println("Email: " + userData.get("Email"));
            System.out.println();
        }
    }

    //update a user
    public void updateUser(String userID, String userName, String address, String phone, String email) {
        // Update User data in Redis as a hash
        jedis.hset("User:" + userID, "UserName", userName);
        jedis.hset("User:" + userID, "Address", address);
        jedis.hset("User:" + userID, "Phone", phone);
        jedis.hset("User:" + userID, "Email", email);
    }

    //delete a user
    public void deleteUser(String userID) {
        // Delete user data from Redis
        jedis.del("User:" + userID);
    }

    //add a product
    public void addProduct(String productID, String productName, String description, double price, int quantity) {
        // Check if the product ID already exists
        if (jedis.exists("Product:" + productID)) {
            // Product ID already exists, throw an error
            throw new IllegalArgumentException("Product ID already exists. Choose a different ID.");
        }

        // Product ID doesn't exist, proceed to add the product
        jedis.hset("Product:" + productID, "ProductID", productID);
        jedis.hset("Product:" + productID, "ProductName", productName);
        jedis.hset("Product:" + productID, "Description", description);
        jedis.hset("Product:" + productID, "Price", String.valueOf(price));
        jedis.hset("Product:" + productID, "Quantity", String.valueOf(quantity));
    }


    //read all products
    public void readAllProducts() {
        // Get all keys matching the pattern "Product:*"
        Set<String> productKeys = jedis.keys("Product:*");

        // Iterate over product keys and fetch product details
        for (String productKey : productKeys) {
            Map<String, String> productData = jedis.hgetAll(productKey);
            System.out.println("Product ID: " + productData.get("ProductID"));
            System.out.println("Product Name: " + productData.get("ProductName"));
            System.out.println("Description: " + productData.get("Description"));
            System.out.println("Price: $" + productData.get("Price"));
            System.out.println("Quantity: " + productData.get("Quantity"));
            System.out.println();
        }
    }

    //update a product
    public void updateProduct(String productID, String productName, String description, double price, int quantity) {
        // Update Product data in Redis as a hash
        jedis.hset("Product:" + productID, "ProductName", productName);
        jedis.hset("Product:" + productID, "Description", description);
        jedis.hset("Product:" + productID, "Price", String.valueOf(price));
        jedis.hset("Product:" + productID, "Quantity", String.valueOf(quantity));
    }

    //delete a product
    public void deleteProduct(String productID) {
        // Delete product data from Redis
        jedis.del("Product:" + productID);
    }

    // Close the connection
    public void close() {
        jedis.close();
    }

    //retrieve all product keys
    public Set<String> getAllProductKeys() {
        return jedis.keys("Product:*");
    }

    //retrieve product data for a given product key
    public Map<String, String> getProductData(String productKey) {
        return jedis.hgetAll(productKey);
    }

    public void addOrder(String productId, String userId, double price, String cardHoldersName, String cardNumber, int cvv) {
        // Generate a random order ID that doesn't already exist
        String orderId;
        do {
            // Generate a random order ID
            orderId = generateRandomOrderId();
        } 
        while (jedis.exists("Order:" + orderId)); // Check if the generated order ID already exists

        // Set Order data in Redis as a hash
        jedis.hset("Order:" + orderId, "OrderID", orderId);
        jedis.hset("Order:" + orderId, "ProductID", productId);
        jedis.hset("Order:" + orderId, "UserID", userId);
        jedis.hset("Order:" + orderId, "Price", String.valueOf(price));
        jedis.hset("Order:" + orderId, "CardHoldersName", cardHoldersName);
        jedis.hset("Order:" + orderId, "CardNumber", cardNumber);
        jedis.hset("Order:" + orderId, "CVV", String.valueOf(cvv));
    }

    //generate a random order ID
    private String generateRandomOrderId() {
        StringBuilder orderId = new StringBuilder();
        Random random = new Random();
        // Generate a random order ID with 6 digits
        for (int i = 0; i < 6; i++) {
            orderId.append(random.nextInt(10)); // Append a random digit (0-9)
        }
        return orderId.toString();
    }
    //retrieve products by price range --- Function 3
    public List<ProductModel> getProductsByPriceRange(double minPrice, double maxPrice) {
        List<ProductModel> products = new ArrayList<>();

        // Get all product keys matching the pattern "Product:*"
        Set<String> productKeys = jedis.keys("Product:*");

        // Iterate over product keys and fetch product details
        for (String productKey : productKeys) {
            Map<String, String> productData = jedis.hgetAll(productKey);
            double price = Double.parseDouble(productData.get("Price"));

            // Check if the price falls within the specified range
            if (price >= minPrice && price <= maxPrice) {
                // Create a ProductModel object and add it to the list
                ProductModel product = new ProductModel(productKey, productKey, productKey, price, 0);
                product.setProductName(productData.get("ProductID"));
                product.setProductName(productData.get("ProductName"));
                product.setDescription(productData.get("Description"));
                product.setPrice(price);
                product.setQuantity(Integer.parseInt(productData.get("Quantity")));
                products.add(product);
            }
        }
        return products;
    }
    public boolean isUserExists(String userId) {
        // Check if the user ID exists in the database
        return jedis.exists("User:" + userId);
    }
    

}
