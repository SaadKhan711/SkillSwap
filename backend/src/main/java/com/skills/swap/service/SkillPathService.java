//package com.skills.swap.service;
//
//import com.skills.swap.model.Skill;
//import com.skills.swap.repository.SkillRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class SkillPathService {
//
//    @Autowired
//    private SkillRepository skillRepository;
//
//    /**
//     * Generates a simple, ordered list of skills related to a goal.
//     * @param goal A keyword, e.g., "backend" or "creative".
//     * @return A sorted list of skills.
//     */
//    public List<Skill> generatePath(String goal) {
//        if (goal == null || goal.trim().isEmpty()) {
//            return List.of();
//        }
//
//        String keyword = goal.toLowerCase().trim();
//
//        // Find all skills that have the keyword in their name or tags.
//        List<Skill> relatedSkills = skillRepository.findAll().stream()
//                .filter(skill -> (skill.getName().toLowerCase().contains(keyword) ||
//                                  (skill.getTags() != null && skill.getTags().toLowerCase().contains(keyword))))
//                .collect(Collectors.toList());
//
//        // For now, we'll sort them alphabetically.
//        // In the future, we could add a "difficulty" or "order" field to sort them more intelligently.
//        relatedSkills.sort(Comparator.comparing(Skill::getName));
//
//        return relatedSkills;
//    }
//}
