# UML Diagrams for Task Manager Application

## Use Case Diagram

```
+------------------+     +------------------+
|   Task Manager   |     |   Task Manager   |
+------------------+     +------------------+
         |                       |
         |                       |
+--------+--------+     +--------+--------+
|                 |     |                 |
|  User           |     |  Admin          |
|                 |     |                 |
+-----------------+     +-----------------+
         |                       |
         |                       |
+--------+--------+     +--------+--------+
|                 |     |                 |
|  Login          |     |  Create User    |
|  Logout         |     |  Set User Avatar|
|                 |     |                 |
+-----------------+     +-----------------+
         |                       |
         |                       |
+--------+--------+     +-----------------+
|                 |     |                 |
|  Task Management|     |  Manage Users   |
|                 |     |                 |
+-----------------+     +-----------------+
         |
         |
+--------+--------+
|                 |
|  Create Task    |
|  Edit Task      |
|  Delete Task    |
|  Complete Task  |
|  Set Due Date   |
|  Set Priority   |
|  Set Category   |
|  Add Description|
|  View Details   |
|                 |
+-----------------+
```

## Class Diagram

```
+----------------+       +----------------+       +----------------+
|     Main       |       |  LoginScreen   |       |  MainWindow    |
+----------------+       +----------------+       +----------------+
| +main(String[]) |       | -usernameField |       | -tasks:List    |
+----------------+       | -passwordField |       | -username:String|
                        | -errorLabel    |       | +MainWindow()   |
                        | +LoginScreen() |       | -createSidebar()|
                        | +getLoggedInUser|       | -createTaskPanel|
                        +----------------+       | -addTaskToList()|
                                                | -createTaskItem()|
                                                | -showTaskDetails()|
                                                +----------------+

+----------------+       +----------------+       +----------------+
|     Task       |       |     User      |       |   UserAuth     |
+----------------+       +----------------+       +----------------+
| -id:String     |       | -password:String|     | -USER_DATABASE |
| -title:String  |       | -avatarPath:String|   | +authenticate()|
| -description:String|   | +User()       |     | +getUserAvatar()|
| -completed:boolean|    | +getPassword()|     | +createUser()   |
| -priority:Priority|    | +getAvatarPath()|   | +setUserAvatar()|
| -category:Category|    +----------------+     +----------------+
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

### Admin Create User Sequence

```
Admin         AdminPanel     UserAuth      FileSystem
  |               |              |              |
  |--create user->|              |              |
  |               |              |              |
  |--enter details|              |              |
  |               |              |              |
  |               |--create----->|              |
  |               |              |              |
  |               |<--user-------|              |
  |               |              |              |
  |--set avatar-->|              |              |
  |               |              |              |
  |               |--save------->|              |
  |               |              |              |
  |<--success-----|              |              |
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

### Task Creation Sequence

```
User          MainWindow     Task          DataPersistence
  |               |              |              |
  |--enter title->|              |              |
  |               |              |              |
  |--click add--->|              |              |
  |               |              |              |
  |               |--create----->|              |
  |               |              |              |
  |               |<--task-------|              |
  |               |              |              |
  |               |--add to list-|              |
  |               |              |              |
  |               |--update UI---|              |
  |               |              |              |
  |<--show task---|              |              |
  |               |              |              |
```

### Task Description Sequence

```
User          MainWindow     Task          JDialog
  |               |              |              |
  |--click desc-->|              |              |
  |               |              |              |
  |               |--------------|--create----->|
  |               |              |              |
  |               |--get desc--->|              |
  |               |              |              |
  |               |<--desc-------|              |
  |               |              |              |
  |<--show dialog-|              |              |
  |               |              |              |
  |--edit desc--->|              |              |
  |               |              |              |
  |--click save-->|              |              |
  |               |              |              |
  |               |--set desc--->|              |
  |               |              |              |
  |               |--------------|--close------>|
  |               |              |              |
```

### Task Due Date Sequence

```
User          MainWindow     Task          JDialog
  |               |              |              |
  |--click date-->|              |              |
  |               |              |              |
  |               |--------------|--create----->|
  |               |              |              |
  |               |--get date--->|              |
  |               |              |              |
  |               |<--date-------|              |
  |               |              |              |
  |<--show dialog-|              |              |
  |               |              |              |
  |--select date->|              |              |
  |               |              |              |
  |--click save-->|              |              |
  |               |              |              |
  |               |--set date--->|              |
  |               |              |              |
  |               |--------------|--close------>|
  |               |              |              |
```

### Task Completion Sequence

```
User          MainWindow     Task          DataPersistence
  |               |              |              |
  |--check box--->|              |              |
  |               |              |              |
  |               |--set completed|              |
  |               |              |              |
  |               |--update UI---|              |
  |               |              |              |
  |               |--save tasks-->|              |
  |               |              |              |
  |<--update UI---|              |              |
  |               |              |              |
```

### Task Deletion Sequence

```
User          MainWindow     Task          JDialog
  |               |              |              |
  |--click del--->|              |              |
  |               |              |              |
  |               |--------------|--create----->|
  |               |              |              |
  |<--show dialog-|              |              |
  |               |              |              |
  |--confirm----->|              |              |
  |               |              |              |
  |               |--remove task-|              |
  |               |              |              |
  |               |--update UI---|              |
  |               |              |              |
  |               |--------------|--close------>|
  |               |              |              |
```

### Application Shutdown Sequence

```
User          MainWindow     DataPersistence
  |               |              |
  |--close app--->|              |
  |               |              |
  |               |--save tasks-->|
  |               |              |
  |               |--dispose----->|
  |               |              |
  |<--close-------|              |
  |               |              |
``` 