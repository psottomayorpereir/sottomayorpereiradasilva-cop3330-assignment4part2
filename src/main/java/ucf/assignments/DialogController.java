/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Pedro Henrique Sotto-Mayor Pereira da Silva
 */

package ucf.assignments;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class DialogController {

    @FXML private TextField titleField;
    @FXML private TextArea descriptionArea;
    @FXML private DatePicker dueDatePicker;
    @FXML private Label descriptionLabel;

    public TodoItem processResults() {
        //get the new item information
        descriptionLabel.setText("");
        String title = titleField.getText().trim();
        if(title==null){
            title="Default Title";
        }
        String description = descriptionArea.getText().trim();
        //make sure the length of the description is within the boundaries
        if(description.length()>256 || description.length()<1){
            description="A default description was set because you have not entered a description between 1 and 256 characters.";
        }
        LocalDate dueDate = dueDatePicker.getValue();
        if(dueDate==null){
            dueDate=LocalDate.now();
        }
        String itemStatus= "Incomplete";
        //instantiate the item object and return it with the item information
        TodoItem newItem = new TodoItem(title, itemStatus, description, dueDate);
        TodoListOfItems.getInstance().addTodoItem(newItem);
        return newItem;
    }
}