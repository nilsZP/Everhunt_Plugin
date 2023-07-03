package me.nils.vdvcraftrevamp.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Chat {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void debug(Player player, String message) {
        player.sendMessage(Component.text(color("&2[DEBUG] &7" + message)));
    }
}
