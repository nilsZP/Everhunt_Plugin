package me.nils.vdvcraftrevamp.constants;

import org.bukkit.ChatColor;

public enum Tier {

    F(ChatColor.DARK_RED),
    E(ChatColor.RED),
    D(ChatColor.GOLD),
    C(ChatColor.YELLOW),
    B(ChatColor.GREEN),
    A(ChatColor.DARK_GREEN),
    S(ChatColor.DARK_AQUA);

    private final ChatColor color;

    Tier(ChatColor color) {
        this.color = color;
    }

    public ChatColor getColor() {
        return color;
    }
}
