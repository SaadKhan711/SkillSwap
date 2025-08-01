//package com.skills.swap.controller;
//
//import com.skills.swap.model.Skill;
//import com.skills.swap.service.SkillPathService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/skill-path")
//public class SkillPathController {
//
//    @Autowired
//    private SkillPathService skillPathService;
//
//    @GetMapping
//    public List<Skill> getSkillPath(@RequestParam String goal) {
//        return skillPathService.generatePath(goal);
//    }
//}
