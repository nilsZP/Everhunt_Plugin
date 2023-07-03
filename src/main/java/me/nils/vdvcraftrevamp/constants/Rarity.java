package me.nils.vdvcraftrevamp.constants;

import org.bukkit.ChatColor;

public enum Rarity {

    COMMON(ChatColor.WHITE),
    UNCOMMON(ChatColor.GREEN),
    RARE(ChatColor.BLUE),
    EPIC(ChatColor.DARK_PURPLE),
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
