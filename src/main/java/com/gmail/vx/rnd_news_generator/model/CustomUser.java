package com.gmail.vx.rnd_news_generator.model;

import com.gmail.vx.rnd_news_generator.model.roles.UserRole;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="CustomUsers")
public class CustomUser {
    @Id
    @GeneratedValue
    private Long id;

    private String login;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy="customUser", cascade=CascadeType.ALL)
    private List<Comment> comments = new ArrayList<Comment>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "customuser_subject",
            joinColumns = @JoinColumn(name = "customuser_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<Subject> likedSubjects = new ArrayList<>();


    private boolean isBlocked;


    @OneToMany(mappedBy="customUser", cascade=CascadeType.ALL)
    private List<UserReport> reports;


    public CustomUser() {

    }

    public CustomUser(String login, String password, UserRole role, boolean isBlocked) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.isBlocked = isBlocked;
    }

    public void addLikedSubject(Subject subject) {
        subject.setUser(this);
        this.likedSubjects.add(subject);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    /*public List<UserReport> getReports() {
        return reports;
    }*/

    public int countReports() {
        return reports.size();
    }

    public void setReport(UserReport report) {
        this.reports.add(report);
    }

    public void setComment(Comment comment) {
        this.comments.add(comment);
    }

    public void setLikedSubject(Subject subject) {
        this.likedSubjects.add(subject);
    }

}
