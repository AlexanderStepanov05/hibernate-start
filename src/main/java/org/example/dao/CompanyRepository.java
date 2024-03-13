package org.example.dao;

import jakarta.persistence.EntityManager;
import org.example.entity.Company;

public class CompanyRepository extends RepositoryBase<Integer, Company> {
    public CompanyRepository(EntityManager entityManager) {
        super(Company.class, entityManager);
    }
}
