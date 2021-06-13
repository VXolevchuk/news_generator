package com.gmail.vx.rnd_news_generator.services;

import com.gmail.vx.rnd_news_generator.model.CustomUser;
import com.gmail.vx.rnd_news_generator.model.Source;
import com.gmail.vx.rnd_news_generator.model.Subject;

import java.util.List;

public interface SubjectService {
    void addSubject(Subject subject);
    void deleteSubjects(long[] idList);
    List<Subject> findAll();
    long count();
    long countBySource(Source source);
    List<Subject> findBySource(Source source);
    boolean existsByUrl(String url);
    Subject findById(long id);
    List<Subject> findByUser(CustomUser customUser);

    List<Subject> findByPattern(String pattern);
    List<Subject> findByDate(int month, int day);

    Subject[] sortByLikes();
    Subject[] sortByComments();

    List<Subject> findByUserComments(CustomUser customUser);
    List<Subject> findCommentedSubjects();

    List<Subject> getSubjectsForMainPage(List<Source> sources);
}
