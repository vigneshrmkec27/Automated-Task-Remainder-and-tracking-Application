package com.taskreminder.app.controller;

import com.taskreminder.app.entity.Task;
import com.taskreminder.app.enums.TaskPriority;
import com.taskreminder.app.enums.TaskStatus;
import com.taskreminder.app.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * TaskController - Handles all HTTP requests related to tasks
 */
@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    /**
     * Display all tasks
     * URL: GET /tasks
     */
    @GetMapping
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("statuses", TaskStatus.values());
        return "tasks";
    }

    /**
     * Show form to add new task
     * URL: GET /tasks/add
     */
    @GetMapping("/add")
    public String showAddTaskForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("priorities", TaskPriority.values());
        model.addAttribute("statuses", TaskStatus.values());
        return "add-task";
    }

    /**
     * Create a new task
     * URL: POST /tasks/add
     */
    @PostMapping("/add")
    public String addTask(@ModelAttribute Task task, RedirectAttributes redirectAttributes) {
        try {
            taskService.createTask(task);
            redirectAttributes.addFlashAttribute("successMessage", "✅ Task created successfully!");
            return "redirect:/tasks";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Error creating task: " + e.getMessage());
            return "redirect:/tasks/add";
        }
    }

    /**
     * View single task details
     * URL: GET /tasks/view/{id}
     */
    @GetMapping("/view/{id}")
    public String viewTask(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return taskService.getTaskById(id)
                .map(task -> {
                    model.addAttribute("task", task);
                    return "view-task";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "❌ Task not found!");
                    return "redirect:/tasks";
                });
    }

    /**
     * Show form to edit task
     * URL: GET /tasks/edit/{id}
     */
    @GetMapping("/edit/{id}")
    public String showEditTaskForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return taskService.getTaskById(id)
                .map(task -> {
                    model.addAttribute("task", task);
                    model.addAttribute("priorities", TaskPriority.values());
                    model.addAttribute("statuses", TaskStatus.values());
                    return "edit-task";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "❌ Task not found!");
                    return "redirect:/tasks";
                });
    }

    /**
     * Update existing task
     * URL: POST /tasks/edit/{id}
     */
    @PostMapping("/edit/{id}")
    public String editTask(@PathVariable Long id, @ModelAttribute Task task, RedirectAttributes redirectAttributes) {
        try {
            taskService.updateTask(id, task);
            redirectAttributes.addFlashAttribute("successMessage", "✅ Task updated successfully!");
            return "redirect:/tasks";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Error updating task: " + e.getMessage());
            return "redirect:/tasks/edit/" + id;
        }
    }

    /**
     * Mark task as completed
     * URL: GET /tasks/complete/{id}
     */
    @GetMapping("/complete/{id}")
    public String markAsCompleted(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            taskService.markAsCompleted(id);
            redirectAttributes.addFlashAttribute("successMessage", "✅ Task marked as completed!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Error: " + e.getMessage());
        }
        return "redirect:/tasks";
    }

    /**
     * Delete task
     * URL: GET /tasks/delete/{id}
     */
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            taskService.deleteTask(id);
            redirectAttributes.addFlashAttribute("successMessage", "✅ Task deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Error deleting task: " + e.getMessage());
        }
        return "redirect:/tasks";
    }
}