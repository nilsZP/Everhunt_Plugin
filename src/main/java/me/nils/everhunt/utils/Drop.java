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
            case "Potato" -> random.nextInt(2,5);
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
}
