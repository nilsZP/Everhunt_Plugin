package me.nils.everhunt.constants;

import org.bukkit.ChatColor;

public enum SoulType {
    BASIC(ChatColor.WHITE),
    KING(ChatColor.GOLD),
    BOSS(ChatColor.DARK_RED),
    ALPHA(ChatColor.DARK_AQUA);

    private final ChatColor color;

    SoulType(ChatColor color) {
        this.color = color;
    }

    public ChatColor getColor() {
        return color;
    }
}
