# Broker Architecture - Authentication System

## Architectural Style

Implements the **Broker architectural pattern** with 3 separate processes: Broker, Client, and Server. Communication occurs over TCP sockets, with the Broker running 3 listener threads on ports 80, 90, and 100 for service discovery, registration, and invocation respectively.

## Tech Stack

- Plain Java (no build tool)
- Jackson library for JSON marshalling
- Swing for client GUI
- TCP sockets for inter-process communication
- ~8 files, ~469 lines of code

## Architecture Details

- **Service registry** stored in a `HashMap<String, ConnectionInfo>`
- **ClientProxy** marshalls a Login object to JSON via Jackson, queries the Broker for service discovery, then invokes the service
- **ServerProxy** registers itself with the Broker, unmarshalls incoming JSON, and delegates to `Server.authenticateUser()`
- **Proxy pattern** - ClientProxy and ServerProxy hide network details from Client and Server
- **Thread pool** of 50 threads (`Executors.newFixedThreadPool(50)`) for concurrent request handling
- **Swing GUI** login form on the client side

## How to Build and Run

This project has no build tool. Compile and run the three processes separately:

```bash
# Compile all Java files (ensure Jackson JARs are in classpath)
javac -cp ".:jackson-databind.jar:jackson-core.jar:jackson-annotations.jar" Clases\ java/*.java

# Run the Broker (port 80, 90, 100)
java -cp ".:jackson-databind.jar:jackson-core.jar:jackson-annotations.jar" broker.Broker

# Run the Server (registers with Broker, listens on port 1080)
java -cp ".:jackson-databind.jar:jackson-core.jar:jackson-annotations.jar" server.ServerProxy

# Run the Client (Swing GUI)
java -cp ".:jackson-databind.jar:jackson-core.jar:jackson-annotations.jar" client.Client
```

Demo credentials: `testuser` / `testpass123` or `testuser2` / `testpass456`

## What This Demonstrates

This project demonstrates how the Broker pattern decouples clients from servers by introducing middleware that handles service discovery, registration, and invocation. The use of proxies for marshalling/unmarshalling and a centralized service registry shows how distributed systems can achieve location transparency and dynamic service binding.
