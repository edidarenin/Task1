package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "12345678";

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {

                Class.forName("org.postgresql.Driver");

                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Подключение к БД успешно!");
            } catch (ClassNotFoundException e) {
                System.out.println("Драйвер PostgreSQL не найден!");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("Ошибка подключения к БД!");
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Соединение с БД закрыто");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties properties = new Properties();

                properties.put(Environment.DRIVER, "org.postgresql.Driver");
                properties.put(Environment.URL, URL);
                properties.put(Environment.USER, USER);
                properties.put(Environment.PASS, PASSWORD);
                properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
                properties.put(Environment.SHOW_SQL, "false");
                properties.put(Environment.HBM2DDL_AUTO, "update");

                sessionFactory = new Configuration()
                        .setProperties(properties)
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory();

                System.out.println("Hibernate SessionFactory создана");
            } catch (Throwable e) {
                System.out.println("Ошибка при создании SessionFactory: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
    public static void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
            sessionFactory = null;
            System.out.println("SessionFactory закрыта");
        }
    }
}


