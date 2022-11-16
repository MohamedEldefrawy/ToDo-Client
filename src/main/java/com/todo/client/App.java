package com.todo.client;

import com.todo.client.entity.ToDo;
import com.todo.client.service.ToDoService;

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

        ToDo toDo = toDoService.selectById(3);
        if (toDo != null)
            System.out.println(toDo.toString());
        else
            System.out.println("No to found");
    }
}
