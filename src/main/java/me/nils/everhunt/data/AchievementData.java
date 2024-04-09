package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.menu.Menu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AchievementData {
    public AchievementData(Player player, String achievement, String description) {
        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblachievement WHERE uuid = '" + player.getUniqueId().toString() + "' AND achievement = '" +
                    achievement + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tblachievement (uuid, achievement,description) VALUES ('" + player.getUniqueId().toString() + "','" + achievement + "','" + description + "')").executeUpdate();
                player.sendTitle(ChatColor.GOLD + achievement,ChatColor.YELLOW + description,1,10,1);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,0.5F,1F);
                PlayerData.data.get(player.getUniqueId().toString()).addXp(25);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<ItemStack> getAchievements(Player player) {
        String uuid = player.getUniqueId().toString();
        ArrayList<ItemStack> list = new ArrayList<>();

        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblachievement where uuid = '" + uuid + "'").executeQuery();

            while (resultSet.next()) {
                String achievement = resultSet.getString("achievement");
                String description = resultSet.getString("description");

                list.add(Menu.makeItem(Material.EMERALD, ChatColor.GREEN + achievement,description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}

