package com.winter.model.service;

import com.winter.model.database.entity.CompanyStatistic;

import java.util.List;
import java.util.Optional;

public interface CompanyStatisticService {

    Optional<CompanyStatistic> findByCompanyId(Long companyId);

    Optional<CompanyStatistic> save(CompanyStatistic companyStatistic);

    List<CompanyStatistic> getTopN(int n);
}
