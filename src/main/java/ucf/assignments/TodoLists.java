/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Pedro Henrique Sotto-Mayor Pereira da Silva
 */

package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class TodoLists {

    //private fields
    private ObservableList<TodoList> lists;

    public TodoLists(){
        //constructor
        this.lists= FXCollections.observableArrayList();
    }

    public ObservableList<TodoList> getLists() {
        //get List
        return lists;
    }

    public void addList(TodoList list) {
        //add List
        this.lists.add(list);
    }

    public void removeList(TodoList list){
        this.lists.remove(list);
    }

}