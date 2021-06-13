package com.gmail.vx.rnd_news_generator.repos;

import com.gmail.vx.rnd_news_generator.model.Comment;
import com.gmail.vx.rnd_news_generator.model.Product;
import com.gmail.vx.rnd_news_generator.model.Source;
import com.gmail.vx.rnd_news_generator.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.subject.id = :id")
    List<Comment> findBySubjectId(@Param("id") long id);

    Comment findCommentById(long id);

    @Query("SELECT c FROM Comment c WHERE c.customUser.login = :login")
    List<Comment> findByUserLogin (@Param("login") String login);

    @Query("SELECT c FROM Comment c WHERE c.pseudoDeleted = true")
    List<Comment> findPseudoDeleted();
}
