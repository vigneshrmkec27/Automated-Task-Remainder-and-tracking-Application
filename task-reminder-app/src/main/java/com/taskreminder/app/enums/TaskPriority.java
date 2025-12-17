package com.taskreminder.app.enums;

/**
 * Enum representing Task Priority Levels
 */
public enum TaskPriority {
    HIGH("High Priority"),
    MEDIUM("Medium Priority"),
    LOW("Low Priority");

    private final String displayName;

    TaskPriority(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 
