package com.gmail.vx.rnd_news_generator.services;

import com.gmail.vx.rnd_news_generator.model.CustomSubject;
import com.gmail.vx.rnd_news_generator.model.Subject;
import com.gmail.vx.rnd_news_generator.repos.CustomSubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomSubjectServiceImpl implements CustomSubjectService{
    private final CustomSubjectRepository customSubjectRepository;

    public CustomSubjectServiceImpl(CustomSubjectRepository customSubjectRepository) {
        this.customSubjectRepository = customSubjectRepository;
    }

    @Transactional
    @Override
    public void addSubject(CustomSubject subject) {
        customSubjectRepository.save(subject);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomSubject> findAll() {
        return customSubjectRepository.findAll();
    }

    @Transactional
    @Override
    public CustomSubject findById(long id) {
        return customSubjectRepository.findById(id);
    }

    @Transactional
    @Override
    public void delete(long id) {
        customSubjectRepository.deleteById(id);
    }

}
