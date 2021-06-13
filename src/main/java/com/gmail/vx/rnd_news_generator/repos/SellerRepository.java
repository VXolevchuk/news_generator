package com.gmail.vx.rnd_news_generator.repos;

import com.gmail.vx.rnd_news_generator.model.Seller;
import com.gmail.vx.rnd_news_generator.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    boolean existsByName(String name);
}
