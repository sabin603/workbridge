package dao;

import com.workbridge.config.DatabaseConnection;
import model.User;
import util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // =========================
    // REGISTER USER
    // =========================

    public boolean registerUser(User user) {

        String sql = """
                INSERT INTO users
                (name, email, password_hash, role)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    user.getName()
            );

            statement.setString(
                    2,
                    user.getEmail()
            );

            statement.setString(
                    3,
                    PasswordUtil.hashPassword(
                            user.getPasswordHash()
                    )
            );

            statement.setString(
                    4,
                    user.getRole()
            );

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println(
                    "Registration error: "
                            + e.getMessage()
            );

            return false;
        }
    }


    // =========================
    // LOGIN USER
    // =========================

    public User loginUser(
            String email,
            String password
    ) {

        String sql = """
                SELECT *
                FROM users
                WHERE email = ?
                AND password_hash = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    email
            );

            statement.setString(
                    2,
                    PasswordUtil.hashPassword(
                            password
                    )
            );

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                User user = new User();

                user.setUserId(
                        result.getInt("user_id")
                );

                user.setName(
                        result.getString("name")
                );

                user.setEmail(
                        result.getString("email")
                );

                user.setRole(
                        result.getString("role")
                );

                return user;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Login error: "
                            + e.getMessage()
            );
        }

        return null;
    }


    // =========================
    // GET ALL USERS
    // =========================

    public List<User> getAllUsers() {

        List<User> users =
                new ArrayList<>();

        String sql = """
                SELECT
                    user_id,
                    name,
                    email,
                    role,
                    created_at
                FROM users
                ORDER BY user_id DESC
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet result =
                        statement.executeQuery()
        ) {

            while (result.next()) {

                User user =
                        new User();

                user.setUserId(
                        result.getInt("user_id")
                );

                user.setName(
                        result.getString("name")
                );

                user.setEmail(
                        result.getString("email")
                );

                user.setRole(
                        result.getString("role")
                );

                users.add(user);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Get all users error: "
                            + e.getMessage()
            );
        }

        return users;
    }


    // =========================
    // DELETE USER
    // =========================

    public boolean deleteUser(
            int userId
    ) {

        String sql = """
                DELETE FROM users
                WHERE user_id = ?
                AND role != 'ADMIN'
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    userId
            );

            int rowsAffected =
                    statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Delete user error: "
                            + e.getMessage()
            );

            return false;
        }
    }
}