package dao;

import com.workbridge.config.DatabaseConnection;
import model.WorkerProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileDAO {

    // GET PROFILE
    public WorkerProfile getProfile(int userId) {

        String sql = """
                SELECT *
                FROM worker_profiles
                WHERE user_id = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                WorkerProfile profile =
                        new WorkerProfile();

                profile.setProfileId(
                        result.getInt("profile_id")
                );

                profile.setUserId(
                        result.getInt("user_id")
                );

                profile.setAddress(
                        result.getString("address")
                );

                profile.setPhone(
                        result.getString("phone")
                );

                profile.setProfilePicture(
                        result.getString("profile_picture")
                );

                profile.setSkills(
                        result.getString("skills")
                );

                return profile;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Get profile error: "
                            + e.getMessage()
            );
        }

        return null;
    }


    // CREATE PROFILE
    public boolean createProfile(
            WorkerProfile profile
    ) {

        String sql = """
                INSERT INTO worker_profiles
                (user_id, address, phone,
                 profile_picture, skills)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    profile.getUserId()
            );

            statement.setString(
                    2,
                    profile.getAddress()
            );

            statement.setString(
                    3,
                    profile.getPhone()
            );

            statement.setString(
                    4,
                    profile.getProfilePicture()
            );

            statement.setString(
                    5,
                    profile.getSkills()
            );

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println(
                    "Create profile error: "
                            + e.getMessage()
            );

            return false;
        }
    }


    // UPDATE PROFILE
    public boolean updateProfile(
            WorkerProfile profile
    ) {

        String sql = """
                UPDATE worker_profiles
                SET address = ?,
                    phone = ?,
                    profile_picture = ?,
                    skills = ?
                WHERE user_id = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    profile.getAddress()
            );

            statement.setString(
                    2,
                    profile.getPhone()
            );

            statement.setString(
                    3,
                    profile.getProfilePicture()
            );

            statement.setString(
                    4,
                    profile.getSkills()
            );

            statement.setInt(
                    5,
                    profile.getUserId()
            );

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println(
                    "Update profile error: "
                            + e.getMessage()
            );

            return false;
        }
    }
}