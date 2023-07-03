package me.nils.vdvcraftrevamp.utils;

import org.bukkit.ChatColor;

public class Color {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
