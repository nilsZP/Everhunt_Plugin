package me.nils.vdvcraftrevamp.constants;

import org.bukkit.ChatColor;

public enum Rarity {

    BASIC(ChatColor.WHITE),
    UNIQUE(ChatColor.GREEN),
    EXCELLENT(ChatColor.BLUE),
    GRAND(ChatColor.DARK_PURPLE),
    LEGENDARY(ChatColor.GOLD),
    GIFTED(ChatColor.AQUA),
    CURSED(ChatColor.DARK_RED);

    private final ChatColor color;

    Rarity(ChatColor color) {
        this.color = color;
    }

    public ChatColor getColor() {
        return color;
    }
}
