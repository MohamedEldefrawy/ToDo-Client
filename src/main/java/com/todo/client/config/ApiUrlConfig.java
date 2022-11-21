package com.todo.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:urls.properties")
public class ApiUrlConfig {
    //    Todo items Api urls
    @Value("${todo.selectAll}")
    public String selectAllToDo;
    @Value("${todo.selectById}")
    public String selectToDoById;
    @Value("${todo.selectByTitle}")
    public String selectToDoByTitle;
    @Value("${todo.create}")
    public String createTodo;
    @Value("${todo.update}")
    public String updateToDo;
    @Value("${todo.selectByDate}")
    public String selectToDoByDate;

    @Value("${todo.deleteById}")
    public String deleteToDoById;
    @Value("${todo.deleteByTitle}")
    public String deleteToDoByTitle;

    @Value("${todo.selectByPriority}")
    public String selectToDoByPriority;

    @Value("${category.selectAll}")
    public String selectAllCategories;
    @Value("${category.selectById}")
    public String selectCategoryById;
    @Value("${category.selectByName}")
    public String selectCategoryByName;
    @Value("${category.create}")
    public String createCategory;
    @Value("${category.deleteById}")
    public String deleteCategoryById;
    @Value("${category.updateById}")
    public String updateCategoryById;

    @Value("${priority.selectAll}")
    public String selectAllPriorities;
    @Value("${priority.selectById}")
    public String selectPriorityById;
    @Value("${priority.selectByName}")
    public String selectPriorityByName;
    @Value("${priority.create}")
    public String createPriority;
    @Value("${priority.deleteById}")
    public String deletePriorityById;
    @Value("${priority.updateById}")
    public String updatePriorityById;

}
