package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.graph.GraphSemantic;

import java.util.Map;

@Slf4j
public class HibernateRunner {
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

//            session.enableFetchProfile("withCompany");

            Map<String, Object> properties = Map.of(
                    GraphSemantic.LOAD.getJakartaHintName(), session.getEntityGraph("withCompanyAndChat")
            );

            var user = session.find(User.class, 1L, properties);
            System.out.println(user.getCompany().getName());
            System.out.println(user.getUserChats().size());

            var users = session.createQuery("select u from User u where 1=1", User.class)
                    .setHint(GraphSemantic.LOAD.getJakartaHintName(), session.getEntityGraph("withCompanyAndChat"))
                    .list();

            users.forEach(it -> System.out.println(it.getPayments().size()));
            users.forEach(it -> System.out.println(it.getCompany().getName()));

            session.getTransaction().commit();
        }
    }
}

