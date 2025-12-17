package com.taskreminder.app.entity;

import com.taskreminder.app.enums.TaskPriority;
import com.taskreminder.app.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Task Entity - Represents a Task in the database
 */
@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Column(name = "created_on", nullable = false, updatable = false)
    private LocalDateTime createdOn;

    @Column(name = "completed_on")
    private LocalDateTime completedOn;

    /**
     * Automatically set createdOn timestamp before persisting
     */
    @PrePersist
    protected void onCreate() {
        this.createdOn = LocalDateTime.now();
        if (this.status == null) {
            this.status = TaskStatus.PENDING;
        }
    }

    /**
     * Automatically set completedOn when status changes to COMPLETED
     */
    @PreUpdate
    protected void onUpdate() {
        if (this.status == TaskStatus.COMPLETED && this.completedOn == null) {
            this.completedOn = LocalDateTime.now();
        } else if (this.status != TaskStatus.COMPLETED) {
            this.completedOn = null;
        }
    }
}