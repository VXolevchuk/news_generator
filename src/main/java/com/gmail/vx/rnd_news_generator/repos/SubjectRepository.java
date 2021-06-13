package com.gmail.vx.rnd_news_generator.repos;

import com.gmail.vx.rnd_news_generator.model.Source;
import com.gmail.vx.rnd_news_generator.model.Subject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("SELECT c FROM Subject c WHERE c.source = :source")
    List<Subject> findBySource(@Param("source") Source source/*, Pageable pageable*/);

    @Query("SELECT COUNT(c) FROM Subject c WHERE c.source = :source")
    long countBySource(@Param("source") Source source);

    @Query("SELECT c FROM Subject c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :pattern, '%'))")
    List<Subject> findByPattern(@Param("pattern") String pattern);

    Subject findById(long id);

    boolean existsByUrl(String url);

    @Query("SELECT DISTINCT c FROM Subject c join fetch c.users u WHERE u.id = :id")
    List<Subject> findByUser(@Param("id") long id);

    @Query("SELECT c FROM Subject c WHERE c.comments.size > 0")
    List<Subject> findCommentedSubjects();





}
