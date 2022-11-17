package com.todo.client;

import com.todo.client.entity.Criteria;
import com.todo.client.entity.ToDo;
import com.todo.client.service.ToDoService;
import com.todo.client.utils.MenuPrinter;

import java.util.List;

/**
 * Hello world!
 */
public class App {
    private static final ToDoService toDoService = new ToDoService();

    public static void main(String[] args) {
        ToDoService toDoService = new ToDoService();
        while (true) {
            Integer result = MenuPrinter.printMainMenu();
            switch (result) {
                case 1:
                    ToDo todo = MenuPrinter.createToDoMenu();
                    toDoService.create(todo);
                    MenuPrinter.resetMenu();
                    break;
                case 2: {
                    ToDo toDo = MenuPrinter.createToDoMenu();
                    ToDo selectedTodo = toDoService.selectByTitle(toDo.getTitle());
                    toDoService.update(selectedTodo.getId(), toDo);
                    break;
                }
                case 3:
                    String deletedToDoTitle = MenuPrinter.printFindByMenu(Criteria.TITLE.name());
                    toDoService.deleteByTitle(deletedToDoTitle);
                    MenuPrinter.resetMenu();
                    break;
                case 4:
                    MenuPrinter.printResults(toDoService.selectAll());
                    break;
                case 5:
//                    MenuPrinter.printResults(toDoService.selectTopFiveNearestByStartDate());
                    break;
                case 6: {
                    String selectedTitle = MenuPrinter.printFindByMenu(Criteria.TITLE.name());
                    ToDo selectedTodo = toDoService.selectByTitle(selectedTitle);
                    if (selectedTodo != null) {
                        MenuPrinter.printResults(selectedTodo);
                        MenuPrinter.resetMenu();
                    } else {
                        System.out.println("No todo found with title: " + selectedTitle);
                        MenuPrinter.resetMenu();
                    }
                    break;
                }
                case 7:
                    String selectedStartDate = MenuPrinter.printFindByMenu(Criteria.START_DATE.name());
                    List<ToDo> selectedToDos = toDoService.selectByDate(0, selectedStartDate);
                    MenuPrinter.printResults(selectedToDos);
                    MenuPrinter.resetMenu();
                    break;
                case 8:
                    String selectedDate = MenuPrinter.printFindByMenu(Criteria.START_DATE.name());
                    List<ToDo> toDos = toDoService.selectByDate(1, selectedDate);
                    MenuPrinter.printResults(toDos);
                    MenuPrinter.resetMenu();
                    break;
                case 9:
                    String selectedPriority = MenuPrinter.printFindByMenu(Criteria.PRIORITY.name());
                    List<ToDo> priorityToDos = toDoService.selectByPriority(selectedPriority.toUpperCase());
                    if (priorityToDos != null) {
                        MenuPrinter.printResults(priorityToDos);
                        MenuPrinter.resetMenu();
                    }
                    MenuPrinter.resetMenu();
                    break;
                case 10: {
//                    String[] updatedValue = MenuPrinter.updateCategory();
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
//                String updateToDoTitle = MenuPrinter.printFindByMenu(Criteria.TITLE.name());
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
