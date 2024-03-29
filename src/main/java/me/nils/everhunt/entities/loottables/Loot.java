package me.nils.everhunt.entities.loottables;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.items.Items;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Loot {

    public static CustomLootTable getLootTable(String name) {
        CustomLootTable.CustomLootTableBuilder builder = new CustomLootTable.CustomLootTableBuilder();
        if (EntityData.data.get(name) != null) {
            EntityData data = EntityData.data.get(name);

            if (data.getType().equals(MobType.ENEMY)) {
                ItemStack flesh = Items.getBase("Monster Flesh");
                flesh.setAmount(new Random().nextInt(1,5));
                builder.add(flesh,100);
            }

            switch (name) {
                case "Wolf King" -> builder.add(Items.getBase("Kings Bone"),20);
                case "Wolfling" -> {
                    ItemStack itemStack = Items.getBase("Wolfling Hide");
                    itemStack.setAmount(new Random().nextInt(1,4));
                    builder.add(itemStack,400);
                }
                case "Forgotten Soul" -> builder.add(Items.getBase("24k Gold Nugget"),1);
            }

            return builder.build();
        } else if (name.contains("Crate")) {
            builder.add(Items.getBase("Wheat"),20);


            return builder.build();
        } else {
            return null;
        }
    }

    public static CustomLootTable getlootTable(Block block, Ability ability) {
        Material material = block.getType();
        Random random = new Random();
        CustomLootTable.CustomLootTableBuilder table = new CustomLootTable.CustomLootTableBuilder();
        int drops = switch (material) {
            case STONE -> random.nextInt(3,11);
            case IRON_ORE -> random.nextInt(1,3);
            case GOLD_ORE,DEEPSLATE_GOLD_ORE,RAW_GOLD_BLOCK,NETHER_GOLD_ORE -> random.nextInt(1,6);
            case WHEAT -> random.nextInt(1,4);
            case POTATOES,CARROTS,COAL_ORE -> random.nextInt(2,5);
            case BEETROOTS -> random.nextInt(0,3);
            default -> 1;
        };

        String item = switch (material) {
            case STONE -> "Stone";
            case COAL_ORE -> "Coal";
            case IRON_ORE -> "Iron Ingot";
            case GOLD_ORE -> "3k Gold Ingot";
            case DEEPSLATE_GOLD_ORE -> "6k Gold Ingot";
            case RAW_GOLD_BLOCK -> "12k Gold Ingot";
            case NETHER_GOLD_ORE -> "24k Gold Nugget";
            case WHEAT -> "Wheat";
            case POTATOES -> "Potato";
            case CARROTS -> "Carrot";
            case BEETROOTS ->  "Beetroot";
            default -> "null";
        };

        if (ability.equals(Ability.FORTUNATE)) {
            if (random.nextInt(0,101) <= 1) {
                drops *= 2;
            }
        }

        ItemStack itemStack = Items.getBase(item);
        itemStack.setAmount(drops);

        table.add(itemStack,10);

        if (ability.equals(Ability.BREAD_MAKER) && item.equals("Wheat")) {
            table.add(Items.getBase("Bread"),10);
        }

        return table.build();
    }

    public static CustomLootTable getlootTable(Block block, Ability ability, Ability helmetAbility) {
        Material material = block.getType();
        Random random = new Random();
        int drops = switch (material) {
            case STONE -> random.nextInt(3,11);
            case IRON_ORE -> random.nextInt(1,3);
            case GOLD_ORE,DEEPSLATE_GOLD_ORE,RAW_GOLD_BLOCK,NETHER_GOLD_ORE -> random.nextInt(1,6);
            case WHEAT -> random.nextInt(1,4);
            case POTATOES,CARROTS,COAL_ORE -> random.nextInt(2,5);
            case BEETROOTS -> random.nextInt(0,3);
            default -> 1;
        };

        String item = switch (material) {
            case STONE -> "Stone";
            case COAL_ORE -> "Coal";
            case IRON_ORE -> "Iron Ingot";
            case GOLD_ORE -> "3k Gold Ingot";
            case DEEPSLATE_GOLD_ORE -> "6k Gold Ingot";
            case RAW_GOLD_BLOCK -> "12k Gold Ingot";
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
        if (helmetAbility.equals(Ability.FORTUNATE)) {
            if (random.nextInt(0,101) <= 1) {
                drops *= 2;
            }
        }

        ItemStack itemStack = Items.getBase(item);
        itemStack.setAmount(drops);

        return new CustomLootTable.CustomLootTableBuilder().add(itemStack,1).build();
    }

    public static CustomLootTable getLootTable() {
        return new CustomLootTable.CustomLootTableBuilder().add(Items.getBase("6k Gold Ingot"),10).add(Items.getBase("Monster Flesh"),40)
                .add(Items.getBase("Ancient Shard"),1).build();
    }
}
