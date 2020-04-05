package com.winter.websocket.event;

public enum WebSocketEvents {
    COMPANY_STATISTIC_EVENT("companyStatistic"),
    PROJECT_STATISTIC_EVENT("projectStatistic");

    private String type;

    WebSocketEvents(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
