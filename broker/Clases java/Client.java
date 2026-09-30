package client;

import client.Models.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Client::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(510, 200);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 5, 5));
        panel.setBackground(Color.decode("#4372C4"));

        JLabel userLabel = new JLabel("Login:");
        JTextField userText = new JTextField();
        JLabel passLabel = new JLabel("Contraseña:");
        JPasswordField passText = new JPasswordField();

        JLabel resultLabel = new JLabel("");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        panel.add(userLabel);
        panel.add(userText);
        panel.add(passLabel);
        panel.add(passText);
        panel.add(okButton);
        panel.add(cancelButton);
        panel.add(resultLabel);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login();
                login.username = userText.getText();
                login.password = new String(passText.getPassword());

                if (registerInProxy(login)) {
                    resultLabel.setText("Bienvenido " + login.username);
                    resultLabel.setForeground(Color.GREEN);
                } else {
                    resultLabel.setText("Usuario o contraseña invalidos");
                    userText.setText("");
                    passText.setText("");
                    resultLabel.setForeground(Color.RED);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private static boolean registerInProxy(Login login) {
        var proxy = new ClientProxy();
        return proxy.isUserAuthenticated(login);
    }
}
