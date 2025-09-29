package com.ty.todo.repository;

import com.ty.todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserUsernameOrderByTargetTimeAsc(String username);
}