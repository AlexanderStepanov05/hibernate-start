package org.example.dao;

import jakarta.persistence.EntityManager;
import org.example.entity.Payment;

public class PaymentRepository extends RepositoryBase<Long, Payment> {
    public PaymentRepository(EntityManager entityManager) {
        super(Payment.class, entityManager);
    }
}
