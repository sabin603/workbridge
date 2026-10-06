package ui;

import dao.ApplicationDAO;
import model.Application;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MyApplicationsFrame extends JFrame {

    private User jobSeeker;
    private ApplicationDAO applicationDAO;

    private JTable applicationTable;
    private DefaultTableModel tableModel;

    // Colors
    private final Color PRIMARY = new Color(37, 99, 235);
    private final Color BACKGROUND = new Color(245, 247, 250);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT = new Color(31, 41, 55);
    private final Color MUTED = new Color(107, 114, 128);

    public MyApplicationsFrame(User jobSeeker) {

        this.jobSeeker = jobSeeker;
        applicationDAO = new ApplicationDAO();

        setTitle("WorkBridge - My Applications");
        setSize(1050, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
        loadApplications();
    }

    private void createUI() {

        getContentPane().setBackground(BACKGROUND);

        setLayout(new BorderLayout());

        // =========================
        // HEADER
        // =========================

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(WHITE);
        headerPanel.setBorder(
                new EmptyBorder(18, 25, 18, 25)
        );

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(
                new BoxLayout(titlePanel, BoxLayout.Y_AXIS)
        );
        titlePanel.setBackground(WHITE);

        JLabel titleLabel = new JLabel("My Applications");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        titleLabel.setForeground(TEXT);

        JLabel subtitleLabel =
                new JLabel(
                        "Track the progress of your job applications"
                );

        subtitleLabel.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        subtitleLabel.setForeground(MUTED);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitleLabel);

        headerPanel.add(
                titlePanel,
                BorderLayout.WEST
        );

        add(
                headerPanel,
                BorderLayout.NORTH
        );


        // =========================
        // TABLE
        // =========================

        String[] columns = {

                "Job Title",
                "Category",
                "Location",
                "Job Type",
                "Applied Date",
                "Application Status"
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
                new JTable(tableModel);

        applicationTable.setRowHeight(42);

        applicationTable.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        applicationTable.setForeground(TEXT);

        applicationTable.setBackground(WHITE);

        applicationTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        applicationTable.setGridColor(
                new Color(229, 231, 235)
        );

        applicationTable.setShowVerticalLines(false);

        applicationTable.setIntercellSpacing(
                new Dimension(0, 1)
        );


        // =========================
        // TABLE HEADER
        // =========================

        applicationTable.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                13
                        )
                );

        applicationTable.getTableHeader()
                .setBackground(
                        new Color(239, 246, 255)
                );

        applicationTable.getTableHeader()
                .setForeground(TEXT);

        applicationTable.getTableHeader()
                .setPreferredSize(
                        new Dimension(0, 40)
                );


        // =========================
        // STATUS RENDERER
        // =========================

        applicationTable.getColumnModel()
                .getColumn(5)
                .setCellRenderer(
                        new StatusRenderer()
                );


        JScrollPane scrollPane =
                new JScrollPane(
                        applicationTable
                );

        scrollPane.setBorder(
                new EmptyBorder(15, 20, 10, 20)
        );

        scrollPane.getViewport()
                .setBackground(WHITE);

        add(
                scrollPane,
                BorderLayout.CENTER
        );


        // =========================
        // BOTTOM PANEL
        // =========================

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout()
                );

        bottomPanel.setBackground(
                BACKGROUND
        );

        bottomPanel.setBorder(
                new EmptyBorder(
                        5,
                        20,
                        15,
                        20
                )
        );


        JLabel workflowLabel =
                new JLabel(
                        "Application Process:  Applied  →  Shortlisted  →  Interview  →  Selected / Rejected"
                );

        workflowLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        workflowLabel.setForeground(MUTED);


        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        buttonPanel.setBackground(
                BACKGROUND
        );


        JButton refreshButton =
                new JButton("Refresh");

        refreshButton.setFocusPainted(false);

        refreshButton.setBackground(PRIMARY);

        refreshButton.setForeground(WHITE);

        refreshButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );


        JButton closeButton =
                new JButton("Close");

        closeButton.setFocusPainted(false);

        closeButton.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );


        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);


        bottomPanel.add(
                workflowLabel,
                BorderLayout.WEST
        );

        bottomPanel.add(
                buttonPanel,
                BorderLayout.EAST
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

                            application.getJobTitle(),

                            application.getJobCategory(),

                            application.getJobLocation(),

                            application.getJobType(),

                            application.getAppliedAt(),

                            formatStatus(
                                    application.getStatus()
                            )
                    }
            );
        }
    }


    // =========================
    // FORMAT STATUS
    // =========================

    private String formatStatus(String status) {

        if (status == null) {
            return "Applied";
        }

        switch (status.toUpperCase()) {

            case "PENDING":
            case "APPLIED":
                return "Applied";

            case "SHORTLISTED":
                return "Shortlisted";

            case "INTERVIEW":
                return "Interview";

            case "SELECTED":
            case "ACCEPTED":
                return "Selected";

            case "REJECTED":
                return "Rejected";

            default:
                return status;
        }
    }


    // =========================
    // STATUS CELL RENDERER
    // =========================

    private class StatusRenderer
            extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column
        ) {

            JLabel label =
                    (JLabel) super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column
                    );

            label.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            label.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            12
                    )
            );

            String status =
                    value == null
                            ? ""
                            : value.toString();

            if (status.equals("Applied")) {

                label.setForeground(
                        new Color(37, 99, 235)
                );

            } else if (status.equals("Shortlisted")) {

                label.setForeground(
                        new Color(124, 58, 237)
                );

            } else if (status.equals("Interview")) {

                label.setForeground(
                        new Color(217, 119, 6)
                );

            } else if (status.equals("Selected")) {

                label.setForeground(
                        new Color(22, 163, 74)
                );

            } else if (status.equals("Rejected")) {

                label.setForeground(
                        new Color(220, 38, 38)
                );

            } else {

                label.setForeground(TEXT);
            }

            return label;
        }
    }
}