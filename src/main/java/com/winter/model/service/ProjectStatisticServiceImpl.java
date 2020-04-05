package com.winter.model.service;

import com.winter.model.database.entity.CompanyStatistic;
import com.winter.model.database.entity.ProjectStatistic;
import com.winter.model.database.repository.ProjectStatisticRepository;
import org.eclipse.microprofile.opentracing.Traced;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Traced
@ApplicationScoped
public class ProjectStatisticServiceImpl implements ProjectStatisticService {
    private static final Logger logger = Logger.getLogger(ProjectStatisticServiceImpl.class);

    @Inject
    ProjectStatisticRepository projectStatisticRepository;

    @Override
    public Optional<ProjectStatistic> findByProjectId(Long companyId) {
        try {
            ProjectStatistic statistic = projectStatisticRepository.findByProjectId(companyId);
            return Optional.ofNullable(statistic);
        } catch (Exception e) {
            logger.error("", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<ProjectStatistic> save(ProjectStatistic statistic) {
        return Optional.ofNullable(projectStatisticRepository.save(statistic));
    }

    @Override
    public List<ProjectStatistic> getTopN(int n) {
        return projectStatisticRepository.getTopN(n);
    }


}
