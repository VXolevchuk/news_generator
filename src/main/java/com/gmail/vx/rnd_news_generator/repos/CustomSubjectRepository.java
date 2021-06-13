package com.gmail.vx.rnd_news_generator.repos;

import com.gmail.vx.rnd_news_generator.model.CustomSubject;
import com.gmail.vx.rnd_news_generator.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomSubjectRepository extends JpaRepository<CustomSubject,Long> {
    @Query("SELECT c FROM CustomSubject c where c.id = :id")
    CustomSubject findById(@Param("id") long id);
}
