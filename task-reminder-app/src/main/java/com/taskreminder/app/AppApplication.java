package com.taskreminder.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application Class
 * Entry point of the Task Management Application
 */
@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("✅ Task Management App Started!");
        System.out.println("🌐 Access at: http://localhost:8080/tasks");
        System.out.println("🗄️  H2 Console: http://localhost:8080/h2-console");
        System.out.println("========================================\n");
    }
}