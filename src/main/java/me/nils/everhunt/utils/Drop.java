package me.nils.everhunt.utils;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Job;
import me.nils.everhunt.data.JobData;
import me.nils.everhunt.managers.ItemManager;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;

import java.util.Random;

public class Drop {
    public static void getCropDrops(String crop, Ability ability, Player player, Block block, Ageable ageable) {
        String uuid = player.getUniqueId().toString();
        int drops = 0;
        Random random = new Random();
        int random_modifier = random.nextInt(0,2);
        int luck = (int) player.getAttribute(Attribute.GENERIC_LUCK).getValue();
        int farmerLevel = (JobData.hasJob(uuid,Job.FARMER)) ? JobData.getLevel(uuid,Job.FARMER) : 0;

        drops += switch (crop) {
            case "Wheat" -> random.nextInt(1,4);
            case "Potato","Carrot" -> random.nextInt(2,5);
            case "Beetroot" -> random.nextInt(0,3);
            default -> 0;
        };

        if (ability.equals(Ability.BREAD_MAKER) && crop.equals("Wheat")) {
            drops += 3;
        }

        drops += random_modifier + (luck/4) + (farmerLevel/2);

        for (int i = 0; i < drops; i++) {
            player.getWorld().dropItemNaturally(block.getLocation(), ItemManager.items.get(crop).getItemStack());
        }
        ageable.setAge(0);
        block.setBlockData(ageable);
    }

    public static void getBlockDrops(Ability ability, Player player, Block block) {
        String uuid = player.getUniqueId().toString();
        Material material = block.getType();
        int drops = 0;
        Random random = new Random();
        int random_modifier = random.nextInt(0,2);
        int luck = (int) player.getAttribute(Attribute.GENERIC_LUCK).getValue();
        int minerLevel = (JobData.hasJob(uuid,Job.MINER)) ? JobData.getLevel(uuid,Job.MINER) : 0;

        drops += switch (material) {
            case STONE -> random.nextInt(3,11);
            case COAL_ORE -> random.nextInt(2,5);
            case IRON_ORE -> random.nextInt(1,3);
            case GOLD_ORE,DEEPSLATE_GOLD_ORE,GOLD_BLOCK,NETHER_GOLD_ORE -> random.nextInt(1,6);
            default -> 0;
        };

        String drop = switch (material) {
            case STONE -> "Stone";
            case COAL_ORE -> "Coal";
            case IRON_ORE -> "Iron Ingot";
            case GOLD_ORE -> "3k Gold Ingot";
            case DEEPSLATE_GOLD_ORE -> "6k Gold Ingot";
            case GOLD_BLOCK -> "12k Gold Ingot";
            case NETHER_GOLD_ORE -> "24k Gold Nugget";
            default -> "null";
        };

        drops += random_modifier + (luck/4) + (minerLevel/3);

        for (int i = 0; i < drops; i++) {
            player.getWorld().dropItemNaturally(block.getLocation(), ItemManager.items.get(drop).getItemStack());
        }
    }
}
