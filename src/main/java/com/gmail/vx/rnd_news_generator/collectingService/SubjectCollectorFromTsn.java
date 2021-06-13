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
public class SubjectCollectorFromTsn implements SubjectCollector{
    private final static String adress = "https://tsn.ua/ukrayina";

    @Autowired
    private ConnectionFactory connectionFactory;

    @Override
    public ArrayList<SubjectDTO> collectNewSubjects() {
        ArrayList<SubjectDTO> subjectDTOS = new ArrayList<>();

        try {
            Document doc = connectionFactory.getDocument(adress);

            Elements subjects = doc.select("body > div.l-page__main.l-sheet.l-sheet__gap > div > div > main > div:nth-child(4) > div > div");
            for (int i = 1; i <= subjects.size(); i++) {
                Elements urlAndTitleHolder = doc.select("body > div.l-page__main.l-sheet.l-sheet__gap > div > div > main > div:nth-child(4) > div > div:nth-child("+ i +") > article > div > div > h3 > a");
                String url = urlAndTitleHolder.attr("href");
                String title = urlAndTitleHolder.text();

                Elements time = doc.select("body > div.l-page__main.l-sheet.l-sheet__gap > div > div > main > div:nth-child(4) > div > div:nth-child("+ i +") > article > div > div > footer > div > div > dl > dd > time");
                String subjectDate = time.text();
                String date = dateConstruct(subjectDate);

                String pictureUrl = pictureUrlCatch(url);

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

    private  String pictureUrlCatch(String subjectUrl) throws IOException {
        Document doc = connectionFactory.getDocument(subjectUrl);
        Elements picture = doc.select("body > div.l-page__main.l-sheet.l-sheet__gap > div > div > main > div > div.c-card.c-card--embed.c-card--embed-xl.c-card--bold.c-card--media-row.c-card--title-xxl > figure > div > picture > img");
        return picture.attr("src");
    }

    private String dateConstruct(String subjectDate) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("y MM dd");
        String finalDate = sdf.format(date) + " " + subjectDate;
        return finalDate;
    }

}
