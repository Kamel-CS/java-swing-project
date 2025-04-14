package com.taskmanager;

import com.taskmanager.model.Task;
import com.taskmanager.util.DataPersistence;
import com.taskmanager.util.UserAuth;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame {
    private List<Task> tasks;
    private JPanel activeTaskListPanel;
    private JPanel completedTaskListPanel;
    private JLabel activeTaskCounter;
    private JLabel completedTaskCounter;
    private JPanel mainContentPanel;
    private CardLayout cardLayout;
    private String username;

    public MainWindow(String username) {
        this.username = username;
        setTitle("Task Manager - " + username);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Load tasks from file for this user
        tasks = new ArrayList<>(DataPersistence.loadTasks(username));

        // Create main layout
        setLayout(new BorderLayout());

        // Create left sidebar (Account Section)
        JPanel sidebar = createSidebar();
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setBackground(new Color(44, 62, 80));

        // Create main content panel with card layout
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setBackground(Color.WHITE);

        // Create active tasks panel
        JPanel activeTasksPanel = createTaskPanel(false);
        activeTasksPanel.setBackground(Color.WHITE);
        mainContentPanel.add(activeTasksPanel, "active");

        // Create completed tasks panel
        JPanel completedTasksPanel = createTaskPanel(true);
        completedTasksPanel.setBackground(Color.WHITE);
        mainContentPanel.add(completedTasksPanel, "completed");

        // Add panels to main layout
        add(sidebar, BorderLayout.WEST);
        add(mainContentPanel, BorderLayout.CENTER);

        // Load existing tasks into the UI
        tasks.forEach(this::addTaskToList);
        updateTaskCounters();

        // Add window listener to save tasks when closing
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                DataPersistence.saveTasks(username, tasks);
            }
        });
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Profile image
        JPanel profilePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                try {
                    // Get the avatar path for the current user
                    String avatarPath = UserAuth.getUserAvatar(username);
                    // Load the image from resources
                    java.net.URL imageUrl = getClass().getResource(avatarPath);
                    if (imageUrl != null) {
                        Image img = new ImageIcon(imageUrl).getImage();
                        // Draw circular clipping
                        java.awt.Shape clip = new java.awt.geom.Ellipse2D.Float(0, 0, getWidth(), getHeight());
                        g2d.setClip(clip);
                        // Draw the image
                        g2d.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                    } else {
                        // Fallback to default silhouette if image not found
                        drawDefaultSilhouette(g2d);
                    }
                } catch (Exception e) {
                    // Fallback to default silhouette on any error
                    drawDefaultSilhouette(g2d);
                }
            }

            private void drawDefaultSilhouette(Graphics2D g2d) {
                // Draw white circle background
                g2d.setColor(Color.WHITE);
                g2d.fillOval(0, 0, getWidth(), getHeight());
                
                // Draw gray silhouette
                g2d.setColor(new Color(200, 200, 200));
                
                // Head circle
                int headSize = getWidth() / 2;
                int headX = getWidth() / 4;
                int headY = getHeight() / 6;
                g2d.fillOval(headX, headY, headSize, headSize);
                
                // Body shape
                int bodyWidth = getWidth() / 2;
                int bodyHeight = getHeight() / 2;
                int bodyX = getWidth() / 4;
                int bodyY = getHeight() / 2;
                g2d.fillOval(bodyX, bodyY, bodyWidth, bodyHeight);
            }
        };
        profilePanel.setPreferredSize(new Dimension(100, 100));
        profilePanel.setMaximumSize(new Dimension(100, 100));
        profilePanel.setOpaque(false);

        // Username
        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Separator
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.WHITE);

        // Task navigation buttons
        JButton activeTasksButton = createNavButton("Tasks", false);
        JButton completedTasksButton = createNavButton("Done", true);
        
        // Add components to sidebar
        sidebar.add(profilePanel);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(usernameLabel);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(separator);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(activeTasksButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(completedTasksButton);
        
        // Add flexible space to push logout button to the bottom
        sidebar.add(Box.createVerticalGlue());
        
        // Add separator before logout button
        JSeparator logoutSeparator = new JSeparator();
        logoutSeparator.setForeground(Color.WHITE);
        sidebar.add(logoutSeparator);
        sidebar.add(Box.createVerticalStrut(20));
        
        // Add logout button
        JButton logoutButton = createLogoutButton();
        sidebar.add(logoutButton);

        return sidebar;
    }
    
    private JButton createLogoutButton() {
        JButton logoutButton = new JButton("Logout");
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setMaximumSize(new Dimension(160, 40));
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(231, 76, 60)); // Red color for logout
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        
        // Add hover effect
        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(new Color(192, 57, 43)); // Darker red on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(new Color(231, 76, 60));
            }
        });
        
        // Add logout action
        logoutButton.addActionListener(e -> {
            // Save tasks before logging out
            DataPersistence.saveTasks(username, tasks);
            
            // Close the main window
            dispose();
            
            // Show the login screen
            SwingUtilities.invokeLater(() -> {
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.setVisible(true);
            });
        });
        
        return logoutButton;
    }
    
    private JButton createNavButton(String text, boolean isCompleted) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(160, 40));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 73, 94));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(41, 128, 185));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(52, 73, 94));
            }
        });
        
        // Add click action
        button.addActionListener(e -> {
            cardLayout.show(mainContentPanel, isCompleted ? "completed" : "active");
        });
        
        return button;
    }

    private JPanel createTaskPanel(boolean isCompletedPanel) {
        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new BorderLayout(10, 10));
        taskPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Task input section (only for active tasks)
        if (!isCompletedPanel) {
            JPanel inputSection = new JPanel(new BorderLayout(10, 0));
            JTextField taskInput = new JTextField();
            JButton addButton = new JButton("Add Task");
            addButton.setBackground(new Color(41, 128, 185));
            addButton.setForeground(Color.WHITE);
            addButton.setFocusPainted(false);
            inputSection.add(taskInput, BorderLayout.CENTER);
            inputSection.add(addButton, BorderLayout.EAST);

            // Add task button action
            addButton.addActionListener(e -> {
                String taskTitle = taskInput.getText().trim();
                if (!taskTitle.isEmpty()) {
                    Task newTask = new Task(taskTitle, "", Task.Priority.MEDIUM, Task.Category.PERSONAL);
                    tasks.add(newTask);
                    addTaskToList(newTask);
                    taskInput.setText("");
                    updateTaskCounters();
                }
            });

            taskPanel.add(inputSection, BorderLayout.NORTH);
        }

        // Task list
        if (isCompletedPanel) {
            completedTaskListPanel = new JPanel();
            completedTaskListPanel.setLayout(new BoxLayout(completedTaskListPanel, BoxLayout.Y_AXIS));
            JScrollPane scrollPane = new JScrollPane(completedTaskListPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            taskPanel.add(scrollPane, BorderLayout.CENTER);
            
            // Task counter for completed tasks
            completedTaskCounter = new JLabel("Completed: 0");
            taskPanel.add(completedTaskCounter, BorderLayout.SOUTH);
        } else {
            activeTaskListPanel = new JPanel();
            activeTaskListPanel.setLayout(new BoxLayout(activeTaskListPanel, BoxLayout.Y_AXIS));
            JScrollPane scrollPane = new JScrollPane(activeTaskListPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            taskPanel.add(scrollPane, BorderLayout.CENTER);
            
            // Task counter for active tasks
            activeTaskCounter = new JLabel("Active: 0");
            taskPanel.add(activeTaskCounter, BorderLayout.SOUTH);
        }

        return taskPanel;
    }

    private void addTaskToList(Task task) {
        JPanel taskItem = createTaskItem(task);
        
        if (task.isCompleted()) {
            completedTaskListPanel.add(taskItem);
            completedTaskListPanel.revalidate();
            completedTaskListPanel.repaint();
        } else {
            activeTaskListPanel.add(taskItem);
            activeTaskListPanel.revalidate();
            activeTaskListPanel.repaint();
        }
        
        updateTaskCounters();
    }
    
    private JPanel createTaskItem(Task task) {
        JPanel taskItem = new JPanel(new BorderLayout(10, 0));
        taskItem.setBackground(new Color(248, 249, 250));
        taskItem.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        taskItem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        // Left side: Checkbox and title
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setBackground(new Color(248, 249, 250));
        JCheckBox completedCheck = new JCheckBox();
        completedCheck.setSelected(task.isCompleted());
        
        JLabel titleLabel = new JLabel(task.getTitle());
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        
        // Apply styling for completed tasks
        if (task.isCompleted()) {
            titleLabel.setForeground(Color.GRAY);
            titleLabel.setFont(titleLabel.getFont().deriveFont(Font.ITALIC));
        }
        
        leftPanel.add(completedCheck);
        leftPanel.add(titleLabel);

        // Right side: Controls
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        rightPanel.setBackground(new Color(248, 249, 250));
        
        JComboBox<Task.Priority> priorityCombo = new JComboBox<>(Task.Priority.values());
        priorityCombo.setMaximumSize(new Dimension(100, 30));
        
        JComboBox<Task.Category> categoryCombo = new JComboBox<>(Task.Category.values());
        categoryCombo.setMaximumSize(new Dimension(100, 30));
        
        JButton dateButton = new JButton(task.getFormattedDueDate());
        dateButton.setMaximumSize(new Dimension(150, 30));
        
        JButton descButton = new JButton("Description");
        descButton.setMaximumSize(new Dimension(100, 30));
        
        JButton detailsButton = new JButton("Details");
        detailsButton.setMaximumSize(new Dimension(70, 30));
        
        JButton deleteButton = new JButton("Delete");
        deleteButton.setMaximumSize(new Dimension(70, 30));
        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.WHITE);

        rightPanel.add(priorityCombo);
        rightPanel.add(categoryCombo);
        rightPanel.add(dateButton);
        rightPanel.add(descButton);
        rightPanel.add(detailsButton);
        rightPanel.add(deleteButton);

        taskItem.add(leftPanel, BorderLayout.WEST);
        taskItem.add(rightPanel, BorderLayout.EAST);

        // Add action listeners
        completedCheck.addActionListener(e -> {
            task.setCompleted(completedCheck.isSelected());
            
            // Remove from current panel
            Container parent = taskItem.getParent();
            parent.remove(taskItem);
            parent.revalidate();
            parent.repaint();
            
            // Add to appropriate panel
            if (task.isCompleted()) {
                titleLabel.setForeground(Color.GRAY);
                titleLabel.setFont(titleLabel.getFont().deriveFont(Font.ITALIC));
                completedTaskListPanel.add(taskItem);
                completedTaskListPanel.revalidate();
                completedTaskListPanel.repaint();
                cardLayout.show(mainContentPanel, "completed");
            } else {
                titleLabel.setForeground(Color.BLACK);
                titleLabel.setFont(titleLabel.getFont().deriveFont(Font.PLAIN));
                activeTaskListPanel.add(taskItem);
                activeTaskListPanel.revalidate();
                activeTaskListPanel.repaint();
                cardLayout.show(mainContentPanel, "active");
            }
            
            updateTaskCounters();
        });

        priorityCombo.setSelectedItem(task.getPriority());
        priorityCombo.addActionListener(e -> task.setPriority((Task.Priority) priorityCombo.getSelectedItem()));

        categoryCombo.setSelectedItem(task.getCategory());
        categoryCombo.addActionListener(e -> task.setCategory((Task.Category) categoryCombo.getSelectedItem()));

        descButton.addActionListener(e -> {
            JDialog descDialog = new JDialog(this, "Task Description", true);
            descDialog.setLayout(new BorderLayout(10, 10));
            descDialog.setSize(400, 300);
            descDialog.setLocationRelativeTo(this);

            JPanel descPanel = new JPanel(new BorderLayout(10, 10));
            descPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JTextArea descArea = new JTextArea(task.getDescription());
            descArea.setLineWrap(true);
            descArea.setWrapStyleWord(true);
            descArea.setFont(new Font("Arial", Font.PLAIN, 14));
            
            JScrollPane scrollPane = new JScrollPane(descArea);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            
            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(ev -> {
                task.setDescription(descArea.getText());
                descDialog.dispose();
            });

            descPanel.add(scrollPane, BorderLayout.CENTER);
            descPanel.add(saveButton, BorderLayout.SOUTH);
            descDialog.add(descPanel);
            descDialog.setVisible(true);
        });

        dateButton.addActionListener(e -> {
            JDialog dateDialog = new JDialog(this, "Set Due Date", true);
            dateDialog.setLayout(new BorderLayout(10, 10));
            dateDialog.setSize(300, 200);
            dateDialog.setLocationRelativeTo(this);

            JPanel datePanel = new JPanel(new GridLayout(3, 1, 5, 5));
            datePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Date picker
            JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
            JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
            dateSpinner.setEditor(dateEditor);
            if (task.getDueDate() != null) {
                dateSpinner.setValue(java.sql.Timestamp.valueOf(task.getDueDate()));
            }

            // Time picker
            JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
            JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
            timeSpinner.setEditor(timeEditor);
            if (task.getDueDate() != null) {
                timeSpinner.setValue(java.sql.Timestamp.valueOf(task.getDueDate()));
            }

            // Clear date button
            JButton clearButton = new JButton("Clear Date");
            clearButton.addActionListener(ev -> {
                task.setDueDate(null);
                dateButton.setText("No due date");
                dateDialog.dispose();
            });

            datePanel.add(dateSpinner);
            datePanel.add(timeSpinner);
            datePanel.add(clearButton);

            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(ev -> {
                java.util.Date date = (java.util.Date) dateSpinner.getValue();
                java.util.Date time = (java.util.Date) timeSpinner.getValue();
                
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                calendar.setTime(date);
                
                java.util.Calendar timeCalendar = java.util.Calendar.getInstance();
                timeCalendar.setTime(time);
                
                calendar.set(java.util.Calendar.HOUR_OF_DAY, timeCalendar.get(java.util.Calendar.HOUR_OF_DAY));
                calendar.set(java.util.Calendar.MINUTE, timeCalendar.get(java.util.Calendar.MINUTE));
                
                task.setDueDate(calendar.getTime().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
                dateButton.setText(task.getFormattedDueDate());
                dateDialog.dispose();
            });

            dateDialog.add(datePanel, BorderLayout.CENTER);
            dateDialog.add(saveButton, BorderLayout.SOUTH);
            dateDialog.setVisible(true);
        });

        detailsButton.addActionListener(e -> showTaskDetails(task));

        deleteButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this task?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (choice == JOptionPane.YES_OPTION) {
                tasks.remove(task);
                Container parent = taskItem.getParent();
                parent.remove(taskItem);
                parent.revalidate();
                parent.repaint();
                updateTaskCounters();
            }
        });

        return taskItem;
    }

    private void showTaskDetails(Task task) {
        JDialog dialog = new JDialog(this, "Task Details", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        content.add(new JLabel("Title: " + task.getTitle()));
        content.add(Box.createVerticalStrut(10));
        content.add(new JLabel("Description: " + task.getDescription()));
        content.add(Box.createVerticalStrut(10));
        content.add(new JLabel("Priority: " + task.getPriority()));
        content.add(Box.createVerticalStrut(10));
        content.add(new JLabel("Category: " + task.getCategory()));
        content.add(Box.createVerticalStrut(10));
        content.add(new JLabel("Due Date: " + task.getFormattedDueDate()));

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());

        dialog.add(content, BorderLayout.CENTER);
        dialog.add(closeButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void updateTaskCounters() {
        long completedCount = tasks.stream().filter(Task::isCompleted).count();
        long activeCount = tasks.size() - completedCount;
        
        activeTaskCounter.setText("Active: " + activeCount);
        completedTaskCounter.setText("Completed: " + completedCount);
    }
} 