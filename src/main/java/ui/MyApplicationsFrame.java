package ui;

import dao.ApplicationDAO;
import model.Application;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MyApplicationsFrame extends JFrame {

    private User jobSeeker;

    private ApplicationDAO applicationDAO;

    private JTable applicationTable;

    private DefaultTableModel tableModel;


    public MyApplicationsFrame(User jobSeeker) {

        this.jobSeeker = jobSeeker;

        applicationDAO = new ApplicationDAO();

        setTitle("WorkBridge - My Applications");

        setSize(900, 500);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        loadApplications();
    }


    private void createUI() {

        setLayout(
                new BorderLayout()
        );


        // =========================
        // TOP PANEL
        // =========================

        JLabel titleLabel =
                new JLabel("My Applications");

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
        // APPLICATION TABLE
        // =========================

        String[] columns = {

                "ID",
                "Job Title",
                "Category",
                "Location",
                "Job Type",
                "Applied Date",
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


        applicationTable =
                new JTable(
                        tableModel
                );


        applicationTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        applicationTable
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


        JButton refreshButton =
                new JButton("Refresh");


        JButton closeButton =
                new JButton("Close");


        bottomPanel.add(
                refreshButton
        );


        bottomPanel.add(
                closeButton
        );


        add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // =========================
        // BUTTON ACTIONS
        // =========================

        refreshButton.addActionListener(
                e -> loadApplications()
        );


        closeButton.addActionListener(
                e -> dispose()
        );
    }


    // =========================
    // LOAD APPLICATIONS
    // =========================

    private void loadApplications() {

        tableModel.setRowCount(0);


        List<Application> applications =
                applicationDAO.getApplicationsByApplicant(
                        jobSeeker.getUserId()
                );


        for (Application application : applications) {

            tableModel.addRow(
                    new Object[]{

                            application.getApplicationId(),

                            application.getJobTitle(),

                            application.getJobCategory(),

                            application.getJobLocation(),

                            application.getJobType(),

                            application.getAppliedAt(),

                            application.getStatus()
                    }
            );
        }
    }
}