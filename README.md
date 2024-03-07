# TODOClippo Project

This Project is based on Mohit Singh's implementation of the "Do-It" App and serves as a personal practice for app development.  
TODOClippo is a task management application, meticulously adapted from its [original Java implementation](https://github.com/msindev/Do-It)  to Kotlin, leveraging modern Android development practices and technologies.  
This project showcases the use of Kotlin Coroutines for efficient asynchronous programming, Jetpack libraries for robust application architecture, and Koin for dependency injection, alongside comprehensive testing with unit and Espresso tests.

## Key Features

- **Kotlin-Based**: Fully rewritten in Kotlin for modern, concise, and safe code.
- **Coroutines & Flow**: Utilizes Kotlin Coroutines and Flow for managing asynchronous tasks and data streams.
- **Jetpack Components**: Employs Jetpack libraries such as LiveData, ViewModel, Room Database for enhanced UI development and data management.
- **Koin for Dependency Injection**: Simplifies dependency management with Koin, a pragmatic lightweight dependency injection framework for Kotlin.
- **Testing**: Includes both unit tests and Espresso UI tests.

## Architecture

The TODOClippo app is structured around the MVVM (Model-View-ViewModel) architectural pattern, facilitating separation of concerns and making the codebase more maintainable and testable.

- **Model**: Defined within `model/ToDoModel.kt`, representing task data.
- **View**: UI components such as activities (`MainActivity.kt`, `SplashActivity.kt`) and adapters (`ToDoAdapter.kt`) display tasks to the user.
  
## Technologies Used

- **Kotlin**: The project is entirely written in Kotlin.
- **Android Jetpack**: Uses components like ViewModel, LiveData, and Room for robust app development.
- **Coroutines & Flow**: For asynchronous operations and real-time data handling.
- **Koin**: For dependency injection, improving the scalability and testability of the application.
- **Room Database**: For persistence, allowing offline data caching and ensuring data is safely stored.
- **Espresso & JUnit**: For testing of UI and logic through `MainActivityUITest.kt` and  `TaskViewModelTest.kt`.

## Running Tests

### Unit Tests

- Navigate to the `src/test/java/com/example/todo_clippo` directory.
- Right-click on a test file `TaskViewModelTest.kt` and select 'Run' to execute the tests.

### Espresso Tests

- Ensure your Android device or emulator is running.
- Navigate to the `src/androidTest/java/com/example/todo_clippo` directory.
- Right-click on `MainActivityUITest.kt` and select 'Run' to execute the Espresso UI test.

## License

This project is licensed under the MIT License. It is adapted from the [Do-It](https://github.com/msindev/Do-It) project by Mohit Singh, also under the MIT License. The adaptation includes conversion from Java to Kotlin, addition of Coroutines, Jetpack, and Koin, alongside unit and Espresso tests.
