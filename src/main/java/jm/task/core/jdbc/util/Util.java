package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
