package me.nils.everhunt.entities.loottables;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.managers.ItemManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
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

    public static CustomLootTable getlootTable(Block block, Ability ability) {
        Material material = block.getType();
        Random random = new Random();
        int drops = switch (material) {
            case STONE -> random.nextInt(3,11);
            case IRON_ORE -> random.nextInt(1,3);
            case GOLD_ORE,DEEPSLATE_GOLD_ORE,GOLD_BLOCK,NETHER_GOLD_ORE -> random.nextInt(1,6);
            case WHEAT -> random.nextInt(1,4);
            case POTATOES,CARROTS,COAL_ORE -> random.nextInt(2,5);
            case BEETROOTS -> random.nextInt(0,3);
            default -> 0;
        };

        String item = switch (material) {
            case STONE -> "Stone";
            case COAL_ORE -> "Coal";
            case IRON_ORE -> "Iron Ingot";
            case GOLD_ORE -> "3k Gold Ingot";
            case DEEPSLATE_GOLD_ORE -> "6k Gold Ingot";
            case GOLD_BLOCK -> "12k Gold Ingot";
            case NETHER_GOLD_ORE -> "24k Gold Nugget";
            case WHEAT -> "Wheat";
            case POTATOES -> "Potato";
            case CARROTS -> "Carrot";
            case BEETROOTS ->  "Beetroot";
            default -> "null";
        };

        if (ability.equals(Ability.BREAD_MAKER) && item.equals("Wheat")) {
            drops += 3;
        }

        ItemStack itemStack = ItemManager.items.get(item).getItemStack();
        itemStack.setAmount(drops);

        return new CustomLootTable.CustomLootTableBuilder().add(itemStack,1).build();
    }
}
