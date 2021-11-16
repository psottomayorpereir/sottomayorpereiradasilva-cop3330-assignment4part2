/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Pedro Henrique Sotto-Mayor Pereira da Silva
 */

package ucf.assignments;

import java.time.LocalDate;


public class TodoItem {

    //private fields
    private String title;
    private String status;
    private String description;
    private LocalDate dueDate;

    public TodoItem(String title, String status, String description, LocalDate dueDate) {
        //constructor
        this.title = title;
        this.status = status;
        this.description = description;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        //get item title
        return title;
    }

    public void setTitle(String title) {
        //set item title
        this.title = title;
    }

    public String getStatus() {
        //get item status
        return status;
    }

    public void setStatus(String status) {
        //set item status
        this.status = status;
    }

    public String getDescription() {
        //get item description
        return description;
    }

    public void setDescription(String description) {
        //set item description
        this.description = description;
    }

    public LocalDate getDueDate() {
        //get due date
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        //set due date
        this.dueDate = dueDate;
    }
}