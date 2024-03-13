package org.example.util;

import lombok.experimental.UtilityClass;
import org.example.converter.BirthdayConverter;
import org.example.entity.*;
import org.example.interceptor.GlobalInterceptor;
import org.example.listener.AuditTableListener;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;

@UtilityClass
public class HibernateUtil {
    public static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
//        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAttributeConverter(new BirthdayConverter());
        configuration.addAnnotatedClass(UserChat.class);
        configuration.addAnnotatedClass(Payment.class);
        configuration.addAnnotatedClass(Audit.class);
        configuration.addAnnotatedClass(Chat.class);
        configuration.addAnnotatedClass(Profile.class);
        configuration.addAnnotatedClass(Company.class);
        configuration.addAnnotatedClass(User.class);
        configuration.configure();

        var sessionFactory = configuration.buildSessionFactory();
        var sessionFactoryImpl = sessionFactory.unwrap(SessionFactoryImpl.class);
        var listenerRegistry = sessionFactoryImpl.getServiceRegistry().getService(EventListenerRegistry.class);
        var auditTableListener = new AuditTableListener();
        listenerRegistry.appendListeners(EventType.PRE_INSERT, auditTableListener);
        listenerRegistry.appendListeners(EventType.PRE_DELETE, auditTableListener);

        return sessionFactory;
    }

    public static Configuration buildConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAttributeConverter(new BirthdayConverter());
        configuration.addAnnotatedClass(Payment.class);
        configuration.addAnnotatedClass(Audit.class);
        configuration.addAnnotatedClass(UserChat.class);
        configuration.addAnnotatedClass(Chat.class);
        configuration.addAnnotatedClass(Profile.class);
        configuration.addAnnotatedClass(Company.class);
        configuration.addAnnotatedClass(User.class);
        configuration.setInterceptor(new GlobalInterceptor());
        return configuration;
    }
}
