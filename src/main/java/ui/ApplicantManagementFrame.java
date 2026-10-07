package ui;

import dao.ApplicationDAO;
import dao.ProfileDAO;
import model.Application;
import model.Job;
import model.User;
import model.WorkerProfile;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.List;

public class ApplicantManagementFrame extends JFrame {

    private User employer;
    private Job job;

    private ApplicationDAO applicationDAO;
    private ProfileDAO profileDAO;

    private JPanel applicantsPanel;

    private final Color BACKGROUND =
            new Color(245, 247, 250);

    private final Color WHITE =
            Color.WHITE;

    private final Color TEXT =
            new Color(31, 41, 55);

    private final Color MUTED =
            new Color(107, 114, 128);

    private final Color PRIMARY =
            new Color(37, 99, 235);

    public ApplicantManagementFrame(
            User employer,
            Job job
    ) {

        this.employer = employer;
        this.job = job;

        applicationDAO =
                new ApplicationDAO();

        profileDAO =
                new ProfileDAO();

        setTitle(
                "Applicants - "
                        + job.getTitle()
        );

        setSize(1100, 700);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        loadApplicants();
    }


    // =========================================================
    // CREATE UI
    // =========================================================

    private void createUI() {

        getContentPane()
                .setBackground(BACKGROUND);

        setLayout(
                new BorderLayout()
        );


        // =====================================================
        // HEADER
        // =====================================================

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.setBackground(
                new Color(25, 35, 50)
        );

        headerPanel.setBorder(
                new EmptyBorder(
                        18,
                        25,
                        18,
                        25
                )
        );


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
                        "Applicant Management"
                );

