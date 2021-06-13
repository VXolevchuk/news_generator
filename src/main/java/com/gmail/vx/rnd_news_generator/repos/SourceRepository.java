package com.gmail.vx.rnd_news_generator.repos;


import com.gmail.vx.rnd_news_generator.model.Source;
import com.gmail.vx.rnd_news_generator.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SourceRepository extends JpaRepository<Source, Long> {
    boolean existsByName(String name);
    Source findSourceByName(String name);
}
