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
public class SubjectCollectorFromUkrinform implements SubjectCollector{
    private final static String adress = "https://www.ukrinform.ua/";

    @Autowired
    private ConnectionFactory connectionFactory;

    @Override
    public ArrayList<SubjectDTO> collectNewSubjects() {
        ArrayList<SubjectDTO> subjectDTOS = new ArrayList<>();

        try {
            Document doc = connectionFactory.getDocument(adress);
            Elements subjects = doc.select("#content > div.infoBlock > aside.others > div:nth-child(2) > div.othersBody > div");
            for (int i = 1; i <= subjects.size(); i++) {
                Elements e = doc.select("#content > div.infoBlock > aside.others > div:nth-child(2) > div.othersBody > div:nth-child(" + i + ") > a");
                String url = "https://www.ukrinform.ua" + e.attr("href");
                String dateAndTitle = e.text();

                StringBuilder builder = new StringBuilder(dateAndTitle);
                builder.delete(0, 5);
                String title = builder.toString();

                String subjectDate = dateAndTitle.split(" ")[0];
                String date = dateConstruct(subjectDate);

                String pictureUrl = pictureUrlCatch(url);

                SubjectDTO subjectDTO = SubjectDTO.of(title, date, pictureUrl, url);
                if (!title.equals("")) {
                    subjectDTOS.add(subjectDTO);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return subjectDTOS;
    }

    private  String pictureUrlCatch(String subjectUrl) throws IOException {
        Document doc = connectionFactory.getDocument(subjectUrl);
        Elements picture = doc.select("#content > div:nth-child(2) > div.innerBlock > article > figure > img");
        return picture.attr("src");
    }

    private String dateConstruct(String subjectDate) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("y MM dd");
        String finalDate = sdf.format(date) + " " + subjectDate;
        return finalDate;
    }
}
