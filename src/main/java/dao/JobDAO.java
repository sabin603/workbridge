package dao;

import com.workbridge.config.DatabaseConnection;
import model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDAO {

    // CREATE JOB
    public boolean createJob(Job job) {

        String sql = """
                INSERT INTO jobs
                (employer_id, title, category, description,
                 requirements, location, salary, job_type)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, job.getEmployerId());
            statement.setString(2, job.getTitle());
            statement.setString(3, job.getCategory());
            statement.setString(4, job.getDescription());
            statement.setString(5, job.getRequirements());
            statement.setString(6, job.getLocation());
            statement.setString(7, job.getSalary());
            statement.setString(8, job.getJobType());

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println(
                    "Create job error: " + e.getMessage()
            );

            return false;
        }
    }


    // GET EMPLOYER'S JOBS
    public List<Job> getJobsByEmployer(int employerId) {

        List<Job> jobs = new ArrayList<>();

        String sql = """
                SELECT *
                FROM jobs
                WHERE employer_id = ?
                ORDER BY created_at DESC
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

            while (result.next()) {

                Job job = new Job();

                job.setJobId(
                        result.getInt("job_id")
                );

                job.setEmployerId(
                        result.getInt("employer_id")
                );

                job.setTitle(
                        result.getString("title")
                );

                job.setCategory(
                        result.getString("category")
                );

                job.setDescription(
                        result.getString("description")
                );

                job.setRequirements(
                        result.getString("requirements")
                );

                job.setLocation(
                        result.getString("location")
                );

                job.setSalary(
                        result.getString("salary")
                );

                job.setJobType(
                        result.getString("job_type")
                );

                jobs.add(job);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Get jobs error: " + e.getMessage()
            );
        }

        return jobs;
    }


    // UPDATE JOB
    public boolean updateJob(Job job) {

        String sql = """
                UPDATE jobs
                SET title = ?,
                    category = ?,
                    description = ?,
                    requirements = ?,
                    location = ?,
                    salary = ?,
                    job_type = ?
                WHERE job_id = ?
                AND employer_id = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, job.getTitle());
            statement.setString(2, job.getCategory());
            statement.setString(3, job.getDescription());
            statement.setString(4, job.getRequirements());
            statement.setString(5, job.getLocation());
            statement.setString(6, job.getSalary());
            statement.setString(7, job.getJobType());

            statement.setInt(8, job.getJobId());
            statement.setInt(9, job.getEmployerId());

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println(
                    "Update job error: " + e.getMessage()
            );

            return false;
        }
    }


    // DELETE JOB
    public boolean deleteJob(
            int jobId,
            int employerId
    ) {

        String sql = """
                DELETE FROM jobs
                WHERE job_id = ?
                AND employer_id = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, jobId);
            statement.setInt(2, employerId);

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println(
                    "Delete job error: " + e.getMessage()
            );

            return false;
        }
    }
    // ADMIN DELETE JOB
    public boolean deleteJobByAdmin(int jobId) {

        String sql = """
            DELETE FROM jobs
            WHERE job_id = ?
            """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, jobId);

            int rowsAffected =
                    statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Admin delete job error: "
                            + e.getMessage()
            );

            return false;
        }
    }
    // GET ALL JOBS
    public List<Job> getAllJobs() {

        List<Job> jobs = new ArrayList<>();

        String sql = """
            SELECT *
            FROM jobs
            ORDER BY created_at DESC
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

                Job job = createJobFromResult(result);

                jobs.add(job);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Get all jobs error: "
                            + e.getMessage()
            );
        }

        return jobs;
    }


    // SEARCH AND FILTER JOBS
    public List<Job> searchJobs(
            String keyword,
            String category,
            String jobType
    ) {

        List<Job> jobs = new ArrayList<>();

        StringBuilder sql =
                new StringBuilder(
                        """
                        SELECT *
                        FROM jobs
                        WHERE 1=1
                        """
                );

        List<String> parameters =
                new ArrayList<>();


        // KEYWORD SEARCH

        if (
                keyword != null &&
                        !keyword.trim().isEmpty()
        ) {

            sql.append(
                    """
                    AND (
                        title LIKE ?
                        OR description LIKE ?
                        OR requirements LIKE ?
                    )
                    """
            );

            String search =
                    "%" + keyword.trim() + "%";

            parameters.add(search);
            parameters.add(search);
            parameters.add(search);
        }


        // CATEGORY FILTER

        if (
                category != null &&
                        !category.equals("All Categories")
        ) {

            sql.append(
                    " AND category = ? "
            );

            parameters.add(category);
        }


        // JOB TYPE FILTER

        if (
                jobType != null &&
                        !jobType.equals("All Types")
        ) {

            sql.append(
                    " AND job_type = ? "
            );

            parameters.add(jobType);
        }


        sql.append(
                " ORDER BY created_at DESC"
        );


        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                sql.toString()
                        )
        ) {

            for (
                    int i = 0;
                    i < parameters.size();
                    i++
            ) {

                statement.setString(
                        i + 1,
                        parameters.get(i)
                );
            }


            ResultSet result =
                    statement.executeQuery();


            while (result.next()) {

                Job job =
                        createJobFromResult(result);

                jobs.add(job);
            }


        } catch (SQLException e) {

            System.out.println(
                    "Search jobs error: "
                            + e.getMessage()
            );
        }


        return jobs;
    }


    // CREATE JOB OBJECT FROM RESULT
    private Job createJobFromResult(
            ResultSet result
    ) throws SQLException {

        Job job = new Job();

        job.setJobId(
                result.getInt("job_id")
        );

        job.setEmployerId(
                result.getInt("employer_id")
        );

        job.setTitle(
                result.getString("title")
        );

        job.setCategory(
                result.getString("category")
        );

        job.setDescription(
                result.getString("description")
        );

        job.setRequirements(
                result.getString("requirements")
        );

        job.setLocation(
                result.getString("location")
        );

        job.setSalary(
                result.getString("salary")
        );

        job.setJobType(
                result.getString("job_type")
        );

        return job;
    }
}