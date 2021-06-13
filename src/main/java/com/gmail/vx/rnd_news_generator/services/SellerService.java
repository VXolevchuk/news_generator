package com.gmail.vx.rnd_news_generator.services;

import com.gmail.vx.rnd_news_generator.model.Seller;

public interface SellerService {
    void addSeller(Seller seller);
    boolean existsByName(String name);

}
