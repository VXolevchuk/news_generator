package com.gmail.vx.rnd_news_generator.controllers;

import com.gmail.vx.rnd_news_generator.model.CustomUser;
import com.gmail.vx.rnd_news_generator.model.Product;
import com.gmail.vx.rnd_news_generator.model.Source;
import com.gmail.vx.rnd_news_generator.model.Subject;
import com.gmail.vx.rnd_news_generator.services.ProductService;
import com.gmail.vx.rnd_news_generator.services.SourceService;
import com.gmail.vx.rnd_news_generator.services.SubjectService;
import com.gmail.vx.rnd_news_generator.services.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class MainPageController {
    private final UserService userService;
    private final SubjectService subjectService;
    private final SourceService sourceService;
    private final ProductService productService;

    public MainPageController(UserService userService, SubjectService subjectService, SourceService sourceService, ProductService productService) {
        this.userService = userService;
        this.subjectService = subjectService;
        this.sourceService = sourceService;
        this.productService = productService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        User user = getCurrentUser();

        String login = user.getUsername();

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

    @RequestMapping(value="/addLike/{id}", method = RequestMethod.GET)
    public String addLike(@PathVariable(value = "id") long subjectId) {
        User user = getCurrentUser();

        String login = user.getUsername();

        userService.addLikedSubject(login, subjectId);
        return "redirect:/";
    }

    @RequestMapping("/userPage")
    public String userPage(Model model) {
        User user = getCurrentUser();

        String login = user.getUsername();

        model.addAttribute("login", login);
        model.addAttribute("roles", user.getAuthorities());

        return "user";
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
