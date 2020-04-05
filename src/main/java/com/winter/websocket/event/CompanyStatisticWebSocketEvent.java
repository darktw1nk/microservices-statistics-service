package com.winter.websocket.event;

import com.winter.model.database.entity.CompanyStatistic;

import java.util.List;

public class CompanyStatisticWebSocketEvent implements WebSocketEvent{
    private String type;
    private List<CompanyStatistic> companies;

    public CompanyStatisticWebSocketEvent() {
        this.type = WebSocketEvents.COMPANY_STATISTIC_EVENT.getType();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CompanyStatistic> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanyStatistic> companies) {
        this.companies = companies;
    }
}
