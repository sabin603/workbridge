package ui;

import dao.JobDAO;
import dao.ApplicationDAO;
import model.Job;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JobSeekerDashboard extends JFrame {

    private User jobSeeker;
    private JobDAO jobDAO;
    private ApplicationDAO applicationDAO;

    private JTextField searchField;
    private JComboBox<String> categoryComboBox;
    private JComboBox<String> jobTypeComboBox;
    private JPanel jobsPanel;

    private final Color BACKGROUND = new Color(245, 247, 250);
    private final Color HEADER = new Color(25, 35, 50);
    private final Color TEXT = new Color(31, 41, 55);
    private final Color SECONDARY_TEXT = new Color(107, 114, 128);
    private final Color PRIMARY = new Color(37, 99, 235);
    private final Color BORDER = new Color(229, 231, 235);

    public JobSeekerDashboard(User user) {
        this.jobSeeker = user;
        this.jobDAO = new JobDAO();
        this.applicationDAO = new ApplicationDAO();

        setTitle("WorkBridge - Job Seeker Dashboard");
        setSize(1000, 700);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
        loadJobs();
    }

    private void createUI() {

        getContentPane().setBackground(BACKGROUND);
        setLayout(new BorderLayout());

        // ================= HEADER =================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER);
        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(18, 25, 18, 25)
        );

        JPanel brandPanel = new JPanel();
        brandPanel.setLayout(new BoxLayout(brandPanel, BoxLayout.Y_AXIS));
        brandPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("WorkBridge");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 25));

        JLabel subtitleLabel = new JLabel("Find your next opportunity");
        subtitleLabel.setForeground(new Color(203, 213, 225));
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        brandPanel.add(titleLabel);
        brandPanel.add(Box.createVerticalStrut(3));
        brandPanel.add(subtitleLabel);

        JPanel rightHeaderPanel = new JPanel();
        rightHeaderPanel.setLayout(new BoxLayout(rightHeaderPanel, BoxLayout.Y_AXIS));
        rightHeaderPanel.setOpaque(false);

        JLabel welcomeLabel = new JLabel(
                "Welcome, " + safe(jobSeeker.getName())
        );
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        welcomeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 6, 0)
        );
        buttonPanel.setOpaque(false);

        JButton matchingButton = createHeaderButton("Find Matching Jobs");
        JButton applicationsButton = createHeaderButton("My Applications");
        JButton profileButton = createHeaderButton("My Profile");
        JButton backButton = createHeaderButton("Logout");

        buttonPanel.add(matchingButton);
        buttonPanel.add(applicationsButton);
        buttonPanel.add(profileButton);
        buttonPanel.add(backButton);

        rightHeaderPanel.add(welcomeLabel);
        rightHeaderPanel.add(Box.createVerticalStrut(10));
        rightHeaderPanel.add(buttonPanel);

        headerPanel.add(brandPanel, BorderLayout.WEST);
        headerPanel.add(rightHeaderPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // ================= MAIN PANEL =================
        JPanel mainPanel = new JPanel(new BorderLayout(0, 12));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );

        // ================= SEARCH PANEL =================
        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        BorderFactory.createEmptyBorder(12, 15, 12, 15)
                )
        );

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(250, 38));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        categoryComboBox = new JComboBox<>(new String[]{
                "All Categories",
                "Frontend",
                "Backend",
                "Full Stack",
                "UI/UX Design",
                "Graphic Design",
                "Data Entry",
                "Marketing",
                "Writing",
                "Plumbing",
                "Electrician",
                "Video Editor",
                "System Administrator",
                "Waiter",
                "Hotel Manager",
                "House Cleaner",
                "Receptionist",
                "Other"
        });
        categoryComboBox.setPreferredSize(new Dimension(160, 38));
        categoryComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        jobTypeComboBox = new JComboBox<>(new String[]{
                "All Types",
                "FULL_TIME",
                "PART_TIME",
                "INTERNSHIP",
                "REMOTE",
                "CONTRACT",
                "TEMPORARY"
        });
        jobTypeComboBox.setPreferredSize(new Dimension(130, 38));
        jobTypeComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(90, 38));
        stylePrimaryButton(searchButton);

        JButton resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(80, 38));
        styleSecondaryButton(resetButton);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setPreferredSize(new Dimension(85, 38));
        styleSecondaryButton(refreshButton);

        JPanel searchControls = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 8, 0)
        );
        searchControls.setOpaque(false);

        searchControls.add(createSearchLabel("Search:"));
        searchControls.add(searchField);
        searchControls.add(createSearchLabel("Category:"));
        searchControls.add(categoryComboBox);
        searchControls.add(createSearchLabel("Type:"));
        searchControls.add(jobTypeComboBox);
        searchControls.add(searchButton);
        searchControls.add(resetButton);
        searchControls.add(refreshButton);

        searchPanel.add(searchControls, BorderLayout.CENTER);

        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // ================= JOBS SECTION =================
        JPanel jobsSection = new JPanel(new BorderLayout(0, 8));
        jobsSection.setOpaque(false);

        JPanel sectionHeader = new JPanel(new BorderLayout());
        sectionHeader.setOpaque(false);

        JLabel availableJobsLabel = new JLabel("Available Jobs");
        availableJobsLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 21)
        );
        availableJobsLabel.setForeground(TEXT);

        JLabel sectionDescription = new JLabel(
                "Explore opportunities and apply for jobs that match your skills."
        );
        sectionDescription.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );
        sectionDescription.setForeground(SECONDARY_TEXT);

        JPanel sectionText = new JPanel();
        sectionText.setLayout(new BoxLayout(sectionText, BoxLayout.Y_AXIS));
        sectionText.setOpaque(false);

        sectionText.add(availableJobsLabel);
        sectionText.add(Box.createVerticalStrut(3));
        sectionText.add(sectionDescription);

        sectionHeader.add(sectionText, BorderLayout.WEST);

        jobsSection.add(sectionHeader, BorderLayout.NORTH);

        // ================= JOB CARDS =================
        jobsPanel = new JPanel();
        jobsPanel.setLayout(
                new BoxLayout(jobsPanel, BoxLayout.Y_AXIS)
        );
        jobsPanel.setBackground(BACKGROUND);
        jobsPanel.setBorder(
                BorderFactory.createEmptyBorder(5, 2, 20, 2)
        );

        JScrollPane scrollPane = new JScrollPane(jobsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBackground(BACKGROUND);
        scrollPane.getViewport().setBackground(BACKGROUND);
        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        jobsSection.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(jobsSection, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // ================= ACTIONS =================

        searchButton.addActionListener(e -> searchJobs());

        resetButton.addActionListener(e -> resetSearch());

        refreshButton.addActionListener(e -> loadJobs());

        matchingButton.addActionListener(e -> {
            new JobRecommendationFrame(jobSeeker).setVisible(true);
        });

        applicationsButton.addActionListener(e -> {
            new MyApplicationsFrame(jobSeeker).setVisible(true);
        });

        profileButton.addActionListener(e -> {
            new ProfileForm(jobSeeker).setVisible(true);
        });

        backButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (result == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });
    }

    // =========================================================
    // JOB CARD
    // =========================================================

    private JPanel createJobCard(Job job) {

        JPanel card = new JPanel(new BorderLayout(15, 10));
        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER, 1
                        ),
                        BorderFactory.createEmptyBorder(
                                17, 20, 17, 20
                        )
                )
        );

        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 220)
        );

        // ================= LEFT / CENTER INFORMATION =================

        JPanel informationPanel = new JPanel();
        informationPanel.setOpaque(false);
        informationPanel.setLayout(
                new BoxLayout(
                        informationPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel jobTitle = new JLabel(
                safe(job.getTitle())
        );
        jobTitle.setFont(
                new Font("Segoe UI", Font.BOLD, 19)
        );
        jobTitle.setForeground(TEXT);

        JLabel categoryLabel = new JLabel(
                safe(job.getCategory())
        );
        categoryLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 12)
        );
        categoryLabel.setForeground(PRIMARY);

        JLabel locationLabel = new JLabel(
                "Location: " + safe(job.getLocation())
        );
        locationLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );
        locationLabel.setForeground(SECONDARY_TEXT);

        JLabel typeLabel = new JLabel(
                "Job Type: " + formatJobType(job.getJobType())
        );
        typeLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );
        typeLabel.setForeground(SECONDARY_TEXT);

        JLabel salaryLabel = new JLabel(
                "Salary: " + safe(job.getSalary())
        );
        salaryLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );
        salaryLabel.setForeground(new Color(22, 101, 52));

        String requirements = shorten(
                safe(job.getRequirements()),
                180
        );

        JLabel requirementsLabel = new JLabel(
                "<html><b>Requirements:</b> "
                        + escapeHtml(requirements)
                        + "</html>"
        );

        requirementsLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );
        requirementsLabel.setForeground(TEXT);

        informationPanel.add(jobTitle);
        informationPanel.add(Box.createVerticalStrut(5));
        informationPanel.add(categoryLabel);
        informationPanel.add(Box.createVerticalStrut(7));
        informationPanel.add(locationLabel);
        informationPanel.add(Box.createVerticalStrut(3));
        informationPanel.add(typeLabel);
        informationPanel.add(Box.createVerticalStrut(3));
        informationPanel.add(salaryLabel);
        informationPanel.add(Box.createVerticalStrut(8));
        informationPanel.add(requirementsLabel);

        // ================= BUTTONS =================

        JPanel actionPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 8, 0)
        );
        actionPanel.setOpaque(false);

        JButton viewButton = new JButton("View Details");
        JButton applyButton = new JButton("Apply Now");

        viewButton.setPreferredSize(
                new Dimension(120, 38)
        );

        applyButton.setPreferredSize(
                new Dimension(110, 38)
        );

        styleSecondaryButton(viewButton);
        stylePrimaryButton(applyButton);

        viewButton.addActionListener(e -> {
            viewJobDetails(job);
        });

        applyButton.addActionListener(e -> {
            applyForJob(job);
        });

        actionPanel.add(viewButton);
        actionPanel.add(applyButton);

        card.add(informationPanel, BorderLayout.CENTER);
        card.add(actionPanel, BorderLayout.SOUTH);

        return card;
    }

    // =========================================================
    // LOAD JOBS
    // =========================================================

    private void loadJobs() {

        List<Job> jobs = jobDAO.getAllJobs();

        displayJobs(jobs);
    }

    // =========================================================
    // DISPLAY JOBS
    // =========================================================

    private void displayJobs(List<Job> jobs) {

        jobsPanel.removeAll();

        if (jobs == null || jobs.isEmpty()) {

            JPanel emptyPanel = new JPanel();
            emptyPanel.setLayout(
                    new BoxLayout(
                            emptyPanel,
                            BoxLayout.Y_AXIS
                    )
            );
            emptyPanel.setOpaque(false);

            JLabel noJobsLabel = new JLabel(
                    "No jobs found"
            );
            noJobsLabel.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            18
                    )
            );
            noJobsLabel.setForeground(TEXT);
            noJobsLabel.setAlignmentX(
                    Component.CENTER_ALIGNMENT
            );

            JLabel messageLabel = new JLabel(
                    "Try changing your search or filter."
            );
            messageLabel.setFont(
                    new Font(
                            "Segoe UI",
                            Font.PLAIN,
                            14
                    )
            );
            messageLabel.setForeground(
                    SECONDARY_TEXT
            );
            messageLabel.setAlignmentX(
                    Component.CENTER_ALIGNMENT
            );

            emptyPanel.add(Box.createVerticalStrut(60));
            emptyPanel.add(noJobsLabel);
            emptyPanel.add(Box.createVerticalStrut(8));
            emptyPanel.add(messageLabel);

            jobsPanel.add(emptyPanel);

        } else {

            for (Job job : jobs) {

                JPanel card = createJobCard(job);

                jobsPanel.add(card);

                jobsPanel.add(
                        Box.createVerticalStrut(12)
                );
            }
        }

        jobsPanel.revalidate();
        jobsPanel.repaint();
    }

    // =========================================================
    // SEARCH
    // =========================================================

    private void searchJobs() {

        String keyword =
                searchField.getText().trim();

        String category =
                (String) categoryComboBox.getSelectedItem();

        String jobType =
                (String) jobTypeComboBox.getSelectedItem();

        List<Job> jobs =
                jobDAO.searchJobs(
                        keyword,
                        category,
                        jobType
                );

        displayJobs(jobs);
    }

    // =========================================================
    // RESET SEARCH
    // =========================================================

    private void resetSearch() {

        searchField.setText("");

        categoryComboBox.setSelectedIndex(0);

        jobTypeComboBox.setSelectedIndex(0);

        loadJobs();
    }

