package com.example;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * JavaFX GUI for the application.*/
public class InventoryApp extends Application {
    TextArea textArea = new TextArea();
    TextArea outputTextArea = new TextArea();
    TextField searchbar = new TextField();
    HashMap<String, PrimeItem> items = new HashMap<String,PrimeItem>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        textArea.setPrefHeight(424);
        textArea.setPrefWidth(450);
        outputTextArea.setPrefHeight(400);
        outputTextArea.setPrefWidth(450);
        searchbar.setPrefWidth(300);
        outputTextArea.setEditable(false);
        ItemsToFile itemsToFile = new ItemsToFile();
        BorderPane pane = new BorderPane();
        Button add = new Button("Add");
        add.setOnAction(e -> {
            outputTextArea.setText("Items to be added:\n");
            String s = textArea.getText().toUpperCase();
            ArrayList<ArrayList<Object>> arrayList = textAreaParse(s);
            for (int i = 0; i < arrayList.size(); i++) {
                PrimeItem primeItem = new PrimeItem(arrayList.get(i).get(0), arrayList.get(i).get(1), arrayList.get(i).get(2));
                if (items.containsKey(primeItem.getName())){
                    items.remove(primeItem.getName());
                }
                items.put(primeItem.getName(),primeItem);
                outputTextArea.appendText(items.get(primeItem.getName()).toString()+"\n");
            }
        });
        Button clear = new Button("Clear");
        clear.setOnAction(e -> {
            textArea.setText("");
        });
        Button readFile = new Button("Read File");
        readFile.setOnAction(e -> {
            items = itemsToFile.read();
            outputTextArea.clear();
            for (PrimeItem value : items.values()) {
                outputTextArea.appendText(value.toString()+"\n");
            }
        });
        Button save = new Button("Save File");
        save.setOnAction(e -> {
            itemsToFile.export(items);
        });
        Button search = new Button("Search");
        search.setOnAction(e -> {
            outputTextArea.setText("Items found:\n");
            String s = searchbar.getText().toUpperCase();
            searchItems(s);
        });
        Button remove = new Button("Remove");
        remove.setOnAction(e -> {
            String s = searchbar.getText().toUpperCase();
            removeItems(s);
            outputTextArea.clear();
        });
        Button refresh = new Button("Refresh");
        refresh.setOnAction(e -> {
            outputTextArea.clear();
            for (PrimeItem value : items.values()) {
                outputTextArea.appendText(value.toString()+"\n");
            }
        });
        HBox hbox1 = new HBox(add,clear);
        HBox hbox2 = new HBox(readFile,save,refresh);
        HBox hbox3 = new HBox(searchbar,search,remove);
        VBox rVbox = new VBox(hbox2,hbox3,outputTextArea);
        VBox lVbox = new VBox(hbox1,textArea);
        hbox1.setSpacing(5);
        hbox2.setSpacing(5);
        hbox3.setSpacing(5);
        pane.setLeft(lVbox);
        pane.setRight(rVbox);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("WF Inventory Exporter");
        primaryStage.show();
    }
    /**
     * Takes in the user input TextArea and extract the relevant information.
     * @param s String from TextArea.
     * @return ArrayList is used to have all of the data needed for PrimeItem contructor.*/
    public ArrayList<ArrayList<Object>> textAreaParse(String s) {
        String[] t = s.split("\n");
        ArrayList<ArrayList<Object>> arrayList = new ArrayList<>();
        for (int i = 0; i < t.length; i++) {
            if (t[i].length() == 0) continue;
            if (t[i].equals("")) {
                continue;
            }
            arrayList.add(PrimeItem.parseItem(t[i]));
        }
        return arrayList;
    }
    /**
     * Searches for items that have match the given string.
     * @param s Name or part of the name of an item.*/
    public void searchItems(String s){
        for (PrimeItem value : items.values()) {
            if (value.getName().contains(s)) {
                outputTextArea.appendText(value.toString()+"\n");
            }
        }
    }
    /**
     * Removes all items with the matching name.
     * @param s Name or part of the name do be deleted.*/
    public void removeItems(String s){
        ArrayList<String> removeItems = new ArrayList<>();
        for (PrimeItem value : items.values()) {
            if (value.getName().contains(s)) {
                removeItems.add(value.getName());
            }
        }
        for (String item : removeItems) {
            items.remove(item);
        }
    }
}
