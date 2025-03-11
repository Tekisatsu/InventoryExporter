package com.example;

import java.util.ArrayList;

public interface FileHandling {
    public void export (ArrayList<PrimeItem> items);
    public ArrayList<PrimeItem> read ();
}
