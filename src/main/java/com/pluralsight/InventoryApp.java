package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class InventoryApp {

    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) throws IOException{

        boolean isRunning = true;
        String userChoice;
        // Start up and fill the array list with items in the inventory
        ArrayList<Product> inventory = getInventory();

        do {

            // Display menu then prompt user for choice
            displayInventoryMenu();
            userChoice = input.nextLine();

            switch(userChoice){
                case "1":
                    // Display
                    displayAllInventory(inventory);
                    break;
                case "2":
                    // Prompt user for ID and search function, output item that match the product ID
                    System.out.println("What is the ID of the product you are looking for?");
                    lookUpID(inventory, input.nextInt());
                    input.nextLine();
                    break;
                case "3":
                    // Prompt user for price range / maximum, output item(s) that are below that amount
                    System.out.println("What is the price range of the product you are looking for?");
                    lookUpPrice(inventory, input.nextDouble());
                    input.nextLine();
                    break;
                case "4":
                    // Method to add items into the array list
                    addProduct(inventory);
                    break;
                case "5":
                    // Turns condition to false to end the loop
                    isRunning = false;
                    break;
            }

            // Commented out sort function, maybe refer back to this in the future
//            inventory.sort((inventory1, inventory2) ->
//                    inventory1.getName().compareTo(inventory2.getName()));
        } while (isRunning);
    }
    static public void displayInventoryMenu(){

        // Display the menu in it's "greatness"
        System.out.println("""
                                What do you want to do?
                                    1- List all products
                                    2- Lookup a product by its id
                                    3- Find all products within a price range
                                    4- Add a new product
                                    5- Quit the application
                                Enter command:""");

    }

    static public void displayAllInventory(ArrayList<Product> inventory) {

        // Loops through inventory list and output ID, name, and price
        System.out.println("We carry the following inventory: ");
        for (int i = 0; i < inventory.size(); i++) {
            Product p = inventory.get(i);
            System.out.printf("id: %d %s - Price: $%.2f%n",
                    p.getID(), p.getName(), p.getPrice());
        }
    }

    static public ArrayList<Product>getInventory() throws IOException {

        // Reads the file
        ArrayList<Product> inventory = new ArrayList<>();
        FileReader fileReader = new FileReader("inventory.csv");
        BufferedReader bufReader = new BufferedReader(fileReader);

        String newInventory = bufReader.readLine();
        String[] parsedInventory;

        while(newInventory != null){
            // Parse the inventory string based on the pipe and add into array list
            parsedInventory = newInventory.split("\\|");
            inventory.add(new Product(Integer.parseInt(parsedInventory[0]), parsedInventory[1], Double.parseDouble(parsedInventory[2])));

            // Read next line
            newInventory = bufReader.readLine();
        }
        return inventory;
    }
    static public void lookUpID(ArrayList<Product> inventory, int id){

        // Loop through inventory list, looks for a matching ID and output if it is
        boolean isFound = false;
        for (Product item : inventory){
            if (id == item.getID()){
                System.out.printf("id: %d %s - Price: $%.2f%n",
                        item.getID(), item.getName(), item.getPrice());
                isFound = true;
            }
        }
        // If item is not found, tell user that there was no matching ID
        if(!isFound){
            System.out.println("We could not find an item with that ID");
        }
    }
    static public void lookUpPrice(ArrayList<Product> inventory, double price) {

        // Looks through inventory list, compares price and see if it is lower than the user's "maximum"
        // Output item information if below that price range
        boolean isFound = false;
        for (Product item : inventory) {
            if (price >= item.getPrice()) {
                System.out.printf("id: %d %s - Price: $%.2f%n",
                        item.getID(), item.getName(), item.getPrice());
                isFound = true;
            }
        }
        if (!isFound) {
            System.out.println("We could not find an item within that price range");
        }
    }
    static public void addProduct(ArrayList<Product> inventory){

        // Prompt the user for product information
        System.out.println("What is the ID of the product?");
        int id = input.nextInt();
        input.nextLine(); // Clears buffer
        System.out.println("What is the name of the product?");
        String name = input.nextLine();
        System.out.println("What is the price of the product?");
        double price = input.nextDouble();
        input.nextLine(); // Clears buffer

        // Add to Array list
        inventory.add(new Product(id, name, price));
    }
}
