# Task Manager Application

A Java Swing application for managing tasks with a modern user interface.

## Features

- Secure login system
- Modern UI with gradient backgrounds
- Task management with priority and category support
- Task completion tracking
- Data persistence using simple text file storage
- Profile section with avatar placeholder

## Requirements

- Java 17 or higher

## Login Credentials

- Username: `oop`
- Password: `123`

## Running the Application

1. Compile the Java files:
   ```bash
   javac -d bin src/main/java/com/taskmanager/*.java src/main/java/com/taskmanager/model/*.java src/main/java/com/taskmanager/util/*.java
   ```

2. Run the application:
   ```bash
   java -cp bin com.taskmanager.Main
   ```

## Usage

1. Login using the provided credentials
2. Add tasks using the input field at the top
3. Set task priority and category using the dropdown menus
4. Mark tasks as complete using the checkbox
5. View task details by clicking the "Details" button
6. Delete tasks using the "Delete" button
7. Tasks are automatically saved when closing the application

## Project Structure

- `src/main/java/com/taskmanager/`
  - `Main.java` - Application entry point
  - `LoginScreen.java` - Login window implementation
  - `MainWindow.java` - Main application window
  - `model/Task.java` - Task data model
  - `util/DataPersistence.java` - Data persistence utilities 