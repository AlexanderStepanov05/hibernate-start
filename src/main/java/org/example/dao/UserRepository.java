package org.example.dao;

import jakarta.persistence.EntityManager;
import org.example.entity.User;

public class UserRepository extends RepositoryBase<Long, User> {
    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }
}
