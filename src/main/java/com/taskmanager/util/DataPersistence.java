package com.taskmanager.util;

import com.taskmanager.model.Task;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DataPersistence {
    private static final String DATA_DIRECTORY = "user_data";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static String getTaskFilePath(String username) {
        // Create user_data directory if it doesn't exist
        File directory = new File(DATA_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return DATA_DIRECTORY + "/" + username + "_tasks.txt";
    }

    public static void saveTasks(String username, List<Task> tasks) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(getTaskFilePath(username)))) {
            for (Task task : tasks) {
                // Write each task as a pipe-separated line: title|description|priority|category|completed|dueDate
                writer.println(String.format("%s|%s|%s|%s|%b|%s",
                    task.getTitle(),
                    task.getDescription(),
                    task.getPriority(),
                    task.getCategory(),
                    task.isCompleted(),
                    task.getDueDate() != null ? task.getDueDate().format(DATE_FORMATTER) : ""
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }

    public static List<Task> loadTasks(String username) {
        List<Task> tasks = new ArrayList<>();
        File taskFile = new File(getTaskFilePath(username));
        
        if (!taskFile.exists()) {
            return tasks; // Return empty list if file doesn't exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(taskFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    Task task = new Task(
                        parts[0], // title
                        parts[1], // description
                        Task.Priority.valueOf(parts[2]), // priority
                        Task.Category.valueOf(parts[3])  // category
                    );
                    task.setCompleted(Boolean.parseBoolean(parts[4])); // completed
                    
                    // Load due date if it exists
                    if (parts.length > 5 && !parts[5].isEmpty()) {
                        task.setDueDate(LocalDateTime.parse(parts[5], DATE_FORMATTER));
                    }
                    
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
        }
        
        return tasks;
    }
} 