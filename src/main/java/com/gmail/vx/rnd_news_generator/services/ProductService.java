package com.gmail.vx.rnd_news_generator.services;

import com.gmail.vx.rnd_news_generator.model.Product;
import com.gmail.vx.rnd_news_generator.model.Subject;

import java.util.List;

public interface ProductService {
    void addProduct(Product product);
    List<Product> findAll();
    boolean existsByUrl(String url);
}
