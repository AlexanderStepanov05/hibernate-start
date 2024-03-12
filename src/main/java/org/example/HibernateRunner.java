package org.example;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Payment;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Slf4j
public class HibernateRunner {

    @Transactional
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession();
             Session session1 = sessionFactory.openSession()) {
//                TestDataImporter.importData(sessionFactory);
                session.beginTransaction();
                session1.beginTransaction();

                var payment = session.find(Payment.class, 1L, LockModeType.PESSIMISTIC_READ);
                payment.setAmount(payment.getAmount() + 10);

                var payment1 = session.find(Payment.class, 1L);
                payment1.setAmount(payment1.getAmount() + 20);

                session.getTransaction().commit();
                session1.getTransaction().commit();

//          setDefaultReadOnly(true), setReadOnly(); createQuery().setReadOnly(true).setHint(QueryHints.READ_ONLY, true);
//            createNativeQuery("set transaction read only;").executeUpdate();
        }
    }
}

