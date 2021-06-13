package com.gmail.vx.rnd_news_generator.services.blocker;

import com.gmail.vx.rnd_news_generator.model.CustomUser;
import com.gmail.vx.rnd_news_generator.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class AutoBlocker {
    @Autowired
    private UserService userService;

    @Scheduled(fixedDelay = 6000000)
    public void blockUsers() {
        List<CustomUser> customUsers = userService.getAllUsers();
        for (CustomUser customUser : customUsers) {
            int reports = userService.countReports(customUser.getLogin());
            if (reports >= 3) {
                userService.blockUser(customUser.getLogin());
            }
        }
    }
}

