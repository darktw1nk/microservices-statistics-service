package com.winter.model.kafka.task;

import com.winter.model.database.entity.CompanyStatistic;
import com.winter.model.event.BeanEvents;
import com.winter.model.event.CompanyStatisticBeanEvent;
import com.winter.model.service.CompanyStatisticService;
import io.vertx.core.eventbus.EventBus;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class CompanyStatisticTask implements Runnable {

    @Inject
    CompanyStatisticService companyStatisticService;

    @Inject
    EventBus eventBus;

    com.winter.model.kafka.model.CompanyStatistic statistic;

    @Override
    public void run() {
        Optional<CompanyStatistic> dbStatisticOptional = companyStatisticService.findByCompanyId(statistic.getCompanyId());
        if (dbStatisticOptional.isPresent()) {
            com.winter.model.database.entity.CompanyStatistic dbStatistic = dbStatisticOptional.get();
            dbStatistic.setWorkers(statistic.getWorkers());
            dbStatistic.setMoney(statistic.getMoney());
            companyStatisticService.save(dbStatistic);
        } else {
            com.winter.model.database.entity.CompanyStatistic dbStatistic = new com.winter.model.database.entity.CompanyStatistic();
            dbStatistic.setCompanyId(statistic.getCompanyId());
            dbStatistic.setCompanyName(statistic.getCompanyName());
            dbStatistic.setWorkers(statistic.getWorkers());
            dbStatistic.setMoney(statistic.getMoney());
            companyStatisticService.save(dbStatistic);
        }

        List<CompanyStatistic> topCompanies = companyStatisticService.getTopN(10);
        CompanyStatisticBeanEvent event = new CompanyStatisticBeanEvent();
        event.setTop(topCompanies);
        eventBus.send(BeanEvents.COMPANY_STATISTIC_EVENT.getEvent(),event);
    }

    public com.winter.model.kafka.model.CompanyStatistic getStatistic() {
        return statistic;
    }

    public void setStatistic(com.winter.model.kafka.model.CompanyStatistic statistic) {
        this.statistic = statistic;
    }
}
