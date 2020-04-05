package com.winter.model.kafka;

import com.winter.model.event.BeanEvents;
import com.winter.model.event.CompanyStatisticBeanEvent;
import com.winter.model.event.ProjectStatisticBeanEvent;
import com.winter.model.kafka.model.ProjectStatistic;
import com.winter.model.kafka.task.ProjectStatisticTask;
import com.winter.model.service.ProjectStatisticService;
import com.winter.resources.WebSocketResource;
import io.vertx.core.eventbus.EventBus;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.lang.annotation.Inherited;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Traced
@ApplicationScoped
public class ProjectStatisticConsumer {
    private static final Logger logger = Logger.getLogger(ProjectStatisticConsumer.class);

    @Inject
    ManagedExecutor managedExecutor;
    @Inject
    ProjectStatisticTask projectStatisticTask;

    @Incoming("project-statistics")
    public void process(ProjectStatistic projectStatistic) {
        try {
            projectStatisticTask.setProjectStatistic(projectStatistic);
            managedExecutor.execute(projectStatisticTask);
        } catch (Exception e){
            logger.error("",e);
        }
    }

}
