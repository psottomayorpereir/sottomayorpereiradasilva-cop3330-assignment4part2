/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Pedro Henrique Sotto-Mayor Pereira da Silva
 */

package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TableViewController implements Initializable{

    //
    @FXML private ListView<Item> todoListView;

    //
    ArrayList<TodoList> lists= new ArrayList<TodoList>();

    //configure the table
    @FXML private TableView<TodoList> tableView;
    @FXML private TableColumn<TodoList, String> listNamesColumn;

    //method to get data from last scene
    public void getDataHomePage(ArrayList<TodoList> lists){
        this.lists=lists;
    }

    //Method to allow the user to double click on a cell of the TableView and update the name of the Todo List
    public void changeTodoListNameCellEvent(TableColumn.CellEditEvent edittedCell){
        TodoList listSelected = tableView.getSelectionModel().getSelectedItem();
        listSelected.setName(edittedCell.getNewValue().toString());
    }

    //Method to change the scene to Homepage
    public void changeScreenButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ucf.assignments/homepage.fxml"));
        Parent tableViewParent= loader.load();
        Scene tableViewScene = new Scene(tableViewParent);
        //Get the Stage information
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        //Set Scene and show
        window.setScene(tableViewScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set the column of the table
        listNamesColumn.setCellValueFactory(new PropertyValueFactory<TodoList, String>("name"));

        //load dummy data
        tableView.setItems(getLists());

        //for (TodoList nam : lists){
        //    String list_name=nam.getName();
        //    todoListView.cellFactoryProperty(list_name);
        //}

        //Update the table to allow the user to edit the name of a Todo List
        tableView.setEditable(true);
        listNamesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    //Method to return an ObservableList of Lists object
    public ObservableList<TodoList> getLists(){
        ObservableList<TodoList> listNames = FXCollections.observableArrayList();
        for (TodoList nam : lists){
            listNames.add(nam);
        }
        return listNames;
    }
}