package me.nils.everhunt.entities.loottables;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CustomLootTable {
    private ArrayList<Entry> entries;

    public CustomLootTable(ArrayList<Entry> entries) {
        this.entries = entries;
    }

    public ItemStack getRandom() {
        double random = Math.random();
        for (Entry entry : entries) {
            if (entry.getChance() > random) return entry.getItem();
        }

        return entries.get(entries.size() -1).getItem();
    }
    public static class CustomLootTableBuilder {
        private int totalWeight = 0;
        private ArrayList<Entry> entries = new ArrayList<>();

        public CustomLootTableBuilder add(ItemStack item, int weight) {
            totalWeight += weight;
            entries.add(new Entry(item, weight));
            return this;
        }

        public boolean isBuilt() {
            return !entries.isEmpty() && totalWeight > 0;
        }

        public CustomLootTable build() {
            if (!isBuilt()) return null;

            double base = 0;
            for (Entry entry : entries) {
                double chance = getChance(base);
                entry.setChance(chance);
                base += entry.getWeight();
            }

            return new CustomLootTable(entries);
        }

        private double getChance(double weight) {
            return weight / totalWeight;
        }
    }
}
