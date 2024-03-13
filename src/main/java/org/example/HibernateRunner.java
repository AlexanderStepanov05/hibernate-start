package org.example;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Payment;
import org.example.entity.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Slf4j
public class HibernateRunner {

    @Transactional
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            User user = null;
            try (Session session = sessionFactory.openSession()) {
//                TestDataImporter.importData(sessionFactory);
                session.beginTransaction();

                user = session.find(User.class, 1L);
                var user1 = session.find(User.class, 1L);

                var payments = session.createQuery("select p from Payment p where p.receiver.id = :userId", Payment.class)
                        .setParameter("userId", 1L)
                        .setCacheable(true)
                        .getResultList();

                session.getTransaction().commit();
            }
            try (Session session = sessionFactory.openSession()) {
//                TestDataImporter.importData(sessionFactory);
                session.beginTransaction();

                var user2 = session.find(User.class, 1L);

                var payments = session.createQuery("select p from Payment p where p.receiver.id = :userId", Payment.class)
                        .setParameter("userId", 1L)
                        .setCacheable(true)
                        .getResultList();

                session.getTransaction().commit();
            }
        }
    }
}

