package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerData {
    private String uuid;
    private String username;
    private int xp;
    private int coins;

    public PlayerData(String uuid, String username, int xp, int coins) {
        this.uuid = uuid;
        this.username = username;
        this.xp = xp;
        this.coins = coins;

        try {
            Everhunt.getDatabase().run("INSERT INTO tblplayer (uuid, username, xp, coins) VALUES (" + uuid + "," + username + "," +
                    xp + "," + coins).executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerPlayerData() {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblplayer").executeQuery();

            while (resultSet.next()) {
                String uuid = resultSet.getString("UUID");
                String username = resultSet.getString("username");
                int xp = resultSet.getInt("xp");
                int coins = resultSet.getInt("coins");

                new PlayerData(uuid,username,xp,coins);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getPlayerID(String uuid) {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblplayer WHERE UUID = " + uuid).executeQuery();

            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getPlayerID(Player player) {
        String uuid = player.getUniqueId().toString();
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblplayer WHERE UUID = " + uuid).executeQuery();

            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
