package server;

import java.util.Hashtable;
import java.util.jar.Attributes;

public class Server {
    private Hashtable<String, String> registeredUsers;

    public Server() {
        registeredUsers = new Hashtable<>();
        // Demo/test credentials for authentication demonstration
        registeredUsers.put("testuser", "testpass123");
        registeredUsers.put("testuser2", "testpass456");
    }

    public boolean authenticateUser(Attributes userAttributes) {
        String username = userAttributes.getValue("username");
        String password = userAttributes.getValue("password");

        return registeredUsers.containsKey(username) && registeredUsers.get(username).equals(password);
    }
}
