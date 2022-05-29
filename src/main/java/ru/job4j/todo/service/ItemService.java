package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.store.ItemStore;

import java.util.List;

@Service
public class ItemService implements IItemService {
    private final ItemStore store;

    public ItemService(ItemStore store) {
        this.store = store;
    }

    @Override
    public Item add(Item item) {
        return null;
    }

    @Override
    public boolean update(Item item) {
        return false;
    }

    @Override
    public List<Item> findAll() {
        return store.findAll();
    }

    @Override
    public Item findById(int id) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
