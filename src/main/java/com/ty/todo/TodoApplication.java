package com.ty.todo;

import com.ty.todo.model.User;
import com.ty.todo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }

    // This bean runs on startup to create a default user
    @Bean
    public CommandLineRunner demo(UserRepository repository, PasswordEncoder passwordEncoder) {
        return (args) -> {
            // Check if the user 'user' already exists to avoid creating duplicates
            if (repository.findByUsername("user").isEmpty()) {
                User user = new User();
                user.setUsername("user");
                // CRITICAL: Encodes the password before saving
                user.setPassword(passwordEncoder.encode("password")); 
                repository.save(user);
                System.out.println("Default user created (user/password)");
            }
        };
    }
}