/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Pedro Henrique Sotto-Mayor Pereira da Silva
 */

package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class homepageController implements Initializable{

    //variables that will be used for adding a TodoList and an item to it
    private List<Item> todoItems;
    private List<TodoList> todoLists;
    TodoLists listOfTodoList = new TodoLists();
    //Create a TodoList variable to store a new TodoList
    TodoList list = new TodoList("",FXCollections.observableArrayList(),DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    Item item = new Item("","","",LocalDate.of(2021,11,10));
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    TodoList selected = new TodoList("",FXCollections.observableArrayList(),DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    //variables for 'Add a Todo List'
    @FXML private Label addListLabel;

    //variables for 'Add an item to a Todo List'
    @FXML private Label addItemTitleLabel;
    @FXML private Label addItemDescriptionLabel;
    @FXML private Label addItemDueDateLabel;
    @FXML private ChoiceBox addItemStatus;
    @FXML private Label addItemStatusLabel;
    @FXML private Label addItemToListLabel;
    @FXML private TextField listNameTxtField;
    @FXML private TextField itemNameTxtField;
    @FXML private TextField descriptionTxtField;
    @FXML private DatePicker dp;

    //variables for delete list
    @FXML private Label deleteListLabel;

    ///////////////////////////////////////////////
    //configure the Todo List table
    @FXML private TableView<TodoList> tableView;
    @FXML private TableColumn<TodoList, String> listNamesColumn;
    @FXML private ListView<Item> todoListView;
    ObservableList<TodoList> obsList = FXCollections.observableArrayList();
    ///////////////////////////////////////////////
    //configure the Item table
    @FXML private TableView<Item> tableViewItem;
    @FXML private TableColumn<Item, String> listNamesColumnItemTitle;
    @FXML private TableColumn<Item, String> listNamesColumnItemDescription;
    @FXML private TableColumn<Item, String> listNamesColumnItemStatus;
    @FXML private TableColumn<Item, LocalDate> listNamesColumnItemDueDate;
    ObservableList<Item> obsListItem = FXCollections.observableArrayList();


    public void addListButtonClick(){
        //method to 'Add a Todo List'
        //Set the name of the list
        list.setName(listNameTxtField.getText());
        //add the list to the obsList
        obsList.add(list);
        //print the label
        this.addListLabel.setText(listNameTxtField.getText() + " Todo List created");
    }

    public void deleteListButtonClick(){
        //method to allow the user to delete a Todo List
        selected = tableView.getSelectionModel().getSelectedItem();
        if(selected==null){
            //no list selected to delete
            deleteListLabel.setText("No list selected to delete");
        }
        else{
            //list selected to delete
            int index = obsList.indexOf(selected);
            obsList.remove(index);
            listOfTodoList.removeList(selected);
            deleteListLabel.setText("Todo List deleted");
        }
    }

    //Method for 'Add an item to a Todo List'
    public void addItemButtonClick(){
        //method to allow the user to add an item to a Todo List
        if(obsList.isEmpty() || tableView.getSelectionModel().getSelectedItem()==null){
            //there is no list to add Item to
            this.addItemTitleLabel.setText("");
            this.addItemDescriptionLabel.setText("");
            this.addItemStatusLabel.setText("");
            this.addItemToListLabel.setText("Select a list do add the item to");

        }
        else{
            //initialize variables
            String itemName="";
            String itemDescription="";
            String itemDueDate="";
            LocalDate dueDate = LocalDate.of(2021,11,10);
            String itemStatus="";
            String selectedName="";
            //Create a String variable to store the item name and assign the text entered by the user to it
            itemName=itemNameTxtField.getText();
            //print the label
            this.addItemTitleLabel.setText(itemNameTxtField.getText() + " title set");
            //Create a String variable to store the item name and assign the text entered by the user to it
            itemDescription=descriptionTxtField.getText();
            //print the label
            this.addItemDescriptionLabel.setText(descriptionTxtField.getText() + " description set");
            if(dp.getValue().toString()!=null){
                //Create a String to store the due date of the item
                itemDueDate= (dp.getValue().toString());
                //print the label
                this.addItemDueDateLabel.setText(itemDueDate + " due date set");
                //format the dueDate
                dueDate = LocalDate.parse(itemDueDate, formatter);
            }
            else{
                dueDate = LocalDate.of(2021,11,10);
                //print the label
                this.addItemDueDateLabel.setText("2021-11-10 default due date set");;
            }
            //Create a String to store the status of the item
            itemStatus= (addItemStatus.getValue().toString());
            this.addItemStatusLabel.setText(itemStatus + " status set");
            //create Item by calling the method of the Item class
            item.setName(itemName);
            item.setDescription(itemDescription);
            item.setStatus(itemStatus);
            item.setDueDate(dueDate);
            //Add item to list
            list.addItem(item);
            //Add list to obsList
            listOfTodoList.addList(list);
            //get selected TodoList to add the item to
            selected = tableView.getSelectionModel().getSelectedItem();
            if(selected==null){
                //no selection was made
                //do not add the item
                this.addItemTitleLabel.setText("Select a list do add the item to");
            }
            else{
                //selection was made
                //add the item to the selected TodoList
                selected.addItem(item);
                //get the name of the list
                selectedName = selected.getName();
                //print label
                this.addItemToListLabel.setText("Item added to list " + selectedName);
            }
        }
    }

    public void deleteItemButtonClick(TableColumn.CellEditEvent edittedCell){
        //Method to allow the user to delete an item of a Todo List in the TableView
    }

    public void changeTodoListNameCellEvent(TableColumn.CellEditEvent edittedCell){
        //Method to allow the user to double click on a cell of the TableView and update the name of the Todo List
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            TodoList listSelected = tableView.getSelectionModel().getSelectedItem();
            listSelected.setName(edittedCell.getNewValue().toString());
        }
    }

    public void changeItemNameCellEvent(TableColumn.CellEditEvent edittedCell){
        //Method to allow the user to double click on a cell of the TableView and update the title of an item
    }

    public void changeItemDescriptionCellEvent(TableColumn.CellEditEvent edittedCell){
        //Method to allow the user to double click on a cell of the TableView and update description
    }

    public void changeItemDueDateCellEvent(TableColumn.CellEditEvent edittedCell){
        //Method to allow the user to double click on a cell of the TableView and update dueDate
    }

    public void changeItemStatusCellEvent(TableColumn.CellEditEvent edittedCell){
        //Method to allow the user to double click on a cell of the TableView and update the status of the item
    }

    public void displayAllItemsInTodoList(TableColumn.CellEditEvent edittedCell){
        //Method to allow the user to see all items in a Todo List on the TableView
    }

    public void displayCompleteItemsInTodoList(TableColumn.CellEditEvent edittedCell){
        //Method to allow the user to see all complete items in a Todo List on the TableView
    }

    public void displayIncompleteItemsInTodoList(TableColumn.CellEditEvent edittedCell){
        //Method to allow the user to see all incomplete items in a Todo List on the TableView
    }

    public void saveToExternalButtonClick(){
        //Method to allow the user to save a Todo List to external storage
    }

    public void saveMultipleToExternalButtonClick(){
        //Method to allow the user to save multiple Todo Lists to external storage
    }

    public void loadFromExternalButtonClick(){
        //Method to allow the user to load a Todo List from external storage
    }

    public void loadMultipleFromExternalButtonClick(){
        //Method to allow the user to load multiple Todo Lists from external storage
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //method to initialize table views, labels, etc

        //Delete list label
        deleteListLabel.setText("");

        //Configuring the 'Add a Todo List'
        addListLabel.setText("");

        //Configuring the 'Add item to a list'
        addItemTitleLabel.setText("");
        addItemDescriptionLabel.setText("");
        addItemDueDateLabel.setText("");
        addItemStatus.getItems().add("Complete");
        addItemStatus.getItems().add("Incomplete");
        addItemStatus.setValue("Incomplete");
        addItemStatusLabel.setText("");
        addItemToListLabel.setText("");


        /////////////////////////////////////////////////////
        //configure the Todo Lists table
        //set the column of the table
        listNamesColumn.setCellValueFactory(new PropertyValueFactory<TodoList, String>("name"));

        //load data to 'Todo Lists'
        tableView.setItems(obsList);

        //Update the table to allow the user to edit the name of a Todo List
        tableView.setEditable(true);
        listNamesColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        //configure the Items table
        //set the columns of the table
        listNamesColumnItemTitle.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        listNamesColumnItemDescription.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));
        listNamesColumnItemStatus.setCellValueFactory(new PropertyValueFactory<Item, String>("status"));
        listNamesColumnItemDueDate.setCellValueFactory(new PropertyValueFactory<Item, LocalDate>("dueDate"));

        obsListItem.addAll(selected.getItems());

        //load data to 'Item' table
        tableViewItem.setItems(obsListItem);

        dp.setValue(LocalDate.of(2021,11,10));

        //Update the table to allow the user to edit Item information
        tableViewItem.setEditable(true);
        listNamesColumnItemTitle.setCellFactory(TextFieldTableCell.forTableColumn());
        listNamesColumnItemDescription.setCellFactory(TextFieldTableCell.forTableColumn());
        listNamesColumnItemStatus.setCellFactory(TextFieldTableCell.forTableColumn());
        //listNamesColumnItemDueDate.setCellFactory(TextFieldTableCell.forTableColumn());
    }

}