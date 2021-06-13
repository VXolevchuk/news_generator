package com.gmail.vx.rnd_news_generator.collectingService.products;

import com.gmail.vx.rnd_news_generator.collectingService.connection.ConnectionFactory;
import com.gmail.vx.rnd_news_generator.dto.ProductDTO;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class ProductCollectorFromBookYe implements ProductCollector {
    private final static String mainAdress = "https://bookclub.ua/";
    private final static String adress = "https://bookclub.ua/catalog/books/pop/?gc=100";

    @Autowired
    private ConnectionFactory connectionFactory;

    @Override
    public ArrayList<ProductDTO> collectNewProducts() {
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();

        try {
            Document doc = connectionFactory.getDocument(adress);

            Elements products = doc.select("#allcontent > section > section > section > section.goodsList > section");

            for (int i = 1; i <= products.size(); i++) {
                Elements e1 = doc.select("#allcontent > section > section > section > section.goodsList > section:nth-child("+ i +") > div.book-inlist-descr > div.book-inlist-name > a");
                String url = e1.attr("href");
                String completeUrl = mainAdress + url;
                String name = e1.text();
                Elements e2 = doc.select("#allcontent > section > section > section > section.goodsList > section:nth-child("+ i +") > div.book-inlist-img > a > img");
                String pictureUrl = mainAdress + e2.attr("src");
                Elements e3 = doc.select("#allcontent > section > section > section > section.goodsList > section:nth-child("+ i +") > div.book-inlist-prbut > div.book-inlist-price");
                String price = e3.text();
                if (price.split(" ").length > 2) {
                    price = price.split(" ")[1] + " " + price.split(" ")[2];
                }

                ProductDTO productDTO = ProductDTO.of(name, price, pictureUrl, completeUrl);
                productDTOS.add(productDTO);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productDTOS;
    }
}
