package com.gmail.vx.rnd_news_generator.controllers;

import com.gmail.vx.rnd_news_generator.config.AppConfig;
import com.gmail.vx.rnd_news_generator.model.Comment;
import com.gmail.vx.rnd_news_generator.model.CustomUser;
import com.gmail.vx.rnd_news_generator.model.Subject;
import com.gmail.vx.rnd_news_generator.model.UserReport;
import com.gmail.vx.rnd_news_generator.services.CommentService;
import com.gmail.vx.rnd_news_generator.services.SubjectService;
import com.gmail.vx.rnd_news_generator.services.UserReportService;
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

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
public class CommentPageController {
    private final SubjectService subjectService;
    private final CommentService commentService;
    private final UserService userService;
    private final UserReportService reportService;

    public CommentPageController(SubjectService subjectService, CommentService commentService, UserService userService, UserReportService reportService) {
        this.subjectService = subjectService;
        this.commentService = commentService;
        this.userService = userService;
        this.reportService = reportService;
    }


    @RequestMapping("/comments/{id}")
    public String listComments(
            @PathVariable(value = "id") long subjectId,
            Model model)
    {
        User user = getCurrentUser();
        String login = user.getUsername();

        Subject subject = subjectService.findById(subjectId);

        List<Comment> comments = commentService
                .findBySubjectAllowed(subjectId);

        model.addAttribute("subject", subject);
        model.addAttribute("comments", comments);
        model.addAttribute("moder", isModer(user));
        model.addAttribute("admin", isAdmin(user));
        model.addAttribute("userName", login);
        model.addAttribute("subjectId", subjectId);

        return "comments";
    }

    @RequestMapping(value="/addComment/{id}", method = RequestMethod.POST)
    public String commentAdd(
            @PathVariable(value = "id") long subjectId,
            @RequestParam String text)
    {
        if (!text.equals("")) {
            User user = getCurrentUser();

            String login = user.getUsername();
            CustomUser customUser = userService.findByLogin(login);

            if (!customUser.isBlocked()) {
                Subject subject = subjectService.findById(subjectId);

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("y MM dd HH:mm");
                String commentDate = sdf.format(date);
                Comment comment = new Comment(text, commentDate, false);
                comment.addCustomUser(customUser);
                comment.addSubject(subject);
                commentService.addComment(comment);
            }
        }

        return  "redirect:/comments/" + subjectId;
    }

    @RequestMapping(value = "/comment/pseudoDelete/{id}/{subjectId}", method = RequestMethod.GET)
    public String pseudoDelete(@PathVariable(value = "id")
                                       long id, @PathVariable(value = "subjectId")
                                       long subjectId) {
        User user = getCurrentUser();

        String login = user.getUsername();

        Comment comment = commentService.findById(id);
        if (isAdmin(user) || isModer(user) || comment.getCustomUser().getLogin().equals(login)) {
            commentService.pseudoDelete(id);
        }

        return "redirect:/comments/" + subjectId;
    }

    @RequestMapping(value = "/comments/delete", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteComments(@RequestParam(value = "toDelete[]", required = false)
                                                       long[] toDelete) {
        if (toDelete != null && toDelete.length > 0)
            commentService.deleteComments(toDelete);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/report/{login}/{commentId}/{subjectId}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String reportUser(@PathVariable(value = "login") String login, @PathVariable(value = "commentId") long commentId,
                             @PathVariable(value = "subjectId") long subjectId)
    {
        CustomUser customUser = userService.findByLogin(login);
        Comment comment = commentService.findById(commentId);
        if ( ! AppConfig.ADMIN.equals(customUser.getLogin()) && comment.getReport() == null) {
            UserReport report = new UserReport(new Date());
            report.addCustomUser(customUser);
            report.addComment(comment);
            reportService.addReport(report);
        }

        return "redirect:/comments/" + subjectId;
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

    private boolean isModer(User user) {
        Collection<GrantedAuthority> roles = user.getAuthorities();

        for (GrantedAuthority auth : roles) {
            if ("ROLE_MODERATOR".equals(auth.getAuthority()))
                return true;
        }

        return false;
    }
}
