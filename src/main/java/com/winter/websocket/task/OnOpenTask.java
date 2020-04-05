package com.winter.websocket.task;

import com.winter.model.database.entity.CompanyStatistic;
import com.winter.model.database.entity.ProjectStatistic;
import com.winter.model.event.BeanEvents;
import com.winter.model.event.CompanyStatisticBeanEvent;
import com.winter.model.event.ProjectStatisticBeanEvent;
import com.winter.model.service.CompanyStatisticService;
import com.winter.model.service.ProjectStatisticService;
import com.winter.resources.WebSocketResource;
import com.winter.websocket.event.CompanyStatisticWebSocketEvent;
import com.winter.websocket.event.ProjectStatisticWebSocketEvent;
import io.vertx.core.eventbus.EventBus;
import org.jboss.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;

@Dependent
public class OnOpenTask implements Runnable {
    private static final Logger logger = Logger.getLogger(OnOpenTask.class);
    private static final Integer TOP_NUMBER = 10;

    @Inject
    CompanyStatisticService companyStatisticService;
    @Inject
    ProjectStatisticService projectStatisticService;
    @Inject
    EventBus eventBus;

    @Override
    public void run() {
        try {
            List<CompanyStatistic> topCompanies = companyStatisticService.getTopN(TOP_NUMBER);
            List<ProjectStatistic> topProjects = projectStatisticService.getTopN(TOP_NUMBER);

            CompanyStatisticBeanEvent event = new CompanyStatisticBeanEvent();
            event.setTop(topCompanies);
            eventBus.send(BeanEvents.COMPANY_STATISTIC_EVENT.getEvent(), event);

            ProjectStatisticBeanEvent projectEvent = new ProjectStatisticBeanEvent();
            projectEvent.setTop(topProjects);
            eventBus.send(BeanEvents.PROJECT_STATISTIC_EVENT.getEvent(), projectEvent);
        } catch (Exception e){
            logger.error("",e);
        }
    }
}
