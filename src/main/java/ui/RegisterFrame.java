
package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    private final Color PRIMARY = new Color(37, 99, 235);
    private final Color DARK = new Color(30, 41, 59);
    private final Color LIGHT_BG = new Color(241, 245, 249);
    private final Color CARD_BG = Color.WHITE;
    private final Color MUTED = new Color(100, 116, 139);

    public RegisterFrame() {

        setTitle("WorkBridge - Create Account");
        setSize(1000, 700);

        setMinimumSize(new Dimension(700, 550));

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();
    }

    private void createUI() {

        JPanel background = new JPanel(
                new GridBagLayout()
        );

        background.setBackground(LIGHT_BG);

        JPanel card = new JPanel(
                new BorderLayout()
        );

        card.setPreferredSize(
                new Dimension(460, 570)
        );

        card.setBackground(CARD_BG);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(226, 232, 240)
                        ),
                        new EmptyBorder(
                                35, 45, 35, 45
                        )
                )
        );

        JPanel content = new JPanel();

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        content.setBackground(CARD_BG);

        JLabel logo =
                new JLabel("WorkBridge");

        logo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        30
                )
        );

        logo.setForeground(PRIMARY);
        logo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel subtitle =
                new JLabel(
                        "Join the professional job network"
                );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(MUTED);

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        content.add(logo);
        content.add(Box.createVerticalStrut(7));
        content.add(subtitle);
        content.add(Box.createVerticalStrut(25));

        JLabel heading =
                new JLabel("Create Your Account");

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        21
                )
        );

        heading.setForeground(DARK);

        heading.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        content.add(heading);
        content.add(Box.createVerticalStrut(22));

        JLabel nameLabel =
                createLabel("Full Name");

        content.add(nameLabel);
        content.add(Box.createVerticalStrut(5));

        nameField =
                new JTextField();

        styleField(nameField);

        content.add(nameField);

        content.add(Box.createVerticalStrut(13));

        JLabel emailLabel =
                createLabel("Email Address");

        content.add(emailLabel);
        content.add(Box.createVerticalStrut(5));

        emailField =
                new JTextField();

        styleField(emailField);

        content.add(emailField);

        content.add(Box.createVerticalStrut(13));

        JLabel passwordLabel =
                createLabel("Password");

        content.add(passwordLabel);
        content.add(Box.createVerticalStrut(5));

        passwordField =
                new JPasswordField();

        styleField(passwordField);

        content.add(passwordField);

        content.add(Box.createVerticalStrut(13));

        JLabel roleLabel =
                createLabel("Account Type");

        content.add(roleLabel);
        content.add(Box.createVerticalStrut(5));

        roleComboBox =
                new JComboBox<>(
                        new String[]{
                                "Employer",
                                "Job Seeker"
                        }
                );

        styleComboBox(roleComboBox);

        content.add(roleComboBox);

        content.add(Box.createVerticalStrut(22));

        JButton registerButton =
                createPrimaryButton("Create Account");

        content.add(registerButton);

        content.add(Box.createVerticalStrut(12));

        JButton backButton =
                createLinkButton("Back to Login");

        content.add(backButton);

        registerButton.addActionListener(
                e -> register()
        );

        backButton.addActionListener(e -> {

            new LoginFrame().setVisible(true);

            dispose();
        });

        card.add(
                content,
                BorderLayout.CENTER
        );

        background.add(card);

        add(background);

        getRootPane().setDefaultButton(
                registerButton
        );
    }

    private JLabel createLabel(String text) {

        JLabel label =
                new JLabel(text);

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

    private void styleField(
            JTextField field
    ) {

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        40
                )
        );

        field.setPreferredSize(
                new Dimension(320, 40)
        );

        field.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(203, 213, 225)
                        ),
                        new EmptyBorder(
                                7, 12, 7, 12
                        )
                )
        );

        field.setBackground(Color.WHITE);
    }

    private void styleComboBox(
            JComboBox<String> comboBox
    ) {

        comboBox.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        40
                )
        );

        comboBox.setPreferredSize(
                new Dimension(320, 40)
        );

        comboBox.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        comboBox.setBackground(Color.WHITE);

        comboBox.setBorder(
                BorderFactory.createLineBorder(
                        new Color(203, 213, 225)
                )
        );
    }

    private JButton createPrimaryButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setPreferredSize(
                new Dimension(320, 42)
        );

        button.setMaximumSize(
                new Dimension(320, 42)
        );

        button.setMinimumSize(
                new Dimension(320, 42)
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

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        button.setForeground(PRIMARY);
        button.setBackground(CARD_BG);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
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

