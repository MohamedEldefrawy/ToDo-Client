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

    // Category Api urls
    private static final String selectAllCategories = "http://localhost:8080/ToDoWebService_war/categories";
    private static final String selectCategoryById = "http://localhost:8080/ToDoWebService_war/categories/";
    private static final String selectCategoryByName = "http://localhost:8080/ToDoWebService_war/categories/name?name=";
    private static final String createCategory = "http://localhost:8080/ToDoWebService_war/categories";
    private static final String deleteCategoryById = "http://localhost:8080/ToDoWebService_war/categories/";
    private static final String updateCategoryById = "http://localhost:8080/ToDoWebService_war/categories/";

    // Priority Api urls
    private static final String selectAllPriorities = "http://localhost:8080/ToDoWebService_war/priorities";
    private static final String selectPriorityById = "http://localhost:8080/ToDoWebService_war/priorities/";
    private static final String selectPriorityByName = "http://localhost:8080/ToDoWebService_war/priorities/name?name=";
    private static final String createPriority = "http://localhost:8080/ToDoWebService_war/priorities";
    private static final String deletePriorityById = "http://localhost:8080/ToDoWebService_war/priorities/";
    private static final String updatePriorityById = "http://localhost:8080/ToDoWebService_war/priorities/";

}
