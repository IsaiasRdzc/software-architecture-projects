# MVC Architecture - Course Registration System

## Architectural Style

Implements the **MVC (Model-View-Controller) pattern** via abstract `Model`, `View`, and `Controller` base classes. The Model uses the **Observer pattern** to maintain a `List<View>` and calls `view.update()` on each when data changes.

## Tech Stack

- Java 22
- Maven build tool
- Swing for desktop GUI
- No REST API

## Architecture Details

- **Abstract Model** base class maintains registered views and notifies them of changes
- **Abstract View** base class holds a reference to a Controller and defines an `update(Object)` method
- **Abstract Controller** base class holds a reference to the Model and defines a `handdleEvent(Object)` method
- **Concrete model `Courses`** - stores an `ArrayList<Course>`, validates duplicate course codes
- **Concrete controller `CourseController`** - creates Course objects and delegates to the model
- **Two views**: `RegisterCourse` (form to add courses) and `RegisterToCourse` (dropdown of available courses) - both auto-update via the Observer mechanism

## How to Build and Run

```bash
# Build with Maven
mvn clean package

# Run the application
mvn exec:java -Dexec.mainClass="org.example.Main"
```

## What This Demonstrates

This project demonstrates clean separation of concerns through the MVC triad, where the Model, View, and Controller are abstracted into independent layers connected by Observer notifications. The bidirectional data flow - View sends events to Controller, Controller manipulates Model, Model broadcasts updates to all Views - shows how MVC keeps views synchronized without tight coupling.
