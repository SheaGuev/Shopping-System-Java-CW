/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.westminstershoppingmanager;

/**
 *
 * @author sheas
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.Locale;


public class WestminsterShoppingManager implements ShoppingManager {
    private static final int MAX_PRODUCTS = 50; // defines max number of products that can be added to the list as 50
    private List<Product> productList; // creates a list of products

    public WestminsterShoppingManager(List<Product> productList) {
        this.productList = productList;
    }
    
    public List<Product> getProductList() {// used for testing to retrieve the list
        return productList;
    }

    @Override
    public void addProduct(Product product) {
        if (productList.size() < MAX_PRODUCTS) {//if the size of the list is less than 50 then add the product
            productList.add(product);
            System.out.println("Product added successfully."); //success message
        } else {
            System.out.println("Maximum number of products reached. Cannot add more."); //failure message
            
        }
    }

    @Override
    public void removeProduct(String productID) {
        Product foundProduct = null;
        for (Product product : productList) {//only carries out action if the product is in the list, avoids errors
            if (product.getProductID().equals(productID)) {//finds corresponding product ID and changed foundProduct to that product
                foundProduct = product;
                break;
            }
        }

        if (foundProduct != null) {
            productList.remove(foundProduct);//removes product
            System.out.println("Product deleted successfully.");//success message
        } else {
            System.out.println("Product not found.");//failure message
        }

        System.out.println("Total number of products left: " + productList.size());//prints the number of products left
    }

    @Override
    public void printProducts() {
        productList.sort((p1, p2) -> p1.getProductID().compareTo(p2.getProductID()));//sorts the list by product ID

        for (Product product : productList) {//prints out the details of each product
            if (product instanceof Electronics) {//checks if the product is an instance of electronics or clothing
                System.out.println("Electronics: " + product.getProductName());
            } else if (product instanceof Clothing) {
                System.out.println("Clothing: " + product.getProductName());//prints specific details of each product
            }

            System.out.println("Product ID: " + product.getProductID());
            System.out.println("Available Items: " + product.getAvailableItems());
            System.out.println("Price: " + product.getPrice());

            if (product instanceof Electronics) {
                Electronics electronics = (Electronics) product;
                System.out.println("Brand: " + electronics.getBrand());
                System.out.println("Warranty Period: " + electronics.getWarrantyPeriod());
            } else if (product instanceof Clothing) {
                Clothing clothing = (Clothing) product;
                System.out.println("Size: " + clothing.getSize());
                System.out.println("Color: " + clothing.getColor());
            }

            System.out.println("---------------------");
        }
    }

    @Override
    public void saveToFile() throws IOException {
     
      FileWriter file = new FileWriter("products.csv", false);//saves files as products.csv
      BufferedWriter writer = new BufferedWriter(file);// creates writer to write to the file
      Iterator var3 = productList.iterator();//creates iterator to iterate through the list

      try {
      while(var3.hasNext()) {
        Product product = (Product)var3.next();
        if (product instanceof Electronics) {
            writer.write(product.getProductName());//writes the details of each product to the file in simple way using commas to seperate each detail
            writer.write(",");
            writer.write(product.getProductID());//order is based on how products are intialised 
            writer.write(",");
            writer.write(Integer.toString(product.getAvailableItems()));
            writer.write(",");
            writer.write(Double.toString(product.getPrice()));
            writer.write(",");
            writer.write(Integer.toString(((Electronics) product).getWarrantyPeriod()));
            writer.write(",");
            writer.write(((Electronics) product).getBrand());
            writer.newLine();
        } else if (product instanceof Clothing) {    
            writer.write(product.getProductName());
            writer.write(",");
            writer.write(product.getProductID());
            writer.write(",");
            writer.write(Integer.toString(product.getAvailableItems()));
            writer.write(",");
            writer.write(Double.toString(product.getPrice()));
            writer.write(",");
            writer.write(((Clothing) product).getSize());
            writer.write(",");
            writer.write(((Clothing) product).getColor());
            writer.newLine();
        
         
         
      }}}finally{
        // Always close the writer and file in a finally block to ensure they are closed even if an exception occurs
        if (writer != null) {
            writer.close();
        }
        if (file != null) {
            file.close();
        }
    
      System.out.println("Students written to CSV successfully.");
      writer.close();//closes writer
      file.close(); //closes file
    }//closed under finally to ensure it is closed even if an exception occurs
   }

   public void loadFile() throws IOException { //loads the file
      
      //Scanner scanner = new Scanner(new File("products.csv"));
      //scanner.useDelimiter(",");
      FileReader file = new FileReader("products.csv");
      BufferedReader reader = new BufferedReader(file);
        
     String line;
      while ((line = reader.readLine())!= null ){
          String[] parts = line.split(",");
          if (parts.length == 6){
         String productName = parts[0];//reads the details of each product from the file
         String productID = parts[1];//order is based on how products are intialised
         int availableItems = Integer.parseInt(parts[2]);//
         double price = Double.parseDouble(parts[3]);
          
         try {//checks if the product is an instance of electronics or clothing by using a try catch and traking advantage of the differeqnt data types
            int warrantyPeriod = Integer.parseInt(parts[4]);
            String brand = parts[5];

            productList.add(new Electronics(productID, productName, availableItems, price, brand, warrantyPeriod));
            
            
         } catch (Exception e) {
            String size = parts[4];
            String color = parts[5];
            
            productList.add(new Clothing(productID, productName, availableItems, price, size, color));
         }
      }}
      file.close();//closes file and reader when done
      reader.close();

      
    }

    

    public class customerGUI extends JFrame{
    
    public customerGUI(){
        super("Westminster Shopping Centre");
        // Panel p1 for 
        JPanel p1 = new JPanel();
        p1.setLayout(new BorderLayout(5, 10));
        p1.setBackground(Color.white);

        JPanel centerPanel = new JPanel();
        p1.add(centerPanel, BorderLayout.CENTER);
        
        JComboBox jcboCategpry = new JComboBox(new String[]{"All", 
        "Electronics", "Clothing"}); 
        centerPanel.add(jcboCategpry, BorderLayout.CENTER);

        JPanel eastPanel = new JPanel();
        p1.add(eastPanel, BorderLayout.EAST);

        eastPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));

        JPanel westPanel = new JPanel();
        p1.add(westPanel, BorderLayout.WEST);

        westPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));

        for (int i =0; i <= 3; i ++){
            JPanel panel = new JPanel();
        
            eastPanel.add(panel);
        }

        eastPanel.add(new JButton("View Cart"));

        JTextField slectCat = new JTextField("Select Product Category");
        westPanel.add(slectCat);



  
        

        //panel p3 that combines p1 and p2
        JPanel p3 = new JPanel();
        p3.setLayout(new GridLayout(3, 1));
        p3.add(p1);
        //p3.add(p2);
        //p3.setBackground(Color.white);
        add(p3);
    } 
    
    public void runGUI() {
        customerGUI frame = new customerGUI();
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    }
    }

    

    public static void main(String[] args) {

        
        // Example usage in the main method
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager(new ArrayList<>());
        Scanner scanner = new Scanner(System.in);

        try {
                shoppingManager.loadFile(); //loads the file when the program is run if it exists
            } catch (IOException e) {
                System.out.println("Error loading : " + e.getMessage());
            }

        while (true) {//loop which displays the console layout to take action in the system
            
            System.out.println("1. Add Product");
            System.out.println("2. Delete Product");
            System.out.println("3. Print Products");
            System.out.println("4. Save to File");
            System.out.println("5. Open Customer GUI");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = getUserChoice(scanner); // calls the getUserChoice method to get the correct user input as try catch in class leads to errors
            
            //scanner.nextLine(); // Consume the newline to prevent skipping the next input

            switch (choice) {
                case 1:
                while (true) {
                    
                
                    System.out.print("Enter the product type (1 for Electronics, 2 for Clothing): ");
                    int productType = getUserChoice(scanner);
                    //scanner.nextLine(); // Consume the newline

                    if (productType == 1) {//if product type is 1 then electronics inputs are required
                        System.out.print("Enter the product ID: ");
                        String productID = scanner.nextLine();
                        System.out.print("Enter the product name: ");
                        String productName = scanner.nextLine();
                        System.out.print("Enter the available items: ");

                        int availableItems;
                        while (true) { //loop to check if the input is valid
                           try {
                            availableItems = Integer.parseInt(scanner.nextLine());//Consume the newline and convert it to integer(stops bug where it skips the next input using nextInt)
                            break;
                           } catch (Exception e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                           } 
                        }

                        double price;
                        System.out.print("Enter the price: ");
                        while (true) {//loop to check if the input is valid
                            try {
                            price = Double.parseDouble(scanner.nextLine());
                            break;

                        } catch (Exception e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                        }  
                        }

                        System.out.print("Enter the brand: ");
                        String brand = scanner.nextLine();
                        int warrantyPeriod;
                        System.out.print("Enter the warranty period: ");
                        while (true) {//loop to check if the input is valid
                           try {
                            warrantyPeriod = Integer.parseInt(scanner.nextLine());//Consume the newline and convert it to integer(stops bug where it skips the next input using nextInt)
                            break;
                           } catch (Exception e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                           } 
                        }
                       
                        shoppingManager.addProduct(new Electronics(productID, productName, availableItems, price, brand, warrantyPeriod));//creates new product and adds it to the list


                    } else if (productType == 2) {//if product type is 2 then clothing inputs are required
                        System.out.print("Enter the product ID: ");
                        String productID = scanner.nextLine();
                        System.out.print("Enter the product name: ");
                        String productName = scanner.nextLine();

                        int availableItems;
                        System.out.print("Enter the available items: ");
                        while (true) {//loop to check if the input is valid
                           try {
                            availableItems = Integer.parseInt(scanner.nextLine());//Consume the newline and convert it to integer(stops bug where it skips the next input using nextInt)
                            break;
                           } catch (Exception e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                           } 
                        }

                        System.out.print("Enter the price: ");
                        double price;
                        while (true) {//loop to check if the input is valid
                           try {
                            price = Double.parseDouble(scanner.nextLine());//Consume the newline and convert it to integer(stops bug where it skips the next input using nextInt)
                            break;
                           } catch (Exception e) {
                            System.out.println("Invalid input. Please enter a valid number.");//error message
                           } 
                        }
                        
                        System.out.print("Enter the size: ");
                        String size = scanner.nextLine();
                        System.out.print("Enter the color: ");
                        String color = scanner.nextLine();
                        shoppingManager.addProduct(new Clothing(productID, productName, availableItems, price, size, color));//creates new product and adds it to the list
                    } else {
                        System.out.println("Invalid product type. Please enter a valid option.");//error message
                    }
                    
                    break;
                }   
                break;
                case 2:
                    // Delete Product
                    System.out.print("Enter the product ID to delete: ");
                    String deleteProductID = scanner.nextLine();//takes the product ID of the product to be deleted
                    shoppingManager.removeProduct(deleteProductID);//removes the product from the list
                    break;
                case 3:
                    // Print Products
                    shoppingManager.printProducts();//calls print method to print all the product details
                    break;
                case 4:
                    // Save to File
                    try {
                        shoppingManager.saveToFile();//calls save method to save the products to the file
                    } catch (IOException e) {//cataches any errors if saving doesnt work
                        System.out.println("Error occurred while saving to file: " + e.getMessage());
                    }
                    break;
                case 5:
                    // Open Customer GUI
                    WestminsterShoppingManager.customerGUI customerGui = shoppingManager.new customerGUI();//creates new customer GUI
                    customerGui.runGUI();//runs the GUI
                    break;
                    
                case 6:
                    // Exit
                    System.exit(0);//exits the program
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static int getUserChoice(Scanner scanner) {//method to get the user choice in the console
        int choice = 0;

        while (true) {
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Consume the invalid input
            }
        }

        return choice;
    }
}


