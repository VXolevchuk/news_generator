package com.gmail.vx.rnd_news_generator.collectingService.customsubjectcreator;

public interface CustomSubjectCreator {
    void createCustomSubject(String source, String sourceUrl, String titleSelector, String dateSelector,
                             String pictureUrlSelector, String creatorLogin);
}
