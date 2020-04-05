package com.winter.websocket.event;

import com.winter.model.database.entity.ProjectStatistic;

import java.util.List;

public class ProjectStatisticWebSocketEvent implements WebSocketEvent {
    private String type;
    private List<ProjectStatistic> projects;

    public ProjectStatisticWebSocketEvent() {
        this.type = WebSocketEvents.PROJECT_STATISTIC_EVENT.getType();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ProjectStatistic> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectStatistic> projects) {
        this.projects = projects;
    }
}
