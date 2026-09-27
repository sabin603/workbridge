package ui;

import dao.JobDAO;
import model.Job;
import model.User;

import javax.swing.*;
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

    public EditJobForm(User employer, Job job) {

        this.employer = employer;
        this.job = job;

        setTitle("WorkBridge - Edit Job");

        setSize(600, 650);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        loadJobData();
    }

    private void createUI() {

        JPanel panel = new JPanel();

        panel.setLayout(null);

        JLabel titleLabel =
                new JLabel("Edit Job");

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        titleLabel.setBounds(
                240, 20, 150, 35
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


        // UPDATE BUTTON

        JButton updateButton =
                new JButton("Update Job");

        updateButton.setBounds(
                220, 520, 140, 40
        );

        panel.add(updateButton);


        updateButton.addActionListener(
                e -> updateJob()
        );


        add(panel);
    }


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


    private void updateJob() {

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
                    "Job updated successfully!"
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update job.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}