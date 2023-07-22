package me.nils.vdvcraftrevamp.entities;

import me.nils.vdvcraftrevamp.VDVCraftRevamp;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class Springer {
    public Springer(Location loc) {
        CaveSpider springer = (CaveSpider) loc.getWorld().spawnEntity(loc, EntityType.CAVE_SPIDER);

        springer.setCustomName("Springer");
        springer.setCustomNameVisible(true);

        springer.setMaxHealth(6);
        springer.setHealth(6);
    }
}
