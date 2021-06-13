package com.gmail.vx.rnd_news_generator.collectingService.sheduler;

import com.gmail.vx.rnd_news_generator.collectingService.GeneralCollectorFromRadioSvoboda;
import com.gmail.vx.rnd_news_generator.collectingService.GeneralCollectorFromTsn;
import com.gmail.vx.rnd_news_generator.collectingService.GeneralCollectorFromUkrinform;
import com.gmail.vx.rnd_news_generator.collectingService.products.GeneralCollectorFromBookClub;
import com.gmail.vx.rnd_news_generator.collectingService.GeneralCollectorFromUkrPravda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class CollectingSheduler {
    @Autowired
    private GeneralCollectorFromUkrPravda generalCollectorFromUkrPravda;
    @Autowired
    private GeneralCollectorFromTsn generalCollectorFromTsn;
    @Autowired
    private GeneralCollectorFromUkrinform generalCollectorFromUkrinform;
    @Autowired
    private GeneralCollectorFromRadioSvoboda generalCollectorFromRadioSvoboda;

    @Autowired
    private GeneralCollectorFromBookClub generalCollector2;

    @Scheduled(fixedDelay = 6000000)
    public void collectSubjects() {
        generalCollectorFromUkrinform.collect();

    }
    @Scheduled(fixedDelay = 6000000)
    public void collectSubjects1() {
        generalCollectorFromRadioSvoboda.collect();
    }
    @Scheduled(fixedDelay = 6000000)
    public void collectSubjects2() {
        generalCollectorFromUkrPravda.collect();
    }

    @Scheduled(fixedDelay = 1440000)
    public void collectProducts() {
        generalCollector2.collect();
    }
}
