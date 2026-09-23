/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.fit.mp2;
import java.util.Scanner;
/**
 *
 * @author dastarosa
 */
class Product {
    private String code;
    private String name;
    private double price;
    private int stock;
    
    private static int productCount = 0;

    public Product(String code, String name, double price, int stock) {
        this.code = code;
        this.name = name;
        setPrice(price);
        setStock(stock);
        productCount++;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative.");
        }
        this.stock = stock;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void restock(int quantity) {
        if (quantity > 0) {
            this.stock += quantity;
            System.out.println("Restock successful. New stock for " + name + ": " + this.stock);
        } else {
            System.out.println("Restock failed: Quantity must be positive.");
        }
    }

    public boolean sell(int quantity) {
        if (quantity <= 0) {
            System.out.println("Sale failed: Quantity must be positive.");
            return false;
        }
        if (quantity > this.stock) {
            System.out.println("Sale failed: Quantity exceeds available stock (" + this.stock + ").");
            return false;
        }
        this.stock -= quantity;
        System.out.println("Sale successful. Remaining stock for " + name + ": " + this.stock);
        return true;
    }

    public double getInventoryValue() {
        return this.price * this.stock;
    }

    public static int getProductCount() {
        return productCount;
    }
}

public class Exer3StockInventory {
    public static Product searchProductByCode(Product[] products, String code) {
        for (int i = 0; i < products.length; i++) {
            if (products[i] != null && products[i].getCode().equalsIgnoreCase(code)) {
                return products[i];
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int numProducts = 0;
        while (true) {
            System.out.print("Enter the number of products to create (1 to 8): ");
            if (scanner.hasNextInt()) {
                numProducts = scanner.nextInt();
                if (numProducts >= 1 && numProducts <= 8) {
                    break;
                }
            } else {
                scanner.next();
            }
            System.out.println("Invalid input. Please enter a number between 1 and 8.");
        }
        scanner.nextLine();

        Product[] products = new Product[numProducts];

        for (int i = 0; i < numProducts; i++) {
            System.out.println("\nEnter details for Product " + (i + 1) + ":");
            System.out.print("Product Code: ");
            String code = scanner.nextLine();

            System.out.print("Product Name: ");
            String name = scanner.nextLine();

            double price = -1;
            while (true) {
                System.out.print("Unit Price: ");
                if (scanner.hasNextDouble()) {
                    price = scanner.nextDouble();
                    if (price >= 0) break;
                    System.out.println("Price cannot be negative.");
                } else {
                    System.out.println("Invalid input format.");
                    scanner.next();
                }
            }

            int stock = -1;
            while (true) {
                System.out.print("Opening Stock: ");
                if (scanner.hasNextInt()) {
                    stock = scanner.nextInt();
                    if (stock >= 0) break;
                    System.out.println("Stock cannot be negative.");
                } else {
                    System.out.println("Invalid input format.");
                    scanner.next();
                }
            }
            scanner.nextLine();

            try {
                products[i] = new Product(code, name, price, stock);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                i--;
            }
        }

        System.out.print("\nEnter the number of stock transactions to process: ");
        int numTransactions = scanner.nextInt();
        scanner.nextLine();

        for (int t = 1; t <= numTransactions; t++) {
            System.out.println("\n--- Transaction " + t + " of " + numTransactions + " ---");
            System.out.print("Enter Product Code: ");
            String targetCode = scanner.nextLine();

            Product targetProduct = searchProductByCode(products, targetCode);

            if (targetProduct == null) {
                System.out.println("Product with code '" + targetCode + "' not found.");
                continue;
            }

            System.out.print("Choose action - (R) Restock, (S) Sell: ");
            String action = scanner.nextLine().trim().toLowerCase();

            System.out.print("Enter quantity: ");
            int qty = scanner.nextInt();
            scanner.nextLine();

            if (action.equals("r")) {
                targetProduct.restock(qty);
            } else if (action.equals("s")) {
                targetProduct.sell(qty);
            } else {
                System.out.println("Invalid action selection. Please enter 'r' or 's'.");
            }
        }

        System.out.println("\n========================================");
        System.out.println("         FINAL INVENTORY REPORT         ");
        System.out.println("========================================");

        double totalInventoryValue = 0;

        for (int i = 0; i < products.length; i++) {
            Product p = products[i];
            double invValue = p.getInventoryValue();
            totalInventoryValue += invValue;

            System.out.println("Code: " + p.getCode());
            System.out.println("Name: " + p.getName());
            System.out.println("Unit Price: $" + p.getPrice());
            System.out.println("Remaining Stock: " + p.getStock());
            System.out.println("Inventory Value: $" + invValue);

            if (p.getStock() <= 5) {
                System.out.println("Status: ** LOW STOCK **");
            } else {
                System.out.println("Status: Normal");
            }
            System.out.println("----------------------------------------");
        }

        System.out.println("Total Inventory Value: $" + totalInventoryValue);
        System.out.println("Total Product Objects Created (Static Count): " + Product.getProductCount());
        System.out.println("========================================");

        scanner.close();
    }
}