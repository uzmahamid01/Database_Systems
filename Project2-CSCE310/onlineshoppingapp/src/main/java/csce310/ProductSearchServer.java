package csce310;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


//Run this URL in web browser:  http://localhost:8080/product/search?minprice=700&maxprice=900

public class ProductSearchServer {

    private static DataAdapter dataAdapter;

    public static void main(String[] args) throws IOException {
        dataAdapter = new DataAdapter();

        //HTTP server instance
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        //product search endpoint logic goes in here
        server.createContext("/product/search", exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                try {
                    // Get minprice and maxprice from query parameters
                    Map<String, List<String>> params = splitQuery(exchange.getRequestURI().getQuery());
                    double minprice = Double.parseDouble(params.getOrDefault("minprice", List.of("0")).get(0));
                    double maxprice = Double.parseDouble(params.getOrDefault("maxprice", List.of(Double.toString(Double.MAX_VALUE))).get(0));

                    // Retrieve product data from Redis db based on price range
                    List<ProductModel> products = dataAdapter.getProductsByPriceRange(minprice, maxprice);

                    String response = generateHTML(products);

                    //set response headers
                    exchange.getResponseHeaders().set("Content-Type", "text/html");
                    exchange.sendResponseHeaders(200, response.getBytes().length);

                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                } catch (NumberFormatException | NullPointerException e) {
                    exchange.sendResponseHeaders(400, -1); // 400 Bad Request
                }
            } else {
                exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
            }
        });

        server.start();
        System.out.println("Server is running on port 8080");
    }

    // Method to split query parameters
    private static Map<String, List<String>> splitQuery(String query) {
        return Arrays.stream(query.split("&"))
                .map(param -> param.split("="))
                .collect(Collectors.groupingBy(
                        param -> param[0],
                        Collectors.mapping(param -> param[1], Collectors.toList())
                ));
    }

    // Method to generate HTML response from product data
    private static String generateHTML(List<ProductModel> products) {
        //generate HTML for each product in the list  of ProductModels 
        //sort the list
        Collections.sort(products, (p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));
        StringBuilder productListHTML = new StringBuilder();
        if (products.isEmpty()) {
            productListHTML.append("<div>No products found within the specified price range</div>");
        } else {
            productListHTML.append("<div class=\"product-list\">");
            productListHTML.append("<div class=\"product product-heading\">");
            productListHTML.append("<span class=\"product-name\">Product Name</span>");
            productListHTML.append("<span class=\"product-price\">Price</span>");
            productListHTML.append("</div>");
            for (ProductModel product : products) {
                productListHTML.append("<div class=\"product\">");
                productListHTML.append("<span class=\"product-name\">").append(product.getProductName()).append("</span>");
                productListHTML.append("<span class=\"product-price\">$").append(String.format("%.2f", product.getPrice())).append("</span>");  //display upto 2 decimal places 
                productListHTML.append("</div>");
            }
            productListHTML.append("</div>");
        }
        
        
        // HTML template for the search form and results
        String htmlTemplate = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Product Search</title>
                    <style>
                        .product-list {
                            display: flex;
                            flex-direction: column;
                            gap: 15px;
                        }
                        .product {
                            display: flex;
                            justify-content: space-between;
                            padding: 10px;
                            border: 1px solid #ccc;
                        }
                        .product-name {
                            flex: 1;
                        }
                        .product-heading {
                            /* Remove border */
                            border: none;
                            font-size: 18px;
                            font-weight: bold;
                        }
                    </style>
                </head>
                <body>
                    <h1>Product Search</h1>
                    <form id="searchForm">
                        <label for="minPrice">Min Price:</label>
                        <input type="text" id="minPrice" name="minprice"><br><br>
                        <label for="maxPrice">Max Price:</label>
                        <input type="text" id="maxPrice" name="maxprice"><br><br>
                        <button type="submit" style="background-color: blue; color: white; font-size: 16px; width: 150px; height: 40px;">Search</button>
                    </form>
                    <br> 
                    <div id="searchResults">
                        %s
                    </div>
                    <h1>Done</h1>
                    
                    <script>
                        function searchProducts() {
                            const minPrice = document.getElementById("minPrice").value;
                            const maxPrice = document.getElementById("maxPrice").value;
                            const url = `/product/search?minprice=${minPrice}&maxprice=${maxPrice}`;
                            fetch(url)
                                .then(response => response.text())
                                .then(data => {
                                    document.getElementById("searchResults").innerHTML = data;
                                });
                        }
                    </script>
                </body>
                </html>
                """;
        
        // Insert the product list HTML into the template
        return String.format(htmlTemplate, productListHTML.toString());
        
    }
}
