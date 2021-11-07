package ru.otus.dao;

import java.util.Optional;
import ru.otus.model.User;

public class UserDaoJPA implements UserDao {

    @Override
    public Optional<User> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findRandomUser() {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return Optional.empty();
    }
}
