# Task Manager Application Documentation

## Table of Contents
1. [Introduction](#introduction)
2. [Installation and Setup](#installation-and-setup)
3. [System Architecture](#system-architecture)
4. [User Interface](#user-interface)
5. [Data Model](#data-model)
6. [Authentication System](#authentication-system)
7. [Data Persistence](#data-persistence)
8. [Task Management Features](#task-management-features)
9. [Use Cases](#use-cases)
10. [Class Diagrams](#class-diagrams)
11. [Sequence Diagrams](#sequence-diagrams)
12. [Future Enhancements](#future-enhancements)

## Introduction

The Task Manager is a Java Swing-based desktop application designed to help users organize and track their tasks efficiently. The application provides a modern, user-friendly interface with features for task creation, categorization, prioritization, and completion tracking.

### Key Features
- User authentication with secure login
- Task creation and management
- Task categorization (School, Work, Personal)
- Priority levels (High, Medium, Low)
- Due date and time assignment
- Task descriptions
- Task completion tracking
- Data persistence across sessions
- Modern UI with gradient backgrounds
- Profile section with avatar support

## Installation and Setup

### Prerequisites
- Java 17 or higher
- Git (optional, for cloning the repository)

### Installation Methods

#### Method 1: Using Git (Recommended)
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/java-swing-project.git
   cd java-swing-project
   ```

2. Compile the Java files:
   ```bash
   javac -d bin src/main/java/com/taskmanager/*.java src/main/java/com/taskmanager/model/*.java src/main/java/com/taskmanager/util/*.java
   ```

3. Run the application:
   ```bash
   java -cp bin com.taskmanager.Main
   ```

#### Method 2: Manual Download
1. Download the project ZIP file from the releases page
2. Extract the ZIP file to your desired location
3. Open a terminal/command prompt in the extracted directory
4. Create a `bin` directory:
   ```bash
   mkdir bin
   ```
5. Compile the Java files:
   ```bash
   javac -d bin src/main/java/com/taskmanager/*.java src/main/java/com/taskmanager/model/*.java src/main/java/com/taskmanager/util/*.java
   ```
6. Run the application:
   ```bash
   java -cp bin com.taskmanager.Main
   ```

### Login Credentials
- Username: `oop`
- Password: `123`

### Project Structure
```
java-swing-project/
├── bin/                    # Compiled class files
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── taskmanager/
│                   ├── Main.java           # Application entry point
│                   ├── LoginScreen.java    # Login window
│                   ├── MainWindow.java     # Main application window
│                   ├── model/
│                   │   └── Task.java      # Task data model
│                   └── util/
│                       ├── DataPersistence.java  # Data storage
│                       └── UserAuth.java         # Authentication
└── user_data/            # User task data storage
```

## System Architecture

The Task Manager application follows a modular architecture with clear separation of concerns:

### Components
1. **UI Layer**: Handles all user interface elements and interactions
   - `Main.java`: Application entry point
   - `LoginScreen.java`: User authentication interface
   - `MainWindow.java`: Main application interface

2. **Model Layer**: Contains data models and business logic
   - `Task.java`: Task data model
   - `User.java`: User data model

3. **Utility Layer**: Provides supporting functionality
   - `UserAuth.java`: Authentication logic
   - `DataPersistence.java`: Data storage and retrieval

## User Interface

The Task Manager features a modern, intuitive user interface with the following components:

### Login Screen
- Username and password input fields
- Login button
- Error message display
- Gradient background

### Main Window
- **Sidebar**: Contains user profile, navigation buttons, and logout option
- **Task List**: Displays active and completed tasks
- **Task Input**: Field for adding new tasks
- **Task Controls**: Buttons for managing tasks (Description, Details, Delete)
- **Task Counters**: Shows the number of active and completed tasks

### Task Item
Each task item displays:
- Checkbox for completion status
- Task title
- Priority dropdown
- Category dropdown
- Due date button
- Description button
- Details button
- Delete button

### Dialogs
- **Task Description Dialog**: For adding/editing task descriptions
- **Due Date Dialog**: For setting task due dates and times
- **Task Details Dialog**: For viewing all task information
- **Delete Confirmation Dialog**: For confirming task deletion

## Data Model

### Task Class
The `Task` class represents a task in the system with the following attributes:
- `id`: Unique identifier (UUID)
- `title`: Task title
- `description`: Task description
- `completed`: Boolean indicating completion status
- `priority`: Enum (HIGH, MEDIUM, LOW)
- `category`: Enum (SCHOOL, WORK, PERSONAL)
- `dueDate`: LocalDateTime for task deadline

### User Class
The `User` class represents a user in the system with:
- `password`: User's password
- `avatarPath`: Path to user's avatar image

## Authentication System

The authentication system is implemented in the `UserAuth` class:
- Static user database with a single predefined user
- Authentication method to verify username and password
- Avatar retrieval method for user profiles

### Default User
- Username: `oop`, Password: `123`

## Data Persistence

The `DataPersistence` class handles saving and loading task data:
- Tasks are stored in text files in the `user_data` directory
- Each user has their own task file (`username_tasks.txt`)
- Tasks are saved in a pipe-separated format
- Data is automatically saved when the application closes
- Data is loaded when the application starts

### Data Format
```
title|description|priority|category|completed|dueDate
```

## Task Management Features

### Task Management
The task management system provides a comprehensive set of features for managing tasks:

1. **Task Creation**
   - Create new tasks with title
   - Set initial priority and category
   - Add description
   - Set due date

2. **Task Editing**
   - Modify task title
   - Update description
   - Change priority
   - Change category
   - Update due date

3. **Task Completion**
   - Mark tasks as complete/incomplete
   - Move between active/completed lists
   - Visual indication of completion status

4. **Task Deletion**
   - Remove tasks from the system
   - Confirmation before deletion
   - Automatic UI updates

## Use Cases

### User Authentication
1. User launches the application
2. User enters username and password
3. System validates credentials
4. If valid, system opens main window
5. If invalid, system displays error message
6. User can logout to return to login screen

### Task Management
1. **Task Creation**
   - User enters task title in the input field at the top of the main window
   - User clicks "Add Task" button
   - System creates a new task with:
     * Default priority (MEDIUM)
     * Default category (PERSONAL)
     * Empty description
     * No due date
     * Completion status set to false
   - System adds the task to the active tasks list
   - System updates the active task counter
   - Task appears in the UI with all controls

2. **Task Description Management**
   - User clicks "Description" button on a task
   - System opens description dialog
   - User enters or edits task description
   - User clicks "Save" button
   - System saves the description to the task
   - System closes the dialog
   - Task description is updated in the system

3. **Task Priority Management**
   - User selects a priority from the dropdown menu (HIGH, MEDIUM, LOW)
   - System immediately updates the task's priority
   - Priority change is saved to the task data

4. **Task Category Management**
   - User selects a category from the dropdown menu (SCHOOL, WORK, PERSONAL)
   - System immediately updates the task's category
   - Category change is saved to the task data

5. **Task Due Date Management**
   - User clicks the date button on a task
   - System opens due date dialog
   - User selects date from the date picker
   - User selects time from the time picker
   - User clicks "Save" button
   - System combines date and time selections
   - System saves the due date to the task
   - System updates the date button text to show the selected date
   - System closes the dialog
   - User can also click "Clear Date" to remove the due date

6. **Task Completion**
   - User checks the completion checkbox on a task
   - System marks the task as completed
   - System moves the task from active to completed list
   - System updates the task styling (gray, italic text)
   - System updates both active and completed task counters
   - System switches to the completed tasks view
   - Completion status is saved to the task data

7. **Task Details View**
   - User clicks "Details" button on a task
   - System opens details dialog
   - System displays all task information:
     * Title
     * Description
     * Priority
     * Category
     * Due date
     * Completion status
   - User clicks "Close" button
   - System closes the dialog

8. **Task Deletion**
   - User clicks "Delete" button on a task
   - System displays confirmation dialog
   - User confirms deletion
   - System removes the task from the list
   - System updates the appropriate task counter
   - Task is permanently removed from the system
   - Task data is removed from storage

9. **Task Data Persistence**
   - System automatically saves all task data when:
     * User logs out
     * Application is closed
   - System loads task data when:
     * User logs in
     * Application is started
   - Each user's tasks are stored in a separate file

## Class Diagrams

### Main Classes
```
+----------------+       +----------------+       +----------------+
|     Main       |       |  LoginScreen   |       |  MainWindow    |
+----------------+       +----------------+       +----------------+
| +main(String[]) |       | -usernameField |       | -tasks:List    |
|                 |       | -passwordField |       | -username:String|
|                 |       | -errorLabel    |       | +MainWindow()   |
|                 |       | +LoginScreen() |       | -createSidebar()|
|                 |       | +getLoggedInUser|       | -createTaskPanel|
|                 |       +----------------+       | -addTaskToList()|
|                 |                               | -createTaskItem()|
|                 |                               | -showTaskDetails()|
+----------------+                               +----------------+

+----------------+       +----------------+       +----------------+
|     Task       |       |     User      |       |   UserAuth     |
+----------------+       +----------------+       +----------------+
| -id:String     |       | -password:String|     | -USER_DATABASE |
| -title:String  |       | -avatarPath:String|   | +authenticate()|
| -description:String|   | +User()       |     | +getUserAvatar()|
| -completed:boolean|    | +getPassword()|     +----------------+
| -priority:Priority|    | +getAvatarPath()|
| -category:Category|    +----------------+
| -dueDate:LocalDateTime|
| +Task()        |
| +getters/setters|
+----------------+

+----------------+       +----------------+       +----------------+
|   Priority     |       |   Category    |       | DataPersistence|
+----------------+       +----------------+       +----------------+
| HIGH           |       | SCHOOL        |       | -DATA_DIRECTORY|
| MEDIUM         |       | WORK          |       | +saveTasks()   |
| LOW            |       | PERSONAL      |       | +loadTasks()   |
+----------------+       +----------------+       +----------------+
```

## Sequence Diagrams

### User Login Sequence
```
User          LoginScreen     UserAuth      MainWindow
  |               |              |              |
  |--launch app-->|              |              |
  |               |              |              |
  |--enter cred-->|              |              |
  |               |--validate--->|              |
  |               |<--result-----|              |
  |               |              |              |
  |<--show error--|              |              |
  |               |              |              |
  |--enter cred-->|              |              |
  |               |--validate--->|              |
  |               |<--result-----|              |
  |               |              |              |
  |               |--------------|--create----->|
  |               |              |              |
  |<--close-------|              |              |
  |               |              |              |
```

### Task Management Sequence
```
User          MainWindow     Task          DataPersistence
  |               |              |              |
  |--create task->|              |              |
  |               |              |              |
  |               |--create----->|              |
  |               |              |              |
  |               |<--task-------|              |
  |               |              |              |
  |--edit task--->|              |              |
  |               |              |              |
  |               |--update----->|              |
  |               |              |              |
  |--complete---->|              |              |
  |               |              |              |
  |               |--set completed|              |
  |               |              |              |
  |--delete task->|              |              |
  |               |              |              |
  |               |--remove----->|              |
  |               |              |              |
  |               |--save------->|              |
  |               |              |              |
  |<--update UI---|              |              |
  |               |              |              |
```

### User Logout Sequence
```
User          MainWindow     DataPersistence
  |               |              |
  |--logout------>|              |
  |               |              |
  |               |--save tasks-->|
  |               |              |
  |               |--dispose----->|
  |               |              |
  |<--close-------|              |
  |               |              |
  |               |--show login-->|
  |               |              |
```

## Future Enhancements

### Potential Improvements
1. **Task Filtering**: Add ability to filter tasks by priority, category, or due date
2. **Task Sorting**: Add ability to sort tasks by various criteria
3. **Task Search**: Add search functionality to find tasks quickly
4. **Task Tags**: Add support for custom tags on tasks
5. **Task Reminders**: Add notification system for upcoming due dates
6. **Task Sharing**: Add ability to share tasks with other users
7. **Task Templates**: Add support for creating task templates
8. **Task Statistics**: Add charts and statistics for task completion
9. **Task Export**: Add ability to export tasks to various formats
10. **Task Import**: Add ability to import tasks from various formats
11. **Dark Mode**: Add support for dark mode theme
12. **Custom Categories**: Allow users to create custom task categories
13. **Task Attachments**: Allow users to attach files to tasks
14. **Task Comments**: Add support for comments on tasks
15. **Task Dependencies**: Add support for task dependencies

---

This documentation provides a comprehensive overview of the Task Manager application. The diagrams (use case, sequence, and class) will be added separately as requested. 
