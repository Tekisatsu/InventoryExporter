package com.example;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
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
        outputTextArea.setWrapText(true);
        ItemsToFile itemsToFile = new ItemsToFile();
        Menu fileMenu = new Menu("File");
        Menu viewMenu = new Menu("View");
        MenuItem refresh = new MenuItem("Refresh");
        Menu clear = new Menu("Clear");
        MenuItem clearInput = new MenuItem("Clear Input");
        MenuItem clearOutput = new MenuItem("Clear Output");
        clear.getItems().addAll(clearInput, clearOutput);
        viewMenu.getItems().addAll(refresh,clear);
        MenuItem open = new MenuItem("Open");
        MenuItem save = new MenuItem("Save");
        fileMenu.getItems().addAll(open, save);
        MenuBar menuBar = new MenuBar(fileMenu,viewMenu);
        BorderPane pane = new BorderPane();
        Button add = new Button("Add");
        add.setOnAction(e -> {
            outputTextArea.setText("Items to be added:\n");
            String s = textArea.getText().toUpperCase();
            ArrayList<ArrayList<Object>> arrayList = textAreaParse(s);
            ArrayList<String> alphabetical = new ArrayList<>();
            for (int i = 0; i < arrayList.size(); i++) {
                PrimeItem primeItem = new PrimeItem(arrayList.get(i).get(0), arrayList.get(i).get(1), arrayList.get(i).get(2));
                items.remove(primeItem.getName());
                items.put(primeItem.getName(),primeItem);
                alphabetical.add(primeItem.toString());
            }
            Collections.sort(alphabetical);
            for (int i = 0; i < alphabetical.size(); i++) {
                outputTextArea.appendText(alphabetical.get(i) + "\n");
            }
        });
        clearInput.setOnAction(e -> {
            textArea.setText("");
        });
        clearOutput.setOnAction(e -> {
            outputTextArea.setText("");
        });
        open.setOnAction(e -> {
            items = itemsToFile.read();
            outputTextArea.clear();
            ArrayList<String> alphabetical = new ArrayList<>();
            for (PrimeItem value : items.values()) {
                alphabetical.add(value.toString());
            }
            Collections.sort(alphabetical);
            for (int i = 0; i < alphabetical.size(); i++) {
                outputTextArea.appendText(alphabetical.get(i) + "\n");
            }
        });
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
        refresh.setOnAction(e -> {
            outputTextArea.clear();
            ArrayList<String> alphabetical = new ArrayList<>();
            for (PrimeItem value : items.values()) {
                alphabetical.add(value.toString());
            }
            Collections.sort(alphabetical);
            for (int i = 0; i < alphabetical.size(); i++) {
                outputTextArea.appendText(alphabetical.get(i) + "\n");
            }
        });
        HBox hbox1 = new HBox(add);
        hbox1.setAlignment(Pos.BOTTOM_RIGHT);
        HBox hbox3 = new HBox(searchbar,search,remove);
        VBox rVbox = new VBox(hbox3,outputTextArea);
        VBox lVbox = new VBox(textArea,hbox1);
        hbox1.setSpacing(5);
        hbox3.setSpacing(5);
        pane.setLeft(lVbox);
        pane.setRight(rVbox);
        pane.setTop(menuBar);
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
        ArrayList<String> alphabetical = new ArrayList<>();
        for (PrimeItem value : items.values()) {
            if (value.getName().contains(s)) {
                alphabetical.add(value.toString());
            }
        }
        Collections.sort(alphabetical);
        for (int i = 0; i < alphabetical.size(); i++) {
            outputTextArea.appendText(alphabetical.get(i) + "\n");
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
