package com.winter.model.service;

import com.winter.model.database.entity.CompanyStatistic;
import com.winter.model.database.repository.CompanyStatisticRepository;
import org.eclipse.microprofile.opentracing.Traced;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Traced
@ApplicationScoped
public class CompanyStatisticServiceImpl implements CompanyStatisticService {
    private static final Logger logger = Logger.getLogger(CompanyStatisticServiceImpl.class);

    @Inject
    CompanyStatisticRepository companyStatisticRepository;

    @Override
    public Optional<CompanyStatistic> findByCompanyId(Long companyId) {
        try {
            CompanyStatistic statistic = companyStatisticRepository.findByCompanyId(companyId);
            return Optional.ofNullable(statistic);
        } catch (Exception e) {
            logger.error("", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<CompanyStatistic> save(CompanyStatistic companyStatistic) {
        return Optional.ofNullable(companyStatisticRepository.save(companyStatistic));
    }

    @Override
    public List<CompanyStatistic> getTopN(int n) {
        return companyStatisticRepository.getTopN(n);
    }

}
