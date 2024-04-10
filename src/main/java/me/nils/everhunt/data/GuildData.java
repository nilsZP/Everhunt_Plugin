package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Rank;
import me.nils.everhunt.utils.Stats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class GuildData {
    public GuildData(Player player, String guild, Rank rank) {
        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblguild WHERE uuid = '" + player.getUniqueId().toString() + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tblguild (uuid, guild, rank) VALUES ('" + player.getUniqueId().toString() + "','" + guild + "','" + rank + "')").executeUpdate();
                Stats.setScoreBoard(player);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Player> getReceivers(Player player) {
        ArrayList<Player> list = new ArrayList<>();

        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblguild where guild = '" + getGuild(player) + "'").executeQuery();

            while (resultSet.next()) {
                String uuid = resultSet.getString("uuid");

                list.add(Bukkit.getPlayer(UUID.fromString(uuid)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static String getGuild(Player player) {
        String uuid = player.getUniqueId().toString();

        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblguild where uuid = '" + uuid + "'").executeQuery();

            resultSet.next();

            return resultSet.getString("guild");
        } catch (SQLException e) {
            return null;
        }
    }

    public static Rank getRank(Player player) {
        String uuid = player.getUniqueId().toString();

        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblguild where uuid = '" + uuid + "'").executeQuery();

            resultSet.next();

            return Rank.valueOf(resultSet.getString("rank"));
        } catch (SQLException e) {
            return null;
        }
    }

    public static void promote(Player player, Rank rank) {
        try {
            Everhunt.getDatabase().run("UPDATE tblguild set rank = '" + rank + "' WHERE uuid = '" + player.getUniqueId().toString() + "'").executeUpdate();
            Stats.setScoreBoard(player);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void kick(Player player) {
        try {
            Everhunt.getDatabase().run("DELETE FROM tblguild WHERE uuid = '" + player.getUniqueId().toString() + "'").executeUpdate();
            Stats.setScoreBoard(player);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
