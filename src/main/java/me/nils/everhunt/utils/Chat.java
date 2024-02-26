package me.nils.everhunt.utils;

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

    public static void guide(Player player, String message) {
        player.sendMessage(color("&c!!&7" + message + "&c!!"));
    }

    public static void npc(Player player, String name, String message) {
        player.sendMessage(color("&e" + name + ": &f" + message));
    }
}
