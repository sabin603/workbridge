package ai;

import dao.JobDAO;
import dao.ProfileDAO;
import model.Job;
import model.User;
import model.WorkerProfile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class JobMatchingEngine {

    // =========================================================
    // GET JOB RECOMMENDATIONS
    // =========================================================

    public List<JobMatchResult> getRecommendations(User jobSeeker) {

        List<JobMatchResult> results = new ArrayList<>();

        if (jobSeeker == null) {
            return results;
        }

        try {

            // -------------------------------------------------
            // Get worker profile
            // -------------------------------------------------

            ProfileDAO profileDAO = new ProfileDAO();

            WorkerProfile profile =
                    profileDAO.getProfile(
                            jobSeeker.getUserId()
                    );

            if (profile == null) {
                return results;
            }

            String seekerSkills =
                    safe(profile.getSkills());


            // -------------------------------------------------
            // Get all available jobs
            // -------------------------------------------------

            JobDAO jobDAO = new JobDAO();

            List<Job> jobs =
                    jobDAO.getAllJobs();

            if (jobs == null || jobs.isEmpty()) {
                return results;
            }


            // -------------------------------------------------
            // Calculate match for every job
            // -------------------------------------------------

            for (Job job : jobs) {

                if (job == null) {
                    continue;
                }

                String requirements =
                        safe(job.getRequirements());

                String title =
                        safe(job.getTitle());

                String category =
                        safe(job.getCategory());


                // Main skill matching
                double skillScore =
                        calculateMatchPercentage(
                                seekerSkills,
                                requirements
                        );


                // Small relevance bonus
                double relevanceBonus =
                        calculateRelevanceBonus(
                                seekerSkills,
                                title,
                                category
                        );


                double finalScore =
                        skillScore + relevanceBonus;


                finalScore =
                        Math.min(
                                finalScore,
                                100.0
                        );


                finalScore =
                        Math.round(
                                finalScore * 100.0
                        ) / 100.0;


                // -------------------------------------------------
                // Matched and missing skills
                // -------------------------------------------------

                Set<String> matchedSkills =
                        getMatchedSkills(
                                seekerSkills,
                                requirements
                        );

                Set<String> missingSkills =
                        getMissingSkills(
                                seekerSkills,
                                requirements
                        );


                // -------------------------------------------------
                // Create result
                // -------------------------------------------------

                JobMatchResult result =
                        new JobMatchResult(
                                job,
                                finalScore,
                                matchedSkills,
                                missingSkills
                        );

                results.add(result);
            }


            // -------------------------------------------------
            // Highest match first
            // -------------------------------------------------

            results.sort(
                    Comparator.comparingDouble(
                            JobMatchResult::getMatchPercentage
                    ).reversed()
            );

        } catch (Exception e) {

            System.out.println(
                    "Job recommendation error: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }

        return results;
    }


    // =========================================================
    // CALCULATE MATCH PERCENTAGE
    // =========================================================

    public static double calculateMatchPercentage(
            String seekerSkills,
            String jobRequirements
    ) {

        Set<String> seekerSkillSet =
                SkillExtractor.extractSkills(
                        safe(seekerSkills)
                );

        Set<String> requiredSkillSet =
                SkillExtractor.extractSkills(
                        safe(jobRequirements)
                );


        // No requirements
        if (requiredSkillSet.isEmpty()) {
            return 0.0;
        }


        // -----------------------------------------------------
        // Exact matches
        // -----------------------------------------------------

        Set<String> matchedSkills =
                new HashSet<>(
                        seekerSkillSet
                );

        matchedSkills.retainAll(
                requiredSkillSet
        );


        double exactMatchScore =
                (
                        (double) matchedSkills.size()
                                / requiredSkillSet.size()
                ) * 100.0;


        // -----------------------------------------------------
        // Partial matches
        // -----------------------------------------------------

        int partialMatches = 0;

        for (String required :
                requiredSkillSet) {

            // Already matched exactly
            if (matchedSkills.contains(required)) {
                continue;
            }


            for (String seeker :
                    seekerSkillSet) {

                if (isPartialMatch(
                        seeker,
                        required
                )) {

                    partialMatches++;
                    break;
                }
            }
        }


        double partialScore =
                (
                        (double) partialMatches
                                / requiredSkillSet.size()
                ) * 20.0;


        // -----------------------------------------------------
        // Final score
        //
        // Exact matches = 80%
        // Partial matches = 20%
        // -----------------------------------------------------

        double finalScore =
                (exactMatchScore * 0.80)
                        + partialScore;


        finalScore =
                Math.min(
                        finalScore,
                        100.0
                );


        return Math.round(
                finalScore * 100.0
        ) / 100.0;
    }


    // =========================================================
    // GET MATCHED SKILLS
    // =========================================================

    public static Set<String> getMatchedSkills(
            String seekerSkills,
            String jobRequirements
    ) {

        Set<String> seekerSkillSet =
                SkillExtractor.extractSkills(
                        safe(seekerSkills)
                );

        Set<String> requiredSkillSet =
                SkillExtractor.extractSkills(
                        safe(jobRequirements)
                );


        Set<String> matchedSkills =
                new HashSet<>(
                        seekerSkillSet
                );

        matchedSkills.retainAll(
                requiredSkillSet
        );


        return matchedSkills;
    }


    // =========================================================
    // GET MISSING SKILLS
    // =========================================================

    public static Set<String> getMissingSkills(
            String seekerSkills,
            String jobRequirements
    ) {

        Set<String> seekerSkillSet =
                SkillExtractor.extractSkills(
                        safe(seekerSkills)
                );

        Set<String> requiredSkillSet =
                SkillExtractor.extractSkills(
                        safe(jobRequirements)
                );


        Set<String> missingSkills =
                new HashSet<>(
                        requiredSkillSet
                );

        missingSkills.removeAll(
                seekerSkillSet
        );


        return missingSkills;
    }


    // =========================================================
    // PARTIAL MATCH
    // =========================================================

    private static boolean isPartialMatch(
            String seekerSkill,
            String requiredSkill
    ) {

        if (seekerSkill == null
                || requiredSkill == null) {

            return false;
        }


        String seeker =
                seekerSkill
                        .trim()
                        .toLowerCase(
                                Locale.ROOT
                        );


        String required =
                requiredSkill
                        .trim()
                        .toLowerCase(
                                Locale.ROOT
                        );


        if (seeker.isEmpty()
                || required.isEmpty()) {

            return false;
        }


        if (seeker.length() < 3
                || required.length() < 3) {

            return false;
        }


        return seeker.contains(required)
                || required.contains(seeker);
    }


    // =========================================================
    // RELEVANCE BONUS
    // =========================================================

    private static double calculateRelevanceBonus(
            String seekerSkills,
            String jobTitle,
            String jobCategory
    ) {

        Set<String> seekerSkillSet =
                SkillExtractor.extractSkills(
                        safe(seekerSkills)
                );


        if (seekerSkillSet.isEmpty()) {
            return 0.0;
        }


        String title =
                safe(jobTitle)
                        .toLowerCase(
                                Locale.ROOT
                        );


        String category =
                safe(jobCategory)
                        .toLowerCase(
                                Locale.ROOT
                        );


        double bonus = 0.0;


        for (String skill :
                seekerSkillSet) {

            if (skill == null
                    || skill.trim().isEmpty()) {

                continue;
            }


            String normalizedSkill =
                    skill.trim()
                            .toLowerCase(
                                    Locale.ROOT
                            );


            // Skill appears in job title
            if (title.contains(
                    normalizedSkill
            )) {

                bonus += 5.0;
            }


            // Skill appears in job category
            if (category.contains(
                    normalizedSkill
            )) {

                bonus += 3.0;
            }
        }


        // Maximum relevance bonus
        return Math.min(
                bonus,
                10.0
        );
    }


    // =========================================================
    // SAFE STRING
    // =========================================================

    private static String safe(
            String value
    ) {

        if (value == null) {
            return "";
        }

        return value.trim();
    }
}
