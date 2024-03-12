package org.example;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Slf4j
public class HibernateRunner {

    @Transactional
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.doWork(connection -> System.out.println(connection.getTransactionIsolation()));
//            try {
//
//                var transaction = session.beginTransaction();
//
//                var payment = session.find(Payment.class, 1L);
//                var payment2 = session.find(Payment.class, 2L);
//
//                session.getTransaction().commit();
//            } catch (Exception e) {
//                session.getTransaction().rollback();
//                throw e;
//            }
        }
    }
}

