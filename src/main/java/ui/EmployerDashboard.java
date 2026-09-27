package ui;

import dao.JobDAO;
import model.Job;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployerDashboard extends JFrame {

    private User employer;

    private JobDAO jobDAO;

    private JTable jobTable;

    private DefaultTableModel tableModel;


    public EmployerDashboard(User user) {

        this.employer = user;

        jobDAO = new JobDAO();

        setTitle(
                "WorkBridge - Employer Dashboard"
        );

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
        // TOP PANEL
        // =========================

        JPanel topPanel =
                new JPanel(
                        new BorderLayout()
                );


        JLabel welcomeLabel =
                new JLabel(
                        "Welcome, "
                                + employer.getName()
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


        JButton postJobButton =
                new JButton(
                        "Post New Job"
                );


        topPanel.add(
                postJobButton,
                BorderLayout.EAST
        );


        add(
                topPanel,
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
                new JTable(
                        tableModel
                );


        jobTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        jobTable
                );


        add(
                scrollPane,
                BorderLayout.CENTER
        );


        // =========================
        // BOTTOM PANEL
        // =========================

        JPanel bottomPanel =
                new JPanel();


        JButton editButton =
                new JButton(
                        "Edit"
                );


        JButton deleteButton =
                new JButton(
                        "Delete"
                );


        JButton applicantsButton =
                new JButton(
                        "View Applicants"
                );


        JButton refreshButton =
                new JButton(
                        "Refresh"
                );


        bottomPanel.add(
                editButton
        );


        bottomPanel.add(
                deleteButton
        );


        bottomPanel.add(
                applicantsButton
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

        postJobButton.addActionListener(
                e -> openPostJob()
        );


        editButton.addActionListener(
                e -> editSelectedJob()
        );


        deleteButton.addActionListener(
                e -> deleteSelectedJob()
        );


        applicantsButton.addActionListener(
                e -> viewApplicants()
        );


        refreshButton.addActionListener(
                e -> loadJobs()
        );
    }


    // =========================
    // LOAD EMPLOYER JOBS
    // =========================

    private void loadJobs() {

        tableModel.setRowCount(0);


        List<Job> jobs =
                jobDAO.getJobsByEmployer(
                        employer.getUserId()
                );


        for (
                Job job :
                jobs
        ) {

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
                jobDAO.getJobsByEmployer(
                        employer.getUserId()
                );


        for (
                Job job :
                jobs
        ) {

            if (
                    job.getJobId() == jobId
            ) {

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
    // POST NEW JOB
    // =========================

    private void openPostJob() {

        new JobForm(
                employer
        ).setVisible(true);
    }


    // =========================
    // EDIT JOB
    // =========================

    private void editSelectedJob() {

        Job selectedJob =
                getSelectedJob();


        if (selectedJob == null) {

            return;
        }


        new EditJobForm(
                employer,
                selectedJob
        ).setVisible(true);
    }


    // =========================
    // DELETE JOB
    // =========================

    private void deleteSelectedJob() {

        Job selectedJob =
                getSelectedJob();


        if (selectedJob == null) {

            return;
        }


        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this job?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );


        if (
                confirm !=
                        JOptionPane.YES_OPTION
        ) {

            return;
        }


        boolean success =
                jobDAO.deleteJob(
                        selectedJob.getJobId(),
                        employer.getUserId()
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Job deleted successfully!"
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


    // =========================
    // VIEW APPLICANTS
    // =========================

    private void viewApplicants() {

        Job selectedJob =
                getSelectedJob();


        if (selectedJob == null) {

            return;
        }


        new ApplicantManagementFrame(
                employer,
                selectedJob
        ).setVisible(true);
    }
}