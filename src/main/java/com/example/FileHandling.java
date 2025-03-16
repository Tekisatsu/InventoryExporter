package com.example;

import java.util.HashMap;

/**
 * Interface for reading and writing things into a file.*/
public interface FileHandling {
    /**
     * Exporting a HashMap of items to a file.
     * @param items HashMap of items.*/
    public void export (HashMap<String,PrimeItem> items);
    /**
     * read a file.
     * @return returns a HashMap of read items in the file.*/
    public HashMap<String,PrimeItem> read ();
}
