package ui;

import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminDashboard extends JFrame {

    private final User admin;

    // =========================
    // COLORS
    // =========================

    private final Color PRIMARY =
            new Color(37, 99, 235);

    private final Color PRIMARY_DARK =
            new Color(29, 78, 216);

    private final Color SIDEBAR =
            new Color(17, 24, 39);

    private final Color BACKGROUND =
            new Color(245, 247, 250);

    private final Color WHITE =
            Color.WHITE;

    private final Color TEXT =
            new Color(31, 41, 55);

    private final Color MUTED =
            new Color(107, 114, 128);

    private final Color BORDER =
            new Color(229, 231, 235);

    public AdminDashboard(User user) {

        this.admin = user;

        setTitle("WorkBridge - Admin Dashboard");
        setSize(1100, 700);
        setMinimumSize(new Dimension(950, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
    }


    // =========================================================
    // MAIN UI
    // =========================================================

    private void createUI() {

        JPanel rootPanel =
                new JPanel(new BorderLayout());

        rootPanel.setBackground(BACKGROUND);

        setContentPane(rootPanel);


        // =========================
        // SIDEBAR
        // =========================

        JPanel sidebar =
                createSidebar();

        rootPanel.add(
                sidebar,
                BorderLayout.WEST
        );


        // =========================
        // MAIN CONTENT
        // =========================

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.setBackground(BACKGROUND);

        mainPanel.setBorder(
                new EmptyBorder(
                        25,
                        30,
                        25,
                        30
                )
        );


        // =========================
        // HEADER
        // =========================

        JPanel header =
                createHeader();

        mainPanel.add(
                header,
                BorderLayout.NORTH
        );


        // =========================
        // CENTER CONTENT
        // =========================

        JPanel contentPanel =
                new JPanel();

        contentPanel.setLayout(
                new BoxLayout(
                        contentPanel,
                        BoxLayout.Y_AXIS
                )
        );

        contentPanel.setBackground(BACKGROUND);

        contentPanel.setBorder(
                new EmptyBorder(
                        25,
                        0,
                        0,
                        0
                )
        );


        // Dashboard title

        JLabel overviewLabel =
                new JLabel("Platform Overview");

        overviewLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        overviewLabel.setForeground(TEXT);

        overviewLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        contentPanel.add(
                overviewLabel
        );

        contentPanel.add(
                Box.createVerticalStrut(15)
        );


        // =========================
        // STAT CARDS
        // =========================

        JPanel statsPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                18,
                                0
                        )
                );

        statsPanel.setBackground(
                BACKGROUND
        );

        statsPanel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        statsPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        125
                )
        );


        statsPanel.add(
                createStatCard(
                        "Users",
                        "Manage registered users",
                        "USER",
                        PRIMARY
                )
        );

        statsPanel.add(
                createStatCard(
                        "Jobs",
                        "Manage job postings",
                        "JOB",
                        new Color(16, 185, 129)
                ));

        statsPanel.add(
                createStatCard(
                        "Platform",
                        "WorkBridge administration",
                        "ADMIN",
                        new Color(139, 92, 246)
                ));


        contentPanel.add(
                statsPanel
        );

        contentPanel.add(
                Box.createVerticalStrut(30)
        );


        // =========================
        // MANAGEMENT SECTION
        // =========================

        JLabel managementLabel =
                new JLabel(
                        "Management"
                );

        managementLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        managementLabel.setForeground(
                TEXT
        );

        managementLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        contentPanel.add(
                managementLabel
        );

        contentPanel.add(
                Box.createVerticalStrut(15)
        );


        JPanel managementPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                18,
                                0
                        )
                );

        managementPanel.setBackground(
                BACKGROUND
        );

        managementPanel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        managementPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        170
                )
        );


        managementPanel.add(
                createManagementCard(
                        "Manage Users",
                        "View and manage registered "
                                + "job seekers and employers.",
                        "Open User Management",
                        () -> openUserManagement()
                )
        );


        managementPanel.add(
                createManagementCard(
                        "Manage Jobs",
                        "Review and manage jobs "
                                + "posted on WorkBridge.",
                        "Open Job Management",
                        () -> openJobManagement()
                )
        );


        contentPanel.add(
                managementPanel
        );

        contentPanel.add(
                Box.createVerticalStrut(25)
        );


        // =========================
        // ADMIN ACTIVITY
        // =========================

        JPanel activityPanel =
                createActivityPanel();

        activityPanel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        contentPanel.add(
                activityPanel
        );


        // =========================
        // SCROLL
        // =========================

        JScrollPane scrollPane =
                new JScrollPane(
                        contentPanel
                );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollPane.setBackground(
                BACKGROUND
        );

        scrollPane.getViewport()
                .setBackground(BACKGROUND);

        scrollPane.setHorizontalScrollBarPolicy(
                ScrollPaneConstants
                        .HORIZONTAL_SCROLLBAR_NEVER
        );

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        rootPanel.add(
                mainPanel,
                BorderLayout.CENTER
        );
    }


    // =========================================================
    // SIDEBAR
    // =========================================================

    private JPanel createSidebar() {

        JPanel sidebar =
                new JPanel(
                        new BorderLayout()
                );

        sidebar.setPreferredSize(
                new Dimension(
                        235,
                        700
                )
        );

        sidebar.setBackground(
                SIDEBAR
        );


        // =========================
        // LOGO
        // =========================

        JPanel logoPanel =
                new JPanel();

        logoPanel.setLayout(
                new BoxLayout(
                        logoPanel,
                        BoxLayout.Y_AXIS
                )
        );

        logoPanel.setBackground(
                SIDEBAR
        );

        logoPanel.setBorder(
                new EmptyBorder(
                        30,
                        25,
                        30,
                        20
                )
        );


        JLabel logo =
                new JLabel("WorkBridge");

        logo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
        );

        logo.setForeground(
                WHITE
        );


        JLabel adminLabel =
                new JLabel("ADMIN PORTAL");

        adminLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        adminLabel.setForeground(
                new Color(
                        156,
                        163,
                        175
                )
        );


        logoPanel.add(logo);

        logoPanel.add(
                Box.createVerticalStrut(5)
        );

        logoPanel.add(adminLabel);


        sidebar.add(
                logoPanel,
                BorderLayout.NORTH
        );


        // =========================
        // MENU
        // =========================

        JPanel menuPanel =
                new JPanel();

        menuPanel.setLayout(
                new BoxLayout(
                        menuPanel,
                        BoxLayout.Y_AXIS
                )
        );

        menuPanel.setBackground(
                SIDEBAR
        );

        menuPanel.setBorder(
                new EmptyBorder(
                        10,
                        15,
                        10,
                        15
                )
        );


        JButton dashboardButton =
                createSidebarButton(
                        "Dashboard"
                );

        JButton usersButton =
                createSidebarButton(
                        "Manage Users"
                );

        JButton jobsButton =
                createSidebarButton(
                        "Manage Jobs"
                );


        menuPanel.add(
                dashboardButton
        );

        menuPanel.add(
                Box.createVerticalStrut(8)
        );

        menuPanel.add(
                usersButton
        );

        menuPanel.add(
                Box.createVerticalStrut(8)
        );

        menuPanel.add(
                jobsButton
        );


        dashboardButton.addActionListener(
                e -> {
                    // Already on dashboard
                }
        );


        usersButton.addActionListener(
                e -> openUserManagement()
        );


        jobsButton.addActionListener(
                e -> openJobManagement()
        );


        sidebar.add(
                menuPanel,
                BorderLayout.CENTER
        );


        // =========================
        // LOGOUT
        // =========================

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout()
                );

        bottomPanel.setBackground(
                SIDEBAR
        );

        bottomPanel.setBorder(
                new EmptyBorder(
                        15,
                        15,
                        20,
                        15
                )
        );


        JButton logoutButton =
                new JButton("Logout");

        logoutButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        logoutButton.setForeground(
                WHITE
        );

        logoutButton.setBackground(
                new Color(
                        127,
                        29,
                        29
                )
        );

        logoutButton.setFocusPainted(false);

        logoutButton.setBorder(
                BorderFactory.createEmptyBorder(
                        12,
                        15,
                        12,
                        15
                )
        );

        logoutButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        logoutButton.addActionListener(
                e -> logout()
        );


        bottomPanel.add(
                logoutButton,
                BorderLayout.CENTER
        );


        sidebar.add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        return sidebar;
    }


    // =========================================================
    // HEADER
    // =========================================================

    private JPanel createHeader() {

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(
                BACKGROUND
        );


        JLabel title =
                new JLabel(
                        "Admin Dashboard"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        30
                )
        );

        title.setForeground(
                TEXT
        );


        String adminName =
                admin != null &&
                        admin.getName() != null
                        ? admin.getName()
                        : "Administrator";


        JLabel welcome =
                new JLabel(
                        "Welcome back, "
                                + adminName
                                + "  "
                );

        welcome.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        welcome.setForeground(
                MUTED
        );


        JPanel textPanel =
                new JPanel();

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        textPanel.setBackground(
                BACKGROUND
        );

        textPanel.add(title);

        textPanel.add(
                Box.createVerticalStrut(5)
        );

        textPanel.add(welcome);


        header.add(
                textPanel,
                BorderLayout.WEST
        );


        return header;
    }


    // =========================================================
    // STAT CARD
    // =========================================================

    private JPanel createStatCard(
            String title,
            String subtitle,
            String iconText,
            Color accent) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(
                WHITE
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                18,
                                18,
                                18,
                                18
                        )
                )
        );


        JPanel icon =
                new JPanel(
                        new GridBagLayout()
                );

        icon.setPreferredSize(
                new Dimension(
                        55,
                        55
                )
        );

        icon.setBackground(
                accent
        );


        JLabel iconLabel =
                new JLabel(
                        iconText
                );

        iconLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        10
                )
        );

        iconLabel.setForeground(
                WHITE
        );

        icon.add(iconLabel);


        JPanel text =
                new JPanel();

        text.setLayout(
                new BoxLayout(
                        text,
                        BoxLayout.Y_AXIS
                )
        );

        text.setBackground(
                WHITE
        );

        text.setBorder(
                new EmptyBorder(
                        0,
                        15,
                        0,
                        0
                )
        );


        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        titleLabel.setForeground(
                TEXT
        );


        JLabel subtitleLabel =
                new JLabel(
                        "<html>"
                                + subtitle
                                + "</html>"
                );

        subtitleLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        subtitleLabel.setForeground(
                MUTED
        );


        text.add(
                titleLabel
        );

        text.add(
                Box.createVerticalStrut(6)
        );

        text.add(
                subtitleLabel
        );


        card.add(
                icon,
                BorderLayout.WEST
        );

        card.add(
                text,
                BorderLayout.CENTER
        );


        return card;
    }


    // =========================================================
    // MANAGEMENT CARD
    // =========================================================

    private JPanel createManagementCard(
            String title,
            String description,
            String buttonText,
            Runnable action) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(
                WHITE
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                20,
                                20,
                                20,
                                20
                        )
                )
        );


        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        19
                )
        );

        titleLabel.setForeground(
                TEXT
        );


        JLabel descriptionLabel =
                new JLabel(
                        "<html>"
                                + description
                                + "</html>"
                );

        descriptionLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        descriptionLabel.setForeground(
                MUTED
        );


        JButton button =
                new JButton(
                        buttonText
                );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        button.setForeground(
                WHITE
        );

        button.setBackground(
                PRIMARY
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        9,
                        15,
                        9,
                        15
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        button.addActionListener(
                e -> action.run()
        );


        JPanel textPanel =
                new JPanel();

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        textPanel.setBackground(
                WHITE
        );

        textPanel.add(titleLabel);

        textPanel.add(
                Box.createVerticalStrut(8)
        );

        textPanel.add(
                descriptionLabel
        );


        card.add(
                textPanel,
                BorderLayout.CENTER
        );

        card.add(
                button,
                BorderLayout.SOUTH
        );


        return card;
    }


    // =========================================================
    // ACTIVITY PANEL
    // =========================================================

    private JPanel createActivityPanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(
                WHITE
        );

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                20,
                                20,
                                20,
                                20
                        )
                )
        );


        JLabel title =
                new JLabel(
                        "Administration"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        title.setForeground(
                TEXT
        );


        JLabel description =
                new JLabel(
                        "<html>"
                                + "Use the management tools to "
                                + "control WorkBridge users and "
                                + "job postings."
                                + "<br><br>"
                                + "The administrator can review "
                                + "registered accounts and maintain "
                                + "the job marketplace."
                                + "</html>"
                );

        description.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        description.setForeground(
                MUTED
        );


        panel.add(
                title,
                BorderLayout.NORTH
        );

        panel.add(
                description,
                BorderLayout.CENTER
        );


        return panel;
    }


    // =========================================================
    // SIDEBAR BUTTON
    // =========================================================

    private JButton createSidebarButton(
            String text) {

        JButton button =
                new JButton(text);

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        button.setForeground(
                WHITE
        );

        button.setBackground(
                SIDEBAR
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        12,
                        15,
                        12,
                        15
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                ));


        return button;
    }


    // =========================================================
    // OPEN USER MANAGEMENT
    // =========================================================

    private void openUserManagement() {

        UserManagementFrame frame =
                new UserManagementFrame();

        frame.setVisible(true);
    }


    // =========================================================
    // OPEN JOB MANAGEMENT
    // =========================================================

    private void openJobManagement() {

        JobManagementFrame frame =
                new JobManagementFrame();

        frame.setVisible(true);
    }


    // =========================================================
    // LOGOUT
    // =========================================================

    private void logout() {

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (confirm ==
                JOptionPane.YES_OPTION) {

            dispose();

            new LoginFrame().setVisible(true);
        }
    }
}