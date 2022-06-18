package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.UserStore;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final UserStore store;

    public UserService(UserStore store) {
        this.store = store;
    }

    @Override
    public void deleteAll() {
        store.deleteAll();
    }

    @Override
    public Optional<User> add(User user) {
        return store.add(user);
    }

    @Override
    public boolean update(User user) {
        return store.update(user);
    }

    @Override
    public List<User> findAll() {
        return store.findAll();
    }

    @Override
    public boolean delete(int id) {
        return store.delete(id);
    }

    @Override
    public Optional<User> findUserByNameAndPwd(String name, String password) {
        return store.findUserByNameAndPwd(name, password);
    }
}
