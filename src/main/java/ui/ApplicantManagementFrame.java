package ui;

import dao.ApplicationDAO;
import model.Application;
import model.Job;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ApplicantManagementFrame extends JFrame {

    private User employer;
    private Job job;

    private ApplicationDAO applicationDAO;

    private JTable applicantTable;
    private DefaultTableModel tableModel;


    public ApplicantManagementFrame(
            User employer,
            Job job
    ) {

        this.employer = employer;
        this.job = job;

        applicationDAO =
                new ApplicationDAO();

        setTitle(
                "Applicants - "
                        + job.getTitle()
        );

        setSize(850, 500);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        loadApplicants();
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


        JLabel titleLabel =
                new JLabel(
                        "Applicants for: "
                                + job.getTitle()
                );


        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );


        topPanel.add(
                titleLabel,
                BorderLayout.WEST
        );


        add(
                topPanel,
                BorderLayout.NORTH
        );


        // =========================
        // TABLE
        // =========================

        String[] columns = {

                "ID",
                "Applicant Name",
                "Email",
                "Phone",
                "Skills",
                "Status"
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


        applicantTable =
                new JTable(
                        tableModel
                );


        applicantTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        applicantTable
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


        JButton viewButton =
                new JButton(
                        "View Applicant"
                );


        JButton acceptButton =
                new JButton(
                        "Accept"
                );


        JButton rejectButton =
                new JButton(
                        "Reject"
                );


        JButton refreshButton =
                new JButton(
                        "Refresh"
                );


        bottomPanel.add(
                viewButton
        );


        bottomPanel.add(
                acceptButton
        );


        bottomPanel.add(
                rejectButton
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

        viewButton.addActionListener(
                e -> viewApplicant()
        );


        acceptButton.addActionListener(
                e -> updateStatus("ACCEPTED")
        );


        rejectButton.addActionListener(
                e -> updateStatus("REJECTED")
        );


        refreshButton.addActionListener(
                e -> loadApplicants()
        );
    }


    // =========================
    // LOAD APPLICANTS
    // =========================

    private void loadApplicants() {

        tableModel.setRowCount(0);


        List<Application> applications =
                applicationDAO.getApplicantsByJob(
                        job.getJobId(),
                        employer.getUserId()
                );


        for (
                Application application :
                applications
        ) {

            String phone =
                    application.getApplicantPhone();

            String skills =
                    application.getApplicantSkills();


            if (phone == null) {
                phone = "Not provided";
            }


            if (skills == null) {
                skills = "Not provided";
            }


            tableModel.addRow(
                    new Object[]{

                            application.getApplicationId(),

                            application.getApplicantName(),

                            application.getApplicantEmail(),

                            phone,

                            skills,

                            application.getStatus()
                    }
            );
        }


        if (applications.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No applicants found for this job."
            );
        }
    }


    // =========================
    // GET SELECTED APPLICATION
    // =========================

    private Application getSelectedApplication() {

        int selectedRow =
                applicantTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an applicant first."
            );

            return null;
        }


        int applicationId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );


        List<Application> applications =
                applicationDAO.getApplicantsByJob(
                        job.getJobId(),
                        employer.getUserId()
                );


        for (
                Application application :
                applications
        ) {

            if (
                    application.getApplicationId()
                            == applicationId
            ) {

                return application;
            }
        }


        JOptionPane.showMessageDialog(
                this,
                "Application not found."
        );


        return null;
    }


    // =========================
    // VIEW APPLICANT
    // =========================

    private void viewApplicant() {

        Application application =
                getSelectedApplication();


        if (application == null) {

            return;
        }


        String phone =
                application.getApplicantPhone();

        String skills =
                application.getApplicantSkills();


        if (phone == null) {
            phone = "Not provided";
        }


        if (skills == null) {
            skills = "Not provided";
        }


        String details =
                """
                Applicant Name:
                %s

                Email:
                %s

                Phone:
                %s

                Skills:
                %s

                Application Status:
                %s
                """.formatted(

                        application.getApplicantName(),

                        application.getApplicantEmail(),

                        phone,

                        skills,

                        application.getStatus()
                );


        JTextArea textArea =
                new JTextArea(details);


        textArea.setEditable(false);

        textArea.setLineWrap(true);

        textArea.setWrapStyleWord(true);


        JScrollPane scrollPane =
                new JScrollPane(
                        textArea
                );


        scrollPane.setPreferredSize(
                new Dimension(
                        450,
                        350
                )
        );


        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Applicant Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // =========================
    // UPDATE STATUS
    // =========================

    private void updateStatus(
            String status
    ) {

        Application application =
                getSelectedApplication();


        if (application == null) {

            return;
        }


        if (
                status.equals(
                        application.getStatus()
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Application is already "
                            + status + "."
            );

            return;
        }


        String message;


        if (
                status.equals("ACCEPTED")
        ) {

            message =
                    "Are you sure you want to accept this application?";

        } else {

            message =
                    "Are you sure you want to reject this application?";
        }


        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        message,
                        "Confirm Action",
                        JOptionPane.YES_NO_OPTION
                );


        if (
                confirm !=
                        JOptionPane.YES_OPTION
        ) {

            return;
        }


        boolean success =
                applicationDAO.updateApplicationStatus(
                        application.getApplicationId(),
                        employer.getUserId(),
                        status
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Application status updated to "
                            + status + "."
            );


            loadApplicants();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update application status.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}