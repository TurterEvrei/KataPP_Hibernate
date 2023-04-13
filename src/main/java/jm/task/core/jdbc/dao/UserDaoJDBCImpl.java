package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection CONNECTION = Util.getConnection();
    private final static String SQL_CREATE_TABLE = """
                CREATE TABLE IF NOT EXISTS USERS
                (
                    id        int          primary key auto_increment not null,
                    name      varchar(255),
                    last_name varchar(255),
                    age       int         
                );""",
            SQL_DROP_TABLE = "DROP TABLE IF EXISTS USERS",
            SQL_INSERT_USER = "INSERT INTO USERS (name, last_name, age) VALUES (?, ?, ?)",
            SQL_REMOVE_USER = "DELETE FROM USERS WHERE ID=?",
            SQL_SELECT_ALL = "SELECT * FROM USERS",
            SQL_CLEAN_TABLE = "DELETE FROM USERS";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = CONNECTION.createStatement()) {
            statement.execute(SQL_CREATE_TABLE);
            System.out.println("Create table user if not exists - OK");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = CONNECTION.createStatement()) {
            statement.execute(SQL_DROP_TABLE);
            System.out.println("Drop table users - OK");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(SQL_INSERT_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(SQL_REMOVE_USER)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Delete user id (" + id + ") - OK");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsersList = new ArrayList<>();
        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                allUsersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsersList;
    }

    public void cleanUsersTable() {
        try (Statement statement = CONNECTION.createStatement()) {
            statement.execute(SQL_CLEAN_TABLE);
            System.out.println("Clean users table - OK");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
