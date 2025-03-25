package societedestin.cafemanagement2.restImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import societedestin.cafemanagement2.pojo.ToDoList;
import societedestin.cafemanagement2.rest.ToDoListRest;
import societedestin.cafemanagement2.service.ToDoListService;

import java.util.List;

@RestController
@RequestMapping("/api/todolist")
public class ToDoListRestImpl implements ToDoListRest {

    @Autowired
    private ToDoListService toDoListService;

    @Override
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ToDoList>> getTasksForUser(@PathVariable(name = "userId") Integer userId) {
        return ResponseEntity.ok(toDoListService.getAllTasksForUser(userId));
    }

    @Override
    @GetMapping("/{taskId}")
    public ResponseEntity<ToDoList> getTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(toDoListService.getTaskById(taskId));
    }

    @Override
    @PostMapping("/user/{userEmail}")
    public ResponseEntity<ToDoList> createTask(@RequestBody ToDoList task, @PathVariable(name = "userEmail") String userEmail) {
        return ResponseEntity.ok(toDoListService.createTask(task, userEmail));
    }

    @Override
    @PutMapping("/{taskId}")
    public ResponseEntity<ToDoList> updateTask(@PathVariable Long taskId, @RequestBody ToDoList updatedTask) {
        return ResponseEntity.ok(toDoListService.updateTask(taskId, updatedTask));
    }

    @Override
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable(name = "taskId") Long taskId) {
      toDoListService.deleteTask(taskId);
      return ResponseEntity.ok()
                .header("Message", "Tâche supprimée avec succès") // Ajouter un message dans l'en-tête
                .build();
    }


}
