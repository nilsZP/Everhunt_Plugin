package me.nils.everhunt.constants;

import org.bukkit.ChatColor;

public enum Tier {

    BASIC(ChatColor.WHITE),
    MECHANICAL(ChatColor.GOLD),
    NATURAL(ChatColor.DARK_GREEN),
    MAGICAL(ChatColor.DARK_PURPLE),
    CURSED(ChatColor.GREEN),
    A(ChatColor.DARK_GREEN),
    AQUATIC(ChatColor.DARK_AQUA);

    private final ChatColor color;

    Tier(ChatColor color) {
        this.color = color;
    }

    public ChatColor getColor() {
        return color;
    }
}
