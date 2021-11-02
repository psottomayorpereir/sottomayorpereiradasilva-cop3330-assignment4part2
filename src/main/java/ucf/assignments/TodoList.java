/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Pedro Henrique Sotto-Mayor Pereira da Silva
 */

package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class TodoList {
    //private fields
    private static TodoList instance = new TodoList();
    private SimpleStringProperty name;
    private ObservableList<Item> items;
    private DateTimeFormatter formatter;
    private static String filename = "TodoListItems.txt";

    public TodoList(String name, ObservableList<Item> items, DateTimeFormatter formatter){
        //constructor
        this.name = new SimpleStringProperty(name);
        this.items=FXCollections.observableArrayList();
        this.formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public static TodoList getInstance() {
        //return instance of the class
        return instance;
    }

    private TodoList(){
        //format due date
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public ObservableList<Item> getItems() {
        //return the list of items
        return items;
    }

    public void addItem(Item item) {
        //add item
        this.items.add(item);
    }

    public void loadItems() throws IOException {
        //load items from an external storage
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try {
            while ((input = br.readLine()) != null) {
                String[] itemPieces = input.split("\t");

                SimpleStringProperty description = new SimpleStringProperty(itemPieces[0]);
                SimpleStringProperty status = new SimpleStringProperty(itemPieces[1]);
                SimpleStringProperty dateString = new SimpleStringProperty(itemPieces[2]);

                LocalDate dueDate = LocalDate.parse(dateString.toString(), formatter);
                Item todoItem = new Item(name.toString(), description.toString(), status.toString(), dueDate);
                items.add(todoItem);
            }

        } finally {
            if(br != null) {
                br.close();
            }
        }
    }

    public void storeItems() throws IOException {
        //store items from a list to an external storage
        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);
        try {
            Iterator<Item> iter = items.iterator();
            while(iter.hasNext()) {
                Item item = iter.next();
                bw.write(String.format("%s\t%s\t%s",
                        item.getDescription(),
                        item.getStatus(),
                        item.getDueDate().format(formatter)));
                bw.newLine();
            }

        } finally {
            if(bw != null) {
                bw.close();
            }
        }
    }

    public String getName() {
        //get product name
        return name.get();
    }

    public void setName(String name) {
        //set product name
        this.name = new SimpleStringProperty(name);
    }

}