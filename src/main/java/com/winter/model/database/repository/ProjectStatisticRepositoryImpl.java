package com.winter.model.database.repository;

import com.winter.model.database.entity.CompanyStatistic;
import com.winter.model.database.entity.ProjectStatistic;
import io.quarkus.hibernate.orm.panache.runtime.JpaOperations;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import org.eclipse.microprofile.opentracing.Traced;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.security.Policy;
import java.util.List;

@Traced
@ApplicationScoped
public class ProjectStatisticRepositoryImpl implements ProjectStatisticRepository{

    @Override
    public ProjectStatistic findByProjectId(Long projectId) {
        return find("projectId",projectId).singleResult();
    }

    @Transactional
    public ProjectStatistic save(ProjectStatistic statistic){
        EntityManager em = JpaOperations.getEntityManager();
        if (statistic.getId() == null) {
            em.persist(statistic);
            return statistic;
        } else {
            return em.merge(statistic);

        }
    }

    @Override
    public List<ProjectStatistic> getTopN(int n) {
        return find("order by revenue desc").page(Page.ofSize(n)).list();
    }
}
