package ru.job4j.todo.store;

import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryStore {
    void deleteAll();

    void add(Category category);

    List<Category> findAll();

    Optional<Category> findById(int id);
}
