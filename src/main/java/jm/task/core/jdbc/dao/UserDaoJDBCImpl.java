package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }
@Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(45) NOT NULL, " +
                "last_name VARCHAR(45) NOT NULL, " +
                "age SMALLINT)";
        try (Statement stmt = Util.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Таблица users создана");
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы: " + e.getMessage());
            e.printStackTrace();
        }

    }
@Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Statement stmt = Util.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Таблица users удалена");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении таблицы: " + e.getMessage());
            e.printStackTrace();
        }
    }
@Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = Util.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setByte(3, age);
            pstmt.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении пользователя: " + e.getMessage());
            e.printStackTrace();
        }
    }
@Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement pstmt = Util.getConnection().prepareStatement(sql)) {
            pstmt.setLong(1, id);
        int rows = pstmt.executeUpdate();
        if (rows > 0) {
            System.out.println("User с id " + id + " удалён");
        } else {
            System.out.println("User с id " + id + " не найден");
        }
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении пользователя: " + e.getMessage());
            e.printStackTrace();
        }
    }
@Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement stmt = Util.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setLastName(rs.getString("last_name"));
            user.setAge(rs.getByte("age"));
            users.add(user);
        }
            System.out.println("Получено пользователей: " + users.size());
        } catch (SQLException e) {
            System.out.println("Ошибка при получении списка пользователей: " + e.getMessage());
            e.printStackTrace();
        }
    return users;
    }
@Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";
        try (Statement stmt = Util.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Таблица users очищена");
        } catch (SQLException e) {
            System.out.println("Ошибка при очистке таблицы: " + e.getMessage());
            e.printStackTrace();
    }
    }
}
