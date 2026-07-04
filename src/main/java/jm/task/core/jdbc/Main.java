package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserService userService = UserServiceImpl.withHibernate();

        System.out.println("\n Создание таблицы ---");
        userService.createUsersTable();

        System.out.println("\n Добавление пользователей ---");
        userService.saveUser("Sergey", "Melnikov", (byte) 25);
        userService.saveUser("Vitaliy", "Morozov", (byte) 30);
        userService.saveUser("Eduard", "Darenin", (byte) 35);
        userService.saveUser("louis", "Vuitton", (byte) 40);

        System.out.println("\n Список всех пользователей ---");
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

        System.out.println("\n Удаление пользователя с id=4 ---");
        userService.removeUserById(4L);

        System.out.println("\n Список после удаления ---");
        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }

        System.out.println("\n Очистка таблицы ---");
        userService.cleanUsersTable();

        System.out.println("\n Список после очистки ---");
        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }

        System.out.println("\n Удаление таблицы ---");
        userService.dropUsersTable();

        System.out.println("\n Программа завершена!");

        Util.closeSessionFactory();
    }
}


