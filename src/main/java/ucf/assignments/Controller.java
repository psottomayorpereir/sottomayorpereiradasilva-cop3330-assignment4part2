/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Pedro Henrique Sotto-Mayor Pereira da Silva
 */

package ucf.assignments;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import java.util.Optional;
import java.util.Comparator;
import java.util.List;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import java.util.function.Predicate;

public class Controller {

    //private fields
    @FXML private ListView<TodoItem> todoListView;
    @FXML private TextArea itemDescriptionTextArea;
    @FXML private Label deadlineLabel;
    @FXML private Label statusLabel;
    @FXML private BorderPane mainBorderPane;
    @FXML private ContextMenu listContextMenu;
    @FXML private ToggleButton filterTodayToggleButton;
    @FXML private ToggleButton filterCompletedToggleButton;
    @FXML private ToggleButton filterIncompleteToggleButton;
    private List<TodoItem> todoItems;
    private FilteredList<TodoItem> todayFilteredList;
    private FilteredList<TodoItem> completedFilteredList;
    private FilteredList<TodoItem> incompleteFilteredList;
    private Predicate<TodoItem> wantCompletedItems;
    private Predicate<TodoItem> wantIncompleteItems;
    private Predicate<TodoItem> wantAllItems;
    private Predicate<TodoItem> wantTodaysItems;


