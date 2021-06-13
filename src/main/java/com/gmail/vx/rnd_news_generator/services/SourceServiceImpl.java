package com.gmail.vx.rnd_news_generator.services;


import com.gmail.vx.rnd_news_generator.model.Source;
import com.gmail.vx.rnd_news_generator.repos.SourceRepository;
import com.gmail.vx.rnd_news_generator.repos.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SourceServiceImpl implements SourceService{
    private final SourceRepository sourceRepository;

    public SourceServiceImpl( SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }



    @Transactional
    @Override
    public void addSource(Source source) {
        sourceRepository.save(source);
    }

    @Transactional(readOnly=true)
    @Override
    public List<Source> findSources() {
        return sourceRepository.findAll();
    }

    @Transactional(readOnly=true)
    @Override
    public Source findSource(long id) {
        return sourceRepository.findById(id).get();
    }

    @Transactional(readOnly=true)
    @Override
    public boolean existsByName(String name) {
        return sourceRepository.existsByName(name);
    }

    @Transactional(readOnly=true)
    @Override
    public Source findByName(String name) {
       return sourceRepository.findSourceByName(name);
    }

}
