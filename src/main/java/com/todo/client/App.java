package com.todo.client;

import com.todo.client.entity.Category;
import com.todo.client.entity.Priority;
import com.todo.client.entity.ToDo;
import com.todo.client.service.ToDoService;

import java.util.Date;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        ToDoService toDoService = new ToDoService();
//        List<ToDo> toDoList = toDoService.selectAll();
//        for (ToDo todo : toDoList
//        ) {
//            System.out.println(todo.toString());
//        }
//
//        ToDo toDo = toDoService.selectById(3);
//        if (toDo != null)
//            System.out.println(toDo.toString());
//        else
//            System.out.println("No to found");

//
//        ToDo toDo = toDoService.selectByTitle("xx");
//        if (toDo != null)
//            System.out.println(toDo.toString());
//        else
//            System.out.println("No to found");

        ToDo toDo = new ToDo();
        toDo.setCategory(new Category(1,null));
        toDo.setDescription("testing client app");
        toDo.setFavourite(false);
        toDo.setPriority(new Priority(1,null));
        toDo.setTitle("client app test");
        toDo.setEndDate(new Date());
        toDo.setStartDate(new Date());
        ToDo result = toDoService.create(toDo);
        if (result != null)
            System.out.println(result.toString());
        else
            System.out.println("Faild to create new Tod");
    }
}
