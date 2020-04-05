package com.winter.resources;

import com.winter.model.database.entity.CompanyStatistic;
import com.winter.model.database.entity.ProjectStatistic;
import com.winter.model.event.*;
import com.winter.model.service.CompanyStatisticService;
import com.winter.model.service.ProjectStatisticService;
import com.winter.websocket.event.CompanyStatisticWebSocketEvent;
import com.winter.websocket.event.ProjectStatisticWebSocketEvent;
import com.winter.websocket.event.WebSocketEvent;
import com.winter.websocket.task.OnOpenTask;
import io.quarkus.vertx.ConsumeEvent;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@ServerEndpoint("/api/statistic/websocket/{username}")
@ApplicationScoped
public class WebSocketResource {
    private static final Logger logger = Logger.getLogger(WebSocketResource.class);
    private static final Integer TOP_NUMBER = 10;

    @Inject
    ManagedExecutor managedExecutor;
    //@Inject
    //OnOpenTask onOpenTask;

    List<CompanyStatistic> companyStatisticsCache = new CopyOnWriteArrayList<>();
    List<ProjectStatistic> projectStatisticsCache = new CopyOnWriteArrayList<>();
    Map<String, Session> sessions = new ConcurrentHashMap<>();
    private Jsonb jsonb = JsonbBuilder.create();

    @ConsumeEvent(value = "companyStatistic", blocking = true)
    public void companyStatisticBeanEvent(CompanyStatisticBeanEvent beanEvent) {
        try {
            companyStatisticsCache.clear();
            companyStatisticsCache.addAll(beanEvent.getTop());

            CompanyStatisticWebSocketEvent event = new CompanyStatisticWebSocketEvent();
            event.setCompanies(beanEvent.getTop());
            sendEvent(event);
        } catch (Exception e) {
            logger.error("", e);
        }

    }

    @ConsumeEvent(value = "projectStatistic", blocking = true)
    public void projectStatisticBeanEvent(ProjectStatisticBeanEvent beanEvent) {
        try {
            projectStatisticsCache.clear();
            projectStatisticsCache.addAll(beanEvent.getTop());

            ProjectStatisticWebSocketEvent event = new ProjectStatisticWebSocketEvent();
            event.setProjects(beanEvent.getTop());
            sendEvent(event);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    private void sendEvent(WebSocketEvent event) {
        String eventJson = jsonb.toJson(event);
        logger.error("sending json: " + eventJson);
        broadcast(eventJson);
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        try {
            sessions.put(username, session);

            CompanyStatisticWebSocketEvent companyEvent = new CompanyStatisticWebSocketEvent();
            companyEvent.setCompanies(companyStatisticsCache);
            String companyEventJson = jsonb.toJson(companyEvent);
            session.getAsyncRemote().sendObject(companyEventJson, result -> {
                if (result.getException() != null) {
                    logger.error("Unable to send message: " + result.getException());
                }
            });

            ProjectStatisticWebSocketEvent projectEvent = new ProjectStatisticWebSocketEvent();
            projectEvent.setProjects(projectStatisticsCache);
            String projectEventJson = jsonb.toJson(projectEvent);
            session.getAsyncRemote().sendObject(projectEventJson, result -> {
                if (result.getException() != null) {
                    logger.error("Unable to send message: " + result.getException());
                }
            });
          //  managedExecutor.execute(onOpenTask);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        sessions.remove(username);
        //  broadcast("User " + username + " left");
    }

    @OnError
    public void onError(Session session, @PathParam("username") String username, Throwable throwable) {
        sessions.remove(username);
        //  broadcast("User " + username + " left on error: " + throwable);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("username") String username) {
        //broadcast(">> " + username + ": " + message);
    }

    private void broadcast(String message) {
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result -> {
                if (result.getException() != null) {
                    logger.error("Unable to send message: " + result.getException());
                }
            });
        });
    }

}
