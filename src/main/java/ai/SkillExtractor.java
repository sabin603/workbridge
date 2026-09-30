package ai;

import java.util.*;

public class SkillExtractor {

    // Skills recognized by the WorkBridge matching system
    private static final Set<String> KNOWN_SKILLS = new HashSet<>(
            Arrays.asList(
                    "java",
                    "python",
                    "c++",
                    "c#",
                    "html",
                    "css",
                    "javascript",
                    "react",
                    "php",
                    "mysql",
                    "mongodb",
                    "sql",
                    "git",
                    "github",
                    "spring boot",
                    "spring",
                    "rest api",
                    "figma",
                    "ui/ux",
                    "ui ux",
                    "photoshop",
                    "illustrator",
                    "excel",
                    "word",
                    "powerpoint",
                    "data entry",
                    "communication",
                    "marketing",
                    "seo",
                    "content writing"
            )
    );

    public static Set<String> extractSkills(String text) {

        Set<String> extractedSkills =
                new HashSet<>();

        if (text == null || text.trim().isEmpty()) {
            return extractedSkills;
        }

        String normalizedText =
                text.toLowerCase();

        for (String skill : KNOWN_SKILLS) {

            if (normalizedText.contains(skill.toLowerCase())) {
                extractedSkills.add(skill);
            }
        }

        return extractedSkills;
    }
}