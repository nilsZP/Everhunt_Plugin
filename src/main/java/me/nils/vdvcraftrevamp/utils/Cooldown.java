package me.nils.vdvcraftrevamp.utils;

import me.nils.vdvcraftrevamp.VDVCraftRevamp;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class Cooldown {
    public static HashMap<String, Double> cooldowns;

    public static void setupCooldown() {
        cooldowns = new HashMap<>();
    }

    public static void setCooldown(ItemStack item, double seconds) {
        double delay = (System.currentTimeMillis() + (seconds * 1000L));
        String ability = item.getItemMeta().getPersistentDataContainer().get(VDVCraftRevamp.getKey(), PersistentDataType.STRING);
        cooldowns.put(ability, Double.valueOf(delay));
    }

    public static boolean hasCooldown(ItemStack item) {
        String ability = item.getItemMeta().getPersistentDataContainer().get(VDVCraftRevamp.getKey(), PersistentDataType.STRING);
        return cooldowns.containsKey(ability) && cooldowns.get(ability) > System.currentTimeMillis();
    }
}