// =========================================================
// VIEW JOB DETAILS - COMPACT UI
// =========================================================
private void viewJobDetails(Job job) {

    JDialog dialog = new JDialog(
            this,
            "Job Details",
            true
    );

    dialog.setSize(760, 560);
    dialog.setResizable(false);
    dialog.setLocationRelativeTo(this);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBackground(BACKGROUND);

    mainPanel.setBorder(
            BorderFactory.createEmptyBorder(
                    20, 24, 16, 24
            )
    );

    // =====================================================
    // HEADER
    // =====================================================

    JPanel headerPanel = new JPanel(
            new BorderLayout()
    );

    headerPanel.setOpaque(false);

    JPanel titlePanel = new JPanel();

    titlePanel.setLayout(
            new BoxLayout(
                    titlePanel,
                    BoxLayout.Y_AXIS
            )
    );

    titlePanel.setOpaque(false);

    JLabel titleLabel = new JLabel(
            safe(job.getTitle())
    );

    titleLabel.setFont(
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    23
            )
    );

    titleLabel.setForeground(TEXT);


    JLabel categoryLabel = new JLabel(
            safe(job.getCategory())
                    + "  •  "
                    + formatJobType(
                    job.getJobType()
            )
    );

    categoryLabel.setFont(
            new Font(
                    "Segoe UI",
                    Font.PLAIN,
                    13
            )
    );

    categoryLabel.setForeground(PRIMARY);


    titlePanel.add(titleLabel);

    titlePanel.add(
            Box.createVerticalStrut(4)
    );

    titlePanel.add(categoryLabel);


    headerPanel.add(
            titlePanel,
            BorderLayout.WEST
    );


    mainPanel.add(
            headerPanel,
            BorderLayout.NORTH
    );


    // =====================================================
    // CONTENT
    // =====================================================

    JPanel contentPanel = new JPanel(
            new BorderLayout()
    );

    contentPanel.setOpaque(false);

    contentPanel.setBorder(
            BorderFactory.createEmptyBorder(
                    18, 0, 10, 0
            )
    );


    // =====================================================
    // TOP INFO BOXES
    // =====================================================

    JPanel infoPanel = new JPanel(
            new GridLayout(
                    1,
                    3,
                    14,
                    0
            )
    );

    infoPanel.setOpaque(false);


    infoPanel.add(
            createDetailBox(
                    "LOCATION",
                    safe(job.getLocation())
            )
    );


    infoPanel.add(
            createDetailBox(
                    "SALARY",
                    safe(job.getSalary())
            )
    );


    infoPanel.add(
            createDetailBox(
                    "JOB TYPE",
                    formatJobType(
                            job.getJobType()
                    )
            )
    );


    // =====================================================
    // DESCRIPTION + REQUIREMENTS
    // =====================================================

    JPanel textPanel = new JPanel();

    textPanel.setLayout(
            new BoxLayout(
                    textPanel,
                    BoxLayout.Y_AXIS
            )
    );

    textPanel.setOpaque(false);

    textPanel.setBorder(
            BorderFactory.createEmptyBorder(
                    18, 0, 0, 0
            )
    );


    // =====================================================
    // JOB DESCRIPTION
    // =====================================================

    JLabel descriptionTitle =
            new JLabel("Job Description");

    descriptionTitle.setFont(
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    15
            )
    );

    descriptionTitle.setForeground(TEXT);

    descriptionTitle.setAlignmentX(
            Component.LEFT_ALIGNMENT
    );


    textPanel.add(
            descriptionTitle
    );

    textPanel.add(
            Box.createVerticalStrut(6)
    );


    JLabel descriptionLabel =
            new JLabel(
                    "<html>"
                            + "<div style='width:690px;'>"
                            + escapeHtml(
                            safe(
                                    job.getDescription()
                            )
                    )
                            + "</div>"
                            + "</html>"
            );

    descriptionLabel.setFont(
            new Font(
                    "Segoe UI",
                    Font.PLAIN,
                    13
            )
    );

    descriptionLabel.setForeground(
            SECONDARY_TEXT
    );

    descriptionLabel.setAlignmentX(
            Component.LEFT_ALIGNMENT
    );


    textPanel.add(
            descriptionLabel
    );


    textPanel.add(
            Box.createVerticalStrut(16)
    );


    // =====================================================
    // REQUIREMENTS
    // =====================================================

    JLabel requirementsTitle =
            new JLabel("Requirements");

    requirementsTitle.setFont(
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    15
            )
    );

    requirementsTitle.setForeground(TEXT);

    requirementsTitle.setAlignmentX(
            Component.LEFT_ALIGNMENT
    );


    textPanel.add(
            requirementsTitle
    );

    textPanel.add(
            Box.createVerticalStrut(6)
    );


    JLabel requirementsLabel =
            new JLabel(
                    "<html>"
                            + "<div style='width:690px;'>"
                            + escapeHtml(
                            safe(
                                    job.getRequirements()
                            )
                    )
                            + "</div>"
                            + "</html>"
            );

    requirementsLabel.setFont(
            new Font(
                    "Segoe UI",
                    Font.PLAIN,
                    13
            )
    );

    requirementsLabel.setForeground(
            SECONDARY_TEXT
    );

    requirementsLabel.setAlignmentX(
            Component.LEFT_ALIGNMENT
    );


    textPanel.add(
            requirementsLabel
    );


    // =====================================================
    // COMBINE CONTENT
    // =====================================================

    JPanel detailsPanel = new JPanel(
            new BorderLayout()
    );

    detailsPanel.setOpaque(false);

    detailsPanel.add(
            infoPanel,
            BorderLayout.NORTH
    );

    detailsPanel.add(
            textPanel,
            BorderLayout.CENTER
    );


    contentPanel.add(
            detailsPanel,
            BorderLayout.CENTER
    );


    mainPanel.add(
            contentPanel,
            BorderLayout.CENTER
    );


    // =====================================================
    // BUTTONS
    // =====================================================

    JPanel buttonPanel = new JPanel(
            new FlowLayout(
                    FlowLayout.RIGHT,
                    8,
                    0
            )
    );

    buttonPanel.setOpaque(false);


    JButton closeButton =
            new JButton("Close");

    JButton applyButton =
            new JButton("Apply Now");


    closeButton.setPreferredSize(
            new Dimension(
                    100,
                    38
            )
    );

    applyButton.setPreferredSize(
            new Dimension(
                    120,
                    38
            )
    );


    styleSecondaryButton(
            closeButton
    );

    stylePrimaryButton(
            applyButton
    );


    closeButton.addActionListener(
            e -> dialog.dispose()
    );


    applyButton.addActionListener(
            e -> {

                dialog.dispose();

                applyForJob(job);
            }
    );


    buttonPanel.add(
            closeButton
    );

    buttonPanel.add(
            applyButton
    );


    mainPanel.add(
            buttonPanel,
            BorderLayout.SOUTH
    );


    // =====================================================
    // SHOW
    // =====================================================

    dialog.add(mainPanel);

    dialog.setVisible(true);
}

    // =========================================================
