package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import java.util.List;

@Repository
public class ItemDbStore implements ItemStore {

    private final SessionFactory sf;

    public ItemDbStore(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public void deleteAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("delete from Item", Item.class).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Item add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.persist(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    @Override
    public boolean update(Item item) {
        return false;
    }

    @Override
    public List<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> rsl = session.createQuery("from Item", Item.class).list();
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    @Override
    public Item findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item rsl = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
