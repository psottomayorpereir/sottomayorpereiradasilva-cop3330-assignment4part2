/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Pedro Henrique Sotto-Mayor Pereira da Silva
 */

package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Item {
    //private fields
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private SimpleStringProperty status;
    private LocalDate dueDate;

    public Item(String name, String description, String status, LocalDate dueDate){
        //constructor
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.status = new SimpleStringProperty(status);
        this.dueDate=dueDate;
    }

    public String getName() {
        //get product name
        return name.get();
    }

    public void setName(String name) {
        //set product name
        this.name = new SimpleStringProperty(name);
    }

    public String getDescription() {
        //get product name
        return description.get();
    }

    public void setDescription(String description) {
        //set product name
        this.description = new SimpleStringProperty(description);
    }

    public String getStatus() {
        //get product name
        return status.get();
    }

    public void setStatus(String status) {
        //set product name
        this.status = new SimpleStringProperty(status);
    }

    public LocalDate getDueDate() {
        //get product name
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        //set product name
        this.dueDate = dueDate;
    }

}