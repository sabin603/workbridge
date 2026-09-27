package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserManagementFrame extends JFrame {

    private UserDAO userDAO;

    private JTable userTable;

    private DefaultTableModel tableModel;


    public UserManagementFrame() {

        userDAO = new UserDAO();

        setTitle(
                "WorkBridge - User Management"
        );

        setSize(
                800,
                500
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        loadUsers();
    }


    // =========================
    // CREATE UI
    // =========================

    private void createUI() {

        setLayout(
                new BorderLayout()
        );


        // =========================
        // TOP PANEL
        // =========================

        JLabel titleLabel =
                new JLabel(
                        "User Management"
                );


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


        topPanel.add(
                titleLabel
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
                            int column
                    ) {

                        return false;
                    }
                };


        userTable =
                new JTable(
                        tableModel
                );


        userTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        userTable
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


        JButton deleteButton =
                new JButton(
                        "Delete User"
                );


        JButton refreshButton =
                new JButton(
                        "Refresh"
                );


        JButton closeButton =
                new JButton(
                        "Close"
                );


        bottomPanel.add(
                deleteButton
        );


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

        deleteButton.addActionListener(
                e -> deleteSelectedUser()
        );


        refreshButton.addActionListener(
                e -> loadUsers()
        );


        closeButton.addActionListener(
                e -> dispose()
        );
    }


    // =========================
    // LOAD USERS
    // =========================

    private void loadUsers() {

        tableModel.setRowCount(0);


        List<User> users =
                userDAO.getAllUsers();


        for (User user : users) {

            tableModel.addRow(
                    new Object[]{

                            user.getUserId(),

                            user.getName(),

                            user.getEmail(),

                            user.getRole()
                    }
            );
        }
    }


    // =========================
    // DELETE USER
    // =========================

    private void deleteSelectedUser() {

        int selectedRow =
                userTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a user first."
            );

            return;
        }


        int userId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );


        String name =
                tableModel.getValueAt(
                        selectedRow,
                        1
                ).toString();


        String role =
                tableModel.getValueAt(
                        selectedRow,
                        3
                ).toString();


        // =========================
        // PREVENT ADMIN DELETE
        // =========================

        if (role.equals("ADMIN")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Admin users cannot be deleted.",
                    "Action Not Allowed",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // =========================
        // CONFIRM DELETE
        // =========================

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete user:\n"
                                + name
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
                userDAO.deleteUser(
                        userId
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "User deleted successfully!"
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
}