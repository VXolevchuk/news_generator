package com.gmail.vx.rnd_news_generator.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Sources")
public class Source {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy="source", cascade=CascadeType.ALL)
    private List<Subject> subjects = new ArrayList<Subject>();

    public Source() {
    }

    public Source(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public void setSubject(Subject subject) {
        this.subjects.add(subject);
    }
}
