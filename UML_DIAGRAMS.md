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
|  User           |     |  System         |
|                 |     |                 |
+-----------------+     +-----------------+
         |                       |
         |                       |
+--------+--------+     +--------+--------+
|                 |     |                 |
|  Login          |     |  Show Splash    |
|  Logout         |     |  Screen         |
|                 |     |                 |
+-----------------+     +-----------------+
         |                       |
         |                       |
+--------+--------+     +-----------------+
|                 |     |                 |
|  Task Management|     |  Data Persistence|
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
|     Main       |       |  SplashScreen  |       |  LoginScreen   |
+----------------+       +----------------+       +----------------+
| +main(String[]) |       | -timer:Timer   |       | -usernameField |
|                 |       | -splashLabel   |       | -passwordField |
|                 |       | +SplashScreen()|       | -errorLabel    |
|                 |       | -createUI()    |       | +LoginScreen() |
|                 |       +----------------+       | -createUI()    |
|                 |                               +----------------+
+----------------+

+----------------+       +----------------+       +----------------+
|  MainWindow    |       |     Task      |       |     User      |
+----------------+       +----------------+       +----------------+
| -tasks:List    |       | -id:String     |       | -password:String|
| -username:String|      | -title:String  |       | -avatarPath:String|
| +MainWindow()  |      | -description:String|   | +User()       |
| -createSidebar()|     | -completed:boolean|    | +getPassword()|
| -createTaskPanel|     | -priority:Priority|    | +getAvatarPath()|
| -addTaskToList()|     | -category:Category|    +----------------+
| -createTaskItem()|    | -dueDate:LocalDateTime|
| -showTaskDetails()|   | +Task()        |
| -loadTasks()    |     | +getters/setters|
| -saveTasks()    |     +----------------+
+----------------+

+----------------+       +----------------+       +----------------+
|   Priority     |       |   Category    |       |   UserAuth    |
+----------------+       +----------------+       +----------------+
| HIGH           |       | SCHOOL        |       | -USER_DATABASE |
| MEDIUM         |       | WORK          |       | +authenticate()|
| LOW            |       | PERSONAL      |       | +getUserAvatar()|
+----------------+       +----------------+       +----------------+

+----------------+
| DataPersistence|
+----------------+
| -DATA_DIRECTORY|
| +saveTasks()   |
| +loadTasks()   |
+----------------+
```

## Sequence Diagrams

### Application Start Sequence

```
User          Main          SplashScreen     LoginScreen
  |             |                |                |
  |--start app->|                |                |
  |             |--create------->|                |
  |             |                |                |
  |             |<--timer ends---|                |
  |             |                |                |
  |             |----------------|--create------->|
  |             |                |                |
  |<--show login|                |                |
  |             |                |                |
```

### User Login Sequence

```
User          LoginScreen     UserAuth      MainWindow
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
  |               |--create----->|              |
  |               |              |              |
  |               |<--task-------|              |
  |               |              |              |
  |--edit task--->|              |              |
  |               |--update----->|              |
  |               |              |              |
  |--complete---->|              |              |
  |               |--set completed|              |
  |               |              |              |
  |--delete task->|              |              |
  |               |--remove----->|              |
  |               |              |              |
  |               |--save------->|              |
  |               |              |              |
  |<--update UI---|              |              |
  |               |              |              |
```

### Task Description Sequence

```
User          MainWindow     Task          JDialog
  |               |              |              |
  |--click desc-->|              |              |
  |               |--get desc--->|              |
  |               |              |              |
  |               |<--desc-------|              |
  |               |              |              |
  |               |--------------|--create----->|
  |               |              |              |
  |<--show dialog-|              |              |
  |               |              |              |
  |--edit desc--->|              |              |
  |               |              |              |
  |--click save-->|              |              |
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
  |               |--get date--->|              |
  |               |              |              |
  |               |<--date-------|              |
  |               |              |              |
  |               |--------------|--create----->|
  |               |              |              |
  |<--show dialog-|              |              |
  |               |              |              |
  |--select date->|              |              |
  |               |              |              |
  |--click save-->|              |              |
  |               |--set date--->|              |
  |               |              |              |
  |               |--------------|--close------>|
  |               |              |              |
```

### Application Close Sequence

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