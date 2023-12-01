package me.nils.everhunt.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

import java.sql.*;

public class Database {

    private final Connection connection;

    public Database() {
        try {
            String HOST = "localhost"; //107.189.2.22
            String DATABASE = "dbeverhunt"; //nilsvandeveldesql1
            String USERNAME = "root";
            String PASSWORD = ""; //mRgljnsAkd
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + HOST + ":3306/" + DATABASE + "?useSSL=false", USERNAME, PASSWORD);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void disconnect() {
        if (!(connection == null)) {
            try {
                connection.close();
            } catch (SQLException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    public PreparedStatement run(String query) {
        try {
            return connection.prepareStatement(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
