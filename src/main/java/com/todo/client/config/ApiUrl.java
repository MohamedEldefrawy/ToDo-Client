package com.todo.client.config;

public class ApiUrl {
    public static final String selectAllToDo = "http://localhost:8080/ToDoWebService_war/items";
    public static final String selectToDoById = "http://localhost:8080/ToDoWebService_war/items/";
    public static final String selectToDoByTitle = "http://localhost:8080/ToDoWebService_war/items/title/";

    public static final String createTodo = "http://localhost:8080/ToDoWebService_war/items";

    public static final String updateToDo = "http://localhost:8080/ToDoWebService_war/items/";
}
