package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

public class Main {
    public static void main(String[] args) {
//        UserDao user = new UserDaoJDBCImpl();
        UserDao user = new UserDaoHibernateImpl();
        user.createUsersTable();
        user.saveUser("Иван", "Иванов", (byte) 30);
        user.saveUser("Сергей", "Сергеев", (byte) 67);
        user.saveUser("Павел", "Павлов", (byte) 22);
        user.saveUser("Имя", "Фамилия", (byte) 12);
        for (User u: user.getAllUsers()) {
            System.out.println(u.toString());
        }
        user.removeUserById(2);
        user.cleanUsersTable();
        user.dropUsersTable();
    }
}
