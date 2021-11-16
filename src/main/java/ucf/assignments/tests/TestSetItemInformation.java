/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Pedro Henrique Sotto-Mayor Pereira da Silva
 */

package ucf.assignments.tests;

import org.junit.jupiter.api.Test;
import ucf.assignments.TodoItem;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TestSetItemInformation {
    @Test
    public void addTitleToItem(){
        //Test setting the title of an item
        TodoItem obj = new TodoItem("title", "Complete", "description", LocalDate.of(2021,11,15));
        assertEquals("title",obj.getTitle());
    }
}
