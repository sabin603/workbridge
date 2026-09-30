package ai;

import model.Job;

import java.util.Set;

public class JobMatchResult {

    private Job job;
    private double matchPercentage;
    private Set<String> matchedSkills;
    private Set<String> missingSkills;

    public JobMatchResult(
            Job job,
            double matchPercentage,
            Set<String> matchedSkills,
            Set<String> missingSkills) {

        this.job = job;
        this.matchPercentage = matchPercentage;
        this.matchedSkills = matchedSkills;
        this.missingSkills = missingSkills;
    }

    public Job getJob() {
        return job;
    }

    public double getMatchPercentage() {
        return matchPercentage;
    }

    public Set<String> getMatchedSkills() {
        return matchedSkills;
    }

    public Set<String> getMissingSkills() {
        return missingSkills;
    }
}