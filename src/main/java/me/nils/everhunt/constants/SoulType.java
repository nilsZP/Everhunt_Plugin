package me.nils.everhunt.constants;

import org.bukkit.ChatColor;

public enum SoulType {
    BASIC(ChatColor.WHITE),
    KING(ChatColor.GOLD),
    BOSS(ChatColor.LIGHT_PURPLE),
    ALPHA(ChatColor.DARK_AQUA),
    CONQUERER(ChatColor.DARK_RED);

    private final ChatColor color;

    SoulType(ChatColor color) {
        this.color = color;
    }

    public ChatColor getColor() {
        return color;
    }
}
