package com.winter.model.kafka.task;

import com.winter.model.database.entity.CompanyStatistic;
import com.winter.model.database.entity.ProjectStatistic;
import com.winter.model.event.BeanEvents;
import com.winter.model.event.ProjectStatisticBeanEvent;
import com.winter.model.service.ProjectStatisticService;
import io.vertx.core.eventbus.EventBus;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class ProjectStatisticTask implements Runnable{

    @Inject
    ProjectStatisticService projectStatisticService;

    @Inject
    EventBus eventBus;

    com.winter.model.kafka.model.ProjectStatistic projectStatistic;

    @Override
    public void run() {
        Optional<ProjectStatistic> projectStatisticOptional = projectStatisticService.findByProjectId(projectStatistic.getProjectId());

        if (projectStatisticOptional.isPresent()) {
            com.winter.model.database.entity.ProjectStatistic dbProjectStatistic = projectStatisticOptional.get();
            dbProjectStatistic.setRevenue(projectStatistic.getRevenue());
            projectStatisticService.save(dbProjectStatistic);

        } else {
            com.winter.model.database.entity.ProjectStatistic dbProjectStatistic = new com.winter.model.database.entity.ProjectStatistic();
            dbProjectStatistic.setProjectId(projectStatistic.getProjectId());
            dbProjectStatistic.setCompanyName(projectStatistic.getCompanyName());
            dbProjectStatistic.setProjectName(projectStatistic.getName());
            dbProjectStatistic.setRevenue(projectStatistic.getRevenue());

            projectStatisticService.save(dbProjectStatistic);
        }

        ProjectStatisticBeanEvent event = new ProjectStatisticBeanEvent();
        event.setTop(projectStatisticService.getTopN(10));
        eventBus.send(BeanEvents.PROJECT_STATISTIC_EVENT.getEvent(),event);
    }

    public com.winter.model.kafka.model.ProjectStatistic getProjectStatistic() {
        return projectStatistic;
    }

    public void setProjectStatistic(com.winter.model.kafka.model.ProjectStatistic projectStatistic) {
        this.projectStatistic = projectStatistic;
    }
}
