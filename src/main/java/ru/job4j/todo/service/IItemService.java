package ru.job4j.todo.service;

import ru.job4j.todo.model.Item;

import java.util.List;

public interface IItemService {

    Item add(Item item);

    boolean update(Item item);

    List<Item> findAll();

    Item findById(int id);

    boolean delete(int id);
}
