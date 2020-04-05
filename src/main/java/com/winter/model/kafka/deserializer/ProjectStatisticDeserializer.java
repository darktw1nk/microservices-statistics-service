package com.winter.model.kafka.deserializer;

import com.winter.model.kafka.model.ProjectStatistic;
import io.quarkus.kafka.client.serialization.JsonbDeserializer;

public class ProjectStatisticDeserializer extends JsonbDeserializer<ProjectStatistic> {

    public ProjectStatisticDeserializer(){
        super(ProjectStatistic.class);
    }

}
