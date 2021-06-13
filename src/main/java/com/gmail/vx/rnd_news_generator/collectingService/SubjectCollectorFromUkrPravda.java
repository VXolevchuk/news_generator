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
public class SubjectCollectorFromUkrPravda implements SubjectCollector{
    private final static String adress = "https://www.pravda.com.ua/news/";

    @Autowired
    private ConnectionFactory connectionFactory;

    @Override
    public ArrayList<SubjectDTO> collectNewSubjects()  {
        ArrayList<SubjectDTO> subjectDTOS = new ArrayList<>();

        try {
            Document doc = connectionFactory.getDocument(adress);

            Elements subjects = doc.select("body > div.main_content > div > div.container_sub_news_list > div.container_sub_news_list_wrapper.mode1 > div");
            for (int i = 1; i <= subjects.size(); i++) {
                Elements e = doc.select("body > div.main_content > div > div.container_sub_news_list > div.container_sub_news_list_wrapper.mode1 > div:nth-child(" + i + ") > div.article_content > div.article_header > a ");
                String url = e.attr("href");
                if(!url.contains("https://")) {
                    url = "https://www.pravda.com.ua" + url;
                }

                String title = e.text();

                Elements time = doc.select("body > div.main_content > div > div.container_sub_news_list > div.container_sub_news_list_wrapper.mode1 > div:nth-child(" + i + ") > div.article_time");
                String subjectDate = time.text();
                String date = dateConstruct(subjectDate);

                String pictureUrl = pictureUrlCatch(url);
                if (!pictureUrl.contains("https://")) {
                    pictureUrl = null;
                }

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

        if (!subjectUrl.contains("epravda")) {
            Document doc = connectionFactory.getDocument(subjectUrl);
            Elements picture =doc.select("body > div.main_content > div > div.container_sub_post_news > article > div.block_post > div.post_photo_news > img");
            return picture.attr("src");
        }
        if (subjectUrl.contains("epravda")) {
            Document doc = connectionFactory.getDocument(subjectUrl);
            Elements picture =doc.select("body > div.layout.layout_second > article > div > div > div > div.post__text > div > img");
            return picture.attr("src");
        } else {
            return null;
        }
    }

    private String dateConstruct(String subjectDate) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("y MM dd");
        String finalDate = sdf.format(date) + " " + subjectDate;
        return finalDate;
    }
}
