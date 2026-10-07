package ui;

import ai.JobMatchResult;
import ai.JobMatchingEngine;
import dao.ApplicationDAO;
import model.Job;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JobRecommendationFrame extends JFrame {

    private User jobSeeker;
    private JPanel jobsPanel;

    private final Color BACKGROUND = new Color(245, 247, 250);
    private final Color HEADER = new Color(25, 35, 50);
    private final Color TEXT = new Color(31, 41, 55);
    private final Color SECONDARY_TEXT = new Color(107, 114, 128);
    private final Color PRIMARY = new Color(37, 99, 235);
    private final Color BORDER = new Color(229, 231, 235);
    private final Color SUCCESS = new Color(22, 101, 52);

    public JobRecommendationFrame(User jobSeeker) {

        this.jobSeeker = jobSeeker;

        setTitle("WorkBridge - AI Job Recommendations");
        setSize(1000, 700);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
        loadRecommendations();
    }

    // =========================================================
    // UI
    // =========================================================

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

        JLabel titleLabel = new JLabel(
                "AI Job Recommendations"
        );

        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        JLabel subtitleLabel = new JLabel(
                "Jobs matched to your skills and profile"
        );

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

        JPanel rightPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT,
                        0,
                        5
                )
        );

        rightPanel.setOpaque(false);

        JButton closeButton =
                createHeaderButton("Close");

        closeButton.addActionListener(
                e -> dispose()
        );

        rightPanel.add(closeButton);

        headerPanel.add(
                brandPanel,
                BorderLayout.WEST
        );

        headerPanel.add(
                rightPanel,
                BorderLayout.EAST
        );

        add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // MAIN
        // =====================================================

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(0, 12)
                );

        mainPanel.setBackground(BACKGROUND);

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 20, 15, 20
                )
        );

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

        JLabel heading =
                new JLabel(
                        "Recommended Jobs"
                );

        heading.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        21
                )
        );

        heading.setForeground(TEXT);

        JLabel description =
                new JLabel(
                        "Based on your skills, experience and profile."
                );

        description.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        description.setForeground(
                SECONDARY_TEXT
        );

        sectionText.add(heading);

        sectionText.add(
                Box.createVerticalStrut(3)
        );

        sectionText.add(description);

        sectionHeader.add(
                sectionText,
                BorderLayout.WEST
        );

        mainPanel.add(
                sectionHeader,
                BorderLayout.NORTH
        );

        // =====================================================
        // JOBS PANEL
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
                new JScrollPane(jobsPanel);

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollPane.setBackground(BACKGROUND);

        scrollPane.getViewport().setBackground(
                BACKGROUND
        );

        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        add(
                mainPanel,
                BorderLayout.CENTER
        );
    }

    // =========================================================
    // LOAD RECOMMENDATIONS
    // =========================================================

    private void loadRecommendations() {

        jobsPanel.removeAll();

        try {

            JobMatchingEngine engine =
                    new JobMatchingEngine();

            List<JobMatchResult> recommendations =
                    engine.getRecommendations(jobSeeker);

            if (recommendations == null ||
                    recommendations.isEmpty()) {

                showEmptyMessage();

            } else {

                for (JobMatchResult result :
                        recommendations) {

                    JPanel card =
                            createJobCard(result);

                    jobsPanel.add(card);

                    jobsPanel.add(
                            Box.createVerticalStrut(12)
                    );
                }
            }

        } catch (Exception e) {

            showEmptyMessage();

            e.printStackTrace();
        }

        jobsPanel.revalidate();
        jobsPanel.repaint();
    }

    // =========================================================
    // JOB CARD
    // =========================================================

    private JPanel createJobCard(
            JobMatchResult result
    ) {

        Job job = result.getJob();

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
                        220
                )
        );

        // =====================================================
        // INFORMATION
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

        JLabel titleLabel =
                new JLabel(
                        safe(job.getTitle())
                );

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        19
                )
        );

        titleLabel.setForeground(TEXT);

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

        salaryLabel.setForeground(SUCCESS);

        String requirements =
                shorten(
                        safe(job.getRequirements()),
                        180
                );

        JLabel requirementsLabel =
                new JLabel(
                        "<html><b>Requirements:</b> "
                                + escapeHtml(requirements)
                                + "</html>"
                );

        requirementsLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        requirementsLabel.setForeground(TEXT);

        informationPanel.add(titleLabel);

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

        informationPanel.add(requirementsLabel);

        // =====================================================
        // MATCH SCORE
        // =====================================================

        JPanel matchPanel =
                new JPanel();

        matchPanel.setLayout(
                new BoxLayout(
                        matchPanel,
                        BoxLayout.Y_AXIS
                )
        );

        matchPanel.setOpaque(false);

        JLabel matchTitle =
                new JLabel("AI MATCH");

        matchTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        10
                )
        );

        matchTitle.setForeground(
                SECONDARY_TEXT
        );

        JLabel matchScore =
                new JLabel(
                        formatPercentage(
                                result.getMatchPercentage()
                        )
                );

        matchScore.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        23
                )
        );

        matchScore.setForeground(PRIMARY);

        matchPanel.add(matchTitle);

        matchPanel.add(
                Box.createVerticalStrut(2)
        );

        matchPanel.add(matchScore);

        // =====================================================
        // ACTIONS
        // =====================================================

        JPanel actionPanel =
                new JPanel(
                        new BorderLayout()
                );

        actionPanel.setOpaque(false);

        actionPanel.add(
                matchPanel,
                BorderLayout.WEST
        );

        JPanel buttons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );

        buttons.setOpaque(false);

        JButton detailsButton =
                new JButton("View Details");

        JButton whyButton =
                new JButton("Why This Matches");

        JButton applyButton =
                new JButton("Apply Now");

        detailsButton.setPreferredSize(
                new Dimension(120, 38)
        );

        whyButton.setPreferredSize(
                new Dimension(145, 38)
        );

        applyButton.setPreferredSize(
                new Dimension(110, 38)
        );

        styleSecondaryButton(detailsButton);
        styleSecondaryButton(whyButton);
        stylePrimaryButton(applyButton);

        detailsButton.addActionListener(
                e -> showJobDetails(result)
        );

        whyButton.addActionListener(
                e -> showMatchDetails(result)
        );

        applyButton.addActionListener(
                e -> applyForJob(result)
        );

        buttons.add(detailsButton);
        buttons.add(whyButton);
        buttons.add(applyButton);

        actionPanel.add(
                buttons,
                BorderLayout.EAST
        );

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
    // JOB DETAILS
    // =========================================================
    private void showJobDetails(
            JobMatchResult result
    ) {

        Job job = result.getJob();

        JDialog dialog =
                new JDialog(
                        this,
                        "Job Details",
                        true
                );

        dialog.setSize(
                760,
                560
        );

        dialog.setResizable(false);

        dialog.setLocationRelativeTo(this);


        // =====================================================
        // MAIN PANEL
        // =====================================================

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        mainPanel.setBackground(BACKGROUND);

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        24,
                        16,
                        24
                )
        );


        // =====================================================
        // HEADER
        // =====================================================

        JPanel titlePanel =
                new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setOpaque(false);


        JLabel titleLabel =
                new JLabel(
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


        JLabel categoryLabel =
                new JLabel(
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


        mainPanel.add(
                titlePanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // CONTENT
        // =====================================================

        JPanel contentPanel =
                new JPanel(
                        new BorderLayout()
                );

        contentPanel.setOpaque(false);

        contentPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        18,
                        0,
                        10,
                        0
                )
        );


        // =====================================================
        // INFO BOXES
        // =====================================================

        JPanel infoPanel =
                new JPanel(
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

        JPanel textPanel =
                new JPanel();

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        textPanel.setOpaque(false);

        textPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        18,
                        0,
                        0,
                        0
                )
        );


        // =====================================================
        // JOB DESCRIPTION
        // =====================================================

        JLabel descriptionTitle =
                new JLabel(
                        "Job Description"
                );

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
                                + "<div style='width:700px;'>"
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
                new JLabel(
                        "Requirements"
                );

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
                                + "<div style='width:700px;'>"
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
        // ADD CONTENT
        // =====================================================

        JPanel detailsPanel =
                new JPanel(
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

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );

        buttonPanel.setOpaque(false);


        JButton closeButton =
                new JButton(
                        "Close"
                );

        JButton applyButton =
                new JButton(
                        "Apply Now"
                );


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

                    applyForJob(result);
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
    // DETAIL BOX
    // =========================================================

    private JPanel createDetailBox(
            String title,
            String value
    ) {

        JPanel panel =
                new JPanel();

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
                                9,
                                11,
                                9,
                                11
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(title);

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

        JLabel valueLabel =
                new JLabel(
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

        valueLabel.setForeground(TEXT);

        panel.add(titleLabel);

        panel.add(
                Box.createVerticalStrut(4)
        );

        panel.add(valueLabel);

        return panel;
    }

    // =========================================================
    // WHY THIS MATCHES
    // =========================================================

    private void showMatchDetails(
            JobMatchResult result
    ) {

        String matchedSkills =
                setToText(
                        result.getMatchedSkills()
                );

        String missingSkills =
                setToText(
                        result.getMissingSkills()
                );

        JDialog dialog =
                new JDialog(
                        this,
                        "Why This Job Matches",
                        true
                );

        dialog.setSize(
                520,
                350
        );

        dialog.setResizable(false);

        dialog.setLocationRelativeTo(this);

        JPanel main =
                new JPanel(
                        new BorderLayout()
                );

        main.setBackground(BACKGROUND);

        main.setBorder(
                BorderFactory.createEmptyBorder(
                        18,
                        20,
                        15,
                        20
                )
        );

        JLabel title =
                new JLabel(
                        "Why This Job Matches You"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        title.setForeground(TEXT);

        main.add(
                title,
                BorderLayout.NORTH
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

        content.add(
                Box.createVerticalStrut(15)
        );

        content.add(
                createMatchBox(
                        "AI Match Score",
                        formatPercentage(
                                result.getMatchPercentage()
                        )
                )
        );

        content.add(
                Box.createVerticalStrut(10)
        );

        content.add(
                createMatchBox(
                        "Matched Skills",
                        shorten(
                                matchedSkills,
                                180
                        )
                )
        );

        content.add(
                Box.createVerticalStrut(10)
        );

        content.add(
                createMatchBox(
                        "Skills to Improve",
                        shorten(
                                missingSkills,
                                180
                        )
                )
        );

        main.add(
                content,
                BorderLayout.CENTER
        );

        JButton closeButton =
                new JButton("Close");

        closeButton.setPreferredSize(
                new Dimension(90, 36)
        );

        styleSecondaryButton(closeButton);

        closeButton.addActionListener(
                e -> dialog.dispose()
        );

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        buttonPanel.setOpaque(false);

        buttonPanel.add(closeButton);

        main.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        dialog.add(main);

        dialog.setVisible(true);
    }

    // =========================================================
    // MATCH BOX
    // =========================================================

    private JPanel createMatchBox(
            String title,
            String value
    ) {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                10,
                                5
                        )
                );

        panel.setBackground(Color.WHITE);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                10,
                                12,
                                10,
                                12
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        titleLabel.setForeground(
                SECONDARY_TEXT
        );

        JLabel valueLabel =
                new JLabel(
                        "<html>"
                                + escapeHtml(value)
                                + "</html>"
                );

        valueLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        valueLabel.setForeground(TEXT);

        panel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        panel.add(
                valueLabel,
                BorderLayout.CENTER
        );

        return panel;
    }

    // =========================================================
    // APPLY
    // =========================================================

    private void applyForJob(
            JobMatchResult result
    ) {

        Job job = result.getJob();

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Do you want to apply for:\n\n"
                                + job.getTitle()
                                + "?",
                        "Confirm Application",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            ApplicationDAO applicationDAO =
                    new ApplicationDAO();

            boolean success =
                    applicationDAO.applyForJob(
                            job.getJobId(),
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

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to submit application.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    // =========================================================
    // EMPTY
    // =========================================================

    private void showEmptyMessage() {

        JPanel empty =
                new JPanel();

        empty.setLayout(
                new BoxLayout(
                        empty,
                        BoxLayout.Y_AXIS
                )
        );

        empty.setOpaque(false);

        JLabel title =
                new JLabel(
                        "No matching jobs found"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        title.setForeground(TEXT);

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel message =
                new JLabel(
                        "Update your profile and skills to improve your recommendations."
                );

        message.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        message.setForeground(
                SECONDARY_TEXT
        );

        message.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        empty.add(
                Box.createVerticalStrut(60)
        );

        empty.add(title);

        empty.add(
                Box.createVerticalStrut(8)
        );

        empty.add(message);

        jobsPanel.add(empty);
    }

    // =========================================================
    // HEADER BUTTON
    // =========================================================

    private JButton createHeaderButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        button.setForeground(Color.WHITE);

        button.setBackground(
                new Color(
                        51,
                        65,
                        85
                )
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        8,
                        12,
                        8,
                        12
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
    // PRIMARY BUTTON
    // =========================================================

    private void stylePrimaryButton(
            JButton button
    ) {

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
    }

    // =========================================================
    // SECONDARY BUTTON
    // =========================================================

    private void styleSecondaryButton(
            JButton button
    ) {

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
    }

    // =========================================================
    // HELPERS
    // =========================================================

    private String safe(String value) {

        if (value == null ||
                value.trim().isEmpty()) {

            return "Not specified";
        }

        return value.trim();
    }

    private String formatPercentage(
            double percentage
    ) {

        if (percentage == (int) percentage) {
            return (int) percentage + "%";
        }

        return String.format(
                "%.1f%%",
                percentage
        );
    }

    private String setToText(
            java.util.Set<String> skills
    ) {

        if (skills == null ||
                skills.isEmpty()) {

            return "None";
        }

        return String.join(
                ", ",
                skills
        );
    }

    private String formatJobType(
            String jobType
    ) {

        if (jobType == null ||
                jobType.trim().isEmpty()) {

            return "Not specified";
        }

        String value =
                jobType
                        .replace("_", " ")
                        .toLowerCase();

        switch (value) {

            case "full time":
                return "Full Time";

            case "part time":
                return "Part Time";

            case "remote":
                return "Remote";

            case "contract":
                return "Contract";

            case "internship":
                return "Internship";

            case "temporary":
                return "Temporary";

            default:
                return value;
        }
    }

    private String shorten(
            String text,
            int maxLength
    ) {

        if (text == null ||
                text.trim().isEmpty()) {

            return "Not specified";
        }

        text =
                text.replace(
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