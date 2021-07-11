package com.gmail.vx.rnd_news_generator.controllers;


import com.gmail.vx.rnd_news_generator.model.Comment;
import com.gmail.vx.rnd_news_generator.model.CustomUser;
import com.gmail.vx.rnd_news_generator.model.Subject;
import com.gmail.vx.rnd_news_generator.services.CommentService;
import com.gmail.vx.rnd_news_generator.services.SourceService;
import com.gmail.vx.rnd_news_generator.services.SubjectService;
import com.gmail.vx.rnd_news_generator.services.UserService;
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
public class CommentsController {
    private final SubjectService subjectService;
    private final CommentService commentService;
    private final UserService userService;

    public CommentsController(SubjectService subjectService, CommentService commentService, UserService userService) {
        this.subjectService = subjectService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @RequestMapping("/allComments")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String allComments(Model model) {
        User user = getCurrentUser();

        String login = user.getUsername();

        List<Subject> subjects = subjectService.findCommentedSubjects();

        List<CustomUser> users = userService.getAllUsers();

        model.addAttribute("login", login);
        model.addAttribute("roles", user.getAuthorities());
        model.addAttribute("admin", isAdmin(user));
        model.addAttribute("subjects", subjects);
        model.addAttribute("users", users);

        List<Comment> comments = commentService.findAll();
        model.addAttribute("comments", comments);

        return "allcomments";
    }

    @RequestMapping("/allComments/{subjectId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String viewCommentsBySubject(@PathVariable(value = "subjectId") long subjectId,
                                        Model model) {
        List<Comment> comments = commentService.findBySubject(subjectId);

        model.addAttribute("comments", comments);

        return "allcomments";
    }

    @RequestMapping("/commentsBy/{login}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String viewCommentsByUser(@PathVariable(value = "login") String login,
                                     Model model) {
        List<Comment> comments = commentService.findByUser(login);

        model.addAttribute("comments", comments);

        return "allcomments";
    }

    @RequestMapping(value = "/searchComments/date", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String findCommentsByDate(@RequestParam int month, @RequestParam int day, Model model) {
        model.addAttribute("comments", commentService.findByDate(month, day));

        return "allcomments";
    }

    @RequestMapping("/viewPseudoDeleted")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String viewPseudoDeleted(Model model) {
        List<Comment> comments = commentService.findPseudoDeletedComments();

        model.addAttribute("comments", comments);

        return "allcomments";
    }



    private User getCurrentUser() {
        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    private boolean isAdmin(User user) {
        Collection<GrantedAuthority> roles = user.getAuthorities();

        for (GrantedAuthority auth : roles) {
            if ("ROLE_ADMIN".equals(auth.getAuthority()))
                return true;
        }

        return false;
    }

}
