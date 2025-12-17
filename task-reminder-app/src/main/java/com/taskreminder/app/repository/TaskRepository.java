package com.taskreminder.app.repository;

import com.taskreminder.app.entity.Task;
import com.taskreminder.app.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TaskRepository - Data Access Layer
 * Provides CRUD operations for Task entity
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Find tasks by status
     */
    List<Task> findByStatus(TaskStatus status);

    /**
     * Find tasks by title containing (case-insensitive search)
     */
    List<Task> findByTitleContainingIgnoreCase(String title);

    /**
     * Find all tasks ordered by createdOn descending
     */
    List<Task> findAllByOrderByCreatedOnDesc();
}