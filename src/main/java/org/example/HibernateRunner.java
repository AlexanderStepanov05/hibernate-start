package org.example;

import org.example.converter.BirthdayConverter;
import org.example.entity.Birthday;
import org.example.entity.Role;
import org.example.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) throws SQLException {
//        Connection connection = DriverManager.getConnection("db.url", "db.username", "db.password");

        Configuration configuration = new Configuration();
//        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAttributeConverter(new BirthdayConverter());
        configuration.addAnnotatedClass(User.class);
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
//            User user = User.builder()
//                    .username("ivan@gmail.com")
//                    .firstname("Ivan")
//                    .lastname("Ivanov")
//                    .birthDate(new Birthday(LocalDate.of(2000, 1, 1)))
//                    .role(Role.ADMIN)
//                    .info("""
//                            {
//                            "name": "Ivan",
//                            "id": 25
//                            }
//                            """)
//                    .build();

            session.get(User.class, "ivan@gmail.com");

//            session.evict(user);
//            session.clear();
//            session.close();

            session.getTransaction().commit();
        }
    }
}
