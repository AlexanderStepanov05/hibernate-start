package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Company;
import org.example.entity.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Slf4j
public class HibernateRunner {

//    private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);

    public static void main(String[] args) {
        Company company = Company.builder()
                .name("Google")
                .build();

//        User user = User.builder()          // transient to s1 and s2
//                .username("ivan2@gmail.com")
//                .personalInfo(PersonalInfo.builder()
//                        .firstname("Ivan")
//                        .lastname("Ivanov")
//                        .birthDate(new Birthday(LocalDate.of(2000, 1, 1)))
//                        .build())
//                .company(company)
//                .build();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try (session1) {
                Transaction transaction = session1.beginTransaction();
                session1.get(User.class, 1L);

//                session1.persist(company);
//                session1.persist(user); // persistent to s1, transient to s2

                session1.getTransaction().commit();
            }
        }
    }
}
