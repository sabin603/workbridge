package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class UserManagementFrame extends JFrame {

    private final UserDAO userDAO;

    private JTable userTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    private JTextField searchField;
    private JComboBox<String> roleComboBox;
    private JLabel resultLabel;

    // =========================
    // COLORS
    // =========================

    private final Color PRIMARY =
            new Color(37, 99, 235);

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

    private final Color DANGER =
            new Color(220, 38, 38);


    public UserManagementFrame() {

        userDAO = new UserDAO();

        setTitle(
                "WorkBridge - User Management"
        );

        setSize(
                1050,
                650
        );

        setMinimumSize(
                new Dimension(
                        850,
                        550
                )
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        loadUsers();
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

        setContentPane(
                rootPanel
        );


        // =========================
        // HEADER
        // =========================

        rootPanel.add(
                createHeader(),
                BorderLayout.NORTH
        );


        // =========================
        // MAIN
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
        // FILTERS
        // =========================

        mainPanel.add(
                createFilterPanel(),
                BorderLayout.NORTH
        );


        // =========================
        // TABLE
        // =========================

        mainPanel.add(
                createTablePanel(),
                BorderLayout.CENTER
        );


        // =========================
        // BOTTOM
        // =========================

        mainPanel.add(
                createBottomPanel(),
                BorderLayout.SOUTH
        );


        rootPanel.add(
                mainPanel,
                BorderLayout.CENTER
        );


        // =========================
        // FILTER EVENTS
        // =========================

        searchField.getDocument()
                .addDocumentListener(
                        new javax.swing.event.DocumentListener() {

                            @Override
                            public void insertUpdate(
                                    javax.swing.event.DocumentEvent e) {
                                applyFilters();
                            }

                            @Override
                            public void removeUpdate(
                                    javax.swing.event.DocumentEvent e) {
                                applyFilters();
                            }

                            @Override
                            public void changedUpdate(
                                    javax.swing.event.DocumentEvent e) {
                                applyFilters();
                            }
                        }
                );


        roleComboBox.addActionListener(
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
                        "User Management"
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
                        "Manage registered job seekers, "
                                + "employers and administrators"
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
                                10,
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
                        320,
                        38
                )
        );

        searchField.setToolTipText(
                "Search by name or email"
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
        // ROLE
        // =========================

        JPanel rolePanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                0
                        )
                );

        rolePanel.setBackground(
                WHITE
        );


        JLabel roleLabel =
                new JLabel(
                        "Role"
                );

        roleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        roleLabel.setForeground(
                TEXT
        );


        roleComboBox =
                new JComboBox<>(
                        new String[]{
                                "All Roles",
                                "JOB_SEEKER",
                                "EMPLOYER",
                                "ADMIN"
                        }
                );

        roleComboBox.setPreferredSize(
                new Dimension(
                        170,
                        38
                )
        );


        rolePanel.add(
                roleLabel,
                BorderLayout.WEST
        );

        rolePanel.add(
                roleComboBox,
                BorderLayout.CENTER
        );


        panel.add(
                searchPanel,
                BorderLayout.CENTER
        );

        panel.add(
                rolePanel,
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
        // COLUMNS
        // =========================

        String[] columns = {
                "Name",
                "Email",
                "Role"
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


        userTable =
                new JTable(
                        tableModel
                );


        userTable.setSelectionMode(
                ListSelectionModel
                        .SINGLE_SELECTION
        );

        userTable.setRowHeight(
                48
        );

        userTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        userTable.setForeground(
                TEXT
        );

        userTable.setBackground(
                WHITE
        );

        userTable.setGridColor(
                BORDER
        );

        userTable.setShowVerticalLines(
                false
        );

        userTable.setIntercellSpacing(
                new Dimension(
                        0,
                        1
                )
        );


        // =========================
        // HEADER
        // =========================

        userTable.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                13
                        )
                );

        userTable.getTableHeader()
                .setForeground(
                        TEXT
                );

        userTable.getTableHeader()
                .setBackground(
                        new Color(
                                249,
                                250,
                                251
                        )
                );

        userTable.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                42
                        )
                );


        // =========================
        // COLUMN WIDTHS
        // =========================

        userTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(
                        260
                );

        userTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(
                        380
                );

        userTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(
                        160
                );


        // =========================
        // ROLE RENDERER
        // =========================

        userTable.getColumnModel()
                .getColumn(2)
                .setCellRenderer(
                        new RoleCellRenderer()
                );


        // =========================
        // SORTING
        // =========================

        sorter =
                new TableRowSorter<>(
                        tableModel
                );

        userTable.setRowSorter(
                sorter
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        userTable
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
                        "Users"
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


        JButton deleteButton =
                createButton(
                        "Delete User",
                        DANGER
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
                deleteButton
        );

        buttonPanel.add(
                refreshButton
        );

        buttonPanel.add(
                closeButton
        );


        deleteButton.addActionListener(
                e -> deleteSelectedUser()
        );

        refreshButton.addActionListener(
                e -> loadUsers()
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
    // BUTTON
    // =========================================================

    private JButton createButton(
            String text,
            Color color) {

        JButton button =
                new JButton(
                        text
                );

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
    // LOAD USERS
    // =========================================================

    private void loadUsers() {

        tableModel.setRowCount(
                0
        );


        List<User> users =
                userDAO.getAllUsers();


        for (User user : users) {

            tableModel.addRow(
                    new Object[]{

                            user.getName(),

                            user.getEmail(),

                            user.getRole()
                    }
            );
        }


        updateResultLabel();
    }


    // =========================================================
    // FILTER
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


        String selectedRole =
                roleComboBox
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

                        String name =
                                entry.getStringValue(
                                        0
                                ).toLowerCase();

                        String email =
                                entry.getStringValue(
                                        1
                                ).toLowerCase();

                        String role =
                                entry.getStringValue(
                                        2
                                );


                        // Search

                        if (!searchText.isEmpty()) {

                            boolean matches =
                                    name.contains(
                                            searchText
                                    )
                                            || email.contains(
                                            searchText
                                    );

                            if (!matches) {
                                return false;
                            }
                        }


                        // Role

                        if (!selectedRole.equals(
                                "All Roles"
                        )) {

                            if (!role.equalsIgnoreCase(
                                    selectedRole
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
    // RESULT COUNT
    // =========================================================

    private void updateResultLabel() {

        int visibleRows =
                userTable.getRowCount();

        int totalRows =
                tableModel.getRowCount();


        resultLabel.setText(
                visibleRows
                        + " user"
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
    // DELETE USER
    // =========================================================

    private void deleteSelectedUser() {

        int selectedRow =
                userTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a user first.",
                    "No User Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // Convert table view row to model row

        int modelRow =
                userTable.convertRowIndexToModel(
                        selectedRow
                );


        String name =
                tableModel.getValueAt(
                        modelRow,
                        0
                ).toString();


        String email =
                tableModel.getValueAt(
                        modelRow,
                        1
                ).toString();


        String role =
                tableModel.getValueAt(
                        modelRow,
                        2
                ).toString();


        // =========================
        // PROTECT ADMIN
        // =========================

        if (role.equalsIgnoreCase(
                "ADMIN"
        )) {

            JOptionPane.showMessageDialog(
                    this,

                    "Administrator accounts cannot "
                            + "be deleted.",

                    "Action Not Allowed",

                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // =========================
        // FIND INTERNAL USER ID
        // =========================

        int userId =
                findUserId(
                        name,
                        email,
                        role
                );


        if (userId == -1) {

            JOptionPane.showMessageDialog(
                    this,

                    "Unable to identify the selected user.",

                    "Error",

                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        // =========================
        // CONFIRM
        // =========================

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,

                        "Are you sure you want to delete:\n\n"
                                + name
                                + "\n"
                                + email
                                + "?",

                        "Confirm User Deletion",

                        JOptionPane.YES_NO_OPTION,

                        JOptionPane.WARNING_MESSAGE
                );


        if (confirm !=
                JOptionPane.YES_OPTION) {

            return;
        }


        boolean success =
                userDAO.deleteUser(
                        userId
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,

                    "User deleted successfully!",

                    "Success",

                    JOptionPane.INFORMATION_MESSAGE
            );


            loadUsers();

        } else {

            JOptionPane.showMessageDialog(
                    this,

                    "Failed to delete user.",

                    "Error",

                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // FIND USER ID
    // =========================================================

    private int findUserId(
            String name,
            String email,
            String role) {

        List<User> users =
                userDAO.getAllUsers();


        for (User user : users) {

            if (user.getName() != null
                    && user.getEmail() != null
                    && user.getRole() != null
                    && user.getName().equals(name)
                    && user.getEmail().equals(email)
                    && user.getRole().equals(role)) {

                return user.getUserId();
            }
        }


        return -1;
    }


    // =========================================================
    // ROLE RENDERER
    // =========================================================

    private class RoleCellRenderer
            extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {

            JLabel label =
                    (JLabel) super
                            .getTableCellRendererComponent(
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


            if (!isSelected) {

                String role =
                        value == null
                                ? ""
                                : value.toString();


                if (role.equalsIgnoreCase(
                        "ADMIN"
                )) {

                    label.setForeground(
                            new Color(
                                    124,
                                    58,
                                    237
                            )
                    );

                } else if (
                        role.equalsIgnoreCase(
                                "EMPLOYER"
                        )
                ) {

                    label.setForeground(
                            new Color(
                                    5,
                                    150,
                                    105
                            )
                    );

                } else {

                    label.setForeground(
                            PRIMARY
                    );
                }
            }


            return label;
        }
    }
}