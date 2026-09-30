package ui;

import ai.JobMatchResult;
import ai.JobRecommendationEngine;
import dao.ApplicationDAO;
import dao.JobDAO;
import dao.ProfileDAO;
import model.Application;
import model.Job;
import model.User;
import model.WorkerProfile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class JobRecommendationFrame extends JFrame {

    private User jobSeeker;

    private ProfileDAO profileDAO;
    private JobDAO jobDAO;
    private ApplicationDAO applicationDAO;

    private JTable recommendationTable;
    private DefaultTableModel tableModel;

    private List<JobMatchResult> recommendations;


    public JobRecommendationFrame(User jobSeeker) {

        this.jobSeeker = jobSeeker;

        profileDAO = new ProfileDAO();
        jobDAO = new JobDAO();
        applicationDAO = new ApplicationDAO();

        setTitle("WorkBridge - AI Job Recommendations");
        setSize(1000, 550);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        loadRecommendations();
    }


    // =========================
    // CREATE UI
    // =========================

    private void createUI() {

        setLayout(new BorderLayout());


        // =========================
        // TOP PANEL
        // =========================

        JPanel topPanel =
                new JPanel(
                        new BorderLayout()
                );

        JLabel titleLabel =
                new JLabel(
                        "AI Job Recommendations"
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        topPanel.add(
                titleLabel,
                BorderLayout.WEST
        );


        JLabel infoLabel =
                new JLabel(
                        "Jobs are ranked according to your skills"
                );

        topPanel.add(
                infoLabel,
                BorderLayout.EAST
        );

        add(
                topPanel,
                BorderLayout.NORTH
        );


        // =========================
        // TABLE
        // =========================

        String[] columns = {
                "Job ID",
                "Job Title",
                "Category",
                "Location",
                "Job Type",
                "Match %"
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


        recommendationTable =
                new JTable(tableModel);

        recommendationTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        add(
                new JScrollPane(
                        recommendationTable
                ),
                BorderLayout.CENTER
        );


        // =========================
        // BOTTOM PANEL
        // =========================

        JPanel bottomPanel =
                new JPanel();


        JButton viewButton =
                new JButton(
                        "View Match Details"
                );

        JButton applyButton =
                new JButton(
                        "Apply for Job"
                );

        JButton refreshButton =
                new JButton(
                        "Refresh"
                );

        JButton closeButton =
                new JButton(
                        "Close"
                );


        bottomPanel.add(viewButton);
        bottomPanel.add(applyButton);
        bottomPanel.add(refreshButton);
        bottomPanel.add(closeButton);


        add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // =========================
        // BUTTON ACTIONS
        // =========================

        viewButton.addActionListener(
                e -> viewMatchDetails()
        );

        applyButton.addActionListener(
                e -> applyForSelectedJob()
        );

        refreshButton.addActionListener(
                e -> loadRecommendations()
        );

        closeButton.addActionListener(
                e -> dispose()
        );
    }


    // =========================
    // LOAD RECOMMENDATIONS
    // =========================

    private void loadRecommendations() {

        tableModel.setRowCount(0);


        // Get job seeker's profile
        WorkerProfile profile =
                profileDAO.getProfile(
                        jobSeeker.getUserId()
                );


        // Check whether profile exists
        if (profile == null) {

            JOptionPane.showMessageDialog(
                    this,

                    "Please create your profile first.",

                    "Profile Required",

                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // Get skills
        String seekerSkills =
                profile.getSkills();


        if (
                seekerSkills == null ||
                        seekerSkills.trim().isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,

                    "Please add your skills to your profile first.",

                    "Skills Required",

                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // Get all jobs
        List<Job> jobs =
                jobDAO.getAllJobs();


        if (jobs.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,

                    "No jobs are currently available."
            );

            return;
        }


        // Generate recommendations
        recommendations =
                JobRecommendationEngine.recommendJobs(
                        seekerSkills,
                        jobs
                );


        // Add results to table
        for (
                JobMatchResult result :
                recommendations
        ) {

            Job job =
                    result.getJob();


            tableModel.addRow(
                    new Object[]{
                            job.getJobId(),
                            job.getTitle(),
                            job.getCategory(),
                            job.getLocation(),
                            job.getJobType(),
                            result.getMatchPercentage()
                                    + "%"
                    }
            );
        }
    }


    // =========================
    // VIEW MATCH DETAILS
    // =========================

    private void viewMatchDetails() {

        int selectedRow =
                recommendationTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,

                    "Please select a job first."
            );

            return;
        }


        JobMatchResult result =
                recommendations.get(
                        selectedRow
                );


        Job job =
                result.getJob();


        String matchedSkills =
                result.getMatchedSkills()
                        .isEmpty()
                        ? "None"
                        : String.join(
                        ", ",
                        result.getMatchedSkills()
                );


        String missingSkills =
                result.getMissingSkills()
                        .isEmpty()
                        ? "None"
                        : String.join(
                        ", ",
                        result.getMissingSkills()
                );


        String details =
                "Job Title: "
                        + job.getTitle()

                        + "\nCategory: "
                        + job.getCategory()

                        + "\nLocation: "
                        + job.getLocation()

                        + "\nJob Type: "
                        + job.getJobType()

                        + "\n\nMatch Percentage: "
                        + result.getMatchPercentage()
                        + "%"

                        + "\n\nMatched Skills:\n"
                        + matchedSkills

                        + "\n\nMissing Skills:\n"
                        + missingSkills;


        JTextArea textArea =
                new JTextArea(details);

        textArea.setEditable(false);

        textArea.setLineWrap(true);

        textArea.setWrapStyleWord(true);

        textArea.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );


        JScrollPane scrollPane =
                new JScrollPane(textArea);

        scrollPane.setPreferredSize(
                new Dimension(
                        500,
                        350
                )
        );


        JOptionPane.showMessageDialog(
                this,

                scrollPane,

                "AI Match Details",

                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // =========================
    // APPLY FOR SELECTED JOB
    // =========================

    private void applyForSelectedJob() {

        int selectedRow =
                recommendationTable.getSelectedRow();


        // No job selected
        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,

                    "Please select a job first.",

                    "No Job Selected",

                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // Get selected recommendation
        JobMatchResult result =
                recommendations.get(
                        selectedRow
                );


        Job selectedJob =
                result.getJob();


        // Show confirmation
        int confirm =
                JOptionPane.showConfirmDialog(
                        this,

                        "Are you sure you want to apply for:\n\n"
                                + selectedJob.getTitle()
                                + "\n\nMatch Percentage: "
                                + result.getMatchPercentage()
                                + "%",

                        "Confirm Application",

                        JOptionPane.YES_NO_OPTION
                );


        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }


        // Apply for job
        boolean success =
                applicationDAO.applyForJob(
                        selectedJob.getJobId(),
                        jobSeeker.getUserId()
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,

                    "Application submitted successfully!",

                    "Application Successful",

                    JOptionPane.INFORMATION_MESSAGE
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