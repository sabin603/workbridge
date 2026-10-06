package ui;

import dao.JobDAO;
import model.Job;
import model.User;

import javax.swing.*;
import java.awt.*;

public class JobForm extends JFrame {

    private User employer;

    private JTextField titleField;
    private JComboBox<String> categoryComboBox;
    private JTextArea descriptionArea;
    private JTextArea requirementsArea;
    private JTextField locationField;
    private JTextField salaryField;
    private JComboBox<String> jobTypeComboBox;

    public JobForm(User employer) {

        this.employer = employer;

        setTitle("WorkBridge - Post a Job");
        setSize(700, 720);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
    }

    private void createUI() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        // ================= HEADER =================

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(25, 35, 50));
        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        );

        JLabel titleLabel =
                new JLabel("Post a New Job");

        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 25)
        );

        JLabel subtitleLabel =
                new JLabel(
                        "Create a job opportunity and find the right candidate."
                );

        subtitleLabel.setForeground(
                new Color(203, 213, 225)
        );

        subtitleLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitleLabel);

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // ================= FORM =================

        JPanel formPanel = new JPanel(
                new GridBagLayout()
        );

        formPanel.setBackground(
                new Color(245, 247, 250)
        );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 35, 15, 35
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(7, 7, 7, 7);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.anchor =
                GridBagConstraints.NORTHWEST;

        // JOB TITLE

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;

        formPanel.add(
                createLabel("Job Title"),
                gbc
        );

        titleField = new JTextField();

        styleTextField(titleField);

        gbc.gridx = 1;
        gbc.weightx = 1;

        formPanel.add(
                titleField,
                gbc
        );

        // CATEGORY

        gbc.gridx = 0;
        gbc.gridy++;

        formPanel.add(
                createLabel("Category"),
                gbc
        );

        categoryComboBox =
                new JComboBox<>(
                        new String[]{
                                "Frontend",
                                "Backend",
                                "Full Stack",
                                "UI/UX Design",
                                "Graphic Design",
                                "Data Entry",
                                "Marketing",
                                "Writing",
                                "Mobile Development",
                                "Database",
                                "Other"
                        }
                );

        styleComboBox(categoryComboBox);

        gbc.gridx = 1;

        formPanel.add(
                categoryComboBox,
                gbc
        );

        // DESCRIPTION

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        formPanel.add(
                createLabel("Description"),
                gbc
        );

        descriptionArea =
                new JTextArea(5, 30);

        styleTextArea(descriptionArea);

        JScrollPane descriptionScroll =
                new JScrollPane(
                        descriptionArea
                );

        gbc.gridx = 1;
        gbc.fill =
                GridBagConstraints.BOTH;
        gbc.weighty = 0.5;

        formPanel.add(
                descriptionScroll,
                gbc
        );

        // REQUIREMENTS

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill =
                GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;

        formPanel.add(
                createLabel("Requirements"),
                gbc
        );

        requirementsArea =
                new JTextArea(5, 30);

        styleTextArea(requirementsArea);

        JScrollPane requirementsScroll =
                new JScrollPane(
                        requirementsArea
                );

        gbc.gridx = 1;
        gbc.fill =
                GridBagConstraints.BOTH;
        gbc.weighty = 0.5;

        formPanel.add(
                requirementsScroll,
                gbc
        );

        // LOCATION

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill =
                GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;

        formPanel.add(
                createLabel("Location"),
                gbc
        );

        locationField =
                new JTextField();

        styleTextField(locationField);

        gbc.gridx = 1;

        formPanel.add(
                locationField,
                gbc
        );

        // SALARY

        gbc.gridx = 0;
        gbc.gridy++;

        formPanel.add(
                createLabel("Salary"),
                gbc
        );

        salaryField =
                new JTextField();

        styleTextField(salaryField);

        salaryField.setToolTipText(
                "Example: NPR 40,000 - 60,000"
        );

        gbc.gridx = 1;

        formPanel.add(
                salaryField,
                gbc
        );

        // JOB TYPE

        gbc.gridx = 0;
        gbc.gridy++;

        formPanel.add(
                createLabel("Job Type"),
                gbc
        );

        jobTypeComboBox =
                new JComboBox<>(
                        new String[]{
                                "FULL_TIME",
                                "PART_TIME",
                                "CONTRACT",
                                "INTERNSHIP",
                                "REMOTE"
                        }
                );

        styleComboBox(jobTypeComboBox);

        gbc.gridx = 1;

        formPanel.add(
                jobTypeComboBox,
                gbc
        );

        mainPanel.add(
                new JScrollPane(formPanel),
                BorderLayout.CENTER
        );

        // ================= BUTTONS =================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                12,
                                12
                        )
                );

        buttonPanel.setBackground(
                new Color(245, 247, 250)
        );

        JButton cancelButton =
                new JButton("Cancel");

        JButton postButton =
                new JButton("Post Job");

        styleSecondaryButton(cancelButton);
        stylePrimaryButton(postButton);

        buttonPanel.add(cancelButton);
        buttonPanel.add(postButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // ================= ACTIONS =================

        cancelButton.addActionListener(
                e -> dispose()
        );

        postButton.addActionListener(
                e -> postJob()
        );

        add(mainPanel);
    }

    // =========================================================
    // POST JOB
    // =========================================================

    private void postJob() {

        String title =
                titleField.getText().trim();

        String category =
                (String) categoryComboBox
                        .getSelectedItem();

        String description =
                descriptionArea.getText().trim();

        String requirements =
                requirementsArea.getText().trim();

        String location =
                locationField.getText().trim();

        String salary =
                salaryField.getText().trim();

        String jobType =
                (String) jobTypeComboBox
                        .getSelectedItem();

        // ================= VALIDATION =================

        if (title.isEmpty()) {

            showWarning(
                    "Please enter the job title."
            );

            titleField.requestFocus();
            return;
        }

        if (description.isEmpty()) {

            showWarning(
                    "Please enter the job description."
            );

            descriptionArea.requestFocus();
            return;
        }

        if (requirements.isEmpty()) {

            showWarning(
                    "Please enter the job requirements."
            );

            requirementsArea.requestFocus();
            return;
        }

        if (location.isEmpty()) {

            showWarning(
                    "Please enter the job location."
            );

            locationField.requestFocus();
            return;
        }

        if (salary.isEmpty()) {

            showWarning(
                    "Please enter the salary information."
            );

            salaryField.requestFocus();
            return;
        }

        // ================= CREATE JOB =================

        Job job =
                new Job(
                        employer.getUserId(),
                        title,
                        category,
                        description,
                        requirements,
                        location,
                        salary,
                        jobType
                );

        JobDAO jobDAO =
                new JobDAO();

        boolean success =
                jobDAO.createJob(job);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Job posted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to post job.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // UI HELPERS
    // =========================================================

    private JLabel createLabel(String text) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        label.setForeground(
                new Color(31, 41, 55)
        );

        return label;
    }

    private void styleTextField(
            JTextField field
    ) {

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        field.setPreferredSize(
                new Dimension(350, 34)
        );
    }

    private void styleTextArea(
            JTextArea area
    ) {

        area.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        area.setBorder(
                BorderFactory.createEmptyBorder(
                        8, 8, 8, 8
                )
        );
    }

    private void styleComboBox(
            JComboBox<String> comboBox
    ) {

        comboBox.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        comboBox.setPreferredSize(
                new Dimension(350, 34)
        );
    }

    private void stylePrimaryButton(
            JButton button
    ) {

        button.setBackground(
                new Color(37, 99, 235)
        );

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
                        10, 20, 10, 20
                )
        );
    }

    private void styleSecondaryButton(
            JButton button
    ) {

        button.setBackground(Color.WHITE);

        button.setForeground(
                new Color(31, 41, 55)
        );

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
                                new Color(209, 213, 219)
                        ),
                        BorderFactory.createEmptyBorder(
                                9, 18, 9, 18
                        )
                )
        );
    }

    private void showWarning(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Missing Information",
                JOptionPane.WARNING_MESSAGE
        );
    }
}