package com.winter.model.kafka.deserializer;

import com.winter.model.kafka.model.CompanyStatistic;
import io.quarkus.kafka.client.serialization.JsonbDeserializer;

public class CompanyStatisticDeserializer extends JsonbDeserializer<CompanyStatistic> {

    public CompanyStatisticDeserializer(){
        super(CompanyStatistic.class);
    }

}
