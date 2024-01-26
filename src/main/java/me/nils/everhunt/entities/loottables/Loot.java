package me.nils.everhunt.entities.loottables;

import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.managers.ItemManager;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;

import java.util.Random;

public class Loot {

    public static CustomLootTable getLootTable(String mob) {
        if (EntityData.data.get(mob) != null) {
            EntityData data = EntityData.data.get(mob);
            CustomLootTable.CustomLootTableBuilder builder = new CustomLootTable.CustomLootTableBuilder();

            if (data.getType().equals(MobType.ENEMY) || data.getType().equals(MobType.BOSS)) {
                ItemStack flesh = ItemManager.items.get("Monster Flesh").getItemStack();
                flesh.setAmount(new Random().nextInt(1,5));
                builder.add(flesh,80);
            }

            switch (mob) {
                case "Wolf King" -> builder.add(ItemManager.items.get("Kings Bone").getItemStack(),20);
                case "Wolfling" -> {
                    ItemStack itemStack = ItemManager.items.get("Wolfling Hide").getItemStack();
                    itemStack.setAmount(new Random().nextInt(1,4));
                    builder.add(itemStack,50);
                }
            }

            return builder.build();
        } else {
            return null;
        }
    }
}
