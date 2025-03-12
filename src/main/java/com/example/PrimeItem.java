package com.example;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Extends data for handling prime items.*/
public class PrimeItem extends Data implements Serializable {
    /**
     * @param amount Object will be turned into an int.
     * @param name Object will be turned into a string
     * @param ducats Object will be turned into an int
     * @exception NumberFormatException Will be thrown when invalid data is provided.*/
    PrimeItem(Object amount, Object name, Object ducats) {
        try {
            this.amount = Integer.parseInt(amount.toString());
            this.name = name.toString();
            this.ducats = Integer.parseInt(ducats.toString());
        }
        catch (NumberFormatException e) {
            System.out.println("PrimeItem contructor error. Invalid data: "+e.getMessage());
        }
    }

    /**Parses the provided string into an ArrayList.
     * @param s String in the format: amount name ducats
     * @return Returns and Arraylist in the format: amount,name,ducats
     * @exception NumberFormatException when no amount is given (default to 1) or is missing ducat amount.
     * @implNote Parses only one item string at a time.*/
    public static ArrayList<Object> parseItem(String s){
        if (s == null) return null;
        if (s.length() == 0) return null;

        //Regex to remove extra spaces. I used regex101 to make and check this.
        s = s.replaceAll("\\s+"," ");
        s.trim();
        String[] split = s.split(" ");
        String name = "";
        Integer a = 1;
        Integer d = 0;
        ArrayList<Object> result = new ArrayList<>();
        //Getting the amount of an item and it's ducat value.
        try {
            a = Integer.parseInt(split[0]);
            d = Integer.parseInt(split[split.length-1])/a;
        }
        catch (NumberFormatException e){
            System.out.println("Error parsing data or no amount given: " + e.getMessage());
            try {d = Integer.parseInt(split[split.length-1]);}
            catch (NumberFormatException e1){
                System.out.println("Error parsing data or no amount given: " + e.getMessage());
            }
        }
        if (d == 0) {
            System.out.println("No ducats given");
            return new ArrayList<>();
        }
        result.add(a);
        //Finding the Prime keyword for getting the correct data for name and ducats.
        //We are skipping the last index on purpose, as we already know they are not relevant for finding the name.
        //Do a less janky name parsing at some point.
        int sIndex = 0;
        if (split[1].equals("X")) {
            sIndex = 2;
        }
        for (int i = sIndex; i < split.length-1; i++) {
            name = name + split[i] + " ";
        }
        result.add(name.trim());
        result.add(d);
        return result;
    }
}
