package ru.job4j.todo.store;

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
        CommonMetods.tx(session -> session.createQuery("delete from Item", Item.class)
                        .executeUpdate(),
                sf);

    }

    @Override
    public Item add(Item item) {
        return CommonMetods.tx(session -> {
                    session.save(item);
                    return item;
                },
                sf);
    }

    @Override
    public boolean update(Item item) {
        return CommonMetods.tx(session -> {
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
                },
                sf);
    }

    @Override
    public List<Item> findAll() {
        return CommonMetods.tx(
                session -> session.createQuery("from Item", Item.class).list(),
                sf);
    }

    @Override
    public List<Item> findAll(boolean done) {
        return CommonMetods.tx(session -> session.createQuery("from Item where done = :fDone")
                        .setParameter("fDone", done)
                        .list(),
                sf);
    }

    @Override
    public Optional<Item> findById(int id) {
        return CommonMetods.tx(session -> Optional.ofNullable(session.get(Item.class, id)), sf);
    }

    @Override
    public boolean delete(int id) {
        return CommonMetods.tx(session -> {
                    int count = session.createQuery(
                                    "delete Item where id = :fId")
                            .setParameter("fId", id)
                            .executeUpdate();
                    return count != 0;
                },
                sf);
    }

    @Override
    public void done(int id) {
        CommonMetods.tx(session ->
                        session.createQuery("update Item set done = true where id = :fId")
                                .setParameter("fId", id)
                                .executeUpdate(),
                sf);
    }
}
