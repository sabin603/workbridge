package dao;

import com.workbridge.config.DatabaseConnection;
import model.Application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO {

    // =========================================================
    // APPLY FOR JOB
    // =========================================================

    public boolean applyForJob(int jobId, int applicantId) {

        // First check whether this applicant already applied
        String checkSql = """
                SELECT application_id
                FROM applications
                WHERE job_id = ?
                AND applicant_id = ?
                """;

        // Insert new application
        String insertSql = """
                INSERT INTO applications
                (job_id, applicant_id, status)
                VALUES (?, ?, 'APPLIED')
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement checkStatement =
                        connection.prepareStatement(checkSql)
        ) {

            // =========================
            // CHECK EXISTING APPLICATION
            // =========================

            checkStatement.setInt(1, jobId);
            checkStatement.setInt(2, applicantId);

            ResultSet result =
                    checkStatement.executeQuery();

            if (result.next()) {

                System.out.println(
                        "Applicant already applied. "
                                + "Job ID: " + jobId
                                + ", Applicant ID: " + applicantId
                );

                return false;
            }

            // =========================
            // INSERT APPLICATION
            // =========================

            try (
                    PreparedStatement insertStatement =
                            connection.prepareStatement(insertSql)
            ) {

                insertStatement.setInt(1, jobId);
                insertStatement.setInt(2, applicantId);

                int rows =
                        insertStatement.executeUpdate();

                if (rows > 0) {

                    System.out.println(
                            "Application inserted successfully. "
                                    + "Job ID: " + jobId
                                    + ", Applicant ID: " + applicantId
                    );

                    return true;
                }

                return false;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Apply job SQL error:"
            );

            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // GET APPLICANTS FOR EMPLOYER
    // =========================================================

    public List<Application> getApplicantsByJob(
            int jobId,
            int employerId
    ) {

        List<Application> applications =
                new ArrayList<>();

        String sql = """
                SELECT
                    a.application_id,
                    a.job_id,
                    a.applicant_id,
                    a.applied_at,
                    a.status,

                    u.name AS applicant_name,
                    u.email AS applicant_email,

                    wp.phone AS applicant_phone,
                    wp.skills AS applicant_skills,
                    wp.cv_path AS applicant_cv

                FROM applications a

                JOIN jobs j
                    ON a.job_id = j.job_id

                JOIN users u
                    ON a.applicant_id = u.user_id

                LEFT JOIN worker_profiles wp
                    ON a.applicant_id = wp.user_id

                WHERE a.job_id = ?
                AND j.employer_id = ?

                ORDER BY a.applied_at DESC
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, jobId);
            statement.setInt(2, employerId);

            ResultSet result =
                    statement.executeQuery();

            while (result.next()) {

                Application application =
                        new Application();

                application.setApplicationId(
                        result.getInt("application_id")
                );

                application.setJobId(
                        result.getInt("job_id")
                );

                application.setApplicantId(
                        result.getInt("applicant_id")
                );

                application.setAppliedAt(
                        result.getString("applied_at")
                );

                application.setStatus(
                        result.getString("status")
                );

                application.setApplicantName(
                        result.getString("applicant_name")
                );

                application.setApplicantEmail(
                        result.getString("applicant_email")
                );

                application.setApplicantPhone(
                        result.getString("applicant_phone")
                );

                application.setApplicantSkills(
                        result.getString("applicant_skills")
                );

                application.setApplicantCv(
                        result.getString("applicant_cv")
                );

                applications.add(application);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Get applicants error:"
            );

            e.printStackTrace();
        }

        return applications;
    }


    // =========================================================
    // GET APPLICATIONS BY APPLICANT
    // =========================================================

    public List<Application> getApplicationsByApplicant(
            int applicantId
    ) {

        List<Application> applications =
                new ArrayList<>();

        String sql = """
                SELECT
                    a.application_id,
                    a.job_id,
                    a.applicant_id,
                    a.applied_at,
                    a.status,

                    j.title AS job_title,
                    j.category AS job_category,
                    j.location AS job_location,
                    j.job_type AS job_type

                FROM applications a

                JOIN jobs j
                    ON a.job_id = j.job_id

                WHERE a.applicant_id = ?

                ORDER BY a.applied_at DESC
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, applicantId);

            ResultSet result =
                    statement.executeQuery();

            while (result.next()) {

                Application application =
                        new Application();

                application.setApplicationId(
                        result.getInt("application_id")
                );

                application.setJobId(
                        result.getInt("job_id")
                );

                application.setApplicantId(
                        result.getInt("applicant_id")
                );

                application.setAppliedAt(
                        result.getString("applied_at")
                );

                application.setStatus(
                        result.getString("status")
                );

                application.setJobTitle(
                        result.getString("job_title")
                );

                application.setJobCategory(
                        result.getString("job_category")
                );

                application.setJobLocation(
                        result.getString("job_location")
                );

                application.setJobType(
                        result.getString("job_type")
                );

                applications.add(application);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Get applications error:"
            );

            e.printStackTrace();
        }

        return applications;
    }


    // =========================================================
    // UPDATE APPLICATION STATUS
    // =========================================================

    public boolean updateApplicationStatus(
            int applicationId,
            int employerId,
            String status
    ) {

        String sql = """
                UPDATE applications
                SET status = ?
                WHERE application_id = ?
                AND job_id IN (
                    SELECT job_id
                    FROM jobs
                    WHERE employer_id = ?
                )
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, status);
            statement.setInt(2, applicationId);
            statement.setInt(3, employerId);

            int rows =
                    statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Update application status error:"
            );

            e.printStackTrace();

            return false;
        }
    }
}