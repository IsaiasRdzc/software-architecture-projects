package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import client.Models.Login;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientProxy {
    private final ObjectMapper objectMapper;

    public ClientProxy() {
        this.objectMapper = new ObjectMapper();
    }

    public boolean isUserAuthenticated(Login login) {
        String userJson = convertUserToJson(login);
        if (userJson == null) {
            return false;
        }

        if (!isServiceAvailable("authenticateUser")) {
            System.out.println("El servicio no está disponible");
            return false;
        }
        return invokeService("authenticateUser", userJson);
    }

    private String convertUserToJson(Login login) {
        try {
            return objectMapper.writeValueAsString(login);
        } catch (Exception e) {
            System.out.println("Error al convertir el login a JSON: " + e.getMessage());
            return null;
        }
    }

    private boolean isServiceAvailable(String serviceName) {
        String response = sendRequest("127.0.0.1", 80, serviceName, null);
        return response != null && response.equals("200");
    }

    private boolean invokeService(String serviceName, String inputJson) {
        String response = sendRequest("127.0.0.1", 100, serviceName, inputJson);
        return response != null && response.equals("200");
    }

    private String sendRequest(String host, int port, String serviceName, String inputJson) {
        try (Socket socket = new Socket(host, port);
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter requestWriter = new PrintWriter(socket.getOutputStream(), true)) {

            requestWriter.println(serviceName);
            if (inputJson != null) {
                requestWriter.println(inputJson);
            }

            return responseReader.readLine();
        } catch (Exception e) {
            System.out.println("Error en la solicitud al servicio " + serviceName + ": " + e.getMessage());
            return null;
        }
    }
}
