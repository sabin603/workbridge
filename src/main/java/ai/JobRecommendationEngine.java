package ai;

import model.Job;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JobRecommendationEngine {

    /**
     * Generates job recommendations based on
     * the job seeker's skills.
     */
    public static List<JobMatchResult> recommendJobs(
            String seekerSkills,
            List<Job> availableJobs) {

        List<JobMatchResult> recommendations =
                new ArrayList<>();

        if (seekerSkills == null ||
                seekerSkills.trim().isEmpty() ||
                availableJobs == null) {

            return recommendations;
        }

        // Compare the seeker's skills with every job
        for (Job job : availableJobs) {

            double matchPercentage =
                    JobMatchingEngine.calculateMatchPercentage(
                            seekerSkills,
                            job.getRequirements()
                    );

            var matchedSkills =
                    JobMatchingEngine.getMatchedSkills(
                            seekerSkills,
                            job.getRequirements()
                    );

            var missingSkills =
                    JobMatchingEngine.getMissingSkills(
                            seekerSkills,
                            job.getRequirements()
                    );

            JobMatchResult result =
                    new JobMatchResult(
                            job,
                            matchPercentage,
                            matchedSkills,
                            missingSkills
                    );

            recommendations.add(result);
        }

        // Sort from highest match to lowest match
        recommendations.sort(
                Comparator.comparingDouble(
                        JobMatchResult::getMatchPercentage
                ).reversed()
        );

        return recommendations;
    }
}