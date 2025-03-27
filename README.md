# Task CLI Application

This is a simple Task Tracker application implemented in Java for managing tasks. You can add, update, delete, mark, and list tasks, all while storing data in a JSON file, ensuring your tasks are saved between sessions.

## Features

- **Add Task**: Add a new task with a description.
- **Update Task**: Update the description of an existing task.
- **Delete Task**: Delete a task by its ID.
- **Mark Task In Progress**: Mark a task as in progress.
- **Mark Task Done**: Mark a task as done.
- **List All Tasks**: List all tasks.
- **List Tasks In Progress**: List tasks that are in progress.
- **List Tasks Done**: List tasks that are done.
- **List Tasks TODO**: List tasks that are to be done.
- **List Tasks Not Done**: List tasks that are not done.

## Prerequisites

- Java 17 or higher
- Maven

## How to Run

1. Clone the repository:
    ```sh
    git clone https://github.com/Farnam-Hs/Task-Tracker.git
    ```
2. Navigate to the project directory:
    ```sh
    cd TaskTracker
    ```
3. Compile the project using Maven:
    ```sh
    mvn compile
    ```
4. Run the application:
    ```sh
    mvn exec:java -Dexec.mainClass="TaskTracker"
    ```

## Usage

Once the application is running, you can use the following commands:

- `add`: Add a new task.
- `update`: Update an existing task.
- `delete`: Delete a task.
- `mark-in-progress`: Mark a task as in progress.
- `mark-done`: Mark a task as done.
- `list-all-tasks`: List all tasks.
- `list-tasks-that-are-inProgress`: List tasks that are in progress.
- `list-tasks-that-are-Done`: List tasks that are done.
- `list-tasks-that-are-TODO`: List tasks that are to be done.
- `list-tasks-that-are-not-Done`: List tasks that are not done.
- `help`: Display the help menu.
- `exit`: Exit the application.


## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any changes.
