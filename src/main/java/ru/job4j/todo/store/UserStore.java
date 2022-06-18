package ru.job4j.todo.store;

import ru.job4j.todo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStore {

    void deleteAll();

    Optional<User> add(User user);

    boolean update(User user);

    List<User> findAll();

    boolean delete(int id);

    Optional<User> findUserByNameAndPwd(String name, String password);
}
