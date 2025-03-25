package societedestin.cafemanagement2.rest;


import org.springframework.http.ResponseEntity;
import societedestin.cafemanagement2.pojo.ToDoList;

import java.util.List;


public interface ToDoListRest {
    ResponseEntity<List<ToDoList>> getTasksForUser(Integer userId);
    ResponseEntity<ToDoList> getTask(Long taskId);
    ResponseEntity<ToDoList> createTask(ToDoList task, String userEmail);
    ResponseEntity<ToDoList> updateTask(Long taskId, ToDoList updatedTask);
    ResponseEntity<Void> deleteTask(Long taskId);
}
