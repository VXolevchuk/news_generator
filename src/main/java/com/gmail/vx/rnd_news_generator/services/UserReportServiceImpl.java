package com.gmail.vx.rnd_news_generator.services;

import com.gmail.vx.rnd_news_generator.model.Subject;
import com.gmail.vx.rnd_news_generator.model.UserReport;
import com.gmail.vx.rnd_news_generator.repos.UserReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserReportServiceImpl implements UserReportService{
    private final UserReportRepository reportRepository;

    public UserReportServiceImpl(UserReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Transactional
    @Override
    public void addReport(UserReport report) { reportRepository.save(report);
    }

    @Transactional(readOnly=true)
    @Override
    public List<UserReport> findAll() {
        return reportRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteReport(long id) {
        reportRepository.deleteById(id);
    }
}
