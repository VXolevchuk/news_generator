package com.gmail.vx.rnd_news_generator.services;

import com.gmail.vx.rnd_news_generator.model.Source;

import java.util.List;

public interface SourceService {
    void addSource(Source source);
    List<Source> findSources();
    Source findSource(long id);
    boolean existsByName(String name);
    Source findByName(String name);

}
