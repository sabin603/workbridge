package ui;

import dao.JobDAO;
import model.Job;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class JobManagementFrame extends JFrame {

    private JobDAO jobDAO;
    private JTable jobTable;
    private DefaultTableModel tableModel;

    public JobManagementFrame() {

        jobDAO = new JobDAO();

        setTitle("WorkBridge - Job Management");
        setSize(1000, 550);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        loadJobs();
    }

    private void createUI() {

        setLayout(new BorderLayout());

        // =========================
        // TOP PANEL
        // =========================

        JLabel titleLabel =
                new JLabel("Job Management");

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        JPanel topPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        topPanel.add(titleLabel);

        add(
                topPanel,
                BorderLayout.NORTH
        );


        // =========================
        // JOB TABLE
        // =========================

        String[] columns = {
                "ID",
                "Employer ID",
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

        jobTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(jobTable);

        add(
                scrollPane,
                BorderLayout.CENTER
        );


        // =========================
        // BUTTON PANEL
        // =========================

        JPanel bottomPanel =
                new JPanel();

        JButton deleteButton =
                new JButton("Delete Job");

        JButton refreshButton =
                new JButton("Refresh");

        JButton viewButton =
                new JButton("View Details");

        JButton closeButton =
                new JButton("Close");

        bottomPanel.add(deleteButton);
        bottomPanel.add(viewButton);
        bottomPanel.add(refreshButton);
        bottomPanel.add(closeButton);

        add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // =========================
        // BUTTON ACTIONS
        // =========================

        deleteButton.addActionListener(
                e -> deleteSelectedJob()
        );

        viewButton.addActionListener(
                e -> viewSelectedJob()
        );

        refreshButton.addActionListener(
                e -> loadJobs()
        );

        closeButton.addActionListener(
                e -> dispose()
        );
    }


    // =========================
    // LOAD ALL JOBS
    // =========================

    private void loadJobs() {

        tableModel.setRowCount(0);

        List<Job> jobs =
                jobDAO.getAllJobs();

        for (Job job : jobs) {

            tableModel.addRow(
                    new Object[]{
                            job.getJobId(),
                            job.getEmployerId(),
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
    // DELETE SELECTED JOB
    // =========================

    private void deleteSelectedJob() {

        int selectedRow =
                jobTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a job first."
            );

            return;
        }


        int jobId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );


        String jobTitle =
                tableModel.getValueAt(
                        selectedRow,
                        2
                ).toString();


        int confirm =
                JOptionPane.showConfirmDialog(
                        this,

                        "Are you sure you want to delete:\n"
                                + jobTitle
                                + "?",

                        "Confirm Delete",

                        JOptionPane.YES_NO_OPTION,

                        JOptionPane.WARNING_MESSAGE
                );


        if (
                confirm !=
                        JOptionPane.YES_OPTION
        ) {

            return;
        }


        boolean success =
                jobDAO.deleteJobByAdmin(
                        jobId
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
    // VIEW JOB DETAILS
    // =========================

    private void viewSelectedJob() {

        int selectedRow =
                jobTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a job first."
            );

            return;
        }


        int jobId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );

        int employerId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        1
                );

        String title =
                tableModel.getValueAt(
                        selectedRow,
                        2
                ).toString();

        String category =
                tableModel.getValueAt(
                        selectedRow,
                        3
                ).toString();

        String location =
                tableModel.getValueAt(
                        selectedRow,
                        4
                ).toString();

        String salary =
                tableModel.getValueAt(
                        selectedRow,
                        5
                ).toString();

        String jobType =
                tableModel.getValueAt(
                        selectedRow,
                        6
                ).toString();


        String details =
                "Job ID: " + jobId
                        + "\nEmployer ID: "
                        + employerId
                        + "\nTitle: "
                        + title
                        + "\nCategory: "
                        + category
                        + "\nLocation: "
                        + location
                        + "\nSalary: "
                        + salary
                        + "\nJob Type: "
                        + jobType;


        JOptionPane.showMessageDialog(
                this,
                details,
                "Job Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}