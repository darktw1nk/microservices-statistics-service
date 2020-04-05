package com.winter.model.database.repository;

import com.winter.model.database.entity.CompanyStatistic;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.List;

public interface CompanyStatisticRepository extends PanacheRepository<CompanyStatistic> {

    CompanyStatistic findByCompanyId(Long companyId);

    CompanyStatistic save(CompanyStatistic statistic);

    List<CompanyStatistic> getTopN(int n);
}
