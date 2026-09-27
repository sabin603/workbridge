package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    public RegisterFrame() {

        setTitle("WorkBridge - Registration");

        setSize(450, 400);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();
    }

    private void createUI() {

        JPanel panel = new JPanel();

        panel.setLayout(null);

        JLabel title =
                new JLabel("Create WorkBridge Account");

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        title.setBounds(
                80, 25, 300, 35
        );

        panel.add(title);

        JLabel nameLabel =
                new JLabel("Name:");

        nameLabel.setBounds(
                60, 90, 100, 25
        );

        panel.add(nameLabel);

        nameField =
                new JTextField();

        nameField.setBounds(
                160, 90, 210, 25
        );

        panel.add(nameField);

        JLabel emailLabel =
                new JLabel("Email:");

        emailLabel.setBounds(
                60, 130, 100, 25
        );

        panel.add(emailLabel);

        emailField =
                new JTextField();

        emailField.setBounds(
                160, 130, 210, 25
        );

        panel.add(emailField);

        JLabel passwordLabel =
                new JLabel("Password:");

        passwordLabel.setBounds(
                60, 170, 100, 25
        );

        panel.add(passwordLabel);

        passwordField =
                new JPasswordField();

        passwordField.setBounds(
                160, 170, 210, 25
        );

        panel.add(passwordField);

        JLabel roleLabel =
                new JLabel("Account Type:");

        roleLabel.setBounds(
                60, 210, 100, 25
        );

        panel.add(roleLabel);

        roleComboBox =
                new JComboBox<>(
                        new String[]{
                                "Employer",
                                "Job Seeker"
                        }
                );

        roleComboBox.setBounds(
                160, 210, 210, 25
        );

        panel.add(roleComboBox);

        JButton registerButton =
                new JButton("Register");

        registerButton.setBounds(
                160, 260, 100, 35
        );

        panel.add(registerButton);

        JButton backButton =
                new JButton("Back to Login");

        backButton.setBounds(
                145, 315, 140, 30
        );

        panel.add(backButton);

        registerButton.addActionListener(
                e -> register()
        );

        backButton.addActionListener(e -> {

            new LoginFrame().setVisible(true);

            dispose();
        });

        add(panel);
    }

    private void register() {

        String name =
                nameField.getText().trim();

        String email =
                emailField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        String selectedRole =
                (String) roleComboBox
                        .getSelectedItem();

        if (name.isEmpty() ||
                email.isEmpty() ||
                password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields."
            );

            return;
        }

        String role;

        if (selectedRole.equals("Employer")) {

            role = "EMPLOYER";

        } else {

            role = "JOB_SEEKER";
        }

        User user =
                new User(
                        name,
                        email,
                        password,
                        role
                );

        UserDAO userDAO =
                new UserDAO();

        boolean success =
                userDAO.registerUser(user);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Account created successfully!"
            );

            new LoginFrame().setVisible(true);

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration failed. Email may already exist.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}