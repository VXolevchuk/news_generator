package com.gmail.vx.rnd_news_generator.model;


import com.gmail.vx.rnd_news_generator.dto.SubjectDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name="Subjects")
public class Subject {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="source_id")
    private Source source;

    private String title;
    private String date;
    private String pictureUrl;
    private String url;


    @OneToMany(mappedBy="subject", cascade=CascadeType.ALL)
    private List<Comment> comments = new ArrayList<Comment>();

    @ManyToMany(mappedBy = "likedSubjects")
    private List<CustomUser> users = new ArrayList<>();


    public Subject() {
    }

    public Subject(Source source, String title, String date, String pictureUrl, String url) {
        this.source = source;
        this.title = title;
        this.date = date;
        this.pictureUrl = pictureUrl;
        this.url = url;
    }

    public void addSource(Source source) {
        source.setSubject(this);
        this.source = source;
    }

    public Subject(String title, String date, String pictureUrl, String url) {
        this.title = title;
        this.date = date;
        this.pictureUrl = pictureUrl;
        this.url = url;
    }

    public static Subject of(String title, String date, String pictureUrl, String url) {
        return new Subject(title, date, pictureUrl, url);
    }

    public SubjectDTO toDTO() {
        return  SubjectDTO.of(title, date, pictureUrl, url);
    }

    public static Subject fromDTO(SubjectDTO subjectDTO) {
        return Subject.of(subjectDTO.getTitle(), subjectDTO.getDate(), subjectDTO.getPictureUrl(), subjectDTO.getUrl());
    }

    public int countLikes() {
        return users.size();
    }

    public int countComments() {
        return comments.size();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public void setComment(Comment comment) {
        this.comments.add(comment);
    }

    public void setUser(CustomUser customUser) {
        this.users.add(customUser);
    }
}
