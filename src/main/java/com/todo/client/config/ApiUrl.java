package com.todo.client.config;

public class ApiUrl {
    //    Todo items Api urls
    public static final String selectAllToDo = "http://localhost:8080/ToDoWebService_war/items";
    public static final String selectToDoById = "http://localhost:8080/ToDoWebService_war/items/";
    public static final String selectToDoByTitle = "http://localhost:8080/ToDoWebService_war/items/title/";
    public static final String createTodo = "http://localhost:8080/ToDoWebService_war/items";
    public static final String updateToDo = "http://localhost:8080/ToDoWebService_war/items/";
    public static final String selectToDoByDate = "http://localhost:8080/ToDoWebService_war/items/date?mode=";

    public static final String deleteToDoById = "http://localhost:8080/ToDoWebService_war/items/";
    public static final String deleteToDoByTitle = "http://localhost:8080/ToDoWebService_war/items/title/";
    public static final String selectToDoByPriority = "http://localhost:8080/ToDoWebService_war/items/priority/";

    // Category Api urls
    public static final String selectAllCategories = "http://localhost:8080/ToDoWebService_war/categories";
    public static final String selectCategoryById = "http://localhost:8080/ToDoWebService_war/categories/";
    public static final String selectCategoryByName = "http://localhost:8080/ToDoWebService_war/categories/name?name=";
    public static final String createCategory = "http://localhost:8080/ToDoWebService_war/categories";
    public static final String deleteCategoryById = "http://localhost:8080/ToDoWebService_war/categories/";
    public static final String updateCategoryById = "http://localhost:8080/ToDoWebService_war/categories/";

    // Priority Api urls
    public static final String selectAllPriorities = "http://localhost:8080/ToDoWebService_war/priorities";
    public static final String selectPriorityById = "http://localhost:8080/ToDoWebService_war/priorities/";
    public static final String selectPriorityByName = "http://localhost:8080/ToDoWebService_war/priorities/name?name=";
    public static final String createPriority = "http://localhost:8080/ToDoWebService_war/priorities";
    public static final String deletePriorityById = "http://localhost:8080/ToDoWebService_war/priorities/";
    public static final String updatePriorityById = "http://localhost:8080/ToDoWebService_war/priorities/";

}
