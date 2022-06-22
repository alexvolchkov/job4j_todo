package ru.job4j.todo.store;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryDbStore implements CategoryStore {
    private final SessionFactory sf;

    public CategoryDbStore(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public void deleteAll() {
        CommonMetods.tx(session -> session.createQuery(
                "delete from Category"
        ).executeUpdate(),
                sf);
    }

    @Override
    public void add(Category category) {
        Session session = sf.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.persist(category);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Category> findAll() {
       return CommonMetods.tx(session -> session.createQuery(
                "from Category"
        ).list(), sf);
    }

    @Override
    public Optional<Category> findById(int id) {
        return Optional.ofNullable(
                CommonMetods.tx(session -> session.get(Category.class, id), sf));
    }
}
