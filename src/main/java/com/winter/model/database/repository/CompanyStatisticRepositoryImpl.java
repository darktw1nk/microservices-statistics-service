package com.winter.model.database.repository;

import com.winter.model.database.entity.CompanyStatistic;
import com.winter.model.database.entity.ProjectStatistic;
import io.quarkus.hibernate.orm.panache.runtime.JpaOperations;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import org.eclipse.microprofile.opentracing.Traced;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Traced
@ApplicationScoped
public class CompanyStatisticRepositoryImpl implements CompanyStatisticRepository {

    @Override
    public CompanyStatistic findByCompanyId(Long companyId) {
        return find("companyId",companyId).singleResult();
    }

    @Transactional
    public CompanyStatistic save(CompanyStatistic statistic){
        EntityManager em = JpaOperations.getEntityManager();
        if (statistic.getId() == null) {
            em.persist(statistic);
            return statistic;
        } else {
            return em.merge(statistic);

        }
    }

    @Override
    public List<CompanyStatistic> getTopN(int n) {
        return find("order by money desc").page(Page.ofSize(n)).list();
    }
}
