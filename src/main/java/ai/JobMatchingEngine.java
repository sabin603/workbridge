package ai;

import java.util.HashSet;
import java.util.Set;

public class JobMatchingEngine {

    /**
     * Calculates how well a job seeker matches
     * the requirements of a job.
     *
     * Formula:
     *
     * Match % =
     * (Matched Skills / Required Skills) * 100
     */
    public static double calculateMatchPercentage(
            String seekerSkills,
            String jobRequirements) {

        Set<String> seekerSkillSet =
                SkillExtractor.extractSkills(seekerSkills);

        Set<String> requiredSkillSet =
                SkillExtractor.extractSkills(jobRequirements);

        // No recognizable requirements
        if (requiredSkillSet.isEmpty()) {
            return 0.0;
        }

        // Find common skills
        Set<String> matchedSkills =
                new HashSet<>(seekerSkillSet);

        matchedSkills.retainAll(requiredSkillSet);

        double percentage =
                ((double) matchedSkills.size()
                        / requiredSkillSet.size()) * 100;

        return Math.round(percentage * 100.0) / 100.0;
    }


    /**
     * Returns the skills that match between
     * the seeker and the job.
     */
    public static Set<String> getMatchedSkills(
            String seekerSkills,
            String jobRequirements) {

        Set<String> seekerSkillSet =
                SkillExtractor.extractSkills(seekerSkills);

        Set<String> requiredSkillSet =
                SkillExtractor.extractSkills(jobRequirements);

        Set<String> matchedSkills =
                new HashSet<>(seekerSkillSet);

        matchedSkills.retainAll(requiredSkillSet);

        return matchedSkills;
    }


    /**
     * Returns required skills that the
     * job seeker does not have.
     */
    public static Set<String> getMissingSkills(
            String seekerSkills,
            String jobRequirements) {

        Set<String> seekerSkillSet =
                SkillExtractor.extractSkills(seekerSkills);

        Set<String> requiredSkillSet =
                SkillExtractor.extractSkills(jobRequirements);

        Set<String> missingSkills =
                new HashSet<>(requiredSkillSet);

        missingSkills.removeAll(seekerSkillSet);

        return missingSkills;
    }
}