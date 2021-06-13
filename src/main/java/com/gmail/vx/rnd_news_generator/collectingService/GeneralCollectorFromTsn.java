package com.gmail.vx.rnd_news_generator.collectingService;

import com.gmail.vx.rnd_news_generator.dto.SubjectDTO;
import com.gmail.vx.rnd_news_generator.model.Source;
import com.gmail.vx.rnd_news_generator.model.Subject;
import com.gmail.vx.rnd_news_generator.services.SourceService;
import com.gmail.vx.rnd_news_generator.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class GeneralCollectorFromTsn {
    @Autowired
    private SourceService sourceService;

    private Source source = new Source("Tsn");

    @Autowired
    private SubjectCollectorFromTsn subjectCollector;
    @Autowired
    private SubjectService subjectService;

    public void collect() {
        ArrayList<SubjectDTO> subjectDTOS = subjectCollector.collectNewSubjects();
        if (!(sourceService.existsByName(source.getName()))) {
            sourceService.addSource(source);
        }

        for(SubjectDTO subjectDTO : subjectDTOS) {
            if (!(subjectService.existsByUrl(subjectDTO.getUrl()))) {
                Subject subject = Subject.fromDTO(subjectDTO);
                subject.addSource(this.source);
                subjectService.addSubject(subject);
            }
        }

    }
}
