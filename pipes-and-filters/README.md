# Pipes and Filters Architecture - KWIC System

## Architectural Style

Implements the **Pipes and Filters architectural style** as a **KWIC (Key Word In Context) system**, where data flows through a chain of independent, reusable filters connected by a pipe (`Tuberia`) that composes them via nested method calls.

## Tech Stack

- Java 22
- Maven build tool
- CLI application (reads stdin, prints stdout)
- No REST API

## Architecture Details

- **Abstract `Filtro` base class** with `ejecutar(Object)` method - all filters extend it
- **Pipeline of 5 stages**: Input -> Tokenizer -> Combinations -> Sorting -> Output
- **Pipe implemented via nested function composition** - output of one filter feeds directly as input to the next
- **Tokenizer filter** - splits input string by whitespace into `List<String>`
- **Combinations filter** - generates KWIC circular rotations of word order
- **Sorting filter** - alphabetically sorts the list via `Collections.sort()`
- **Input/Output** - source and sink components (not filters) that read from stdin and print to stdout

## How to Build and Run

```bash
# Build with Maven
mvn clean package

# Run the application
mvn exec:java -Dexec.mainClass="com.mycompany.arqui_filtros_y_tuberias.ARQUI_FILTROS_Y_TUBERIAS"
```

Enter a phrase when prompted. The program will output all circular rotations of the words, sorted alphabetically.

## What This Demonstrates

This project demonstrates how complex processing can be decomposed into simple, self-contained transformation stages. Each filter extends an abstract base class with a single method, making filters independent and interchangeable - you can reorder, add, or remove stages without modifying other filters, which is the key benefit of the pipes-and-filters style.