// DETAIL INFO BOX
// =========================================================

    private JPanel createDetailBox(
            String title,
            String value
    ) {

        JPanel panel = new JPanel();
        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setBackground(Color.WHITE);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                9, 11, 9, 11
                        )
                )
        );

        JLabel titleLabel = new JLabel(
                title
        );

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        10
                )
        );

        titleLabel.setForeground(
                SECONDARY_TEXT
        );

        JLabel valueLabel = new JLabel(
                "<html>"
                        + escapeHtml(
                        shorten(value, 24)
                )
                        + "</html>"
        );

        valueLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        valueLabel.setForeground(
                TEXT
        );

        panel.add(titleLabel);
        panel.add(
                Box.createVerticalStrut(4)
        );
        panel.add(valueLabel);

        return panel;
    }
    // =========================================================
    // APPLY FOR JOB
    // =========================================================

    private void applyForJob(Job selectedJob) {

        if (selectedJob == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to select this job.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Do you want to apply for:\n\n"
                                + selectedJob.getTitle()
                                + "?",
                        "Confirm Application",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean success =
                applicationDAO.applyForJob(
                        selectedJob.getJobId(),
                        jobSeeker.getUserId()
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Application submitted successfully!",
                    "Application Submitted",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "You have already applied for this job.",
                    "Already Applied",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // =========================================================
    // UI HELPERS
    // =========================================================

    private JButton createHeaderButton(String text) {

        JButton button = new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        button.setForeground(Color.WHITE);
        button.setBackground(
                new Color(51, 65, 85)
        );

        button.setFocusPainted(false);
        button.setBorder(
                BorderFactory.createEmptyBorder(
                        8, 10, 8, 10
                )
        );

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        return button;
    }

    private JLabel createSearchLabel(String text) {

        JLabel label = new JLabel(text);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        label.setForeground(TEXT);

        return label;
    }

    private void stylePrimaryButton(JButton button) {

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
                        8, 14, 8, 14
                )
        );

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
    }

    private void styleSecondaryButton(JButton button) {

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
                                7, 13, 7, 13
                        )
                )
        );

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
    }

    private String safe(String value) {

        if (value == null || value.trim().isEmpty()) {
            return "Not specified";
        }

        return value.trim();
    }

    private String formatJobType(String jobType) {

        if (jobType == null ||
                jobType.trim().isEmpty()) {
            return "Not specified";
        }

        return jobType
                .replace("_", " ")
                .toLowerCase()
                .replace(
                        "full time",
                        "Full Time"
                )
                .replace(
                        "part time",
                        "Part Time"
                );
    }

    private String shorten(
            String text,
            int maxLength
    ) {

        if (text == null) {
            return "Not specified";
        }

        text = text.replace(
                "\n",
                " "
        ).trim();

        if (text.length() <= maxLength) {
            return text;
        }

        return text.substring(
                0,
                maxLength
        ) + "...";
    }

    private String escapeHtml(String text) {

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