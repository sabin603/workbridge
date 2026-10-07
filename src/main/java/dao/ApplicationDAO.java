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

        String checkSql = """
                SELECT application_id
                FROM applications
                WHERE job_id = ?
                AND applicant_id = ?
                """;

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

            checkStatement.setInt(1, jobId);
            checkStatement.setInt(2, applicantId);

            ResultSet checkResult =
                    checkStatement.executeQuery();

            if (checkResult.next()) {

                System.out.println(
                        "Applicant already applied for this job."
                );

                return false;
            }

            try (
                    PreparedStatement insertStatement =
                            connection.prepareStatement(insertSql)
            ) {

                insertStatement.setInt(1, jobId);
                insertStatement.setInt(2, applicantId);

                insertStatement.executeUpdate();

                return true;
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

                    a.interview_date,
                    a.interview_time,
                    a.interview_notes,

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


                // =============================================
                // INTERVIEW INFORMATION
                // =============================================

                application.setInterviewDate(
                        result.getString("interview_date")
                );

                application.setInterviewTime(
                        result.getString("interview_time")
                );

                application.setInterviewNotes(
                        result.getString("interview_notes")
                );


                // =============================================
                // APPLICANT INFORMATION
                // =============================================

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
    // GET APPLICATIONS FOR JOB SEEKER
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

                    a.interview_date,
                    a.interview_time,
                    a.interview_notes,

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


                // =============================================
                // INTERVIEW INFORMATION
                // =============================================

                application.setInterviewDate(
                        result.getString("interview_date")
                );

                application.setInterviewTime(
                        result.getString("interview_time")
                );

                application.setInterviewNotes(
                        result.getString("interview_notes")
                );


                // =============================================
                // JOB INFORMATION
                // =============================================

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


    // =========================================================
    // SCHEDULE INTERVIEW
    // =========================================================

    public boolean scheduleInterview(
            int applicationId,
            int employerId,
            String interviewDate,
            String interviewTime,
            String interviewNotes
    ) {

        String sql = """
                UPDATE applications
                SET
                    status = 'INTERVIEW',
                    interview_date = ?,
                    interview_time = ?,
                    interview_notes = ?
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

            statement.setString(1, interviewDate);
            statement.setString(2, interviewTime);
            statement.setString(3, interviewNotes);
            statement.setInt(4, applicationId);
            statement.setInt(5, employerId);

            int rows =
                    statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Schedule interview error:"
            );

            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // TOTAL APPLICANTS
    // =========================================================

    public int getTotalApplicantsByEmployer(
            int employerId
    ) {

        String sql = """
                SELECT COUNT(*)
                FROM applications a
                JOIN jobs j
                    ON a.job_id = j.job_id
                WHERE j.employer_id = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, employerId);

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                return result.getInt(1);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return 0;
    }


    // =========================================================
    // SELECTED APPLICANTS
    // =========================================================

    public int getSelectedApplicantsByEmployer(
            int employerId
    ) {

        String sql = """
                SELECT COUNT(*)
                FROM applications a
                JOIN jobs j
                    ON a.job_id = j.job_id
                WHERE j.employer_id = ?
                AND a.status = 'SELECTED'
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, employerId);

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                return result.getInt(1);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return 0;
    }
}