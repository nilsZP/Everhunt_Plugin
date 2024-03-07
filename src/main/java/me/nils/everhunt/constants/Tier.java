package me.nils.everhunt.constants;

import org.bukkit.ChatColor;

public enum Tier {

    BASIC(ChatColor.WHITE),
    MECHANICAL(ChatColor.GOLD),
    NATURAL(ChatColor.DARK_GREEN),
    MAGICAL(ChatColor.DARK_PURPLE),
    CURSED(ChatColor.DARK_RED),
    SOUL(ChatColor.DARK_AQUA),
    MENU(ChatColor.YELLOW),
    ACHIEVEMENT(ChatColor.GREEN);

    private final ChatColor color;

    Tier(ChatColor color) {
        this.color = color;
    }

    public ChatColor getColor() {
        return color;
    }
}
