package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import java.util.List;
import java.util.Optional;

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
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    @Override
    public boolean update(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        int count = session.createQuery(
                        "update Item set name = :fName, "
                                + "description = :fDescription,"
                                + "created = :fCreated, "
                                + "done = :fDone "
                                + "where id = :fId")
                .setParameter("fName", item.getName())
                .setParameter("fDescription", item.getDescription())
                .setParameter("fCreated", item.getCreated())
                .setParameter("fDone", item.isDone())
                .setParameter("fId", item.getId())
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return count != 0;
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
    public List<Item> findAll(boolean done) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> rsl = session.createQuery("from Item where done = :fDone")
                .setParameter("fDone", done)
                .list();
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    @Override
    public Optional<Item> findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Optional<Item> rsl = Optional.ofNullable(session.get(Item.class, id));
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    @Override
    public boolean delete(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        int count = session.createQuery(
                        "delete Item where id = :fId")
                .setParameter("fId", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return count != 0;
    }
}
