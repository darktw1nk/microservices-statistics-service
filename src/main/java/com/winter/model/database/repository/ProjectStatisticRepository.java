package com.winter.model.database.repository;

import com.winter.model.database.entity.ProjectStatistic;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.List;

public interface ProjectStatisticRepository extends PanacheRepository<ProjectStatistic> {

    ProjectStatistic findByProjectId(Long projectId);

    ProjectStatistic save(ProjectStatistic projectStatistic);

    List<ProjectStatistic> getTopN(int n);
}
