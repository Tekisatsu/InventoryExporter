package com.example;

import java.util.ArrayList;
import java.util.HashMap;

public interface FileHandling {
    public void export (HashMap<String,PrimeItem> items);
    public HashMap<String,PrimeItem> read ();
}
