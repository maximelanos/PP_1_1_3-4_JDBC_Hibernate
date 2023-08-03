package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Maksim", "Bardikov", (byte) 41);
        userService.saveUser("Ekaterina", "Artemieva", (byte) 37);
        userService.saveUser("Ivan", "Ivanov", (byte) 31);
        userService.saveUser("Tom", "Cruz", (byte) 38);

        userService.removeUserById(1);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
