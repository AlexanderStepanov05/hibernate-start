package org.example;

import org.example.converter.BirthdayConverter;
import org.example.entity.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateRunner {
    public static void main(String[] args) {
        User user = User.builder()          // transient to s1 and s2
                .username("ivan@gmail.com")
                .firstname("Ivan")
                .lastname("Ivanov")
                .build();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session1 = sessionFactory.openSession()) {
                session1.beginTransaction();

                session1.saveOrUpdate(user); // persistent to s1, transient to s2

                session1.getTransaction().commit();
            }

            try (Session session2 = sessionFactory.openSession()) {
                session2.beginTransaction();

//                session2.remove(user);
//                refresh/merge

                session2.getTransaction().commit();
            }
        }
    }
}
