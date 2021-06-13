package com.gmail.vx.rnd_news_generator.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="UserReports")
public class UserReport {
    @Id
    @GeneratedValue
    private Long id;

    private Date date;

    @ManyToOne
    @JoinColumn(name="customuser_id")
    private CustomUser customUser;


    @OneToOne(mappedBy="report", cascade=CascadeType.MERGE)
    private Comment comment;

    public UserReport() {
    }

    public UserReport(Date date) {
        this.date = date;
    }

    public void addCustomUser(CustomUser customUser) {
        customUser.setReport(this);
        this.customUser = customUser;
    }

    public void addComment(Comment comment) {
        comment.setReport(this);
        this.comment = comment;

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
