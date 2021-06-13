package com.gmail.vx.rnd_news_generator.model;


import com.gmail.vx.rnd_news_generator.dto.ProductDTO;
import com.gmail.vx.rnd_news_generator.dto.SubjectDTO;

import javax.persistence.*;

@Entity
@Table(name="Products")
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="seller_id")
    private Seller seller;

    private String name;
    private String price;
    private String pictureUrl;
    private String url;

    public Product() {
    }

    public Product(String name, String price, String pictureUrl, String url) {
        this.name = name;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.url = url;
    }

    public Product(Seller seller, String name, String price, String pictureUrl, String url) {
        this.seller = seller;
        this.name = name;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.url = url;
    }

    public void addSeller(Seller seller) {
        seller.setProduct(this);
        this.seller = seller;
    }

    public static Product of(String name, String price, String pictureUrl, String url) {
        return new Product(name, price, pictureUrl, url);
    }

    public ProductDTO toDTO() {
        return  ProductDTO.of(name, price, pictureUrl, url);
    }

    public static Product fromDTO(ProductDTO productDTO) {
        return Product.of(productDTO.getName(), productDTO.getPrice(), productDTO.getPictureUrl(), productDTO.getUrl());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
}
