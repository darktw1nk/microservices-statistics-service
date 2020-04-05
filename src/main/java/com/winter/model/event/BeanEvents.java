package com.winter.model.event;

public enum BeanEvents {
    COMPANY_STATISTIC_EVENT("companyStatistic"),
    PROJECT_STATISTIC_EVENT("projectStatistic");


    private String event;

    BeanEvents(String event) {
        this.event = event;
    }

    public String getEvent() {
        return event;
    }
}
