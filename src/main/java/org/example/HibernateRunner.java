package org.example;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.PaymentRepository;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.Proxy;

@Slf4j
public class HibernateRunner {

    @Transactional
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
//                TestDataImporter.importData(sessionFactory);

            var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (o, method, objects) -> method.invoke(sessionFactory.getCurrentSession(), objects));

            session.beginTransaction();

            var paymentRepository = new PaymentRepository(session);
            paymentRepository.findById(1L).ifPresent(System.out::println);

            session.getTransaction().commit();
        }
    }
}

