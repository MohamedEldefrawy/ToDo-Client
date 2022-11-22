package com.todo.client;

import com.todo.client.config.ApplicationContextConfig;
import com.todo.client.entity.Criteria;
import com.todo.client.entity.ToDo;
import com.todo.client.service.ToDoService;
import com.todo.client.utils.MenuPrinter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
        ToDoService toDoService = annotationConfigApplicationContext.getBean(ToDoService.class);
        MenuPrinter printer = annotationConfigApplicationContext.getBean(MenuPrinter.class);

        while (true) {
            Integer result = printer.printMainMenu();
            switch (result) {
                case 1:
                    ToDo todo = printer.createToDoMenu();
                    toDoService.create(todo);
                    printer.resetMenu();
                    break;
                case 2: {
                    ToDo toDo = printer.createToDoMenu();
                    ToDo selectedTodo = toDoService.selectByTitle(toDo.getTitle());
                    toDoService.update(selectedTodo.getId(), toDo);
                    break;
                }
                case 3:
                    String deletedToDoTitle = printer.printFindByMenu(Criteria.TITLE.name());
                    toDoService.deleteByTitle(deletedToDoTitle);
                    printer.resetMenu();
                    break;
                case 4:
                    printer.printResults(toDoService.selectAll());
                    break;
                case 5:
//                    printer.printResults(toDoService.selectTopFiveNearestByStartDate());
                    break;
                case 6: {
                    String selectedTitle = printer.printFindByMenu(Criteria.TITLE.name());
                    ToDo selectedTodo = toDoService.selectByTitle(selectedTitle);
                    if (selectedTodo != null) {
                        printer.printResults(selectedTodo);
                        printer.resetMenu();
                    } else {
                        System.out.println("No todo found with title: " + selectedTitle);
                        printer.resetMenu();
                    }
                    break;
                }
                case 7:
                    String selectedStartDate = printer.printFindByMenu(Criteria.START_DATE.name());
                    List<ToDo> selectedToDos = toDoService.selectByDate(0, selectedStartDate);
                    printer.printResults(selectedToDos);
                    printer.resetMenu();
                    break;
                case 8:
                    String selectedDate = printer.printFindByMenu(Criteria.START_DATE.name());
                    List<ToDo> toDos = toDoService.selectByDate(1, selectedDate);
                    printer.printResults(toDos);
                    printer.resetMenu();
                    break;
                case 9:
                    String selectedPriority = printer.printFindByMenu(Criteria.PRIORITY.name());
                    List<ToDo> priorityToDos = toDoService.selectByPriority(selectedPriority.toUpperCase());
                    if (priorityToDos != null) {
                        printer.printResults(priorityToDos);
                        printer.resetMenu();
                    }
                    printer.resetMenu();
                    break;
                case 10: {
//                    String[] updatedValue = printer.updateCategory();
//                    if (updatedValue != null) {
//                        String updateToDoTitle = updatedValue[0];
//                        String updateToDoCategory = updatedValue[1];
//                        boolean isAdded = toDoService.addItemToCategory(updateToDoTitle, updateToDoCategory);
//                        if (isAdded)
//                            System.out.println("==========category was updated===============");
//                        else
//                            System.out.println("==========category wasn't updated===============");

                    break;
                }
// add to favourite
                case 11:
//                String updateToDoTitle = printer.printFindByMenu(Criteria.TITLE.name());
//                    boolean isAdded = toDoService.addItemToFavourite(updateToDoTitle);
//                    if (isAdded)
//                        System.out.println("Item has been added successfully.");
//                    System.out.println("no to found with title: " + updateToDoTitle);
                    break;
// Exit
                case 12:
                    System.exit(0);
            }

        }

    }
}
