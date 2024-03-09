package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class GuildData {
    public GuildData(Player player, String guild) {
        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblguild WHERE uuid = '" + player.getUniqueId().toString() + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tblguild (uuid, guild) VALUES ('" + player.getUniqueId().toString() + "','" + guild + "')").executeUpdate();
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
            e.printStackTrace();
        }

        return null;
    }
}
