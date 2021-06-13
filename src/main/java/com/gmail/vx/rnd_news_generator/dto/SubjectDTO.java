package com.gmail.vx.rnd_news_generator.dto;


public class SubjectDTO {
    private String title;
    private String date;
    private String pictureUrl;
    private String url;

    public SubjectDTO(String title, String date, String pictureUrl, String url) {
        this.title = title;
        this.date = date;
        this.pictureUrl = pictureUrl;
        this.url = url;
    }

    public static SubjectDTO of( String title, String date, String pictureUrl, String url ) {
        return new SubjectDTO(title, date, pictureUrl, url);
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getUrl() {
        return url;
    }

}