    public void initialize() {
        //initialize method
        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            //delete an item by clicking right-clicking on an item
            @Override
            public void handle(ActionEvent event) {
                TodoItem item = todoListView.getSelectionModel().getSelectedItem();
                deleteItem(item);
            }
        });

        listContextMenu.getItems().addAll(deleteMenuItem);
        todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TodoItem>() {
            @Override
            public void changed(ObservableValue<? extends TodoItem> observable, TodoItem oldValue, TodoItem newValue) {
                //deal with changes on the selected item on the table
                if(newValue != null) {
                    TodoItem item = todoListView.getSelectionModel().getSelectedItem();
                    itemDescriptionTextArea.setText(item.getDescription());
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    deadlineLabel.setText(df.format(item.getDueDate()));
                    statusLabel.setText(item.getStatus());
                }
            }
        });

        //adding a listener to let the user change the description of an item
        itemDescriptionTextArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                //get the new description if requirements are met
                if (newValue != null) {
                    TodoItem item = todoListView.getSelectionModel().getSelectedItem();
                    if(item!=null){
                        if (itemDescriptionTextArea.getText().length() > 256 || itemDescriptionTextArea.getText().length() < 1) {
                            item.setDescription("A default description was set because you have not entered a description between 1 and 256 characters.");
                        } else {
                            item.setDescription(itemDescriptionTextArea.getText());
                        }
                    }
                }
            }
        });

        wantCompletedItems = new Predicate<TodoItem>() {
            //get completed items only
            @Override
            public boolean test(TodoItem todoItem) {
                return (todoItem.getStatus().equals("Completed"));
            }
        };
        //make a filtered list of completed items
        completedFilteredList = new FilteredList<TodoItem>(TodoListOfItems.getInstance().getTodoItems(), wantCompletedItems);

        wantIncompleteItems = new Predicate<TodoItem>() {
            //get incomplete items only
            @Override
            public boolean test(TodoItem todoItem) {
                return (todoItem.getStatus().equals("Incomplete"));
            }
        };
        //make a filtered list of incomplete items
        incompleteFilteredList = new FilteredList<TodoItem>(TodoListOfItems.getInstance().getTodoItems(), wantIncompleteItems);

        wantAllItems = new Predicate<TodoItem>() {
            //get all items
            //also used to restore the original looking table
            @Override
            public boolean test(TodoItem todoItem) {
                return true;
            }
        };

        wantTodaysItems = new Predicate<TodoItem>() {
            //return items that are due today
            @Override
            public boolean test(TodoItem todoItem) {
                return (todoItem.getDueDate().equals(LocalDate.now()));
            }
        };
        //make a filtered list of items due today
        todayFilteredList = new FilteredList<TodoItem>(TodoListOfItems.getInstance().getTodoItems(), wantTodaysItems);

        SortedList<TodoItem> sortedList = new SortedList<TodoItem>(todayFilteredList, new Comparator<TodoItem>() {
            //compare the due date of two items
                    @Override
                    public int compare(TodoItem o1, TodoItem o2) {
                        return o1.getDueDate().compareTo(o2.getDueDate());
                    }
                });

        //sort the items by due date
        todoListView.setItems(sortedList);
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectFirst();

        todoListView.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>() {
            //let the user know if an item due date is past by setting the item title to red, otherwise brown
            @Override
            public ListCell<TodoItem> call(ListView<TodoItem> param) {
                ListCell<TodoItem> cell = new ListCell<TodoItem>() {

                    @Override
                    protected void updateItem(TodoItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty) {
                            setText(null);
                        } else {
                            setText(item.getTitle());
                            if(item.getDueDate().isBefore(LocalDate.now().plusDays(1))) {
                                setTextFill(Color.RED);
                            } else if(item.getDueDate().equals(LocalDate.now().plusDays(1))) {
                                setTextFill(Color.BROWN);
                            }
                        }
                    }
                };
                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty) -> {
                            if(isNowEmpty) {
                                cell.setContextMenu(null);
                            } else {
                                cell.setContextMenu(listContextMenu);
                            }
                        });
                return cell;
            }
        });
    }

    @FXML
    public void handleClickListView() {
        TodoItem item = todoListView.getSelectionModel().getSelectedItem();
        itemDescriptionTextArea.setText(item.getDescription());
        deadlineLabel.setText(item.getDueDate().toString());
    }

    public void deleteItem(TodoItem item) {
        //delete an item by clicking 'DEL' key on the keyboard
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Item");
        alert.setHeaderText("Delete item: " + item.getTitle());
        alert.setContentText("Click confirm or cancel.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && (result.get() == ButtonType.OK)) {
            TodoListOfItems.getInstance().deleteTodoItem(item);
        }
    }

    public void clearAllItems() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete All Items");
        alert.setHeaderText("Delete all items in the list?: ");
        alert.setContentText("Click confirm or cancel.");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && (result.get() == ButtonType.OK)) {
            if(todoListView!=null){
                //todoListView.clear();
            }
        }

    }

    public void setCompleteStatus(){
        //change the status of the selected item to completed
        TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            selectedItem.setStatus("Completed");
            statusLabel.setText("Completed");
        }
    }

    public void setIncompleteStatus(){
        //change the status of the selected item to incomplete
        TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            selectedItem.setStatus("Incomplete");
            statusLabel.setText("Incomplete");
        }
    }

    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {
        //implementing the delete item tool
        TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            if(keyEvent.getCode().equals(KeyCode.DELETE)) {
                deleteItem(selectedItem);
            }
        }
    }

    @FXML
    public void showNewItemDialog() {
        //display a pop-up dialog to add new item
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add New Item");
        dialog.setHeaderText("Create a new item");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/ucf.assignments/addItemDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch(IOException e) {
            System.out.println("Error loading the dialog");
            e.printStackTrace();
            return;
        }
        //adding the button OK and CANCEL
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        //if OK is clicked, go back to the home scene and make the deletion happen
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            DialogController controller = fxmlLoader.getController();
            TodoItem newItem = controller.processResults();
            todoListView.getSelectionModel().select(newItem);
        }
    }

    @FXML
    public void handleTodayFilterButton() {
        //button to see what items are due today
        TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
        if(filterTodayToggleButton.isSelected()) {
            todayFilteredList.setPredicate(wantTodaysItems);
            if(todayFilteredList.isEmpty()) {
                itemDescriptionTextArea.clear();
                statusLabel.setText("");
                deadlineLabel.setText("");
            } else if(todayFilteredList.contains(selectedItem)) {
                todoListView.getSelectionModel().select(selectedItem);
            } else {
                todoListView.getSelectionModel().selectFirst();
            }
        } else {
            todayFilteredList.setPredicate(wantAllItems);
            todoListView.getSelectionModel().select(selectedItem);
        }
    }

    @FXML
    public void handleCompletedFilterButton() {
        //button to see what items are completed
        TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
        if(filterCompletedToggleButton.isSelected()) {
            todayFilteredList.setPredicate(wantCompletedItems);
            if(completedFilteredList.isEmpty()) {
                itemDescriptionTextArea.clear();
                statusLabel.setText("");
                deadlineLabel.setText("");
            } else if(completedFilteredList.contains(selectedItem)) {
                todoListView.getSelectionModel().select(selectedItem);
            } else {
                todoListView.getSelectionModel().selectFirst();
            }
        } else {
            todayFilteredList.setPredicate(wantAllItems);
            todoListView.getSelectionModel().select(selectedItem);
        }
    }

    @FXML
    public void handleIncompleteFilterButton() {
        //button to see what items are incomplete
        TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
        if(filterIncompleteToggleButton.isSelected()) {
            todayFilteredList.setPredicate(wantIncompleteItems);
            if(incompleteFilteredList.isEmpty()) {
                itemDescriptionTextArea.clear();
                statusLabel.setText("");
                deadlineLabel.setText("");
            } else if(incompleteFilteredList.contains(selectedItem)) {
                todoListView.getSelectionModel().select(selectedItem);
            } else {
                todoListView.getSelectionModel().selectFirst();
            }
        } else {
            todayFilteredList.setPredicate(wantAllItems);
            todoListView.getSelectionModel().select(selectedItem);
        }
    }

    @FXML
    public void handleExit() {
        //exit the program button
        Platform.exit();
    }
}
