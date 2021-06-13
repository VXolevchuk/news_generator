package com.gmail.vx.rnd_news_generator.controllers;


import com.gmail.vx.rnd_news_generator.collectingService.customsubjectcreator.CustomSubjectCreator;
import com.gmail.vx.rnd_news_generator.config.AppConfig;
import com.gmail.vx.rnd_news_generator.model.*;
import com.gmail.vx.rnd_news_generator.model.roles.UserRole;
import com.gmail.vx.rnd_news_generator.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MainController {
    private final SubjectService subjectService;
    private final SourceService sourceService;
    private final CommentService commentService;

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomSubjectService customSubjectService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomSubjectCreator customSubjectCreator;
    @Autowired
    private UserReportService reportService;

    public MainController(SubjectService subjectService, SourceService sourceService, CommentService commentService) {
        this.subjectService = subjectService;
        this.sourceService = sourceService;
        this.commentService = commentService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        User user = getCurrentUser();

        String login = user.getUsername();
        CustomUser customUser = userService.findByLogin(login);

        model.addAttribute("login", login);
        model.addAttribute("roles", user.getAuthorities());
        model.addAttribute("admin", isAdmin(user));

        List<Product> products = productService.findAll();

        ArrayList<Product> products1 = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            products1.add(products.get(i));
        }

        List<Source> sources = sourceService.findSources();

        List<Subject> subjects = subjectService.getSubjectsForMainPage(sources);

        model.addAttribute("subjects", subjects);
        model.addAttribute("sources", sources);
        model.addAttribute("products", products1);

        return "index";
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public String update(@RequestParam String login,
                         @RequestParam String password,
                         Model model) {
        String passHash = passwordEncoder.encode(password);

        if ( ! userService.addUser(login, passHash, UserRole.USER)) {
            model.addAttribute("exists", true);
            model.addAttribute("login", login);
            return "register";
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteUsers(@RequestParam(name = "toDelete[]", required = false) List<Long> ids,
                         Model model) {
        userService.deleteUsers(ids);
        model.addAttribute("users", userService.getAllUsers());

        return "admin";
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // !!!
    public String admin(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @RequestMapping("/userPage")
    public String userPage(Model model) {
        User user = getCurrentUser();

        String login = user.getUsername();
        CustomUser customUser = userService.findByLogin(login);

        model.addAttribute("login", login);
        model.addAttribute("roles", user.getAuthorities());

        return "user";
    }

    @RequestMapping("/unauthorized")
    public String unauthorized(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("login", user.getUsername());
        return "unauthorized";
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
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
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


    @RequestMapping(value="/addLike/{id}", method = RequestMethod.GET)
    public String addLike(@PathVariable(value = "id") long subjectId) {
        User user = getCurrentUser();

        String login = user.getUsername();

        userService.addLikedSubject(login, subjectId);
        return "redirect:/";
    }



    @RequestMapping("/subjects")
    public String allSubjects(Model model) {
        User user = getCurrentUser();

        String login = user.getUsername();
        CustomUser customUser = userService.findByLogin(login);

        model.addAttribute("login", login);
        model.addAttribute("roles", user.getAuthorities());
        model.addAttribute("admin", isAdmin(user));

        List<Subject> subjects = subjectService
                .findAll();
        List<Source> sources = sourceService.findSources();
        model.addAttribute("subjects", subjects);
        model.addAttribute("sources", sources);

        return "subjects";
    }

    @RequestMapping("/source/{id}")
    public String findSubjectsBySource(
            @PathVariable(value = "id") long sourceId,
            Model model)
    {
        Source source = sourceService.findSource(sourceId);

        List<Subject> subjects = subjectService
                .findBySource(source);

        model.addAttribute("sources", sourceService.findSources());
        model.addAttribute("subjects", subjects);
        model.addAttribute("sourceId", sourceId);

        return "subjects";
    }

    @RequestMapping(value = "/subjects/delete", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteSubjects(@RequestParam(value = "toDelete[]", required = false)
                                               long[] toDelete) {
        if (toDelete != null && toDelete.length > 0)
            subjectService.deleteSubjects(toDelete);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@RequestParam String pattern, Model model) {
        model.addAttribute("sources", sourceService.findSources());
        model.addAttribute("subjects", subjectService.findByPattern(pattern));

        return "index";
    }

    @RequestMapping(value = "/search/date", method = RequestMethod.POST)
    public String findByDate(@RequestParam int month, @RequestParam int day, Model model) {
        model.addAttribute("sources", sourceService.findSources());
        model.addAttribute("subjects", subjectService.findByDate(month, day));

        return "subjects";
    }

    @RequestMapping("/findByUserLikes")
    public String findByUserLikes(Model model) {
        User user = getCurrentUser();

        String login = user.getUsername();
        CustomUser customUser = userService.findByLogin(login);

        List<Subject> subjects = subjectService.findByUser(customUser);

        model.addAttribute("subjects", subjects);

        return "subjects";
    }

    @RequestMapping("/findByUserComments")
    public String findByUserComments(Model model) {
        User user = getCurrentUser();

        String login = user.getUsername();
        CustomUser customUser = userService.findByLogin(login);

        List<Subject> subjects = subjectService.findByUserComments(customUser);

        model.addAttribute("subjects", subjects);

        return "subjects";
    }

    @RequestMapping("/sortByLikes")
    public String sortByLikes(Model model) {
        Subject[] subjects = subjectService.sortByLikes();

        model.addAttribute("subjects", subjects);

        return "subjects";
    }

    @RequestMapping("/sortByComments")
    public String sortByComments(Model model) {
        Subject[] subjects = subjectService.sortByComments();

        model.addAttribute("subjects", subjects);

        return "subjects";
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

    @RequestMapping(value = "/addSubject")
    public String subjectAddPage() {
        return "subjectAdd";
    }

    @RequestMapping(value = "/subject/add", method = RequestMethod.POST)
    public String addSubject(@RequestParam String sourceName, @RequestParam String sourceUrl,
                             @RequestParam String titleSelector, @RequestParam String dateSelector,
                             @RequestParam String pictureUrlSelector)
    {
        User user = getCurrentUser();
        String login = user.getUsername();

        customSubjectCreator.createCustomSubject(sourceName, sourceUrl, titleSelector, dateSelector,
                pictureUrlSelector, login);

        return "redirect:/";
    }

    @RequestMapping("/customSubjects")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String viewCustomSubjects(Model model) {
        List<CustomSubject> subjects = customSubjectService.findAll();

        model.addAttribute("subjects", subjects);

        return "customSubjects";
    }

    @RequestMapping("/acceptSubject/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String acceptSubject(@PathVariable(value = "id") long subjectId) {
        CustomSubject customSubject = customSubjectService.findById(subjectId);

        Subject subject = Subject.of(customSubject.getTitle(), customSubject.getDate(),
                customSubject.getPictureUrl(), customSubject.getUrl());

       if (!sourceService.existsByName(customSubject.getSource())) {
           Source source = new Source();
           source.setName(customSubject.getSource());

           sourceService.addSource(source);
           subject.addSource(source);
       } else {
           Source source = sourceService.findByName(customSubject.getSource());
           subject.addSource(source);
       }

        subjectService.addSubject(subject);

        customSubjectService.delete(subjectId);

        return "redirect:/customSubjects";
    }

    @RequestMapping("/declineSubject/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String declineSubject(@PathVariable(value = "id") long subjectId) {
        customSubjectService.delete(subjectId);

        return "redirect:/customSubjects";
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
