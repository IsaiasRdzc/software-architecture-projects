package View;

import Controller.CourseController;
import Model.Course;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterCourse extends View {
    private JTextField courseNameField;
    private JTextField courseCodeField;
    private JButton registerButton;
    private JLabel messageLabel;
    private JButton clearButton;


    public RegisterCourse(CourseController controller) {
        super(controller);
        initializeUI();
    }

    private void initializeUI() {
        JFrame frame = new JFrame("Registrar Curso");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(4, 2));

        JLabel nameLabel = new JLabel("Nombre del curso:");
        courseNameField = new JTextField();
        JLabel codeLabel = new JLabel("Código del curso:");
        courseCodeField = new JTextField();
        registerButton = new JButton("Registrar");
        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);

        clearButton = new JButton("Limpiar");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                courseNameField.setText("");
                courseCodeField.setText("");
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseName = courseNameField.getText();
                String courseCode = courseCodeField.getText();
                controller.handdleEvent(new Course(courseCode, courseName));
            }
        });

        frame.add(nameLabel);
        frame.add(courseNameField);
        frame.add(codeLabel);
        frame.add(courseCodeField);
        frame.add(registerButton);
        frame.add(messageLabel);
        frame.add(clearButton);

        frame.setVisible(true);
    }

    @Override
    public void update(Object newData) {
        if (newData instanceof List<?>) {

            messageLabel.setText("Se ha dado de alta el curso de manera exitosa.");
            messageLabel.setForeground(Color.GREEN);

        } else if (newData == null) {

            messageLabel.setText("Error: ya existe un curso con el código proporcionado.");
            messageLabel.setForeground(Color.RED);
        }
    }
}
