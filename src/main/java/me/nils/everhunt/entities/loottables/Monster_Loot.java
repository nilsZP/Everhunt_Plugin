package me.nils.everhunt.entities.loottables;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.managers.ItemManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Monster_Loot implements LootTable {
    private NamespacedKey key = new NamespacedKey(Everhunt.getInstance(), "monster_loot");
    private Collection<ItemStack> items = new ArrayList<ItemStack>();

    @Override
    public Collection<ItemStack> populateLoot(Random random, LootContext context) {
        double looting_modifier;
        double luck_modifier;

        int looting_level = context.getLootingModifier();
        if (looting_level > 0) {
            looting_modifier = (Math.pow(looting_level, 0.8) + 2) / 2;
        }
        else {
            looting_modifier = 1;
        }

        double luck_level = context.getLuck();
        if (luck_level > 0) {
            luck_modifier = Math.pow(luck_level, 1.5) / 3;
        }
        else if (luck_level == 0){
            luck_modifier = 1;
        }
        else {
            luck_modifier = 0;
        }
        int random_modifier = random.nextInt(6);
        int item_count = (int) (looting_modifier * luck_modifier * random_modifier);

        int flesh_count;

        if (item_count > 0) {
            flesh_count = item_count;
        }
        else {
            flesh_count = 0;
        }

        ItemStack flesh = ItemManager.items.get("Monster Flesh").getItemStack();
        flesh.setAmount(flesh_count);

        items.add(flesh);

        return items;
    }

    @Override
    public void fillInventory(Inventory inventory, Random random, LootContext context) {

    }

    @Override
    public NamespacedKey getKey() {
        return key;
    }
}
