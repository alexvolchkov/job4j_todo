package ru.job4j.todo.store;

import ru.job4j.todo.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemStore {

    void deleteAll();

    Item add(Item item);

    boolean update(Item item);

    List<Item> findAll();

    List<Item> findAll(boolean done);

    Optional<Item> findById(int id);

    boolean delete(int id);
}
