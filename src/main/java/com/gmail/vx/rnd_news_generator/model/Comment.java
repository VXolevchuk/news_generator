package com.gmail.vx.rnd_news_generator.model;


import com.gmail.vx.rnd_news_generator.dto.CommentDTO;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="Comments")
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    private String text;
    private String date;

    private boolean pseudoDeleted;

    @ManyToOne
    @JoinColumn(name="customuser_id")
    private CustomUser customUser;

    @ManyToOne
    @JoinColumn(name="subject_id")
    private Subject subject;

    @OneToOne
    @JoinColumn(name="userreport_id")
    private UserReport report;


    public Comment() {
    }

    public Comment(String text, String date, boolean pseudoDeleted) {
        this.text = text;
        this.date = date;
        this.pseudoDeleted = pseudoDeleted;
    }

    public void addCustomUser(CustomUser customUser) {
        customUser.setComment(this);
        this.customUser = customUser;
    }

    public void addSubject(Subject subject) {
        subject.setComment(this);
        this.subject = subject;
    }

    public static Comment of(String text, String date) {
        return new Comment(text, date, false);
    }

    public CommentDTO toDTO() {
        return CommentDTO.of(date, text);
    }

    public static Comment fromDTO(CommentDTO commentDTO) {
        return Comment.of( commentDTO.getText(), commentDTO.getDate());
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPseudoDeleted() {
        return pseudoDeleted;
    }

    public void setPseudoDeleted(boolean pseudoDeleted) {
        this.pseudoDeleted = pseudoDeleted;
    }

    public CustomUser getCustomUser() {
        return customUser;
    }

    public void setCustomUser(CustomUser customUser) {
        this.customUser = customUser;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public UserReport getReport() {
        return report;
    }

    public void setReport(UserReport report) {
        this.report = report;
    }
}
