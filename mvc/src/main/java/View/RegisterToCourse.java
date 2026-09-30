package View;

import Controller.Controller;
import Model.Course;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterToCourse extends View {
    private JComboBox<Course> courseDropdown;
    private JTextField fullNameField;
    private JTextField emailField;
    private JTextField mobileField;
    private JTextField companyField;
    private JButton okButton;
    private JButton clearButton;

    public RegisterToCourse(Controller controller) {
        super(controller);
        initializeUI();
    }

    private void initializeUI() {
        JFrame frame = new JFrame("Registrarte a un curso");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLayout(new GridLayout(7, 2));

        JLabel nameLabel = new JLabel("Nombre Completo:");
        fullNameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel mobileLabel = new JLabel("Teléfono:");
        mobileField = new JTextField();
        JLabel companyLabel = new JLabel("Compañia:");
        companyField = new JTextField();
        JLabel courseLabel = new JLabel("Cursos Disponibles:");

        courseDropdown = new JComboBox<>();

        okButton = new JButton("OK");
        clearButton = new JButton("Clear");


        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fullNameField.setText("");
                emailField.setText("");
                mobileField.setText("");
                companyField.setText("");
            }
        });

        // Añadir componentes al frame
        frame.add(nameLabel);
        frame.add(fullNameField);
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(mobileLabel);
        frame.add(mobileField);
        frame.add(companyLabel);
        frame.add(companyField);
        frame.add(courseLabel);
        frame.add(courseDropdown);
        frame.add(okButton);
        frame.add(clearButton);

        frame.setVisible(true);
    }

    @Override
    public void update(Object newData) {
        if (newData instanceof List<?>) {
            List<Course> courses = (List<Course>) newData;

            if (courses.isEmpty()) {
                courseDropdown.removeAllItems();
            } else {
                courseDropdown.removeAllItems();
                for (Course course : courses) {
                    courseDropdown.addItem(course);
                }
            }
        } else if (newData == null) {
            courseDropdown.removeAllItems();
        }
    }
}
