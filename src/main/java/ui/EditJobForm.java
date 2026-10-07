package ui;

import dao.JobDAO;
import model.Job;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EditJobForm extends JFrame {

    private User employer;
    private Job job;

    private JTextField titleField;
    private JComboBox<String> categoryComboBox;
    private JTextArea descriptionArea;
    private JTextArea requirementsArea;
    private JTextField locationField;
    private JTextField salaryField;
    private JComboBox<String> jobTypeComboBox;

    private final Color PRIMARY = new Color(37, 99, 235);
    private final Color PRIMARY_DARK = new Color(29, 78, 216);
    private final Color DARK = new Color(30, 41, 59);
    private final Color MUTED = new Color(100, 116, 139);
    private final Color BACKGROUND = new Color(241, 245, 249);
    private final Color BORDER = new Color(203, 213, 225);

    public EditJobForm(User employer, Job job) {

        this.employer = employer;
        this.job = job;

        setTitle("WorkBridge - Edit Job");

        setSize(900, 750);

        setMinimumSize(
                new Dimension(750, 650)
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        loadJobData();
    }

    private void createUI() {

        JPanel background =
                new JPanel(new GridBagLayout());

        background.setBackground(BACKGROUND);

        JPanel card =
                new JPanel(new BorderLayout());

        card.setPreferredSize(
                new Dimension(700, 650)
        );

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(226, 232, 240)
                        ),
                        new EmptyBorder(
                                28, 35, 28, 35
                        )
                )
        );

        // =========================
        // HEADER
        // =========================

        JPanel header =
                new JPanel();

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        header.setBackground(Color.WHITE);

        JLabel titleLabel =
                new JLabel("Edit Job");

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        titleLabel.setForeground(DARK);

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel subtitleLabel =
                new JLabel(
                        "Update the details of your job posting"
                );

        subtitleLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        subtitleLabel.setForeground(MUTED);

        subtitleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        header.add(titleLabel);

        header.add(
                Box.createVerticalStrut(5)
        );

        header.add(subtitleLabel);

        header.add(
                Box.createVerticalStrut(25)
        );

        card.add(
                header,
                BorderLayout.NORTH
        );

        // =========================
        // FORM
        // =========================

        JPanel form =
                new JPanel(new GridBagLayout());

        form.setBackground(Color.WHITE);

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(7, 0, 7, 0);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1.0;

        // JOB TITLE

        addLabel(
                form,
                gbc,
                "Job Title",
                0
        );

        titleField =
                new JTextField();

        styleField(titleField);

        addComponent(
                form,
                gbc,
                titleField,
                1
        );

        // CATEGORY

        addLabel(
                form,
                gbc,
                "Category",
                2
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
                                "Plumbing",
                                "Electrician",
                                "Video Editor",
                                "System Administrator",
                                "Waiter",
                                "Hotel Manager",
                                "House Cleaner",
                                "Receptionist",
                                "Other"
                        }
                );

        styleComboBox(
                categoryComboBox
        );

        addComponent(
                form,
                gbc,
                categoryComboBox,
                3
        );

        // DESCRIPTION

        addLabel(
                form,
                gbc,
                "Job Description",
                4
        );

        descriptionArea =
                new JTextArea(4, 20);

        styleTextArea(
                descriptionArea
        );

        JScrollPane descriptionScroll =
                createScrollPane(
                        descriptionArea
                );

        addComponent(
                form,
                gbc,
                descriptionScroll,
                5
        );

        // REQUIREMENTS

        addLabel(
                form,
                gbc,
                "Requirements",
                6
        );

        requirementsArea =
                new JTextArea(4, 20);

        styleTextArea(
                requirementsArea
        );

        JScrollPane requirementsScroll =
                createScrollPane(
                        requirementsArea
                );

        addComponent(
                form,
                gbc,
                requirementsScroll,
                7
        );

        // LOCATION

        addLabel(
                form,
                gbc,
                "Location",
                8
        );

        locationField =
                new JTextField();

        styleField(locationField);

        addComponent(
                form,
                gbc,
                locationField,
                9
        );

        // SALARY

        addLabel(
                form,
                gbc,
                "Salary",
                10
        );

        salaryField =
                new JTextField();

        styleField(salaryField);

        addComponent(
                form,
                gbc,
                salaryField,
                11
        );

        // JOB TYPE

        addLabel(
                form,
                gbc,
                "Job Type",
                12
        );

        jobTypeComboBox =
                new JComboBox<>(
                        new String[]{
                                "FULL_TIME",
                                "PART_TIME"
                        }
                );

        styleComboBox(
                jobTypeComboBox
        );

        addComponent(
                form,
                gbc,
                jobTypeComboBox,
                13
        );

        JScrollPane formScroll =
                new JScrollPane(form);

        formScroll.setBorder(
                BorderFactory.createEmptyBorder()
        );

        formScroll.setBackground(Color.WHITE);

        formScroll.getVerticalScrollBar()
                .setUnitIncrement(16);

        card.add(
                formScroll,
                BorderLayout.CENTER
        );

        // =========================
        // BUTTONS
        // =========================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                12,
                                15
                        )
                );

        buttonPanel.setBackground(Color.WHITE);

        JButton cancelButton =
                createSecondaryButton(
                        "Cancel"
                );

        JButton updateButton =
                createPrimaryButton(
                        "Update Job"
                );

        buttonPanel.add(cancelButton);

        buttonPanel.add(updateButton);

        cancelButton.addActionListener(
                e -> dispose()
        );

        updateButton.addActionListener(
                e -> updateJob()
        );

        card.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        background.add(card);

        add(background);

        getRootPane().setDefaultButton(
                updateButton
        );
    }

    // =========================
    // FORM HELPERS
    // =========================

    private void addLabel(
            JPanel panel,
            GridBagConstraints gbc,
            String text,
            int row
    ) {

        gbc.gridx = 0;
        gbc.gridy = row;

        gbc.gridwidth = 1;

        gbc.weighty = 0;

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

        panel.add(
                label,
                gbc
        );
    }

    private void addComponent(
            JPanel panel,
            GridBagConstraints gbc,
            Component component,
            int row
    ) {

        gbc.gridx = 0;
        gbc.gridy = row;

        gbc.gridwidth = 1;

        gbc.weighty = 0;

        panel.add(
                component,
                gbc
        );
    }

    private void styleField(
            JTextField field
    ) {

        field.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        field.setPreferredSize(
                new Dimension(
                        500,
                        40
                )
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
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

        comboBox.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        comboBox.setPreferredSize(
                new Dimension(
                        500,
                        40
                )
        );

        comboBox.setBackground(Color.WHITE);

        comboBox.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );
    }

    private void styleTextArea(
            JTextArea area
    ) {

        area.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        area.setLineWrap(true);

        area.setWrapStyleWord(true);

        area.setRows(4);

        area.setBorder(
                new EmptyBorder(
                        9, 12, 9, 12
                )
        );

        area.setBackground(Color.WHITE);
    }

    private JScrollPane createScrollPane(
            JTextArea area
    ) {

        JScrollPane scroll =
                new JScrollPane(area);

        scroll.setPreferredSize(
                new Dimension(
                        500,
                        100
                )
        );

        scroll.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );

        return scroll;
    }

    private JButton createPrimaryButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        button.setForeground(Color.WHITE);

        button.setBackground(PRIMARY);

        button.setPreferredSize(
                new Dimension(
                        140,
                        42
                )
        );

        button.setFocusPainted(false);

        button.setBorderPainted(false);

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

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        button.setForeground(DARK);

        button.setBackground(
                new Color(248, 250, 252)
        );

        button.setPreferredSize(
                new Dimension(
                        110,
                        42
                )
        );

        button.setFocusPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    // =========================
    // LOAD JOB
    // =========================

    private void loadJobData() {

        titleField.setText(
                job.getTitle()
        );

        categoryComboBox.setSelectedItem(
                job.getCategory()
        );

        descriptionArea.setText(
                job.getDescription()
        );

        requirementsArea.setText(
                job.getRequirements()
        );

        locationField.setText(
                job.getLocation()
        );

        salaryField.setText(
                job.getSalary()
        );

        jobTypeComboBox.setSelectedItem(
                job.getJobType()
        );
    }

    // =========================
    // UPDATE JOB
    // =========================

    private void updateJob() {

        String title =
                titleField.getText().trim();

        String category =
                (String) categoryComboBox
                        .getSelectedItem();

        String description =
                descriptionArea
                        .getText()
                        .trim();

        String requirements =
                requirementsArea
                        .getText()
                        .trim();

        String location =
                locationField
                        .getText()
                        .trim();

        String salary =
                salaryField
                        .getText()
                        .trim();

        String jobType =
                (String) jobTypeComboBox
                        .getSelectedItem();

        if (title.isEmpty() ||
                description.isEmpty() ||
                requirements.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in the Job Title, Description and Requirements.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        job.setTitle(title);

        job.setCategory(category);

        job.setDescription(description);

        job.setRequirements(requirements);

        job.setLocation(location);

        job.setSalary(salary);

        job.setJobType(jobType);

        JobDAO jobDAO =
                new JobDAO();

        boolean success =
                jobDAO.updateJob(job);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Job updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update job.",
                    "Update Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}

