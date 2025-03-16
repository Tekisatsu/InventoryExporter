package com.example;

import java.io.*;
import java.util.HashMap;

/**
 * Class for reading and writing HashMap of PrimeItems into a file.*/
public class ItemsToFile implements FileHandling {
    String filename = "primeItems.txt";
    public void setFilename(String filename) {
        if (filename.equals("")) {
            this.filename = "primeItems.txt";
        }
        if (!filename.endsWith(".txt")) {
            filename += ".txt";
        }
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    /**
     * Exports the hashMap of items into a file.
     * @param items HashMap of items to be saved into a file.*/
    public void export (HashMap<String,PrimeItem> items) {
        File file = new File(filename);
        if (!file.exists()) {
            try {file.createNewFile();}
            catch (IOException e) {
                System.out.println("Error creating file: "+e.getMessage());
                e.printStackTrace();
            }
        }
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(items);
            System.out.println("PrimeItems written to file: "+filename);
            oos.close();
        }
        catch (Exception e) {
            System.out.println("Error in serialization");
            e.printStackTrace();
        }
    }
    /**
     * Reads a file.
     * @return HashMap of items in the file.*/
    public HashMap<String,PrimeItem> read () {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
            HashMap<String,PrimeItem> items = new HashMap<String,PrimeItem>();
            items = (HashMap<String,PrimeItem>) ois.readObject();
            ois.close();
            return items;
        }
        catch (Exception e) {
            System.out.println("Error in deserialization");
            e.printStackTrace();
        }
        return null;
    }
}
