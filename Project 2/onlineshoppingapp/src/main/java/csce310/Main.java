package csce310;

import redis.clients.jedis.Jedis;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Connect to Redis
        //Jedis jedis = new Jedis("redis://default:ncjQgvh2VkfmQLoRMfawveEb0fsBzErJ@redis-11998.c12.us-east-1-4.ec2.redns.redis-cloud.com:11998");
        Jedis jedis = new Jedis("redis://default:fw0QQB3zs4dE0SVfUkbJ29SzkBsXNEdR@redis-16046.c266.us-east-1-3.ec2.redns.redis-cloud.com:16046");
        
        try {
            // Drop existing keys if they exist
            jedis.del("Users", "Products", "Orders");

            // populate the Collection with initially some generated data

            // Create and populate Users table
            populateUsersTable(jedis, 100);

            // Create and populate Products table
            populateProductsTable(jedis, 100);

            // Create and populate Products table
            populateOrdersTable(jedis, 50);

            System.out.println("Tables created and populated successfully.");

        } finally {
            // Close Redis connection
            jedis.close();
        }
    }

    private static void populateUsersTable(Jedis jedis, int count) {
        for (int i = 1; i <= count; i++) {
            String name = generateRandomName();
            String address = generateRandomAddress();
            String phone = generateRandomPhoneNumber();
            String email = generateRandomEmail(name);

            jedis.hset("User:" + i, "UserID", String.valueOf(i));
            jedis.hset("User:" + i, "UserName", name);
            jedis.hset("User:" + i, "Address", address);
            jedis.hset("User:" + i, "Phone", phone);
            jedis.hset("User:" + i, "Email", email);
        }
    }

    private static void populateProductsTable(Jedis jedis, int count) {
        Random random = new Random();
        String[] productNames = {"Smartphone", "Laptop", "Tablet", "Smartwatch", "Wireless Headphones"};
        
        for (int i = 1; i <= count; i++) {
            int nameIndex = random.nextInt(productNames.length);
            String productName = productNames[nameIndex];
            String description = "Description for " + productName;
            double price = 100 + random.nextDouble() * 900;
            int quantity = 10 + random.nextInt(91);

            jedis.hset("Product:" + i, "ProductID", String.valueOf(i));
            jedis.hset("Product:" + i, "ProductName", productName);
            jedis.hset("Product:" + i, "Description", description);
            jedis.hset("Product:" + i, "Price", String.valueOf(price));
            jedis.hset("Product:" + i, "Quantity", String.valueOf(quantity));
        }
    }

    private static String generateRandomEmail(String name) {
        String[] domains = {"gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "aol.com"};
        Random random = new Random();
        return name.replaceAll("\\s+", "").toLowerCase() + "@" + domains[random.nextInt(domains.length)];
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
    private static void populateOrdersTable(Jedis jedis, int count) {
        Random random = new Random();
        
        for (int i = 1; i <= count; i++) {
            int productId = random.nextInt(100) + 1; 
            int userId = random.nextInt(100) + 1; 
            double price = 100 + random.nextDouble() * 900; 
            String cardHoldersName = generateRandomName(); 
            String cardNumber = generateRandomCardNumber(); 
            int cvv = random.nextInt(900) + 100; 
            
            // quantity of the product from the Products table
            String quantityStr = jedis.hget("Product:" + productId, "Quantity");
            int quantity = Integer.parseInt(quantityStr);
    
            if (quantity > 0) {
                jedis.hset("Product:" + productId, "Quantity", String.valueOf(quantity - 1));
                jedis.hset("Order:" + i, "OrderID", String.valueOf(i));
                jedis.hset("Order:" + i, "ProductID", String.valueOf(productId));
                jedis.hset("Order:" + i, "UserID", String.valueOf(userId));
                jedis.hset("Order:" + i, "Amount", String.valueOf(price));
                jedis.hset("Order:" + i, "CardHoldersName", cardHoldersName);
                jedis.hset("Order:" + i, "CardNumber", cardNumber);
                jedis.hset("Order:" + i, "CVV", String.valueOf(cvv));
            }
        }
    }
    private static String generateRandomCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }
        return cardNumber.toString();
    }
}
