package broker;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class Broker {
    private static HashMap<String, ConnectionInfo> registeredServices;

    public static void main(String[] args) {
        try {
            registeredServices = new HashMap<>();

            Thread searchServiceThread = new Thread(new SearchServiceHandler(80));
            Thread registrationThread = new Thread(new ServiceRegistrationHandler(90));
            Thread invocationThread = new Thread(new ServiceInvocationHandler(100));

            var pool = Executors.newFixedThreadPool(50);
            pool.execute(searchServiceThread::start);
            pool.execute(registrationThread::start);
            pool.execute(invocationThread::start);

            System.out.println("Broker en ejecución");

        } catch (IOException e) {
            System.out.println("Error al iniciar el broker: " + e.getMessage());
        }
    }

    private static class SearchServiceHandler implements Runnable {
        private final ServerSocket serverSocket;

        public SearchServiceHandler(int portNumber) throws IOException {
            this.serverSocket = new ServerSocket(portNumber);
        }

        @Override
        public void run() {

            while (true) {
                try (Socket socketCliente = serverSocket.accept();
                        PrintWriter out = new PrintWriter(socketCliente.getOutputStream(), true);
                        Scanner in = new Scanner(socketCliente.getInputStream())) {

                    String serviceName = in.nextLine();
                    String responseCode = registeredServices.containsKey(serviceName) ? "200" : "400";
                    out.println(responseCode);

                } catch (IOException e) {
                    System.out.println("Error durante la búsqueda de servicio: " + e.getMessage());
                }
            }
        }
    }

    private static class ServiceRegistrationHandler implements Runnable {
        private final ServerSocket serverSocket;

        public ServiceRegistrationHandler(int portNumber) throws IOException {
            this.serverSocket = new ServerSocket(portNumber);
        }

        @Override
        public void run() {
            while (true) {
                try (Socket socketCliente = serverSocket.accept();
                        PrintWriter out = new PrintWriter(socketCliente.getOutputStream(), true);
                        Scanner in = new Scanner(socketCliente.getInputStream())) {

                    String serviceName = in.nextLine();
                    String serviceIpAddress = in.nextLine();
                    int servicePort = Integer.parseInt(in.nextLine());

                    String response;
                    if (registeredServices.containsKey(serviceName)) {
                        response = "400";
                    } else {
                        registeredServices.put(serviceName, new ConnectionInfo(serviceIpAddress, servicePort));
                        response = "200";
                    }
                    out.println(response);

                } catch (IOException e) {
                    System.out.println("Error durante el registro de servicio: " + e.getMessage());
                }
            }
        }
    }

    private static class ServiceInvocationHandler implements Runnable {
        private final ServerSocket serverSocket;

        public ServiceInvocationHandler(int portNumber) throws IOException {
            this.serverSocket = new ServerSocket(portNumber);
        }

        @Override
        public void run() {

            while (true) {
                try (Socket socketCliente = serverSocket.accept();
                        PrintWriter outCliente = new PrintWriter(socketCliente.getOutputStream(), true);
                        Scanner inCliente = new Scanner(socketCliente.getInputStream())) {

                    String serviceName = inCliente.nextLine();
                    String inputJSON = inCliente.nextLine();

                    if (!registeredServices.containsKey(serviceName)) {
                        outCliente.println("400"); // Servicio no encontrado
                        continue;
                    }

                    // Conexión al proveedor del servicio
                    ConnectionInfo infoConexion = registeredServices.get(serviceName);
                    try (Socket socketServicio = new Socket(infoConexion.getIp(), infoConexion.getPort());
                            PrintWriter outServicio = new PrintWriter(socketServicio.getOutputStream(), true);
                            Scanner inServicio = new Scanner(socketServicio.getInputStream())) {

                        outServicio.println(inputJSON);
                        String serviceResponse = inServicio.nextLine();

                        String respuestaCliente = "200".equals(serviceResponse) ? "200" : "400";
                        outCliente.println(respuestaCliente);

                    } catch (IOException e) {
                        System.out.println("Error durante la conexión al servicio: " + e.getMessage());
                    }
                } catch (IOException e) {
                    System.out.println("Error durante la invocación de servicio: " + e.getMessage());
                }
            }
        }
    }
}
