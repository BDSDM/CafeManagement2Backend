package societedestin.cafemanagement2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import societedestin.cafemanagement2.pojo.ToDoList;

import java.util.List;


@Repository
public interface ToDoListDao extends JpaRepository<ToDoList, Long> {
    List<ToDoList> findByUserId(Integer userId);
}
