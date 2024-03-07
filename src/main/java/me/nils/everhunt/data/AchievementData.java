package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.utils.Stats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class AchievementData {
    public AchievementData(Player player, String achievement) {
        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblachievement WHERE uuid = '" + player.getUniqueId().toString() + "' AND achievement = '" +
                    achievement + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tblachievement (uuid, achievement) VALUES ('" + uuid + "','" + achievement + "')").executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO add achievements getter
    // TODO add achievement menu
    // TODO add achievement icons
}

