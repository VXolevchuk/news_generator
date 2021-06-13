package com.gmail.vx.rnd_news_generator.collectingService.customsubjectcreator;

import com.gmail.vx.rnd_news_generator.collectingService.connection.ConnectionFactory;
import com.gmail.vx.rnd_news_generator.model.CustomSubject;
import com.gmail.vx.rnd_news_generator.services.CustomSubjectService;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CustomSubjectCreatorImpl implements CustomSubjectCreator{
    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private CustomSubjectService customSubjectService;


    public CustomSubjectCreatorImpl() {
    }

    @Override
    public void createCustomSubject(String source, String sourceUrl, String titleSelector, String dateSelector,
                                    String pictureUrlSelector,  String creatorLogin)
    {
        try {
            Document doc = connectionFactory.getDocument(sourceUrl);

            Elements htmlTitle = doc.select(titleSelector);
            String title = htmlTitle.text();

            Elements htmlDate = doc.select(dateSelector);
            String date = htmlDate.text();
            String subjectDate = dateConstruct(date);


            Elements htmlPictureUrl = doc.select(pictureUrlSelector);
            String pictureUrl = htmlPictureUrl.attr("src");

            //Elements htmlUrl = doc.select(urlSelector);
            String url = sourceUrl;

            CustomSubject customSubject = CustomSubject.of(source, title, subjectDate, pictureUrl, url, creatorLogin);
            customSubjectService.addSubject(customSubject);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private String dateConstruct(String subjectDate) {
        Date date = new Date();

        String finalDate = subjectDate.split(" ")[subjectDate.split(" ").length - 1];

        SimpleDateFormat sdf = new SimpleDateFormat("y MM dd");
        String dateToReturn = sdf.format(date) + " " + finalDate;
        return dateToReturn;
    }
}
