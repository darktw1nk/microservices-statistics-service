package com.winter.model.event;

import com.winter.model.database.entity.ProjectStatistic;
import org.dom4j.bean.BeanElement;

import java.math.BigDecimal;
import java.util.List;

public class ProjectStatisticBeanEvent implements BeanEvent {
    private String type;
private List<ProjectStatistic> top;

    public ProjectStatisticBeanEvent() {
        this.type = BeanEvents.PROJECT_STATISTIC_EVENT.getEvent();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ProjectStatistic> getTop() {
        return top;
    }

    public void setTop(List<ProjectStatistic> top) {
        this.top = top;
    }
}
