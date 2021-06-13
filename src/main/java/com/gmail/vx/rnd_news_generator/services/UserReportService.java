package com.gmail.vx.rnd_news_generator.services;

import com.gmail.vx.rnd_news_generator.model.UserReport;

import java.util.List;

public interface UserReportService {
    void addReport(UserReport report);
    List<UserReport> findAll();
    void deleteReport(long id);
}
