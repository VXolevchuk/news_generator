package com.gmail.vx.rnd_news_generator.collectingService;

import com.gmail.vx.rnd_news_generator.dto.SubjectDTO;

import java.util.ArrayList;

public interface SubjectCollector {
    ArrayList<SubjectDTO> collectNewSubjects();
}
