package model;

public class WorkerProfile {

    private int profileId;
    private int userId;

    private String address;
    private String phone;
    private String profilePicture;

    private String skills;
    private String headline;
    private String education;
    private String experience;
    private String cvPath;


    // =========================
    // EMPTY CONSTRUCTOR
    // =========================

    public WorkerProfile() {
    }


    // =========================
    // CONSTRUCTOR
    // =========================

    public WorkerProfile(
            int userId,
            String address,
            String phone,
            String profilePicture,
            String skills
    ) {

        this.userId = userId;
        this.address = address;
        this.phone = phone;
        this.profilePicture = profilePicture;
        this.skills = skills;
    }


    // =========================
    // PROFILE ID
    // =========================

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }


    // =========================
    // USER ID
    // =========================

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    // =========================
    // ADDRESS
    // =========================

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    // =========================
    // PHONE
    // =========================

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    // =========================
    // PROFILE PICTURE
    // =========================

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(
            String profilePicture
    ) {
        this.profilePicture = profilePicture;
    }


    // =========================
    // SKILLS
    // =========================

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }


    // =========================
    // HEADLINE
    // =========================

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }


    // =========================
    // EDUCATION
    // =========================

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }


    // =========================
    // EXPERIENCE
    // =========================

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }


    // =========================
    // CV
    // =========================

    public String getCvPath() {
        return cvPath;
    }

    public void setCvPath(String cvPath) {
        this.cvPath = cvPath;
    }
}
