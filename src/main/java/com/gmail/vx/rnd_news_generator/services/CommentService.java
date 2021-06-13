package com.gmail.vx.rnd_news_generator.services;

import com.gmail.vx.rnd_news_generator.model.Comment;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment);
    List<Comment> findBySubject(long id);
    void pseudoDelete(long id);
    void deleteComments(long[] idList);
    List<Comment> findByUser(String login);

    List<Comment> findBySubjectAllowed(long id);
    List<Comment> findPseudoDeletedComments();
    Comment findById(long id);

    List<Comment> findAll();

    List<Comment> findByDate(int month, int day);
}
