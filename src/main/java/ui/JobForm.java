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

        setSize(600, 650);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();
    }

    private void createUI() {

        JPanel panel = new JPanel();

        panel.setLayout(null);

        JLabel titleLabel =
                new JLabel("Post a New Job");

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        titleLabel.setBounds(
                200, 20, 250, 35
        );

        panel.add(titleLabel);


        // JOB TITLE

        JLabel jobTitleLabel =
                new JLabel("Job Title:");

        jobTitleLabel.setBounds(
                50, 80, 120, 25
        );

        panel.add(jobTitleLabel);

        titleField =
                new JTextField();

        titleField.setBounds(
                180, 80, 350, 25
        );

        panel.add(titleField);


        // CATEGORY

        JLabel categoryLabel =
                new JLabel("Category:");

        categoryLabel.setBounds(
                50, 120, 120, 25
        );

        panel.add(categoryLabel);

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
                                "Other"
                        }
                );

        categoryComboBox.setBounds(
                180, 120, 350, 25
        );

        panel.add(categoryComboBox);


        // DESCRIPTION

        JLabel descriptionLabel =
                new JLabel("Description:");

        descriptionLabel.setBounds(
                50, 160, 120, 25
        );

        panel.add(descriptionLabel);

        descriptionArea =
                new JTextArea();

        descriptionArea.setLineWrap(true);

        descriptionArea.setWrapStyleWord(true);

        JScrollPane descriptionScroll =
                new JScrollPane(descriptionArea);

        descriptionScroll.setBounds(
                180, 160, 350, 80
        );

        panel.add(descriptionScroll);


        // REQUIREMENTS

        JLabel requirementsLabel =
                new JLabel("Requirements:");

        requirementsLabel.setBounds(
                50, 260, 120, 25
        );

        panel.add(requirementsLabel);

        requirementsArea =
                new JTextArea();

        requirementsArea.setLineWrap(true);

        requirementsArea.setWrapStyleWord(true);

        JScrollPane requirementsScroll =
                new JScrollPane(requirementsArea);

        requirementsScroll.setBounds(
                180, 260, 350, 100
        );

        panel.add(requirementsScroll);


        // LOCATION

        JLabel locationLabel =
                new JLabel("Location:");

        locationLabel.setBounds(
                50, 380, 120, 25
        );

        panel.add(locationLabel);

        locationField =
                new JTextField();

        locationField.setBounds(
                180, 380, 350, 25
        );

        panel.add(locationField);


        // SALARY

        JLabel salaryLabel =
                new JLabel("Salary:");

        salaryLabel.setBounds(
                50, 420, 120, 25
        );

        panel.add(salaryLabel);

        salaryField =
                new JTextField();

        salaryField.setBounds(
                180, 420, 350, 25
        );

        panel.add(salaryField);


        // JOB TYPE

        JLabel jobTypeLabel =
                new JLabel("Job Type:");

        jobTypeLabel.setBounds(
                50, 460, 120, 25
        );

        panel.add(jobTypeLabel);

        jobTypeComboBox =
                new JComboBox<>(
                        new String[]{
                                "FULL_TIME",
                                "PART_TIME"
                        }
                );

        jobTypeComboBox.setBounds(
                180, 460, 350, 25
        );

        panel.add(jobTypeComboBox);


        // POST BUTTON

        JButton postButton =
                new JButton("Post Job");

        postButton.setBounds(
                220, 520, 140, 40
        );

        panel.add(postButton);


        postButton.addActionListener(
                e -> postJob()
        );


        add(panel);
    }


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


        if (
                title.isEmpty() ||
                        description.isEmpty() ||
                        requirements.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all required fields."
            );

            return;
        }


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
                    "Job posted successfully!"
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
}