        titleLabel.setForeground(
                Color.WHITE
        );

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        23
                )
        );


        JLabel jobLabel =
                new JLabel(
                        "Review candidates for: "
                                + job.getTitle()
                );

        jobLabel.setForeground(
                new Color(203, 213, 225)
        );

        jobLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );


        titlePanel.add(titleLabel);

        titlePanel.add(
                Box.createVerticalStrut(5)
        );

        titlePanel.add(jobLabel);


        JButton refreshButton =
                new JButton("Refresh");

        JButton closeButton =
                new JButton("Close");


        styleHeaderButton(
                refreshButton
        );

        styleHeaderButton(
                closeButton
        );


        JPanel headerButtons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );

        headerButtons.setOpaque(false);

        headerButtons.add(
                refreshButton
        );

        headerButtons.add(
                closeButton
        );


        headerPanel.add(
                titlePanel,
                BorderLayout.WEST
        );

        headerPanel.add(
                headerButtons,
                BorderLayout.EAST
        );


        add(
                headerPanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // APPLICANTS PANEL
        // =====================================================

        applicantsPanel =
                new JPanel();

        applicantsPanel.setLayout(
                new BoxLayout(
                        applicantsPanel,
                        BoxLayout.Y_AXIS
                )
        );

        applicantsPanel.setBackground(
                BACKGROUND
        );

        applicantsPanel.setBorder(
                new EmptyBorder(
                        15,
                        20,
                        15,
                        20
                )
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        applicantsPanel
                );

        scrollPane.setBorder(null);

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);

        scrollPane.getViewport()
                .setBackground(
                        BACKGROUND
                );


        add(
                scrollPane,
                BorderLayout.CENTER
        );


        // =====================================================
        // ACTIONS
        // =====================================================

        refreshButton.addActionListener(
                e -> loadApplicants()
        );

        closeButton.addActionListener(
                e -> dispose()
        );
    }


    // =========================================================
    // LOAD APPLICANTS
    // =========================================================

    private void loadApplicants() {

        applicantsPanel.removeAll();

        List<Application> applications =
                applicationDAO.getApplicantsByJob(
                        job.getJobId(),
                        employer.getUserId()
                );


        if (applications.isEmpty()) {

            JLabel emptyLabel =
                    new JLabel(
                            "No applicants yet for this job."
                    );

            emptyLabel.setFont(
                    new Font(
                            "Segoe UI",
                            Font.PLAIN,
                            16
                    )
            );

            emptyLabel.setForeground(
                    MUTED
            );

            emptyLabel.setAlignmentX(
                    Component.CENTER_ALIGNMENT
            );

            applicantsPanel.add(
                    Box.createVerticalStrut(100)
            );

            applicantsPanel.add(
                    emptyLabel
            );

        } else {

            for (Application application :
                    applications) {

                applicantsPanel.add(
                        createApplicantCard(
                                application
                        )
                );

                applicantsPanel.add(
                        Box.createVerticalStrut(12)
                );
            }
        }


        applicantsPanel.revalidate();

        applicantsPanel.repaint();
    }


    // =========================================================
    // CREATE APPLICANT CARD
    // =========================================================

    private JPanel createApplicantCard(
            Application application
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                15,
                                10
                        )
                );

        card.setBackground(
                WHITE
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        229,
                                        231,
                                        235
                                )
                        ),
                        new EmptyBorder(
                                15,
                                18,
                                15,
                                18
                        )
                )
        );

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        270
                )
        );


        // =====================================================
        // LEFT - PROFILE PICTURE
        // =====================================================

        JLabel pictureLabel =
                createProfilePicture(
                        application
                );


        JPanel picturePanel =
                new JPanel(
                        new BorderLayout()
                );

        picturePanel.setBackground(
                WHITE
        );

        picturePanel.setPreferredSize(
                new Dimension(
                        110,
                        140
                )
        );

        picturePanel.add(
                pictureLabel,
                BorderLayout.NORTH
        );


        // =====================================================
        // CENTER - APPLICANT INFORMATION
        // =====================================================

        JPanel infoPanel =
                new JPanel();

        infoPanel.setLayout(
                new BoxLayout(
                        infoPanel,
                        BoxLayout.Y_AXIS
                )
        );

        infoPanel.setBackground(
                WHITE
        );


        JLabel nameLabel =
                new JLabel(
                        safe(
                                application
                                        .getApplicantName()
                        )
                );

        nameLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        19
                )
        );

        nameLabel.setForeground(
                TEXT
        );


        WorkerProfile profile =
                profileDAO.getProfile(
                        application
                                .getApplicantId()
                );


        String headline =
                profile != null
                        ? safe(
                        profile.getHeadline()
                )
                        : "";


        String education =
                profile != null
                        ? safe(
                        profile.getEducation()
                )
                        : "";


        String experience =
                profile != null
                        ? safe(
                        profile.getExperience()
                )
                        : "";


        String skills =
                safe(
                        application
                                .getApplicantSkills()
                );


        JLabel headlineLabel =
                new JLabel(
                        headline.isEmpty()
                                ? "Job Seeker"
                                : headline
                );

        headlineLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        headlineLabel.setForeground(
                PRIMARY
        );


        JLabel emailLabel =
                new JLabel(
                        "Email: "
                                + safe(
                                application
                                        .getApplicantEmail()
                        )
                );

        emailLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        emailLabel.setForeground(
                MUTED
        );


        JLabel phoneLabel =
                new JLabel(
                        "Phone: "
                                + displayValue(
                                application
                                        .getApplicantPhone()
                        )
                );

        phoneLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        phoneLabel.setForeground(
                MUTED
        );


        JLabel educationLabel =
                new JLabel(
                        "Education: "
                                + displayValue(
                                education
                        )
                );

        educationLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        educationLabel.setForeground(
                MUTED
        );


        JLabel experienceLabel =
                new JLabel(
                        "Experience: "
                                + displayValue(
                                experience
                        )
                );

        experienceLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        experienceLabel.setForeground(
                MUTED
        );


        JLabel skillsLabel =
                new JLabel(
                        "Skills: "
                                + displayValue(
                                skills
                        )
                );

        skillsLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        skillsLabel.setForeground(
                TEXT
        );


        infoPanel.add(
                nameLabel
        );

        infoPanel.add(
                Box.createVerticalStrut(3)
        );

        infoPanel.add(
                headlineLabel
        );

        infoPanel.add(
                Box.createVerticalStrut(7)
        );

        infoPanel.add(
                emailLabel
        );

        infoPanel.add(
                Box.createVerticalStrut(3)
        );

        infoPanel.add(
                phoneLabel
        );

        infoPanel.add(
                Box.createVerticalStrut(3)
        );

        infoPanel.add(
                educationLabel
        );

        infoPanel.add(
                Box.createVerticalStrut(3)
        );

        infoPanel.add(
                experienceLabel
        );

        infoPanel.add(
                Box.createVerticalStrut(7)
        );

        infoPanel.add(
                skillsLabel
        );


        // =====================================================
        // INTERVIEW DETAILS
        // =====================================================

        if ("INTERVIEW".equals(
                application.getStatus()
        )) {

            String interviewDate =
                    displayValue(
                            application
                                    .getInterviewDate()
                    );

            String interviewTime =
                    displayValue(
                            application
                                    .getInterviewTime()
                    );

            String interviewNotes =
                    displayValue(
                            application
                                    .getInterviewNotes()
                    );


            JLabel interviewLabel =
                    new JLabel(
                            "Interview: "
                                    + interviewDate
                                    + " at "
                                    + interviewTime
                    );

            interviewLabel.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            13
                    )
            );

            interviewLabel.setForeground(
                    new Color(
                            180,
                            83,
                            9
                    )
            );


            JLabel notesLabel =
                    new JLabel(
                            "<html>Notes: "
                                    + escapeHtml(
                                    interviewNotes
                            )
                                    + "</html>"
                    );

            notesLabel.setFont(
                    new Font(
                            "Segoe UI",
                            Font.PLAIN,
                            12
                    )
            );

            notesLabel.setForeground(
                    MUTED
            );


            infoPanel.add(
                    Box.createVerticalStrut(8)
            );

            infoPanel.add(
                    interviewLabel
            );

            infoPanel.add(
                    Box.createVerticalStrut(3)
            );

            infoPanel.add(
                    notesLabel
            );
        }


        // =====================================================
        // RIGHT - STATUS + ACTIONS
        // =====================================================

        JPanel actionPanel =
                new JPanel();

        actionPanel.setLayout(
                new BoxLayout(
                        actionPanel,
                        BoxLayout.Y_AXIS
                )
        );

        actionPanel.setBackground(
                WHITE
        );

        actionPanel.setPreferredSize(
                new Dimension(
                        200,
                        180
                )
        );


        JLabel statusLabel =
                new JLabel(
                        formatStatus(
                                application.getStatus()
                        )
                );

        statusLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        statusLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        statusLabel.setOpaque(true);

        statusLabel.setBorder(
                new EmptyBorder(
                        6,
                        12,
                        6,
                        12
                )
        );

        styleStatusLabel(
                statusLabel,
                application.getStatus()
        );


        actionPanel.add(
                statusLabel
        );

        actionPanel.add(
                Box.createVerticalStrut(10)
        );


        JButton profileButton =
                new JButton(
                        "View Profile"
                );

        JButton cvButton =
                new JButton(
                        "View CV"
                );


        styleSecondaryButton(
                profileButton
        );

        styleSecondaryButton(
                cvButton
        );


        actionPanel.add(
                profileButton
        );

        actionPanel.add(
                Box.createVerticalStrut(6)
        );

        actionPanel.add(
                cvButton
        );

        actionPanel.add(
                Box.createVerticalStrut(10)
        );


        // =====================================================
        // WORKFLOW BUTTONS
        // =====================================================

        JPanel workflowPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                5,
                                0
                        )
                );

        workflowPanel.setBackground(
                WHITE
        );


        String status =
                application.getStatus();


        if (status == null
                || status.equals("PENDING")
                || status.equals("APPLIED")) {

            JButton shortlistButton =
                    new JButton(
                            "Shortlist"
                    );

            stylePrimaryButton(
                    shortlistButton
            );

            shortlistButton.addActionListener(
                    e -> updateStatus(
                            application,
                            "SHORTLISTED"
                    )
            );

            workflowPanel.add(
                    shortlistButton
            );
        }


        if ("SHORTLISTED".equals(status)) {

            JButton interviewButton =
                    new JButton(
                            "Interview"
                    );

            styleInterviewButton(
                    interviewButton
            );

            interviewButton.addActionListener(
                    e -> scheduleInterview(
                            application
                    )
            );

            workflowPanel.add(
                    interviewButton
            );
        }


        if ("INTERVIEW".equals(status)) {

            JButton selectButton =
                    new JButton(
                            "Select"
                    );

            styleSuccessButton(
                    selectButton
            );

            selectButton.addActionListener(
                    e -> updateStatus(
                            application,
                            "SELECTED"
                    )
            );

            workflowPanel.add(
                    selectButton
            );
        }


        if (!"SELECTED".equals(status)
                && !"REJECTED".equals(status)) {

            JButton rejectButton =
                    new JButton(
                            "Reject"
                    );

            styleDangerButton(
                    rejectButton
            );

            rejectButton.addActionListener(
                    e -> updateStatus(
                            application,
                            "REJECTED"
                    )
            );

            workflowPanel.add(
                    rejectButton
            );
        }


        actionPanel.add(
                workflowPanel
        );


        // =====================================================
        // BUTTON ACTIONS
        // =====================================================

        profileButton.addActionListener(
                e -> viewApplicant(
                        application
                )
        );

        cvButton.addActionListener(
                e -> viewCV(
                        application
                )
        );


        // =====================================================
        // ADD TO CARD
        // =====================================================

        card.add(
                picturePanel,
                BorderLayout.WEST
        );

        card.add(
                infoPanel,
                BorderLayout.CENTER
        );

        card.add(
                actionPanel,
                BorderLayout.EAST
        );


        return card;
    }


    // =========================================================
    // PROFILE PICTURE
    // =========================================================

    private JLabel createProfilePicture(
            Application application
    ) {

        JLabel label =
                new JLabel();

        label.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        label.setVerticalAlignment(
                SwingConstants.CENTER
        );

        label.setPreferredSize(
                new Dimension(
                        100,
                        100
                )
        );


        WorkerProfile profile =
                profileDAO.getProfile(
                        application
                                .getApplicantId()
                );


        if (profile != null
                && profile.getProfilePicture() != null
                && !profile.getProfilePicture()
                .trim()
                .isEmpty()) {

            File file =
                    new File(
                            profile
                                    .getProfilePicture()
                    );


            if (file.exists()) {

                ImageIcon icon =
                        new ImageIcon(
                                file.getAbsolutePath()
                        );

                Image image =
                        icon.getImage()
                                .getScaledInstance(
                                        90,
                                        90,
                                        Image.SCALE_SMOOTH
                                );

                label.setIcon(
                        new ImageIcon(image)
                );

                return label;
            }
        }


        label.setText("No Photo");

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        label.setForeground(
                MUTED
        );

        return label;
    }


    // =========================================================
    // VIEW PROFILE
    // =========================================================

    private void viewApplicant(
            Application application
    ) {

        WorkerProfile profile =
                profileDAO.getProfile(
                        application
                                .getApplicantId()
                );


        String headline =
                profile != null
                        ? displayValue(
                        profile.getHeadline()
                )
                        : "Not provided";


        String education =
                profile != null
                        ? displayValue(
                        profile.getEducation()
                )
                        : "Not provided";


        String experience =
                profile != null
                        ? displayValue(
                        profile.getExperience()
                )
                        : "Not provided";


        String skills =
                displayValue(
                        application
                                .getApplicantSkills()
                );


        String phone =
                displayValue(
                        application
                                .getApplicantPhone()
                );


        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                15,
                                10
                        )
                );

        panel.setPreferredSize(
                new Dimension(
                        650,
                        430
                )
        );


        JLabel picture =
                createProfilePicture(
                        application
                );

        JPanel left =
                new JPanel(
                        new BorderLayout()
                );

        left.setPreferredSize(
                new Dimension(
                        120,
                        150
                )
        );

        left.add(
                picture,
                BorderLayout.NORTH
        );


        JTextArea details =
                new JTextArea();

        details.setEditable(false);

        details.setLineWrap(true);

        details.setWrapStyleWord(true);

        details.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        details.setText(
                "Name\n"
                        + application
                        .getApplicantName()
                        + "\n\n"

                        + "Professional Headline\n"
                        + headline
                        + "\n\n"

                        + "Email\n"
                        + application
                        .getApplicantEmail()
                        + "\n\n"

                        + "Phone\n"
                        + phone
                        + "\n\n"

                        + "Education\n"
                        + education
                        + "\n\n"

                        + "Experience\n"
                        + experience
                        + "\n\n"

                        + "Skills\n"
                        + skills
                        + "\n\n"

                        + "Application Status\n"
                        + formatStatus(
                        application
                                .getStatus()
                )
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        details
                );


        panel.add(
                left,
                BorderLayout.WEST
        );

        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        JOptionPane.showMessageDialog(
                this,
                panel,
                "Applicant Profile",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // =========================================================
    // VIEW CV
    // =========================================================

    private void viewCV(
            Application application
    ) {

        String cvPath =
                application.getApplicantCv();


        if (cvPath == null
                || cvPath.trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "This applicant has not uploaded a CV.",
                    "CV Not Available",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }


        File cvFile =
                new File(cvPath);


        if (!cvFile.exists()) {

            JOptionPane.showMessageDialog(
                    this,
                    "CV file was not found.",
                    "CV Not Found",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        try {

            if (Desktop.isDesktopSupported()) {

                Desktop.getDesktop()
                        .open(cvFile);

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Your computer cannot open files automatically.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to open CV:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // UPDATE STATUS
    // =========================================================

    private void updateStatus(
            Application application,
            String newStatus
    ) {

        String currentStatus =
                application.getStatus();


        // -----------------------------------------------------
        // SHORTLIST
        // -----------------------------------------------------

        if ("SHORTLISTED".equals(newStatus)) {

            if (!"PENDING".equals(currentStatus)
                    && !"APPLIED".equals(currentStatus)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Only applied candidates can be shortlisted.",
                        "Invalid Workflow",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }
        }


        // -----------------------------------------------------
        // SELECT
        // -----------------------------------------------------

        if ("SELECTED".equals(newStatus)) {

            if (!"INTERVIEW".equals(currentStatus)) {

                JOptionPane.showMessageDialog(
                        this,
                        "The candidate must complete an interview first.",
                        "Invalid Workflow",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }
        }


        String action;

        switch (newStatus) {

            case "SHORTLISTED":
                action = "shortlist";
                break;

            case "SELECTED":
                action = "select";
                break;

            case "REJECTED":
                action = "reject";
                break;

            default:
                action = "update";
        }


        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to "
                                + action
                                + " "
                                + application
                                .getApplicantName()
                                + "?",
                        "Confirm Action",
                        JOptionPane.YES_NO_OPTION
                );


        if (confirm !=
                JOptionPane.YES_OPTION) {

            return;
        }


        boolean success =
                applicationDAO
                        .updateApplicationStatus(
                                application
                                        .getApplicationId(),
                                employer
                                        .getUserId(),
                                newStatus
                        );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Candidate status updated to "
                            + formatStatus(
                            newStatus
                    )
                            + ".",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadApplicants();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update candidate status.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // SCHEDULE INTERVIEW
    // =========================================================

    private void scheduleInterview(
            Application application
    ) {

        if (!"SHORTLISTED".equals(
                application.getStatus()
        )) {

            JOptionPane.showMessageDialog(
                    this,
                    "Only shortlisted candidates can be interviewed.",
                    "Invalid Workflow",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }



        JTextField dateField =
                new JTextField();

        JTextField timeField =
                new JTextField();

        JTextArea notesArea =
                new JTextArea(
                        4,
                        25
                );

        notesArea.setLineWrap(true);

        notesArea.setWrapStyleWord(true);


        JPanel panel =
                new JPanel(
                        new GridBagLayout()
                );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        6,
                        6,
                        6,
                        6
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        // =====================================================
        // DATE
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(
                new JLabel(
                        "Interview Date (YYYY-MM-DD):"
                ),
                gbc
        );
        panel.add(
                new JLabel(
                        "Interview Date:"
                ),
                gbc
        );


        gbc.gridx = 1;

        panel.add(
                dateField,
                gbc
        );


        // =====================================================
        // TIME
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(
                new JLabel(
                        "Interview Time (HH:MM):"
                ),
                gbc
        );

        panel.add(
                new JLabel(
                        "Interview Time:"
                ),
                gbc
        );


        gbc.gridx = 1;

        panel.add(
                timeField,
                gbc
        );


        // =====================================================
        // NOTES
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 2;

        gbc.anchor =
                GridBagConstraints.NORTHWEST;

        panel.add(
                new JLabel(
                        "Notes:"
                ),
                gbc
        );


        gbc.gridx = 1;

        panel.add(
                new JScrollPane(
                        notesArea
                ),
                gbc
        );


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Schedule Interview",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );


        if (result !=
                JOptionPane.OK_OPTION) {

            return;
        }


        String interviewDate =
                dateField.getText()
                        .trim();

        String interviewTime =
                timeField.getText()
                        .trim();

        String interviewNotes =
                notesArea.getText()
                        .trim();


        // =====================================================
        // VALIDATION
        // =====================================================

        if (interviewDate.isEmpty()
                || interviewTime.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter interview date and time.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // Basic date format validation
        if (!interviewDate.matches(
                "\\d{4}-\\d{2}-\\d{2}"
        )) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter date in this format:\nYYYY-MM-DD",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // Basic time format validation
        if (!interviewTime.matches(
                "\\d{2}:\\d{2}"
        )) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter time in this format:\nHH:MM",
                    "Invalid Time",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // =====================================================
        // SAVE INTERVIEW
        // =====================================================

        boolean success =
                applicationDAO
                        .scheduleInterview(
                                application
                                        .getApplicationId(),
                                employer
                                        .getUserId(),
                                interviewDate,
                                interviewTime,
                                interviewNotes
                        );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Interview scheduled successfully!\n\n"
                            + "Candidate: "
                            + application
                            .getApplicantName()
                            + "\nDate: "
                            + interviewDate
                            + "\nTime: "
                            + interviewTime,
                    "Interview Scheduled",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadApplicants();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to schedule interview.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // STATUS
    // =========================================================

    private String formatStatus(
            String status
    ) {

        if (status == null) {
            return "Applied";
        }


        switch (
                status.toUpperCase()
        ) {

            case "PENDING":
            case "APPLIED":
                return "Applied";

            case "SHORTLISTED":
                return "Shortlisted";

            case "INTERVIEW":
                return "Interview";

            case "SELECTED":
            case "ACCEPTED":
                return "Selected";

            case "REJECTED":
                return "Rejected";

            default:
                return status;
        }
    }


    // =========================================================
    // STATUS STYLE
    // =========================================================

    private void styleStatusLabel(
            JLabel label,
            String status
    ) {

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );


        if ("SHORTLISTED".equals(status)) {

            label.setForeground(
                    new Color(
                            109,
                            40,
                            217
                    )
            );

            label.setBackground(
                    new Color(
                            237,
                            233,
                            254
                    )
            );

        } else if ("INTERVIEW".equals(status)) {

            label.setForeground(
                    new Color(
                            180,
                            83,
                            9
                    )
            );

            label.setBackground(
                    new Color(
                            254,
                            243,
                            199
                    )
            );

        } else if ("SELECTED".equals(status)
                || "ACCEPTED".equals(status)) {

            label.setForeground(
                    new Color(
                            21,
                            128,
                            61
                    )
            );

            label.setBackground(
                    new Color(
                            220,
                            252,
                            231
                    )
            );

        } else if ("REJECTED".equals(status)) {

            label.setForeground(
                    new Color(
                            185,
                            28,
                            28
                    )
            );

            label.setBackground(
                    new Color(
                            254,
                            226,
                            226
                    )
            );

        } else {

            label.setForeground(
                    PRIMARY
            );

            label.setBackground(
                    new Color(
                            219,
                            234,
                            254
                    )
            );
        }
    }


    // =========================================================
    // BUTTON STYLES
    // =========================================================

    private void stylePrimaryButton(
            JButton button
    ) {

        button.setBackground(
                PRIMARY
        );

        button.setForeground(
                Color.WHITE
        );

        button.setFocusPainted(false);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );
    }


    private void styleInterviewButton(
            JButton button
    ) {

        button.setBackground(
                new Color(
                        124,
                        58,
                        237
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setFocusPainted(false);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );
    }


    private void styleSuccessButton(
            JButton button
    ) {

        button.setBackground(
                new Color(
                        22,
                        163,
                        74
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setFocusPainted(false);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );
    }


    private void styleDangerButton(
            JButton button
    ) {

        button.setBackground(
                new Color(
                        220,
                        38,
                        38
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setFocusPainted(false);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );
    }


    private void styleSecondaryButton(
            JButton button
    ) {

        button.setBackground(
                Color.WHITE
        );

        button.setForeground(
                TEXT
        );

        button.setFocusPainted(false);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );
    }


    private void styleHeaderButton(
            JButton button
    ) {

        button.setBackground(
                new Color(
                        51,
                        65,
                        85
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setFocusPainted(false);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );
    }


    // =========================================================
    // HELPERS
    // =========================================================

    private String safe(
            String value
    ) {

        return value == null
                ? ""
                : value.trim();
    }


    private String displayValue(
            String value
    ) {

        if (value == null
                || value.trim().isEmpty()) {

            return "Not provided";
        }

        return value.trim();
    }


    private String escapeHtml(
            String value
    ) {

        if (value == null) {
            return "";
        }

        return value
                .replace(
                        "&",
                        "&amp;"
                )
                .replace(
                        "<",
                        "&lt;"
                )
                .replace(
                        ">",
                        "&gt;"
                );
    }
}