package ru.job4j.todo.service;

import ru.job4j.todo.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    void deleteAll();

    Optional<User> add(User user);

    boolean update(User user);

    List<User> findAll();

    Optional<User> findById(int id);

    boolean delete(int id);

    Optional<User> findUserByNameAndPwd(String name, String password);
}
