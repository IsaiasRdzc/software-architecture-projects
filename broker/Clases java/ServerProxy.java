package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServerProxy {
    private final int portNumber;

    public ServerProxy(int port) {
        this.portNumber = port;
        new ObjectMapper();
    }

    public void startServer() {
        System.out.println("El ServerProxy está escuchando en el puerto " + portNumber);

        try (var listener = new ServerSocket(portNumber)) {
            registerServiceWithBroker();
            var pool = Executors.newFixedThreadPool(50);
            while (true) {
                pool.execute(new authenticateUserProcessor(listener.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerServiceWithBroker() {
        try (Socket socket = new Socket("localhost", 90);
                PrintWriter outputWriter = new PrintWriter(socket.getOutputStream(), true);
                Scanner inputScanner = new Scanner(socket.getInputStream())) {

            outputWriter.println("authenticateUser");
            outputWriter.println("127.0.0.1");
            outputWriter.println(portNumber);

            String respuesta = inputScanner.nextLine();
            if ("200".equals(respuesta)) {
                System.out.println("Servicio registrado correctamente.");
            } else {
                System.out.println("Error al registrar el servicio.");
            }
        } catch (IOException e) {
            System.out.println("Error al registrar el servicio: " + e.getMessage());
        }
    }

    public static class authenticateUserProcessor implements Runnable {

        private final Socket socketBroker;
        private final ObjectMapper objectMapper;

        authenticateUserProcessor(Socket socketBroker) {
            this.socketBroker = socketBroker;
            this.objectMapper = new ObjectMapper();
        }

        @Override
        public synchronized void run() {
            try {
                var outputWriter = new PrintWriter(socketBroker.getOutputStream(), true);
                var inputScanner = new Scanner(socketBroker.getInputStream());

                var rawInputData = inputScanner.nextLine();

                JsonNode jsonData = objectMapper.readTree(rawInputData);

                var requestAttributes = new Attributes();
                requestAttributes.put(new Name("username"), jsonData.get("username").asText());
                requestAttributes.put(new Name("password"), jsonData.get("password").asText());

                var serverInstance = new Server();
                var isUserAuthenticated = serverInstance.authenticateUser(requestAttributes);

                if (isUserAuthenticated) {
                    outputWriter.println("200");
                } else {
                    outputWriter.println("400");
                }
                inputScanner.close();
            } catch (IOException e) {
                System.out.println("Error al manejar la solicitud del Broker: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        int puerto = 1080;
        ServerProxy proxy = new ServerProxy(puerto);
        proxy.startServer();
    }
}
