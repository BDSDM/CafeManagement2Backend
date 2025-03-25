package societedestin.cafemanagement2.serviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import societedestin.cafemanagement2.dao.ToDoListDao;
import societedestin.cafemanagement2.dao.UserRepository;
import societedestin.cafemanagement2.pojo.ToDoList;
import societedestin.cafemanagement2.pojo.User;
import societedestin.cafemanagement2.service.ToDoListService;

import java.util.List;


@Service
public class ToDoListServiceImpl implements ToDoListService {

    @Autowired
    private ToDoListDao toDoListDao;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<ToDoList> getAllTasksForUser(Integer userId) {
        return toDoListDao.findByUserId(userId);
    }

    @Override
    public ToDoList getTaskById(Long taskId) {
        return toDoListDao.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @Override
    public ToDoList createTask(ToDoList task, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
        task.setUser(user);
        return toDoListDao.save(task);
    }

    @Override
    public ToDoList updateTask(Long taskId, ToDoList updatedTask) {
        ToDoList existingTask = getTaskById(taskId);
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCompleted(updatedTask.isCompleted());
        return toDoListDao.save(existingTask);
    }

    @Override
    public void deleteTask(Long taskId) {
        toDoListDao.deleteById(taskId);
    }
}
