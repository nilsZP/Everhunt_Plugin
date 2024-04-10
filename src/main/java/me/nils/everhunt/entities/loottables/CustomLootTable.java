package me.nils.everhunt.entities.loottables;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class CustomLootTable {
    private ArrayList<Entry> entries;

    public CustomLootTable(ArrayList<Entry> entries) {
        this.entries = entries;
    }

    public ItemStack getRandom() {
        for (Entry entry : entries) {
            double random = new Random().nextDouble(0,101);
            if (entry.getChance() > random) return entry.getItem();
        }

        return entries.get(0).getItem();
    }
    public static class CustomLootTableBuilder {
        private ArrayList<Entry> entries = new ArrayList<>();

        public void add(ItemStack item, double chance) {
            entries.add(new Entry(item, chance));
        }

        public boolean isBuilt() {
            return !entries.isEmpty();
        }

        public CustomLootTable build() {
            if (!isBuilt()) return null;

            return new CustomLootTable(entries);
        }
    }
}
