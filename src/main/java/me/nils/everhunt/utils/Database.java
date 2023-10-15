package me.nils.everhunt.utils;

import java.sql.*;

public class Database {

    private final Connection connection;

    public Database() {
        try {
            String HOST = "localhost";
            String DATABASE = "dbeverhunt";
            String USERNAME = "root";
            String PASSWORD = "";
            System.out.println(HOST);
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
                ErrorUtil.handleError("Could not disconnect from the database.", exception);
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
