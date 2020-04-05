package com.winter.model.service;

import com.winter.model.database.entity.CompanyStatistic;
import com.winter.model.database.entity.ProjectStatistic;

import java.util.List;
import java.util.Optional;

public interface ProjectStatisticService {

    Optional<ProjectStatistic> findByProjectId(Long companyId);

    Optional<ProjectStatistic> save(ProjectStatistic statistic);

    List<ProjectStatistic> getTopN(int n);
}
