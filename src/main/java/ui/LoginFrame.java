package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginFrame() {

        setTitle("WorkBridge - Login");

        setSize(450, 350);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();
    }

    private void createUI() {

        JPanel panel = new JPanel();

        panel.setLayout(null);

        JLabel titleLabel =
                new JLabel("WorkBridge");

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        titleLabel.setBounds(
                145, 20, 180, 40
        );

        panel.add(titleLabel);

        JLabel emailLabel =
                new JLabel("Email:");

        emailLabel.setBounds(
                70, 90, 100, 25
        );

        panel.add(emailLabel);

        emailField =
                new JTextField();

        emailField.setBounds(
                160, 90, 200, 25
        );

        panel.add(emailField);

        JLabel passwordLabel =
                new JLabel("Password:");

        passwordLabel.setBounds(
                70, 135, 100, 25
        );

        panel.add(passwordLabel);

        passwordField =
                new JPasswordField();

        passwordField.setBounds(
                160, 135, 200, 25
        );

        panel.add(passwordField);

        JButton loginButton =
                new JButton("Login");

        loginButton.setBounds(
                160, 185, 90, 35
        );

        panel.add(loginButton);

        JButton registerButton =
                new JButton("Create Account");

        registerButton.setBounds(
                140, 240, 150, 30
        );

        panel.add(registerButton);

        loginButton.addActionListener(
                e -> login()
        );

        registerButton.addActionListener(e -> {

            new RegisterFrame().setVisible(true);

            dispose();
        });

        add(panel);
    }

    private void login() {

        String email =
                emailField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        if (email.isEmpty() ||
                password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter email and password."
            );

            return;
        }

        UserDAO userDAO =
                new UserDAO();

        User user =
                userDAO.loginUser(
                        email,
                        password
                );

        if (user != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login successful!"
            );

            openDashboard(user);

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid email or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void openDashboard(User user) {

        if (user.getRole().equals("EMPLOYER")) {

            new EmployerDashboard(user)
                    .setVisible(true);

        } else if (
                user.getRole().equals("JOB_SEEKER")) {

            new JobSeekerDashboard(user)
                    .setVisible(true);

        } else if (
                user.getRole().equals("ADMIN")) {

            new AdminDashboard(user)
                    .setVisible(true);
        }

        dispose();
    }
}