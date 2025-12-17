package com.taskreminder.app.service;

import com.taskreminder.app.entity.Task;
import com.taskreminder.app.enums.TaskStatus;
import com.taskreminder.app.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * TaskService - Business Logic Layer
 * Handles all task-related operations
 */
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    /**
     * Get all tasks ordered by creation date (newest first)
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAllByOrderByCreatedOnDesc();
    }

    /**
     * Get task by ID
     */
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    /**
     * Create a new task
     */
    @Transactional
    public Task createTask(Task task) {
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.PENDING);
        }
        return taskRepository.save(task);
    }

    /**
     * Update an existing task
     */
    @Transactional
    public Task updateTask(Long id, Task updatedTask) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setPriority(updatedTask.getPriority());
        
        // Handle status change
        TaskStatus oldStatus = existingTask.getStatus();
        TaskStatus newStatus = updatedTask.getStatus();
        
        existingTask.setStatus(newStatus);
        
        // Auto-set completedOn when marked as COMPLETED
        if (newStatus == TaskStatus.COMPLETED && oldStatus != TaskStatus.COMPLETED) {
            existingTask.setCompletedOn(LocalDateTime.now());
        } else if (newStatus != TaskStatus.COMPLETED) {
            existingTask.setCompletedOn(null);
        }

        return taskRepository.save(existingTask);
    }

    /**
     * Mark task as completed
     */
    @Transactional
    public Task markAsCompleted(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
        task.setStatus(TaskStatus.COMPLETED);
        task.setCompletedOn(LocalDateTime.now());
        
        return taskRepository.save(task);
    }

    /**
     * Delete a task
     */
    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    /**
     * Get tasks by status
     */
    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    /**
     * Search tasks by title
     */
    public List<Task> searchTasksByTitle(String title) {
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }
}