package com.gmail.vx.rnd_news_generator.collectingService;

import com.gmail.vx.rnd_news_generator.collectingService.connection.ConnectionFactory;
import com.gmail.vx.rnd_news_generator.dto.SubjectDTO;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Component
public class SubjectCollectorFromRadioSvoboda implements SubjectCollector{
    private final static String adress = "https://www.radiosvoboda.org/news";

    @Autowired
    private ConnectionFactory connectionFactory;

    @Override
    public ArrayList<SubjectDTO> collectNewSubjects() {
        ArrayList<SubjectDTO> subjectDTOS = new ArrayList<>();

        try {
            Document doc = connectionFactory.getDocument(adress);

            Elements subjects = doc.select("#ordinaryItems > li");
            for (int i = 1; i <= subjects.size(); i++) {
                Elements urlHolder = doc.select("#ordinaryItems > li:nth-child("+ i +") > div > div > a");
                String url = urlHolder.attr("href");

                Elements titleHolder = doc.select("#ordinaryItems > li:nth-child("+ i +") > div > div > a > h4");
                String title = titleHolder.attr("title");

                Elements time = doc.select("#content > div > div > div > div > div > div.media-block-wrap > div > ul:nth-child("+ i +") > li > div > div > span");
                String subjectDate = time.text();
                String date = dateConstruct(subjectDate);

                Elements pic = doc.select("#ordinaryItems > li:nth-child("+ i +") > div > a > div > img");
                String pictureUrl = pic.attr("data-src");

                SubjectDTO subjectDTO = SubjectDTO.of( title, date, pictureUrl, url);
                if (!title.equals("")) {
                    subjectDTOS.add(subjectDTO);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return subjectDTOS;
    }

    private String dateConstruct(String subjectDate) {
        String dateToReturn = "";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("y MM dd");
        try {
            String time = subjectDate.substring(0, 4);
            dateToReturn = sdf.format(date) + " " + time;
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return dateToReturn;
    }
}
