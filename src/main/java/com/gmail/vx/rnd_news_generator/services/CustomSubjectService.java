package com.gmail.vx.rnd_news_generator.services;

import com.gmail.vx.rnd_news_generator.model.CustomSubject;
import com.gmail.vx.rnd_news_generator.model.Subject;

import java.util.List;

public interface CustomSubjectService {
    void addSubject(CustomSubject customSubject);
    List<CustomSubject> findAll();
    CustomSubject findById(long id);
    void delete(long id);
}
