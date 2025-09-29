package com.ty.todo.controller;

import com.ty.todo.model.Task;
import com.ty.todo.model.User;
import com.ty.todo.repository.TaskRepository;
import com.ty.todo.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.security.Principal;

@Controller
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskController(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/tasks")
    public String getTasks(Model model, Principal principal) {
        String username = principal.getName(); // Get logged-in username
        model.addAttribute("tasks", taskRepository.findByUserUsernameOrderByTargetTimeAsc(username));
        model.addAttribute("newTask", new Task());
        return "tasks";
    }

    @PostMapping("/tasks/add")
    public String addTask(@ModelAttribute Task newTask, Principal principal) {
        String username = principal.getName();
        User currentUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalStateException("User not found"));
        
        newTask.setUser(currentUser);
        taskRepository.save(newTask);
        return "redirect:/tasks";
    }
}