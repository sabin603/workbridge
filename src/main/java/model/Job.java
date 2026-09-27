package model;

public class Job {

    private int jobId;
    private int employerId;
    private String title;
    private String category;
    private String description;
    private String requirements;
    private String location;
    private String salary;
    private String jobType;

    public Job() {
    }

    public Job(
            int employerId,
            String title,
            String category,
            String description,
            String requirements,
            String location,
            String salary,
            String jobType
    ) {
        this.employerId = employerId;
        this.title = title;
        this.category = category;
        this.description = description;
        this.requirements = requirements;
        this.location = location;
        this.salary = salary;
        this.jobType = jobType;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getEmployerId() {
        return employerId;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }
}