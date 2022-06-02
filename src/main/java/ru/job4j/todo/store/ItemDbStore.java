package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class ItemDbStore implements ItemStore {

    private final SessionFactory sf;

    public ItemDbStore(SessionFactory sf) {
        this.sf = sf;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteAll() {
        tx(session -> session.createQuery("delete from Item", Item.class)
                .executeUpdate());

    }

    @Override
    public Item add(Item item) {
        return tx(session -> {
            session.save(item);
            return item;
        });
    }

    @Override
    public boolean update(Item item) {
        return tx(session -> {
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
            return count != 0;
        });
    }

    @Override
    public List<Item> findAll() {
        return tx(session -> session.createQuery("from Item", Item.class).list());
    }

    @Override
    public List<Item> findAll(boolean done) {
        return tx(session -> session.createQuery("from Item where done = :fDone")
                .setParameter("fDone", done)
                .list());
    }

    @Override
    public Optional<Item> findById(int id) {
        return tx(session -> Optional.ofNullable(session.get(Item.class, id)));
    }

    @Override
    public boolean delete(int id) {
        return tx(session -> {
            int count = session.createQuery(
                            "delete Item where id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            return count != 0;
        });
    }
}
