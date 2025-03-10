package com.example;

import java.security.spec.ECField;
import java.util.ArrayList;

public class PrimeItem extends Data {
    PrimeItem(Integer amount, String name, Integer ducats) {
        this.amount = amount;
        this.name = name;
        this.ducats = ducats;
    }
    /**Parses the provided string into an ArrayList.
     * @param s String in the format: amount name ducats
     * @return Returns and Arraylist in the format: amount,name,ducats*/
    public static ArrayList<Object> parseData(String s){
        if (s == null) return null;
        if (s.length() == 0) return null;

        //Regex to remove extra spaces. I used regex101 to make and check this.
        s = s.replaceAll("\\s+"," ");
        s.trim();
        String[] split = s.split(" ");
        String name = "";
        Integer amount = 1;
        Integer ducats = 0;
        ArrayList<Object> result = new ArrayList<>();
        //Getting the amount of an item and it's ducat value.
        try {
            amount = Integer.parseInt(split[0]);
            ducats = Integer.parseInt(split[split.length-1]);
        }
        catch (NumberFormatException e){
            System.out.println("Error parsing data or no amount given: " + e.getMessage());
        }
        if (ducats == 0) {
            System.out.println("No ducats given");
            return new ArrayList<>();
        }
        result.add(amount);
        //Finding the Prime keyword for getting the correct data for name and ducats
        for (int i = 0; i < split.length; i++) {
            if (split[i] == String.valueOf(amount)) {
                continue;
            }
            else if (split[i] == "X") {
                continue;
            }
            else if (split[i] == String.valueOf(ducats)) {
                continue;
            }
            name = name + split[i];
        }
        result.add(name);
        result.add(ducats);
        return result;
    }
}
