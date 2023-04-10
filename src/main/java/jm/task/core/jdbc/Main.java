package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Anna", "Gribok", (byte) 25);
        userService.saveUser("Nastya", "Melkaia", (byte) 21);
        userService.saveUser("Polina", "Contora", (byte) 23);
        userService.saveUser("Ksusha", "Maga", (byte) 21);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
