package com.gmail.vx.rnd_news_generator.repos;

import com.gmail.vx.rnd_news_generator.model.Product;
import com.gmail.vx.rnd_news_generator.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByUrl(String url);
}
