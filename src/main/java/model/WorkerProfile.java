package model;

public class WorkerProfile {

    private int profileId;
    private int userId;
    private String address;
    private String phone;
    private String profilePicture;
    private String skills;

    public WorkerProfile() {
    }

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

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}