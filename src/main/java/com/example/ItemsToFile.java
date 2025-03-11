package com.example;

import java.io.*;
import java.util.ArrayList;

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

    public void export (ArrayList<PrimeItem> items) {
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
    public ArrayList<PrimeItem> read () {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
            ArrayList<PrimeItem> items = new ArrayList<PrimeItem>();
            items = (ArrayList<PrimeItem>) ois.readObject();
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
