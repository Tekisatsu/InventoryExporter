package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class InventoryApp extends Application {
    TextArea textArea = new TextArea();
    TextArea outputTextArea = new TextArea();
    ArrayList<ArrayList<Object>> items = new ArrayList<>();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        outputTextArea.setEditable(false);
        BorderPane pane = new BorderPane();
        Button add = new Button("Add");
        add.setOnAction(e -> {
            String s = textArea.getText();
            textAreaParse(s);
            for (int i = 0; i < items.size(); i++) {
                PrimeItem primeItem = new PrimeItem(items.get(i).get(0),items.get(i).get(1),items.get(i).get(2));
                outputTextArea.appendText(primeItem.toString()+"\n");
            }
        });
        pane.setCenter(textArea);
        pane.setBottom(add);
        pane.setRight(outputTextArea);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("WF Inventory Exporter");
        primaryStage.show();
    }
    public void textAreaParse(String s) {
        String[] t = s.split("\n");
        for (int i = 0; i < t.length; i++) {
            if (t[i].length() == 0) continue;
            if (t[i].equals("")) {
                continue;
            }
            items.add(PrimeItem.parseItem(t[i]));
        }
    }
}
