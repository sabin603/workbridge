package ui;

import dao.ApplicationDAO;
import dao.JobDAO;
import model.Job;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EmployerDashboard extends JFrame {

    private User employer;
    private JobDAO jobDAO;
    private ApplicationDAO applicationDAO;
    private JPanel jobsPanel;

    private JLabel totalJobsValue;
    private JLabel activeJobsValue;
    private JLabel applicantsValue;
    private JLabel selectedValue;

    private final Color BACKGROUND = new Color(245, 247, 250);
    private final Color HEADER = new Color(25, 35, 50);
    private final Color TEXT = new Color(31, 41, 55);
    private final Color SECONDARY_TEXT = new Color(107, 114, 128);
    private final Color PRIMARY = new Color(37, 99, 235);
    private final Color SUCCESS = new Color(22, 101, 52);
    private final Color DANGER = new Color(220, 38, 38);
    private final Color BORDER = new Color(229, 231, 235);

    public EmployerDashboard(User user) {

        this.employer = user;
        this.jobDAO = new JobDAO();
        this.applicationDAO = new ApplicationDAO();

        setTitle("WorkBridge - Employer Dashboard");
        setSize(1100, 750);
        setMinimumSize(new Dimension(950, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
        loadJobs();
    }

    private void createUI() {

        getContentPane().setBackground(BACKGROUND);

        setLayout(new BorderLayout());

        // =====================================================
        // HEADER
        // =====================================================

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER);
        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        18, 25, 18, 25
                )
        );

        JPanel brandPanel = new JPanel();
        brandPanel.setLayout(
                new BoxLayout(
                        brandPanel,
                        BoxLayout.Y_AXIS
                )
        );
        brandPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("WorkBridge");

        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        25
                )
        );

        JLabel subtitleLabel =
                new JLabel("Employer Recruitment Dashboard");

        subtitleLabel.setForeground(
                new Color(203, 213, 225)
        );

        subtitleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        brandPanel.add(titleLabel);
        brandPanel.add(
                Box.createVerticalStrut(3)
        );
        brandPanel.add(subtitleLabel);

        // =====================================================
        // RIGHT HEADER
        // =====================================================

        JPanel rightHeaderPanel = new JPanel();

        rightHeaderPanel.setLayout(
                new BoxLayout(
                        rightHeaderPanel,
                        BoxLayout.Y_AXIS
                )
        );

        rightHeaderPanel.setOpaque(false);

        JLabel welcomeLabel =
                new JLabel(
                        "Welcome, "
                                + safe(employer.getName())
                );

        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        welcomeLabel.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );

        JPanel headerButtons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                7,
                                0
                        )
                );

        headerButtons.setOpaque(false);

        JButton postJobButton =
                createPrimaryButton(
                        "Post New Job"
                );

        JButton refreshButton =
                createHeaderButton(
                        "Refresh"
                );

        JButton logoutButton =
                createHeaderButton(
                        "Logout"
                );

        headerButtons.add(postJobButton);
        headerButtons.add(refreshButton);
        headerButtons.add(logoutButton);

        rightHeaderPanel.add(welcomeLabel);

        rightHeaderPanel.add(
                Box.createVerticalStrut(10)
        );

        rightHeaderPanel.add(headerButtons);

        headerPanel.add(
                brandPanel,
                BorderLayout.WEST
        );

        headerPanel.add(
                rightHeaderPanel,
                BorderLayout.EAST
        );

        add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // MAIN PANEL
        // =====================================================

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                0,
                                14
                        )
                );

        mainPanel.setBackground(BACKGROUND);

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 20, 15, 20
                )
        );

        // =====================================================
        // TOP CONTENT
        // =====================================================

        JPanel topPanel =
                new JPanel();

        topPanel.setLayout(
                new BoxLayout(
                        topPanel,
                        BoxLayout.Y_AXIS
                )
        );

        topPanel.setOpaque(false);

        // =====================================================
        // SECTION HEADER
        // =====================================================

        JPanel sectionHeader =
                new JPanel(
                        new BorderLayout()
                );

        sectionHeader.setOpaque(false);

        JPanel sectionText =
                new JPanel();

        sectionText.setLayout(
                new BoxLayout(
                        sectionText,
                        BoxLayout.Y_AXIS
                )
        );

        sectionText.setOpaque(false);

        JLabel sectionTitle =
                new JLabel("Recruitment Overview");

        sectionTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        21
                )
        );

        sectionTitle.setForeground(TEXT);

        JLabel sectionDescription =
                new JLabel(
                        "Track your vacancies and candidate activity."
                );

        sectionDescription.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        sectionDescription.setForeground(
                SECONDARY_TEXT
        );

        sectionText.add(sectionTitle);

        sectionText.add(
                Box.createVerticalStrut(3)
        );

        sectionText.add(sectionDescription);

        sectionHeader.add(
                sectionText,
                BorderLayout.WEST
        );

        topPanel.add(sectionHeader);

        topPanel.add(
                Box.createVerticalStrut(12)
        );

        // =====================================================
        // STATISTICS CARDS
        // =====================================================

        JPanel statsPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                12,
                                0
                        )
                );

        statsPanel.setOpaque(false);

        JPanel totalJobsCard =
                createStatCard(
                        "Total Jobs",
                        "0",
                        PRIMARY
                );

        JPanel activeJobsCard =
                createStatCard(
                        "Active Jobs",
                        "0",
                        new Color(14, 116, 144)
                );

        JPanel applicantsCard =
                createStatCard(
                        "Applications",
                        "0",
                        new Color(124, 58, 237)
                );

        JPanel selectedCard =
                createStatCard(
                        "Selected",
                        "0",
                        SUCCESS
                );

        totalJobsValue =
                (JLabel) totalJobsCard.getClientProperty(
                        "valueLabel"
                );

        activeJobsValue =
                (JLabel) activeJobsCard.getClientProperty(
                        "valueLabel"
                );

        applicantsValue =
                (JLabel) applicantsCard.getClientProperty(
                        "valueLabel"
                );

        selectedValue =
                (JLabel) selectedCard.getClientProperty(
                        "valueLabel"
                );

        statsPanel.add(totalJobsCard);
        statsPanel.add(activeJobsCard);
        statsPanel.add(applicantsCard);
        statsPanel.add(selectedCard);

        topPanel.add(statsPanel);

        mainPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // JOB SECTION
        // =====================================================

        JPanel jobsSection =
                new JPanel(
                        new BorderLayout(
                                0,
                                8
                        )
                );

        jobsSection.setOpaque(false);

        JLabel jobsTitle =
                new JLabel("Your Job Posts");

        jobsTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        jobsTitle.setForeground(TEXT);

        jobsSection.add(
                jobsTitle,
                BorderLayout.NORTH
        );

        // =====================================================
        // JOB CARDS PANEL
        // =====================================================

        jobsPanel = new JPanel();

        jobsPanel.setLayout(
                new BoxLayout(
                        jobsPanel,
                        BoxLayout.Y_AXIS
                )
        );

        jobsPanel.setBackground(BACKGROUND);

        jobsPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        5, 2, 20, 2
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        jobsPanel
                );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollPane.setBackground(BACKGROUND);

        scrollPane.getViewport()
                .setBackground(BACKGROUND);

        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);

        jobsSection.add(
                scrollPane,
                BorderLayout.CENTER
        );

        mainPanel.add(
                jobsSection,
                BorderLayout.CENTER
        );

        add(
                mainPanel,
                BorderLayout.CENTER
        );

        // =====================================================
        // BUTTON ACTIONS
        // =====================================================

        postJobButton.addActionListener(
                e -> openPostJob()
        );

        refreshButton.addActionListener(
                e -> loadJobs()
        );

        logoutButton.addActionListener(
                e -> logout()
        );
    }

    // =========================================================
    // STAT CARD
    // =========================================================

    private JPanel createStatCard(
            String title,
            String value,
            Color accent
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER,
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                15,
                                17,
                                15,
                                17
                        )
                )
        );

        JPanel accentPanel =
                new JPanel();

        accentPanel.setBackground(accent);
        accentPanel.setPreferredSize(
                new Dimension(5, 60)
        );

        JPanel content =
                new JPanel();

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        content.setOpaque(false);

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        titleLabel.setForeground(
                SECONDARY_TEXT
        );

        JLabel valueLabel =
                new JLabel(value);

        valueLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        27
                )
        );

        valueLabel.setForeground(TEXT);

        content.add(titleLabel);

        content.add(
                Box.createVerticalStrut(5)
        );

        content.add(valueLabel);

        card.add(
                accentPanel,
                BorderLayout.WEST
        );

        card.add(
                content,
                BorderLayout.CENTER
        );

        card.putClientProperty(
                "valueLabel",
                valueLabel
        );

        return card;
    }

    // =========================================================
    // CREATE JOB CARD
    // =========================================================

    private JPanel createJobCard(Job job) {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                15,
                                10
                        )
                );

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER,
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                17,
                                20,
                                17,
                                20
                        )
                )
        );

        card.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        230
                )
        );

        // =====================================================
        // JOB INFORMATION
        // =====================================================

        JPanel informationPanel =
                new JPanel();

        informationPanel.setOpaque(false);

        informationPanel.setLayout(
                new BoxLayout(
                        informationPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel jobTitle =
                new JLabel(
                        safe(job.getTitle())
                );

        jobTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        19
                )
        );

        jobTitle.setForeground(TEXT);

        JLabel categoryLabel =
                new JLabel(
                        safe(job.getCategory())
                );

        categoryLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        categoryLabel.setForeground(PRIMARY);

        JLabel locationLabel =
                new JLabel(
                        "Location: "
                                + safe(job.getLocation())
                );

        locationLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        locationLabel.setForeground(
                SECONDARY_TEXT
        );

        JLabel salaryLabel =
                new JLabel(
                        "Salary: "
                                + safe(job.getSalary())
                );

        salaryLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        salaryLabel.setForeground(
                SUCCESS
        );

        JLabel typeLabel =
                new JLabel(
                        "Job Type: "
                                + formatJobType(
                                job.getJobType()
                        )
                );

        typeLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        typeLabel.setForeground(
                SECONDARY_TEXT
        );

        String description =
                shorten(
                        safe(job.getDescription()),
                        180
                );

        JLabel descriptionLabel =
                new JLabel(
                        "<html><b>Description:</b> "
                                + escapeHtml(
                                description
                        )
                                + "</html>"
                );

        descriptionLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        descriptionLabel.setForeground(TEXT);

        informationPanel.add(jobTitle);

        informationPanel.add(
                Box.createVerticalStrut(5)
        );

        informationPanel.add(categoryLabel);

        informationPanel.add(
                Box.createVerticalStrut(7)
        );

        informationPanel.add(locationLabel);

        informationPanel.add(
                Box.createVerticalStrut(3)
        );

        informationPanel.add(typeLabel);

        informationPanel.add(
                Box.createVerticalStrut(3)
        );

        informationPanel.add(salaryLabel);

        informationPanel.add(
                Box.createVerticalStrut(8)
        );

        informationPanel.add(descriptionLabel);

        // =====================================================
        // ACTION BUTTONS
        // =====================================================

        JPanel actionPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                7,
                                0
                        )
                );

        actionPanel.setOpaque(false);

        JButton viewApplicantsButton =
                createPrimaryButton(
                        "View Applicants"
                );

        JButton editButton =
                createSecondaryButton(
                        "Edit"
                );

        JButton deleteButton =
                createDangerButton(
                        "Delete"
                );

        viewApplicantsButton.addActionListener(
                e -> viewApplicants(job)
        );

        editButton.addActionListener(
                e -> editJob(job)
        );

        deleteButton.addActionListener(
                e -> deleteJob(job)
        );

        actionPanel.add(viewApplicantsButton);
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);

        card.add(
                informationPanel,
                BorderLayout.CENTER
        );

        card.add(
                actionPanel,
                BorderLayout.SOUTH
        );

        return card;
    }

    // =========================================================
    // LOAD JOBS
    // =========================================================

    private void loadJobs() {

        jobsPanel.removeAll();

        List<Job> jobs =
                jobDAO.getJobsByEmployer(
                        employer.getUserId()
                );

        // =====================================================
        // UPDATE STATISTICS
        // =====================================================

        int totalJobs =
                jobs == null
                        ? 0
                        : jobs.size();

        int totalApplicants =
                applicationDAO
                        .getTotalApplicantsByEmployer(
                                employer.getUserId()
                        );

        int selectedApplicants =
                applicationDAO
                        .getSelectedApplicantsByEmployer(
                                employer.getUserId()
                        );

        if (totalJobsValue != null) {
            totalJobsValue.setText(
                    String.valueOf(totalJobs)
            );
        }

        if (activeJobsValue != null) {
            activeJobsValue.setText(
                    String.valueOf(totalJobs)
            );
        }

        if (applicantsValue != null) {
            applicantsValue.setText(
                    String.valueOf(totalApplicants)
            );
        }

        if (selectedValue != null) {
            selectedValue.setText(
                    String.valueOf(selectedApplicants)
            );
        }

        // =====================================================
        // JOB LIST
        // =====================================================

        if (jobs == null || jobs.isEmpty()) {

            JPanel emptyPanel =
                    new JPanel();

            emptyPanel.setLayout(
                    new BoxLayout(
                            emptyPanel,
                            BoxLayout.Y_AXIS
                    )
            );

            emptyPanel.setOpaque(false);

            JLabel emptyTitle =
                    new JLabel(
                            "No job posts yet"
                    );

            emptyTitle.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            18
                    )
            );

            emptyTitle.setForeground(TEXT);

            emptyTitle.setAlignmentX(
                    Component.CENTER_ALIGNMENT
            );

            JLabel emptyMessage =
                    new JLabel(
                            "Click \"Post New Job\" to create your first vacancy."
                    );

            emptyMessage.setFont(
                    new Font(
                            "Segoe UI",
                            Font.PLAIN,
                            14
                    )
            );

            emptyMessage.setForeground(
                    SECONDARY_TEXT
            );

            emptyMessage.setAlignmentX(
                    Component.CENTER_ALIGNMENT
            );

            emptyPanel.add(
                    Box.createVerticalStrut(50)
            );

            emptyPanel.add(emptyTitle);

            emptyPanel.add(
                    Box.createVerticalStrut(8)
            );

            emptyPanel.add(emptyMessage);

            jobsPanel.add(emptyPanel);

        } else {

            for (Job job : jobs) {

                jobsPanel.add(
                        createJobCard(job)
                );

                jobsPanel.add(
                        Box.createVerticalStrut(12)
                );
            }
        }

        jobsPanel.revalidate();
        jobsPanel.repaint();
    }

    // =========================================================
    // POST JOB
    // =========================================================

    private void openPostJob() {

        new JobForm(
                employer
        ).setVisible(true);
    }

    // =========================================================
    // EDIT JOB
    // =========================================================

    private void editJob(Job job) {

        if (job == null) {
            return;
        }

        new EditJobForm(
                employer,
                job
        ).setVisible(true);
    }

    // =========================================================
    // DELETE JOB
    // =========================================================

    private void deleteJob(Job job) {

        if (job == null) {
            return;
        }

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete\n"
                                + "\""
                                + job.getTitle()
                                + "\"?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (
                confirm !=
                        JOptionPane.YES_OPTION
        ) {
            return;
        }

        boolean success =
                jobDAO.deleteJob(
                        job.getJobId(),
                        employer.getUserId()
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Job deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadJobs();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to delete job.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // VIEW APPLICANTS
    // =========================================================

    private void viewApplicants(Job job) {

        if (job == null) {
            return;
        }

        new ApplicantManagementFrame(
                employer,
                job
        ).setVisible(true);
    }

    // =========================================================
    // LOGOUT
    // =========================================================

    private void logout() {

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Confirm Logout",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                confirm ==
                        JOptionPane.YES_OPTION
        ) {

            dispose();

            new LoginFrame()
                    .setVisible(true);
        }
    }

    // =========================================================
    // BUTTON STYLES
    // =========================================================

    private JButton createPrimaryButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setBackground(PRIMARY);
        button.setForeground(Color.WHITE);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        8,
                        14,
                        8,
                        14
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    private JButton createSecondaryButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setBackground(Color.WHITE);
        button.setForeground(TEXT);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                7,
                                13,
                                7,
                                13
                        )
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    private JButton createDangerButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setBackground(Color.WHITE);
        button.setForeground(DANGER);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(254, 202, 202)
                        ),
                        BorderFactory.createEmptyBorder(
                                7,
                                13,
                                7,
                                13
                        )
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    private JButton createHeaderButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setBackground(
                new Color(51, 65, 85)
        );

        button.setForeground(Color.WHITE);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        8,
                        11,
                        8,
                        11
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    // =========================================================
    // HELPERS
    // =========================================================

    private String safe(String value) {

        if (
                value == null ||
                        value.trim().isEmpty()
        ) {
            return "Not specified";
        }

        return value.trim();
    }

    private String formatJobType(
            String jobType
    ) {

        if (
                jobType == null ||
                        jobType.trim().isEmpty()
        ) {
            return "Not specified";
        }

        if (
                jobType.equalsIgnoreCase(
                        "FULL_TIME"
                )
        ) {
            return "Full Time";
        }

        if (
                jobType.equalsIgnoreCase(
                        "PART_TIME"
                )
        ) {
            return "Part Time";
        }

        return jobType;
    }

    private String shorten(
            String text,
            int maxLength
    ) {

        if (text == null) {
            return "";
        }

        text = text
                .replace("\n", " ")
                .trim();

        if (
                text.length() <= maxLength
        ) {
            return text;
        }

        return text.substring(
                0,
                maxLength
        ) + "...";
    }

    private String escapeHtml(
            String text
    ) {

        if (text == null) {
            return "";
        }

        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}