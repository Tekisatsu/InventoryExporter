package com.example;

import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.Serializable;
import java.util.ArrayList;

public class InventoryApp extends Application {
    TextArea textArea = new TextArea();
    TextArea outputTextArea = new TextArea();
    ArrayList<PrimeItem> items = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        textArea.setPrefHeight(400);
        textArea.setPrefWidth(250);
        outputTextArea.setPrefHeight(400);
        outputTextArea.setPrefWidth(250);
        outputTextArea.setEditable(false);
        ItemsToFile itemsToFile = new ItemsToFile();
        BorderPane pane = new BorderPane();
        Button add = new Button("Add");
        add.setOnAction(e -> {
            String s = textArea.getText();
            ArrayList<ArrayList<Object>> arrayList = textAreaParse(s);
            for (int i = 0; i < arrayList.size(); i++) {
                PrimeItem primeItem = new PrimeItem(arrayList.get(i).get(0),arrayList.get(i).get(1),arrayList.get(i).get(2));
                items.add(primeItem);
                System.out.println(primeItem.toString());
                outputTextArea.appendText(items.get(i).toString()+"\n");
            }
        });
        Button readFile = new Button("Read File");
        readFile.setOnAction(e -> {
            items = itemsToFile.read();
            outputTextArea.clear();
            for (int i = 0; i < items.size(); i++) {
                outputTextArea.appendText(items.get(i).toString()+"\n");
            }
        });
        Button save = new Button("Save");
        save.setOnAction(e -> {
            itemsToFile.export(items);
        });
        HBox hbox = new HBox(add, readFile,save);
        pane.setCenter(textArea);
        pane.setBottom(hbox);
        pane.setRight(outputTextArea);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("WF Inventory Exporter");
        primaryStage.show();
    }
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
}
