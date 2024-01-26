package me.nils.everhunt.entities.loottables;

import org.bukkit.inventory.ItemStack;

public class Entry {
    private int weight;
    private ItemStack item;
    private double chance;

    public Entry(ItemStack item, int weight) {
        this.weight = weight;
        this.item = item;
    }

    public ItemStack getItem() {
        return item.clone();
    }

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public int getWeight() {
        return weight;
    }
}
