package ui;

import model.User;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    private User admin;

    public AdminDashboard(User user) {
        this.admin = user;

        setTitle("WorkBridge - Admin Dashboard");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
    }

    private void createUI() {

        setLayout(new BorderLayout());

        // =========================
        // TOP PANEL
        // =========================

        JPanel topPanel = new JPanel(new BorderLayout());

        JLabel welcomeLabel = new JLabel(
                "Welcome Administrator"
        );

        welcomeLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        welcomeLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        topPanel.add(
                welcomeLabel,
                BorderLayout.CENTER
        );

        add(topPanel, BorderLayout.NORTH);


        // =========================
        // BUTTON PANEL
        // =========================

        JPanel buttonPanel = new JPanel(
                new GridLayout(3, 1, 15, 15)
        );

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        40, 150, 40, 150
                )
        );


        JButton manageUsersButton =
                new JButton("Manage Users");

        JButton manageJobsButton =
                new JButton("Manage Jobs");

        JButton logoutButton =
                new JButton("Logout");


        buttonPanel.add(manageUsersButton);
        buttonPanel.add(manageJobsButton);
        buttonPanel.add(logoutButton);

        add(
                buttonPanel,
                BorderLayout.CENTER
        );


        // =========================
        // BUTTON ACTIONS
        // =========================

        manageUsersButton.addActionListener(e -> {

            UserManagementFrame frame =
                    new UserManagementFrame();

            frame.setVisible(true);
        });


        manageJobsButton.addActionListener(e -> {

            JobManagementFrame frame =
                    new JobManagementFrame();

            frame.setVisible(true);
        });


        logoutButton.addActionListener(e -> {

            int confirm =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to logout?",
                            "Logout",
                            JOptionPane.YES_NO_OPTION
                    );

            if (confirm == JOptionPane.YES_OPTION) {

                dispose();

                new LoginFrame().setVisible(true);
            }
        });
    }
}