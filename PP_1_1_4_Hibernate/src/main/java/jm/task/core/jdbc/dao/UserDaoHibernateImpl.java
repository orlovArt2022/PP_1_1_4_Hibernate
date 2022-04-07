package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = Util.getSessionFactory();
    private Transaction transaction;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS user" +
                    "(id INTEGER AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255)," +
                    "lastname VARCHAR(255)," +
                    "age INTEGER)").executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println(String.format("User с именем – %s добавлен в базу данных", name));
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("delete from User where id =:param");
            query.setParameter("param", id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            List<User> list = session.createQuery("from User").getResultList();
            transaction.commit();
            return list;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        System.out.println("нет значений");
        return new ArrayList<>();
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }
}
