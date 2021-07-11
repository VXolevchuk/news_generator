package com.gmail.vx.rnd_news_generator.controllers;

import com.gmail.vx.rnd_news_generator.collectingService.customsubjectcreator.CustomSubjectCreator;
import com.gmail.vx.rnd_news_generator.model.CustomSubject;
import com.gmail.vx.rnd_news_generator.model.Source;
import com.gmail.vx.rnd_news_generator.model.Subject;
import com.gmail.vx.rnd_news_generator.services.CustomSubjectService;
import com.gmail.vx.rnd_news_generator.services.SourceService;
import com.gmail.vx.rnd_news_generator.services.SubjectService;
import com.gmail.vx.rnd_news_generator.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.Collection;
import java.util.List;

@Controller
public class CustomSubjectsController {
    private final SubjectService subjectService;
    private final SourceService sourceService;
    private final CustomSubjectService customSubjectService;
    private final CustomSubjectCreator customSubjectCreator;

    private final UserService userService;

    public CustomSubjectsController(SubjectService subjectService, SourceService sourceService, UserService userService, CustomSubjectService customSubjectService, CustomSubjectCreator customSubjectCreator) {
        this.subjectService = subjectService;
        this.sourceService = sourceService;
        this.userService = userService;
        this.customSubjectService = customSubjectService;
        this.customSubjectCreator = customSubjectCreator;
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

}
