package com.taskmanager.model;

public class Task {
    private String id;
    private String title;
    private String description;
    private boolean completed;
    private Priority priority;
    private Category category;

    public enum Priority {
        HIGH, MEDIUM, LOW
    }

    public enum Category {
        SCHOOL, WORK, PERSONAL
    }

    public Task(String title, String description, Priority priority, Category category) {
        this.id = java.util.UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.completed = false;
        this.priority = priority;
        this.category = category;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
} 