package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.store.ItemStore;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService implements IItemService {
    private final ItemStore store;

    public ItemService(ItemStore store) {
        this.store = store;
    }

    @Override
    public Item add(Item item) {
        return store.add(item);
    }

    @Override
    public boolean update(Item item) {
        return store.update(item);
    }

    @Override
    public List<Item> findAll() {
        return store.findAll();
    }

    @Override
    public List<Item> findAll(boolean done) {
        return store.findAll(done);
    }

    @Override
    public Optional<Item> findById(int id) {
        return store.findById(id);
    }

    @Override
    public boolean delete(int id) {
        return store.delete(id);
    }

    @Override
    public void done(int id) {
        store.done(id);
    }
}
