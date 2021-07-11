package com.gmail.vx.rnd_news_generator.controllers;


import com.gmail.vx.rnd_news_generator.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

@Controller
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // !!!
    public String admin(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @RequestMapping("/block/{login}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> blockUser(@PathVariable(value = "login") String login) {
        userService.blockUser(login);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/unblock/{login}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> unblockUser(@PathVariable(value = "login") String login) {
        userService.unblockUser(login);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/setModer/{login}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> setModerator(@PathVariable(value = "login") String login) {
        userService.setModerator(login);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteUsers(@RequestParam(name = "toDelete[]", required = false) List<Long> ids,
                              Model model) {
        userService.deleteUsers(ids);
        model.addAttribute("users", userService.getAllUsers());

        return "admin";
    }

}
