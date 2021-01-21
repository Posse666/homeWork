package ru.geekbrains.java_two.chat.server.core;

import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;

public class SqlClient {

    private static Connection connection;
    private static PreparedStatement nicknameQuery;
    private static PreparedStatement changeNicknameQuery;
    private static PreparedStatement getMessageQuery;
    private static PreparedStatement putMessageQuery;

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(JDBC.PREFIX + "chat-server/DataBase.db");
            nicknameQuery = connection.prepareStatement("SELECT nickname FROM users WHERE login=? and password=?");
            putMessageQuery = connection.prepareStatement("INSERT INTO messages(message) VALUES (?)");
            getMessageQuery = connection.prepareStatement("SELECT * FROM (SELECT * FROM messages ORDER BY id DESC LIMIT ?) ORDER BY id ASC");
            changeNicknameQuery = connection.prepareStatement("UPDATE users SET nickname=? WHERE login=? AND password=?");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getNickname(String login, String password) {
        try {
            nicknameQuery.setString(1, login);
            nicknameQuery.setString(2, password);
            ResultSet set = nicknameQuery.executeQuery();
            if (set.next()) {
                return set.getString("nickname");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static boolean changeNickname(String newNickname, String login, String password) {
        try {
            changeNicknameQuery.setString(1, newNickname);
            changeNicknameQuery.setString(2, login);
            changeNicknameQuery.setString(3, password);
            return changeNicknameQuery.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void putMessageToDB(String msg) {
        try {
            putMessageQuery.setString(1, msg);
            putMessageQuery.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<String> getMessagesFromDB(int count) {
        ArrayList<String> messages = new ArrayList<>();
        try {
            getMessageQuery.setInt(1, count);
            ResultSet set = getMessageQuery.executeQuery();
            while (set.next()) {
                messages.add(set.getString("message"));
            }
            return messages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void disconnect() {
        try {
            nicknameQuery.close();
            putMessageQuery.close();
            getMessageQuery.close();
            changeNicknameQuery.close();
            connection.close();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }
}
