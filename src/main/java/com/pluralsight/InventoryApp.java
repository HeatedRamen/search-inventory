package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class InventoryApp {

    public static void main(String[] args) throws IOException{

        ArrayList<Product> inventory = getInventory();

        System.out.println("We carry the following inventory: ");

        //Collections.sort(inventory.name);

        for (int i = 0; i < inventory.size(); i++) {
            Product p = inventory.get(i);
            System.out.printf("id: %d %s - Price: $%.2f%n",
                    p.getID(), p.getName(), p.getPrice());
        }
    }
    static public ArrayList<Product>getInventory() throws IOException {

        ArrayList<Product> inventory = new ArrayList<>();
        FileReader fileReader = new FileReader("inventory.csv");
        BufferedReader bufReader = new BufferedReader(fileReader);

        String newInventory = bufReader.readLine();
        String[] parsedInventory;

        while(newInventory != null){
            parsedInventory = newInventory.split("\\|");
            inventory.add(new Product(Integer.parseInt(parsedInventory[0]), parsedInventory[1], Double.parseDouble(parsedInventory[2])));

            newInventory = bufReader.readLine();
        }

        return inventory;
    }

}
