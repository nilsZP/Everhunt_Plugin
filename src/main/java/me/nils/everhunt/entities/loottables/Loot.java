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
                builder.add(flesh,60);
            }

            switch (name) {
                case "Wolf King" -> builder.add(Items.getBase("Kings Bone"),20);
                case "Wolfling" -> {
                    ItemStack itemStack = Items.getBase("Wolfling Hide");
                    itemStack.setAmount(new Random().nextInt(1,4));
                    builder.add(itemStack,400);
                }
                case "Forgotten Soul" -> builder.add(Items.getBase("24k Gold Nugget"),30);
                case "Thunder Bones" -> builder.add(Items.getBase("AzureWrath"),100);
                case "Lost Soul" -> builder.add(Items.getBase("Lost Soul"),30);
            }

            return builder.build();
        } else if (name.contains("Crate")) {
            builder.add(Items.getBase("Wheat"),10);
            builder.add(Items.getBase("Backpack (gray)"),25);
            builder.add(Items.getBase("Compressed Stone"),40);
            builder.add(Items.getBase("Brush"),20);

            if (name.contains("Mechanical")) {
                builder.add(Items.getBase("12k Gold Ingot"),20);
                builder.add(Items.getBase("Compressed Coal"),30);
                builder.add(Items.getBase("Brokkr"),60);
            }

            if (name.contains("Magical")) {
                builder.add(Items.getBase("Ancient Shard"),30);
                builder.add(Items.getBase("Meteor Sword"),60);
            }

            builder.add(Items.getBase("Cyriacus"),10);

            return builder.build();
        } else {
            return null;
        }
    }

    public static CustomLootTable getlootTable(Block block, Ability ability) {
        Material material = block.getType();
        Random random = new Random();
        CustomLootTable.CustomLootTableBuilder builder = new CustomLootTable.CustomLootTableBuilder();

        switch (material) {
            case GOLD_ORE,DEEPSLATE_GOLD_ORE,RAW_GOLD_BLOCK,NETHER_GOLD_ORE -> {
                builder.add(Items.getBase("3k Gold Ingot"),80);
                builder.add(Items.getBase(switch (material) {
                    case DEEPSLATE_GOLD_ORE -> "6k Gold Ingot";
                    case RAW_GOLD_BLOCK -> "12k Gold Ingot";
                    case NETHER_GOLD_ORE -> "24k Gold Nugget";
                    default -> "3k Gold Ingot";
                }),80);

                return builder.build();
            }
        }

        int drops = switch (material) {
            case STONE -> random.nextInt(3,11);
            case IRON_ORE -> random.nextInt(1,3);
            case WHEAT -> random.nextInt(1,4);
            case POTATOES,CARROTS,COAL_ORE -> random.nextInt(2,5);
            case BEETROOTS -> random.nextInt(0,3);
            default -> 1;
        };

        String item = switch (material) {
            case STONE -> "Stone";
            case COAL_ORE -> "Coal";
            case IRON_ORE -> "Iron Ingot";
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

        if (ability.equals(Ability.BREAD_MAKER) && item.equals("Wheat")) {
            builder.add(Items.getBase("Bread"),10);
        }

        ItemStack itemStack = Items.getBase(item);
        itemStack.setAmount(drops);

        builder.add(itemStack,100);

        return builder.build();
    }

    public static CustomLootTable getlootTable(Block block, Ability ability, Ability helmetAbility) {
        Material material = block.getType();
        Random random = new Random();
        CustomLootTable.CustomLootTableBuilder builder = new CustomLootTable.CustomLootTableBuilder();

        switch (material) {
            case GOLD_ORE,DEEPSLATE_GOLD_ORE,RAW_GOLD_BLOCK,NETHER_GOLD_ORE -> {
                builder.add(Items.getBase("3k Gold Ingot"),80);
                builder.add(Items.getBase(switch (material) {
                    case DEEPSLATE_GOLD_ORE -> "6k Gold Ingot";
                    case RAW_GOLD_BLOCK -> "12k Gold Ingot";
                    case NETHER_GOLD_ORE -> "24k Gold Nugget";
                    default -> "3k Gold Ingot";
                }),80);

                return builder.build();
            }
        }

        int drops = switch (material) {
            case STONE -> random.nextInt(3,11);
            case IRON_ORE -> random.nextInt(1,3);
            case WHEAT -> random.nextInt(1,4);
            case POTATOES,CARROTS,COAL_ORE -> random.nextInt(2,5);
            case BEETROOTS -> random.nextInt(0,3);
            default -> 1;
        };

        String item = switch (material) {
            case STONE -> "Stone";
            case COAL_ORE -> "Coal";
            case IRON_ORE -> "Iron Ingot";
            case WHEAT -> "Wheat";
            case POTATOES -> "Potato";
            case CARROTS -> "Carrot";
            case BEETROOTS ->  "Beetroot";
            default -> "null";
        };


        if (helmetAbility.equals(Ability.FORTUNATE)) {
            if (random.nextInt(0,101) <= 1) {
                drops *= 2;
            }
        }

        if (ability.equals(Ability.BREAD_MAKER) && item.equals("Wheat")) {
            builder.add(Items.getBase("Bread"),10);
        }

        ItemStack itemStack = Items.getBase(item);
        itemStack.setAmount(drops);

        builder.add(itemStack,100);

        return builder.build();
    }

    public static CustomLootTable getLootTable() {
        CustomLootTable.CustomLootTableBuilder builder = new CustomLootTable.CustomLootTableBuilder();
        builder.add(Items.getBase("6k Gold Ingot"),40);
        builder.add(Items.getBase("Backpack (orange)"),20);
        builder.add(Items.getBase("Monster Flesh"),70);
        builder.add(Items.getBase("Kings Bone"),30);
        builder.add(Items.getBase("Ancient Shard"),10);
        builder.add(Items.getBase("24k Gold Nugget"),1);
        return builder.build();
    }
}
