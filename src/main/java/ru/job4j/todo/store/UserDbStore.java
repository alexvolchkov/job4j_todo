package ru.job4j.todo.store;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDbStore implements UserStore {
   private final SessionFactory sf;

    public UserDbStore(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public void deleteAll() {
        CommonMetods.tx(
                session -> session.createQuery("delete from User", User.class).executeUpdate(),
                sf);
    }

    @Override
    public Optional<User> add(User user) {
        Optional<User> rsl = Optional.empty();
        try {
            CommonMetods.tx(
                    session -> {
                        session.save(user);
                        return user;
                    },
                    sf);
            rsl = Optional.of(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public boolean update(User user) {
        return CommonMetods.tx(
                session -> {
                    int count = session.createQuery("update User set name = :fName, password = :fPassword")
                            .setParameter("fName", user.getName())
                            .setParameter("fPassword", user.getPassword())
                            .executeUpdate();
                    return count != 0;
                },
                sf);
    }

    @Override
    public List<User> findAll() {
        return CommonMetods.tx(
                session -> session.createQuery("from User", User.class).list(),
                sf);
    }

    @Override
    public Optional<User> findById(int id) {
        return CommonMetods.tx(
                session -> Optional.ofNullable(session.get(User.class, id)),
        sf);
    }

    @Override
    public boolean delete(int id) {
        return CommonMetods.tx(
                session -> {
                    int count = session.createQuery("delete User where id = :fId")
                            .setParameter("fId", id)
                            .executeUpdate();
                    return count != 0;
                },
                sf);
    }

    @Override
    public Optional<User> findUserByNameAndPwd(String name, String password) {
        return CommonMetods.tx(session ->
            session.createQuery("from User where name = :fName and password = :fPassword", User.class)
                    .setParameter("fName", name)
                    .setParameter("fPassword", password)
                    .list().stream().findFirst(), sf);
    }
}
