package org.example;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.example.dao.CompanyRepository;
import org.example.dao.UserRepository;
import org.example.dto.UserCreateDto;
import org.example.entity.PersonalInfo;
import org.example.entity.Role;
import org.example.interceptor.TransactionInterceptor;
import org.example.mapper.CompanyReadMapper;
import org.example.mapper.UserCreateMapper;
import org.example.mapper.UserReadMapper;
import org.example.service.UserService;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.time.LocalDate;

@Slf4j
public class HibernateRunner {

    @Transactional
    public static void main(String[] args) throws NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
//                TestDataImporter.importData(sessionFactory);

            var session = (Session) Proxy.newProxyInstance(
                    SessionFactory.class.getClassLoader(),
                    new Class[]{Session.class},
                    (o, method, objects) -> method.invoke(sessionFactory.getCurrentSession(), objects)
            );

//            session.beginTransaction();
            var userRepository = new UserRepository(session);
            var userReadMapper = new UserReadMapper(new CompanyReadMapper());
            var userCreateMapper = new UserCreateMapper(new CompanyRepository(session));

//            var userService = new UserService(
//                    userRepository,
//                    userReadMapper,
//                    userCreateMapper
//            );

            UserService userService = new ByteBuddy()
                    .subclass(UserService.class)
                    .method(ElementMatchers.any())
                    .intercept(MethodDelegation.to(new TransactionInterceptor(sessionFactory)))
                    .make()
                    .load(UserService.class.getClassLoader())
                    .getLoaded()
                    .getDeclaredConstructor(UserRepository.class, UserReadMapper.class, UserCreateMapper.class)
                    .newInstance(userRepository, userReadMapper, userCreateMapper);

            userService.findById(1L).ifPresent(System.out::println);

            var userCreateDto = new UserCreateDto(
                    PersonalInfo.builder()
                            .firstname("Sonya")
                            .lastname("Habarova")
                            .birthDate(LocalDate.now())
                            .build(),
                    "sonya@gmail.com",
                    null,
                    Role.USER,
                    1
            );

            userService.create(userCreateDto);

//            session.getTransaction().commit();
        }
    }
}

