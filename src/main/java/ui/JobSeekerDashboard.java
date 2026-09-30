package ui;

import dao.JobDAO;
import dao.ApplicationDAO;
import model.Job;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class JobSeekerDashboard extends JFrame {

    private User jobSeeker;

    private JobDAO jobDAO;
    private ApplicationDAO applicationDAO;

    private JTable jobTable;
    private DefaultTableModel tableModel;

    private JTextField searchField;

    private JComboBox<String> categoryComboBox;
    private JComboBox<String> jobTypeComboBox;


    public JobSeekerDashboard(User user) {

        this.jobSeeker = user;

        jobDAO = new JobDAO();
        applicationDAO = new ApplicationDAO();

        setTitle("WorkBridge - Job Seeker Dashboard");

        setSize(900, 600);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        loadJobs();
    }


    private void createUI() {

        setLayout(
                new BorderLayout()
        );


        // =========================
        // MAIN TOP CONTAINER
        // =========================

        JPanel northPanel =
                new JPanel(
                        new BorderLayout()
                );


        // =========================
        // WELCOME + BUTTON PANEL
        // =========================

        JPanel topPanel =
                new JPanel(
                        new BorderLayout()
                );


        JLabel welcomeLabel =
                new JLabel(
                        "Welcome, "
                                + jobSeeker.getName()
                );


        welcomeLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );


        topPanel.add(
                welcomeLabel,
                BorderLayout.WEST
        );


        // =========================
        // TOP RIGHT BUTTONS
        // =========================

        JButton profileButton =
                new JButton("My Profile");


        JButton applicationsButton =
                new JButton("My Applications");
        JButton matchingButton = new JButton("Find Matching Jobs");
        matchingButton.addActionListener(e -> {
            new JobRecommendationFrame(jobSeeker).setVisible(true);
        });

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        buttonPanel.add(matchingButton);
        buttonPanel.add(
                applicationsButton
        );


        buttonPanel.add(
                profileButton
        );


        topPanel.add(
                buttonPanel,
                BorderLayout.EAST
        );


        northPanel.add(
                topPanel,
                BorderLayout.NORTH
        );


        // =========================
        // SEARCH PANEL
        // =========================

        JPanel searchPanel =
                new JPanel();


        searchPanel.add(
                new JLabel("Search:")
        );


        searchField =
                new JTextField(15);


        searchPanel.add(
                searchField
        );


        searchPanel.add(
                new JLabel("Category:")
        );


        categoryComboBox =
                new JComboBox<>(
                        new String[]{
                                "All Categories",
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


        searchPanel.add(
                categoryComboBox
        );


        searchPanel.add(
                new JLabel("Job Type:")
        );


        jobTypeComboBox =
                new JComboBox<>(
                        new String[]{
                                "All Types",
                                "FULL_TIME",
                                "PART_TIME"
                        }
                );


        searchPanel.add(
                jobTypeComboBox
        );


        JButton searchButton =
                new JButton("Search");


        searchPanel.add(
                searchButton
        );


        JButton resetButton =
                new JButton("Reset");


        searchPanel.add(
                resetButton
        );


        northPanel.add(
                searchPanel,
                BorderLayout.SOUTH
        );


        // Add complete top section
        add(
                northPanel,
                BorderLayout.NORTH
        );


        // =========================
        // JOB TABLE
        // =========================

        String[] columns = {

                "ID",
                "Title",
                "Category",
                "Location",
                "Salary",
                "Job Type"
        };


        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {

                        return false;
                    }
                };


        jobTable =
                new JTable(tableModel);


        JScrollPane scrollPane =
                new JScrollPane(jobTable);


        add(
                scrollPane,
                BorderLayout.CENTER
        );


        // =========================
        // BOTTOM PANEL
        // =========================

        JPanel bottomPanel =
                new JPanel();


        JButton viewButton =
                new JButton("View Details");


        JButton applyButton =
                new JButton("Apply");


        JButton refreshButton =
                new JButton("Refresh");


        bottomPanel.add(
                viewButton
        );


        bottomPanel.add(
                applyButton
        );


        bottomPanel.add(
                refreshButton
        );


        add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // =========================
        // BUTTON ACTIONS
        // =========================

        searchButton.addActionListener(
                e -> searchJobs()
        );


        resetButton.addActionListener(
                e -> resetSearch()
        );


        refreshButton.addActionListener(
                e -> loadJobs()
        );


        viewButton.addActionListener(
                e -> viewSelectedJob()
        );


        applyButton.addActionListener(
                e -> applyForSelectedJob()
        );


        // =========================
        // MY PROFILE ACTION
        // =========================

        profileButton.addActionListener(e -> {

            new ProfileForm(jobSeeker)
                    .setVisible(true);

        });


        // =========================
        // MY APPLICATIONS ACTION
        // =========================

        applicationsButton.addActionListener(e -> {

            new MyApplicationsFrame(jobSeeker)
                    .setVisible(true);

        });
    }


    // =========================
    // LOAD ALL JOBS
    // =========================

    private void loadJobs() {

        tableModel.setRowCount(0);

        List<Job> jobs =
                jobDAO.getAllJobs();

        addJobsToTable(jobs);
    }


    // =========================
    // SEARCH JOBS
    // =========================

    private void searchJobs() {

        String keyword =
                searchField
                        .getText()
                        .trim();


        String category =
                (String) categoryComboBox
                        .getSelectedItem();


        String jobType =
                (String) jobTypeComboBox
                        .getSelectedItem();


        List<Job> jobs =
                jobDAO.searchJobs(
                        keyword,
                        category,
                        jobType
                );


        tableModel.setRowCount(0);

        addJobsToTable(jobs);


        if (jobs.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No jobs found."
            );
        }
    }


    // =========================
    // RESET SEARCH
    // =========================

    private void resetSearch() {

        searchField.setText("");

        categoryComboBox.setSelectedIndex(0);

        jobTypeComboBox.setSelectedIndex(0);

        loadJobs();
    }


    // =========================
    // ADD JOBS TO TABLE
    // =========================

    private void addJobsToTable(
            List<Job> jobs
    ) {

        for (Job job : jobs) {

            tableModel.addRow(
                    new Object[]{

                            job.getJobId(),

                            job.getTitle(),

                            job.getCategory(),

                            job.getLocation(),

                            job.getSalary(),

                            job.getJobType()
                    }
            );
        }
    }


    // =========================
    // VIEW JOB DETAILS
    // =========================

    private void viewSelectedJob() {

        Job selectedJob =
                getSelectedJob();


        if (selectedJob == null) {

            return;
        }


        String details =
                """
                Job Title: %s

                Category: %s

                Description:
                %s

                Requirements:
                %s

                Location: %s

                Salary: %s

                Job Type: %s
                """.formatted(

                        selectedJob.getTitle(),

                        selectedJob.getCategory(),

                        selectedJob.getDescription(),

                        selectedJob.getRequirements(),

                        selectedJob.getLocation(),

                        selectedJob.getSalary(),

                        selectedJob.getJobType()
                );


        JTextArea textArea =
                new JTextArea(details);


        textArea.setEditable(false);

        textArea.setLineWrap(true);

        textArea.setWrapStyleWord(true);


        JScrollPane scrollPane =
                new JScrollPane(textArea);


        scrollPane.setPreferredSize(
                new Dimension(
                        500,
                        400
                )
        );


        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Job Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // =========================
    // GET SELECTED JOB
    // =========================

    private Job getSelectedJob() {

        int selectedRow =
                jobTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a job first."
            );

            return null;
        }


        int jobId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );


        List<Job> jobs =
                jobDAO.getAllJobs();


        for (Job job : jobs) {

            if (job.getJobId() == jobId) {

                return job;
            }
        }


        JOptionPane.showMessageDialog(
                this,
                "Job not found."
        );


        return null;
    }


    // =========================
    // APPLY FOR JOB
    // =========================

    private void applyForSelectedJob() {

        Job selectedJob =
                getSelectedJob();


        if (selectedJob == null) {

            return;
        }


        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to apply for this job?",
                        "Confirm Application",
                        JOptionPane.YES_NO_OPTION
                );


        if (
                confirm !=
                        JOptionPane.YES_OPTION
        ) {

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
                    "Application submitted successfully!"
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "You may have already applied for this job.",
                    "Application Failed",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
}