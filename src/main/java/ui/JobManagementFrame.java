package ui;

import dao.JobDAO;
import model.Job;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class JobManagementFrame extends JFrame {

    private final JobDAO jobDAO;

    private JTable jobTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    private JTextField searchField;
    private JComboBox<String> categoryComboBox;
    private JComboBox<String> jobTypeComboBox;

    private JLabel resultLabel;

    // =========================
    // COLORS
    // =========================

    private final Color PRIMARY =
            new Color(37, 99, 235);

    private final Color PRIMARY_DARK =
            new Color(29, 78, 216);

    private final Color BACKGROUND =
            new Color(245, 247, 250);

    private final Color WHITE =
            Color.WHITE;

    private final Color TEXT =
            new Color(31, 41, 55);

    private final Color MUTED =
            new Color(107, 114, 128);

    private final Color BORDER =
            new Color(229, 231, 235);


    public JobManagementFrame() {

        jobDAO = new JobDAO();

        setTitle(
                "WorkBridge - Job Management"
        );

        setSize(
                1150,
                700
        );

        setMinimumSize(
                new Dimension(
                        950,
                        600
                )
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        loadJobs();
    }


    // =========================================================
    // CREATE UI
    // =========================================================

    private void createUI() {

        JPanel rootPanel =
                new JPanel(
                        new BorderLayout()
                );

        rootPanel.setBackground(
                BACKGROUND
        );

        setContentPane(rootPanel);


        // =========================
        // HEADER
        // =========================

        JPanel header =
                createHeader();

        rootPanel.add(
                header,
                BorderLayout.NORTH
        );


        // =========================
        // MAIN CONTENT
        // =========================

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                0,
                                15
                        )
                );

        mainPanel.setBackground(
                BACKGROUND
        );

        mainPanel.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );


        // =========================
        // FILTER PANEL
        // =========================

        JPanel filterPanel =
                createFilterPanel();

        mainPanel.add(
                filterPanel,
                BorderLayout.NORTH
        );


        // =========================
        // TABLE
        // =========================

        JPanel tablePanel =
                createTablePanel();

        mainPanel.add(
                tablePanel,
                BorderLayout.CENTER
        );


        // =========================
        // BOTTOM ACTIONS
        // =========================

        JPanel bottomPanel =
                createBottomPanel();

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        rootPanel.add(
                mainPanel,
                BorderLayout.CENTER
        );


        // =========================
        // FILTER ACTIONS
        // =========================

        searchField.getDocument()
                .addDocumentListener(
                        new javax.swing.event.DocumentListener() {

                            public void insertUpdate(
                                    javax.swing.event.DocumentEvent e) {
                                applyFilters();
                            }

                            public void removeUpdate(
                                    javax.swing.event.DocumentEvent e) {
                                applyFilters();
                            }

                            public void changedUpdate(
                                    javax.swing.event.DocumentEvent e) {
                                applyFilters();
                            }
                        }
                );


        categoryComboBox.addActionListener(
                e -> applyFilters()
        );

        jobTypeComboBox.addActionListener(
                e -> applyFilters()
        );
    }


    // =========================================================
    // HEADER
    // =========================================================

    private JPanel createHeader() {

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(
                WHITE
        );

        header.setBorder(
                new EmptyBorder(
                        22,
                        28,
                        22,
                        28
                )
        );


        JLabel titleLabel =
                new JLabel(
                        "Job Management"
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        titleLabel.setForeground(
                TEXT
        );


        JLabel subtitleLabel =
                new JLabel(
                        "Review, search and manage "
                                + "jobs posted on WorkBridge"
                );

        subtitleLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        subtitleLabel.setForeground(
                MUTED
        );


        JPanel textPanel =
                new JPanel();

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        textPanel.setBackground(
                WHITE
        );

        textPanel.add(
                titleLabel
        );

        textPanel.add(
                Box.createVerticalStrut(5)
        );

        textPanel.add(
                subtitleLabel
        );


        header.add(
                textPanel,
                BorderLayout.WEST
        );


        return header;
    }


    // =========================================================
    // FILTER PANEL
    // =========================================================

    private JPanel createFilterPanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                15,
                                0
                        )
                );

        panel.setBackground(
                WHITE
        );

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                15,
                                18,
                                15,
                                18
                        )
                )
        );


        // =========================
        // SEARCH
        // =========================

        JPanel searchPanel =
                new JPanel(
                        new BorderLayout(
                                8,
                                0
                        )
                );

        searchPanel.setBackground(
                WHITE
        );


        JLabel searchLabel =
                new JLabel(
                        "Search"
                );

        searchLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        searchLabel.setForeground(
                TEXT
        );


        searchField =
                new JTextField();

        searchField.setPreferredSize(
                new Dimension(
                        260,
                        38
                )
        );

        searchField.setToolTipText(
                "Search by job title, location or category"
        );


        searchPanel.add(
                searchLabel,
                BorderLayout.WEST
        );

        searchPanel.add(
                searchField,
                BorderLayout.CENTER
        );


        // =========================
        // CATEGORY
        // =========================

        JPanel categoryPanel =
                new JPanel(
                        new BorderLayout(
                                8,
                                0
                        )
                );

        categoryPanel.setBackground(
                WHITE
        );


        JLabel categoryLabel =
                new JLabel(
                        "Category"
                );

        categoryLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        categoryLabel.setForeground(
                TEXT
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

        categoryComboBox.setPreferredSize(
                new Dimension(
                        160,
                        38
                )
        );


        categoryPanel.add(
                categoryLabel,
                BorderLayout.WEST
        );

        categoryPanel.add(
                categoryComboBox,
                BorderLayout.CENTER
        );


        // =========================
        // JOB TYPE
        // =========================

        JPanel typePanel =
                new JPanel(
                        new BorderLayout(
                                8,
                                0
                        )
                );

        typePanel.setBackground(
                WHITE
        );


        JLabel typeLabel =
                new JLabel(
                        "Type"
                );

        typeLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        typeLabel.setForeground(
                TEXT
        );


        jobTypeComboBox =
                new JComboBox<>(
                        new String[]{
                                "All Types",
                                "FULL_TIME",
                                "PART_TIME"
                        }
                );

        jobTypeComboBox.setPreferredSize(
                new Dimension(
                        140,
                        38
                )
        );


        typePanel.add(
                typeLabel,
                BorderLayout.WEST
        );

        typePanel.add(
                jobTypeComboBox,
                BorderLayout.CENTER
        );


        panel.add(
                searchPanel,
                BorderLayout.CENTER
        );

        panel.add(
                categoryPanel,
                BorderLayout.EAST
        );


        JPanel rightPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                0,
                                0
                        )
                );

        rightPanel.setBackground(
                WHITE
        );

        rightPanel.add(
                typePanel
        );


        panel.add(
                rightPanel,
                BorderLayout.EAST
        );


        return panel;
    }


    // =========================================================
    // TABLE PANEL
    // =========================================================

    private JPanel createTablePanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(
                WHITE
        );

        panel.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );


        // =========================
        // TABLE COLUMNS
        // =========================

        String[] columns = {
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
                            int column) {

                        return false;
                    }
                };


        jobTable =
                new JTable(
                        tableModel
                );


        jobTable.setSelectionMode(
                ListSelectionModel
                        .SINGLE_SELECTION
        );

        jobTable.setRowHeight(
                48
        );

        jobTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        jobTable.setForeground(
                TEXT
        );

        jobTable.setBackground(
                WHITE
        );

        jobTable.setGridColor(
                BORDER
        );

        jobTable.setShowVerticalLines(
                false
        );

        jobTable.setIntercellSpacing(
                new Dimension(
                        0,
                        1
                )
        );


        // =========================
        // HEADER STYLE
        // =========================

        jobTable.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                13
                        )
                );

        jobTable.getTableHeader()
                .setForeground(
                        TEXT
                );

        jobTable.getTableHeader()
                .setBackground(
                        new Color(
                                249,
                                250,
                                251
                        )
                );

        jobTable.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                42
                        )
                );


        // =========================
        // COLUMN WIDTHS
        // =========================

        jobTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(250);

        jobTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(150);

        jobTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(180);

        jobTable.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(140);

        jobTable.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(130);


        // =========================
        // RENDERERS
        // =========================

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );


        jobTable.getColumnModel()
                .getColumn(1)
                .setCellRenderer(
                        centerRenderer
                );

        jobTable.getColumnModel()
                .getColumn(4)
                .setCellRenderer(
                        centerRenderer
                );


        // =========================
        // SORTING
        // =========================

        sorter =
                new TableRowSorter<>(
                        tableModel
                );

        jobTable.setRowSorter(
                sorter
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        jobTable
                );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );


        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        return panel;
    }


    // =========================================================
    // BOTTOM PANEL
    // =========================================================

    private JPanel createBottomPanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(
                BACKGROUND
        );


        resultLabel =
                new JLabel(
                        "Jobs"
                );

        resultLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        resultLabel.setForeground(
                MUTED
        );


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


        JButton viewButton =
                createButton(
                        "View Details",
                        PRIMARY
                );


        JButton deleteButton =
                createButton(
                        "Delete Job",
                        new Color(
                                220,
                                38,
                                38
                        )
                );


        JButton refreshButton =
                createButton(
                        "Refresh",
                        new Color(
                                75,
                                85,
                                99
                        )
                );


        JButton closeButton =
                createButton(
                        "Close",
                        new Color(
                                107,
                                114,
                                128
                        )
                );


        buttonPanel.add(
                viewButton
        );

        buttonPanel.add(
                deleteButton
        );

        buttonPanel.add(
                refreshButton
        );

        buttonPanel.add(
                closeButton
        );


        viewButton.addActionListener(
                e -> viewSelectedJob()
        );

        deleteButton.addActionListener(
                e -> deleteSelectedJob()
        );

        refreshButton.addActionListener(
                e -> loadJobs()
        );

        closeButton.addActionListener(
                e -> dispose()
        );


        panel.add(
                resultLabel,
                BorderLayout.WEST
        );

        panel.add(
                buttonPanel,
                BorderLayout.EAST
        );


        return panel;
    }


    // =========================================================
    // BUTTON CREATOR
    // =========================================================

    private JButton createButton(
            String text,
            Color color) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        button.setForeground(
                WHITE
        );

        button.setBackground(
                color
        );

        button.setFocusPainted(
                false
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        18,
                        10,
                        18
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        return button;
    }


    // =========================================================
    // LOAD JOBS
    // =========================================================

    private void loadJobs() {

        tableModel.setRowCount(0);

        List<Job> jobs =
                jobDAO.getAllJobs();


        for (Job job : jobs) {

            tableModel.addRow(
                    new Object[]{
                            job.getTitle(),
                            job.getCategory(),
                            job.getLocation(),
                            job.getSalary(),
                            job.getJobType()
                    }
            );
        }


        updateResultLabel();
    }


    // =========================================================
    // FILTER JOBS
    // =========================================================

    private void applyFilters() {

        if (sorter == null) {
            return;
        }


        String searchText =
                searchField
                        .getText()
                        .trim()
                        .toLowerCase();


        String category =
                categoryComboBox
                        .getSelectedItem()
                        .toString();


        String jobType =
                jobTypeComboBox
                        .getSelectedItem()
                        .toString();


        sorter.setRowFilter(
                new RowFilter<
                        DefaultTableModel,
                        Integer>() {

                    @Override
                    public boolean include(
                            Entry<
                                    ? extends DefaultTableModel,
                                    ? extends Integer
                                    > entry) {

                        String title =
                                entry.getStringValue(
                                        0
                                ).toLowerCase();

                        String rowCategory =
                                entry.getStringValue(
                                        1
                                ).toLowerCase();

                        String location =
                                entry.getStringValue(
                                        2
                                ).toLowerCase();

                        String rowJobType =
                                entry.getStringValue(
                                        4
                                );


                        // Search filter

                        if (!searchText.isEmpty()) {

                            boolean matchesSearch =
                                    title.contains(
                                            searchText
                                    )
                                            || rowCategory.contains(
                                            searchText
                                    )
                                            || location.contains(
                                            searchText
                                    );

                            if (!matchesSearch) {
                                return false;
                            }
                        }


                        // Category filter

                        if (!category.equals(
                                "All Categories"
                        )) {

                            if (!rowCategory.equalsIgnoreCase(
                                    category
                            )) {

                                return false;
                            }
                        }


                        // Job type filter

                        if (!jobType.equals(
                                "All Types"
                        )) {

                            if (!rowJobType.equalsIgnoreCase(
                                    jobType
                            )) {

                                return false;
                            }
                        }


                        return true;
                    }
                }
        );


        updateResultLabel();
    }


    // =========================================================
    // RESULT LABEL
    // =========================================================

    private void updateResultLabel() {

        int visibleRows =
                jobTable.getRowCount();

        int totalRows =
                tableModel.getRowCount();


        resultLabel.setText(
                visibleRows
                        + " job"
                        + (visibleRows == 1
                        ? ""
                        : "s")
                        + " shown"
                        + " • "
                        + totalRows
                        + " total"
        );
    }


    // =========================================================
    // DELETE JOB
    // =========================================================

    private void deleteSelectedJob() {

        int selectedRow =
                jobTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a job first.",
                    "No Job Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // Convert view row to model row
        int modelRow =
                jobTable.convertRowIndexToModel(
                        selectedRow
                );


        String jobTitle =
                tableModel.getValueAt(
                        modelRow,
                        0
                ).toString();


        // We need the actual job ID.
        // Find the job by title from the DAO list.
        int jobId =
                findJobIdByTitle(
                        jobTitle
                );


        if (jobId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to identify the selected job.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        int confirm =
                JOptionPane.showConfirmDialog(
                        this,

                        "Are you sure you want to delete:\n\n"
                                + jobTitle
                                + "?",

                        "Confirm Job Deletion",

                        JOptionPane.YES_NO_OPTION,

                        JOptionPane.WARNING_MESSAGE
                );


        if (confirm !=
                JOptionPane.YES_OPTION) {

            return;
        }


        boolean success =
                jobDAO.deleteJobByAdmin(
                        jobId
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Job deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
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


    // =========================================================
    // FIND JOB ID
    // =========================================================

    private int findJobIdByTitle(
            String title) {

        List<Job> jobs =
                jobDAO.getAllJobs();


        for (Job job : jobs) {

            if (job.getTitle() != null
                    && job.getTitle()
                    .equals(title)) {

                return job.getJobId();
            }
        }


        return -1;
    }


    // =========================================================
    // VIEW DETAILS
    // =========================================================

    private void viewSelectedJob() {

        int selectedRow =
                jobTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a job first.",
                    "No Job Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        int modelRow =
                jobTable.convertRowIndexToModel(
                        selectedRow
                );


        String title =
                tableModel.getValueAt(
                        modelRow,
                        0
                ).toString();


        String category =
                tableModel.getValueAt(
                        modelRow,
                        1
                ).toString();


        String location =
                tableModel.getValueAt(
                        modelRow,
                        2
                ).toString();


        String salary =
                tableModel.getValueAt(
                        modelRow,
                        3
                ).toString();


        String jobType =
                tableModel.getValueAt(
                        modelRow,
                        4
                ).toString();


        // Get complete job object
        Job selectedJob =
                findJobByTitle(title);


        if (selectedJob == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load job details.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        showJobDetails(
                selectedJob,
                category,
                location,
                salary,
                jobType
        );
    }


    // =========================================================
    // FIND JOB
    // =========================================================

    private Job findJobByTitle(
            String title) {

        List<Job> jobs =
                jobDAO.getAllJobs();


        for (Job job : jobs) {

            if (job.getTitle() != null
                    && job.getTitle()
                    .equals(title)) {

                return job;
            }
        }


        return null;
    }


    // =========================================================
    // JOB DETAILS DIALOG
    // =========================================================

    private void showJobDetails(
            Job job,
            String category,
            String location,
            String salary,
            String jobType) {


        JDialog dialog =
                new JDialog(
                        this,
                        "Job Details",
                        true
                );

        dialog.setSize(
                650,
                600
        );

        dialog.setLocationRelativeTo(
                this
        );


        JPanel main =
                new JPanel(
                        new BorderLayout()
                );

        main.setBackground(
                BACKGROUND
        );

        main.setBorder(
                new EmptyBorder(
                        25,
                        25,
                        25,
                        25
                )
        );


        // =========================
        // TITLE
        // =========================

        JPanel header =
                new JPanel();

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        header.setBackground(
                BACKGROUND
        );


        JLabel titleLabel =
                new JLabel(
                        job.getTitle()
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
        );

        titleLabel.setForeground(
                TEXT
        );


        JLabel categoryLabel =
                new JLabel(
                        category
                                + "  •  "
                                + jobType
                );

        categoryLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        categoryLabel.setForeground(
                PRIMARY
        );


        header.add(
                titleLabel
        );

        header.add(
                Box.createVerticalStrut(7)
        );

        header.add(
                categoryLabel
        );


        main.add(
                header,
                BorderLayout.NORTH
        );


        // =========================
        // DETAILS
        // =========================

        JPanel details =
                new JPanel();

        details.setLayout(
                new BoxLayout(
                        details,
                        BoxLayout.Y_AXIS
                )
        );

        details.setBackground(
                WHITE
        );

        details.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                20,
                                20,
                                20,
                                20
                        )
                )
        );


        details.add(
                createDetailLabel(
                        "Location",
                        location
                )
        );

        details.add(
                Box.createVerticalStrut(12)
        );

        details.add(
                createDetailLabel(
                        "Salary",
                        salary
                )
        );

        details.add(
                Box.createVerticalStrut(12)
        );

        details.add(
                createDetailLabel(
                        "Job Type",
                        jobType
                )
        );

        details.add(
                Box.createVerticalStrut(20)
        );


        // Description

        JLabel descriptionTitle =
                new JLabel(
                        "Description"
                );

        descriptionTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        descriptionTitle.setForeground(
                TEXT
        );


        JTextArea descriptionArea =
                createTextArea(
                        job.getDescription()
                );


        details.add(
                descriptionTitle
        );

        details.add(
                Box.createVerticalStrut(8)
        );

        details.add(
                descriptionArea
        );


        details.add(
                Box.createVerticalStrut(18)
        );


        // Requirements

        JLabel requirementsTitle =
                new JLabel(
                        "Requirements"
                );

        requirementsTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        requirementsTitle.setForeground(
                TEXT
        );


        JTextArea requirementsArea =
                createTextArea(
                        job.getRequirements()
                );


        details.add(
                requirementsTitle
        );

        details.add(
                Box.createVerticalStrut(8)
        );

        details.add(
                requirementsArea
        );


        JScrollPane detailsScroll =
                new JScrollPane(
                        details
                );

        detailsScroll.setBorder(
                BorderFactory.createEmptyBorder()
        );

        detailsScroll.setBackground(
                BACKGROUND
        );


        main.add(
                detailsScroll,
                BorderLayout.CENTER
        );


        // =========================
        // CLOSE BUTTON
        // =========================

        JPanel bottom =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        bottom.setBackground(
                BACKGROUND
        );


        JButton closeButton =
                createButton(
                        "Close",
                        PRIMARY
                );


        closeButton.addActionListener(
                e -> dialog.dispose()
        );


        bottom.add(
                closeButton
        );


        main.add(
                bottom,
                BorderLayout.SOUTH
        );


        dialog.setContentPane(
                main
        );

        dialog.setVisible(true);
    }


    // =========================================================
    // DETAIL LABEL
    // =========================================================

    private JPanel createDetailLabel(
            String label,
            String value) {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                10,
                                0
                        )
                );

        panel.setBackground(
                WHITE
        );


        JLabel labelComponent =
                new JLabel(
                        label
                );

        labelComponent.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        labelComponent.setForeground(
                MUTED
        );


        JLabel valueComponent =
                new JLabel(
                        value == null
                                ? "Not provided"
                                : value
                );

        valueComponent.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        valueComponent.setForeground(
                TEXT
        );


        panel.add(
                labelComponent,
                BorderLayout.WEST
        );

        panel.add(
                valueComponent,
                BorderLayout.CENTER
        );


        return panel;
    }


    // =========================================================
    // TEXT AREA
    // =========================================================

    private JTextArea createTextArea(
            String text) {

        JTextArea area =
                new JTextArea(
                        text == null
                                ? "Not provided"
                                : text
                );

        area.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        area.setForeground(
                TEXT
        );

        area.setBackground(
                new Color(
                        249,
                        250,
                        251
                )
        );

        area.setLineWrap(
                true
        );

        area.setWrapStyleWord(
                true
        );

        area.setEditable(
                false
        );

        area.setRows(
                4
        );

        area.setBorder(
                new EmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );


        return area;
    }
}