package com.gmail.vx.rnd_news_generator.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Customsubjects")
public class CustomSubject {
    @Id
    @GeneratedValue
    private Long id;

    private String source;

    private String title;
    private String date;
    private String pictureUrl;
    private String url;

    private String creatorLogin;

    public CustomSubject() {
    }

    public CustomSubject(String source, String title, String date, String pictureUrl, String url, String creatorLogin) {
        this.source = source;
        this.title = title;
        this.date = date;
        this.pictureUrl = pictureUrl;
        this.url = url;
        this.creatorLogin = creatorLogin;
    }

    public static CustomSubject of(String source, String title, String date, String pictureUrl, String url, String creatorLogin) {
        return new CustomSubject(source, title, date, pictureUrl, url, creatorLogin);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public String getCreatorLogin() {
        return creatorLogin;
    }

    public void setCreatorLogin(String creatorLogin) {
        this.creatorLogin = creatorLogin;
    }
}
