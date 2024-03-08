package org.example.util;

import lombok.experimental.UtilityClass;
import org.example.converter.BirthdayConverter;
import org.example.entity.*;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {
    public static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
//        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAttributeConverter(new BirthdayConverter());
        configuration.addAnnotatedClass(Manager.class);
        configuration.addAnnotatedClass(Programmer.class);
        configuration.addAnnotatedClass(UserChat.class);
        configuration.addAnnotatedClass(Chat.class);
        configuration.addAnnotatedClass(Profile.class);
        configuration.addAnnotatedClass(Company.class);
        configuration.addAnnotatedClass(User.class);
        configuration.configure();

        return configuration.buildSessionFactory();
    }
}
