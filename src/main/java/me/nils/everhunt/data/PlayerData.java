package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.utils.Stats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class PlayerData {
    public static final HashMap<String, PlayerData> data = new HashMap<>();
    private String uuid;
    private String username;
    private int xp;
    private int coins;

    public PlayerData(String uuid, String username, int xp, int coins) {
        this.uuid = uuid;
        this.username = username;
        this.xp = xp;
        this.coins = coins;

        data.put(uuid,this);

        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblplayer WHERE UUID = '" + uuid + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tblplayer (uuid, username, xp, coins) VALUES ('" + uuid + "','" + username + "','" +
                        xp + "','" + coins + "')").executeUpdate();
            }
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

    public int getXp() {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT xp FROM tblplayer WHERE uuid = '" + uuid + "'").executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            return xp;
        }
    }

    public void setXp(int xp) {
        try {
            Everhunt.getDatabase().run("UPDATE tblplayer SET xp = " + xp + " WHERE uuid = '" + uuid + "'").executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.xp = xp;
        Player player = Bukkit.getPlayer(UUID.fromString(uuid));
        if (player == null) return;
        Stats.giveStats(player);
        Stats.setScoreBoard(player);
    }

    public void addXp(int xp) {
        setXp(getXp()+xp);
    }

    public int getCoins() {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT coins FROM tblplayer WHERE uuid = '" + uuid + "'").executeQuery();
            resultSet.next();

            return resultSet.getInt(1);
        } catch (SQLException e) {
            return coins;
        }
    }

    public boolean setCoins(int coins) {
        if (coins < 0) {
            return false;
        }

        try {
            Everhunt.getDatabase().run("UPDATE tblplayer SET coins = " + coins + " WHERE uuid = '" + uuid + "'").executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.coins = coins;
        Stats.setScoreBoard(Bukkit.getPlayer(UUID.fromString(uuid)));
        return true;
    }

    public int getLevel() {
        return getXp() / 100;
    }

    public boolean addCoins(int coins) {
        return setCoins(getCoins()+coins);
    }

    public boolean pay(int coins) {
        return setCoins(getCoins()-coins);
    }
}
