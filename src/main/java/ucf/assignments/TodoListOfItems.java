/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Pedro Henrique Sotto-Mayor Pereira da Silva
 */

package ucf.assignments;

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

public class TodoListOfItems {
    private static TodoListOfItems instance = new TodoListOfItems();
    private static String filename = "ListOfItems.txt";

    private ObservableList<TodoItem> todoItems;
    private DateTimeFormatter formatter;

    public static TodoListOfItems getInstance() {
        //instance of the class
        return instance;
    }

    private TodoListOfItems() {
        //date formatter
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public ObservableList<TodoItem> getTodoItems() {
        //get all items
        return todoItems;
    }

    public void addTodoItem(TodoItem item) {
        //add item to the list
        todoItems.add(item);
    }

    public void loadTodoItems() throws IOException {
        //load items from an external storage
        todoItems = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try {
            while ((input = br.readLine()) != null) {
                String[] itemPieces = input.split("\t");

                String title = itemPieces[0];
                String status = itemPieces[1];
                String description = itemPieces[2];
                String dateString = itemPieces[3];

                LocalDate date = LocalDate.parse(dateString, formatter);
                TodoItem todoItem = new TodoItem(title, status, description, date);
                todoItems.add(todoItem);
            }

        } finally {
            if(br != null) {
                br.close();
            }
        }
    }

    public void storeTodoItems() throws IOException {
        //store items to an external storage
        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);
        try {
            Iterator<TodoItem> iter = todoItems.iterator();
            while(iter.hasNext()) {
                TodoItem item = iter.next();
                bw.write(String.format("%s\t%s\t%s\t%s",
                        item.getTitle(),
                        item.getStatus(),
                        item.getDescription(),
                        item.getDueDate().format(formatter)));
                bw.newLine();
            }

        } finally {
            if(bw != null) {
                bw.close();
            }
        }
    }

    public void deleteTodoItem(TodoItem item) {
        //delete item
        todoItems.remove(item);
    }

}