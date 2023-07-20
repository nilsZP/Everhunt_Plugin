package me.nils.vdvcraftrevamp.constants;

import org.bukkit.ChatColor;

public enum Flow {
    BLOWING(ChatColor.GREEN),
    WAVING(ChatColor.BLUE),
    BURNING(ChatColor.RED),
    SURGING(ChatColor.DARK_AQUA),
    FREEZING(ChatColor.AQUA),
    QUAKING(ChatColor.GOLD);

    private final ChatColor color;

    Flow(ChatColor color) {
        this.color = color;
    }

    public ChatColor getColor() {
        return color;
    }
}
