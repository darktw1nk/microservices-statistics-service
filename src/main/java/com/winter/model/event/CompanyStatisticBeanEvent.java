package com.winter.model.event;

import com.winter.model.database.entity.CompanyStatistic;

import java.math.BigDecimal;
import java.util.List;

public class CompanyStatisticBeanEvent implements BeanEvent {
    private String type;
    private List<CompanyStatistic> top;

    public CompanyStatisticBeanEvent() {
        this.type = BeanEvents.COMPANY_STATISTIC_EVENT.getEvent();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CompanyStatistic> getTop() {
        return top;
    }

    public void setTop(List<CompanyStatistic> top) {
        this.top = top;
    }
}
