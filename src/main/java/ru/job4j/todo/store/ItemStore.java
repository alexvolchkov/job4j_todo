package ru.job4j.todo.store;

import ru.job4j.todo.model.Item;

import java.util.List;

public interface ItemStore {

    void deleteAll();

    Item add(Item item);

    boolean update(Item item);

    List<Item> findAll();

    Item findById(int id);

    boolean delete(int id);
}
