package com.winter.model.kafka;

import com.winter.model.database.repository.CompanyStatisticRepository;
import com.winter.model.event.BeanEvents;
import com.winter.model.event.CompanyStatisticBeanEvent;
import com.winter.model.kafka.model.CompanyStatistic;
import com.winter.model.kafka.task.CompanyStatisticTask;
import com.winter.model.service.CompanyStatisticService;
import com.winter.resources.WebSocketResource;
import io.vertx.core.eventbus.EventBus;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Traced
@ApplicationScoped
public class CompanyStatisticConsumer {
    private static final Logger logger = Logger.getLogger(CompanyStatisticConsumer.class);

    @Inject
    ManagedExecutor managedExecutor;
    @Inject
    CompanyStatisticTask companyStatisticTask;


    @Incoming("company-statistics")
    public void process(CompanyStatistic statistic) {
        try {
            companyStatisticTask.setStatistic(statistic);
            managedExecutor.execute(companyStatisticTask);
        } catch (Exception e){
            logger.error("",e);
        }
    }

}
