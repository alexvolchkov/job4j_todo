package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.store.CategoryStore;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {
    private final CategoryStore store;

    public CategoryService(CategoryStore store) {
        this.store = store;
    }

    @Override
    public void deleteAll() {
        store.deleteAll();
    }

    @Override
    public void add(Category category) {
        store.add(category);
    }

    @Override
    public List<Category> findAll() {
        return store.findAll();
    }

    @Override
    public Optional<Category> findById(int id) {
        return store.findById(id);
    }
}
