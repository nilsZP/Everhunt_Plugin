package me.nils.everhunt.entities.loottables;

import org.bukkit.inventory.ItemStack;

public class Entry {
    private ItemStack item;
    private double chance;

    public Entry(ItemStack item, double chance) {
        this.chance = chance;
        this.item = item;
    }

    public ItemStack getItem() {
        return item.clone();
    }

    public double getChance() {
        return chance;
    }
}
