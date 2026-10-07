package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    private final Color PRIMARY = new Color(37, 99, 235);
    private final Color DARK = new Color(30, 41, 59);
    private final Color LIGHT_BG = new Color(241, 245, 249);
    private final Color CARD_BG = Color.WHITE;
    private final Color MUTED = new Color(100, 116, 139);

    public LoginFrame() {

        setTitle("WorkBridge - Login");
        setSize(1000, 650);

        setMinimumSize(new Dimension(700, 500));

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();
    }

    private void createUI() {

        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(LIGHT_BG);

        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(430, 500));
        card.setBackground(CARD_BG);
        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(226, 232, 240)
                        ),
                        new EmptyBorder(35, 45, 35, 45)
                )
        );

        JPanel content = new JPanel();
        content.setLayout(
                new BoxLayout(content, BoxLayout.Y_AXIS)
        );
        content.setBackground(CARD_BG);

        JLabel logo = new JLabel("WorkBridge");
        logo.setFont(
                new Font("Arial", Font.BOLD, 30)
        );
        logo.setForeground(PRIMARY);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "Find opportunities. Build your career."
        );
        subtitle.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );
        subtitle.setForeground(MUTED);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(logo);
        content.add(Box.createVerticalStrut(8));
        content.add(subtitle);
        content.add(Box.createVerticalStrut(35));

        JLabel welcome = new JLabel("Welcome Back");
        welcome.setFont(
                new Font("Arial", Font.BOLD, 22)
        );
        welcome.setForeground(DARK);
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(welcome);
        content.add(Box.createVerticalStrut(25));

        JLabel emailLabel = createLabel("Email Address");
        content.add(emailLabel);
        content.add(Box.createVerticalStrut(6));

        emailField = new JTextField();
        styleField(emailField);
        content.add(emailField);

        content.add(Box.createVerticalStrut(18));

        JLabel passwordLabel = createLabel("Password");
        content.add(passwordLabel);
        content.add(Box.createVerticalStrut(6));

        passwordField = new JPasswordField();
        styleField(passwordField);
        content.add(passwordField);

        content.add(Box.createVerticalStrut(25));

        JButton loginButton =
                createPrimaryButton("Login");

        content.add(loginButton);

        content.add(Box.createVerticalStrut(15));

        JLabel accountLabel =
                new JLabel("Don't have an account?");

        accountLabel.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );
        accountLabel.setForeground(MUTED);
        accountLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        content.add(accountLabel);
        content.add(Box.createVerticalStrut(7));

        JButton registerButton =
                createLinkButton("Create Account");

        content.add(registerButton);

        loginButton.addActionListener(
                e -> login()
        );

        registerButton.addActionListener(e -> {

            new RegisterFrame().setVisible(true);

            dispose();
        });

        card.add(content, BorderLayout.CENTER);

        background.add(card);

        add(background);

        getRootPane().setDefaultButton(loginButton);
    }

    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        label.setForeground(DARK);

        label.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        return label;
    }

    private void styleField(JTextField field) {

        field.setPreferredSize(
                new Dimension(300, 42)
        );

        field.setMaximumSize(
                new Dimension(300, 42)
        );

        field.setMinimumSize(
                new Dimension(300, 42)
        );

        field.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        field.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(203, 213, 225)
                        ),
                        new EmptyBorder(
                                8, 12, 8, 12
                        )
                )
        );

        field.setBackground(Color.WHITE);
    }

    private JButton createPrimaryButton(
            String text
    ) {

        JButton button = new JButton(text);

        button.setPreferredSize(
                new Dimension(300, 42)
        );

        button.setMaximumSize(
                new Dimension(300, 42)
        );

        button.setMinimumSize(
                new Dimension(300, 42)
        );

        button.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    private JButton createLinkButton(
            String text
    ) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        button.setForeground(PRIMARY);
        button.setBackground(CARD_BG);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        return button;
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

