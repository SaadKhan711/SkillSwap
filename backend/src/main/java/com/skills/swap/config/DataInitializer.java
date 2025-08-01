package com.skills.swap.config;

import com.skills.swap.service.BadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired 
    private BadgeService badgeService;

    @Override
    public void run(String... args) throws Exception {
        // Initialize the master list of badges on startup
        badgeService.initializeBadges();
    }
}