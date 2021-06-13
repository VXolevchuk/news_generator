package com.gmail.vx.rnd_news_generator.dto;

public class ProductDTO {
    private String name;
    private String price;
    private String pictureUrl;
    private String url;

    public ProductDTO(String name, String price, String pictureUrl, String url) {
        this.name = name;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.url = url;
    }

    public static ProductDTO of(String name, String price, String pictureUrl, String url ) {
        return new ProductDTO(name, price, pictureUrl, url);
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getUrl() {
        return url;
    }
}
